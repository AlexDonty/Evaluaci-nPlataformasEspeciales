# Sistema de Procesamiento de Transacciones

## Descripción

Se debe desarrollar una aplicación basada en **APIs REST** para procesar los datos de una transacción.

La solución estará compuesta principalmente por:

- Frontend web.
- API de recepción y validación de operaciones.
- API de procesamiento y persistencia.
- Base de datos H2.
- Cifrado AES-256 para el atributo `secreto`.
- BCrypt para las contraseñas de los usuarios.
- Comunicación entre APIs.
- Consulta paginada de transacciones.
- Actualización del estatus de las operaciones.

---

## 1. API de recepción de operaciones

La primera API deberá recibir una petición con la siguiente estructura:

```json
{
  "operacion": "venta",
  "importe": "100.00",
  "cliente": "Angel",
  "secreto": "jejdjw134&3#$$"
}
```

### Validaciones

Los atributos recibidos deberán validarse utilizando mecanismos de validación de Spring, tales como:

- `@Valid`
- `@Pattern`
- `@RestControllerAdvice`

Las validaciones mínimas serán:

| Campo | Validación |
|---|---|
| `operacion` | Solo caracteres |
| `importe` | Formato monetario |
| `cliente` | Solo caracteres |
| `secreto` | Campo requerido |

La longitud máxima y mínima de los campos queda a criterio del desarrollador.

Los errores de validación deberán manejarse mediante:

```java
@RestControllerAdvice
```

---

## 2. Cifrado AES-256

El atributo `secreto` será cifrado desde el frontend utilizando el algoritmo **AES-256**.

El flujo será:

```text
Frontend
   |
   | secreto cifrado con AES-256
   v
API 1
   |
   | descifrado utilizando la clave secreta
   v
Secreto original
```

Al recibir la petición, la primera API deberá descifrar el atributo utilizando la clave configurada para la aplicación.

---

## 3. Comunicación entre APIs

Si el JSON recibido es válido, la primera API enviará la información a una segunda API.

Para realizar la comunicación se podrá utilizar alguna de las siguientes alternativas:

- OpenFeign
- RestTemplate
- Apache HttpClient

Ejemplo utilizando OpenFeign:

```java
@FeignClient(
    name = "clienteApi",
    url = "${cliente.api.url}"
)
public interface Client {

    @PostMapping("/api/v1/operation")
    OperationResponse createOperation(
        @RequestBody OperationDto operationDto
    );
}
```

---

# 4. API de procesamiento de operaciones

La segunda API será responsable de almacenar las operaciones recibidas.

Se utilizará:

- Spring Data JPA
- `JpaRepository`
- H2 Database

La estructura de la tabla de operaciones será:

| Campo | Descripción |
|---|---|
| PK | Identificador único |
| operación | Tipo de operación |
| importe | Importe de la transacción |
| cliente | Nombre del cliente |
| referencia | Referencia de 6 dígitos |
| estatus | Estado de la transacción |
| secreto | Secreto descifrado |

---

## 5. Base de datos H2

La aplicación utilizará **H2 Database**.

La configuración se realizará mediante:

```text
pom.xml
application.properties
```

La persistencia será implementada mediante:

```java
JpaRepository
```

Ejemplo:

```java
public interface OperacionRepository
        extends JpaRepository<OperacionEntity, Long> {
}
```

---

## 6. Registro de la operación

Una vez recibida correctamente la información, la segunda API deberá:

1. Guardar la operación.
2. Generar una referencia numérica aleatoria de **6 dígitos**.
3. Guardar el secreto descifrado.
4. Establecer el estatus inicial como:

```text
Aprobada
```

Ejemplo de referencia:

```text
262737
```

---

## 7. Response de la operación

Después de almacenar correctamente la operación, la segunda API responderá a la primera API.

La primera API deberá generar una respuesta similar a:

```json
{
  "id": "2376",
  "estatus": "Aprobada",
  "referencia": "262737",
  "operacion": "venta"
}
```

Donde:

- `id`: PK del registro almacenado.
- `estatus`: estado actual de la operación.
- `referencia`: referencia numérica generada.
- `operacion`: tipo de operación realizada.

---

# 8. Frontend

Para enviar las peticiones se deberá desarrollar una aplicación web.

Se puede utilizar:

- React
- Angular
- Vue.js

El frontend deberá contar al menos con dos pantallas:

1. Login.
2. Registro de operaciones.

---

## 9. Login

La pantalla de login deberá contener:

```text
Usuario:
[________________]

Password:
[________________]

[ Login ]
```

Los usuarios deberán almacenarse en una tabla de base de datos con al menos:

| Campo | Descripción |
|---|---|
| usuario | Nombre de usuario |
| password | Password almacenado mediante BCrypt |

El password deberá almacenarse utilizando **BCrypt**.

Ejemplo:

```java
BCryptPasswordEncoder encoder =
        new BCryptPasswordEncoder();

String passwordHash =
        encoder.encode(password);
```

Para validar el login:

```java
encoder.matches(
    passwordIngresado,
    passwordGuardado
);
```

Si las credenciales son correctas, el usuario podrá acceder a la pantalla de registro de operaciones.

---

# 10. Registro de operación

La pantalla deberá contener:

```text
Operación:
[________________]

Importe:
[________________]

Cliente:
[________________]

Secreto:
[________________]

[ Registrar operación ]
```

Al presionar el botón se realizará una petición:

```http
POST /api/v1/operation
```

El atributo `secreto` deberá ser cifrado desde el frontend antes de enviarse al backend.

El usuario podrá introducir cualquier palabra en el campo `secreto`.

---

## 11. Notificación de respuesta

Cuando el frontend reciba la respuesta de la API, deberá mostrar el resultado mediante una notificación.

Ejemplo:

```text
Operación registrada correctamente

ID: 2376
Estatus: Aprobada
Referencia: 262737
Operación: venta
```

---

# 12. Cancelación de una operación

Se deberá utilizar el método HTTP:

```http
PATCH
```

para actualizar una transacción de:

```text
Aprobada
```

a:

```text
Cancelada
```

La petición deberá enviar únicamente:

```json
{
  "id": 2376,
  "referencia": "262737",
  "estatus": "cancelar"
}
```

La actualización deberá implementarse utilizando:

```java
@Query
```

Ejemplo:

```java
@Modifying
@Query("""
    UPDATE OperacionEntity o
    SET o.estatus = :estatus
    WHERE o.id = :id
      AND o.referencia = :referencia
""")
int updateEstatus(
    @Param("id") Long id,
    @Param("referencia") String referencia,
    @Param("estatus") String estatus
);
```

Esta funcionalidad podrá probarse desde Postman.

---

# 13. Consulta paginada de transacciones

Se deberá implementar un endpoint para consultar las transacciones almacenadas utilizando la paginación proporcionada por **Spring Data JPA**.

Los parámetros mínimos requeridos son:

| Parámetro | Descripción |
|---|---|
| `page` | Número de página |
| `size` | Número de registros por página |
| `sortBy` | Campo utilizado para el ordenamiento |
| `direction` | Dirección del ordenamiento (`ASC` / `DESC`) |

Ejemplo:

```http
GET /api/v1/operations?page=0&size=10&sortBy=importe&direction=DESC
```

La implementación utilizará:

```java
Pageable
PageRequest
Sort
```

Ejemplo:

```java
Sort sort = Sort.by(sortBy).descending();

Pageable pageable = PageRequest.of(
    page,
    size,
    sort
);

Page<OperacionEntity> operaciones =
        repository.findAll(pageable);
```

---

# 14. Flujo general

```text
                  FRONTEND
                     |
                     | Login
                     v
                API Usuarios
                     |
                     | BCrypt
                     v
               Base de datos
                     |
                Login correcto
                     |
                     v
             Registrar operación
                     |
                     | AES-256
                     v
                  API 1
             Validación JSON
                     |
                     | OpenFeign
                     v
                  API 2
                     |
                     | Spring Data JPA
                     v
                     H2
                     |
          +----------+----------+
          |                     |
     Generar referencia     Estatus
       6 dígitos            Aprobada
          |                     |
          +----------+----------+
                     |
                     v
                  Response
                     |
                     v
                  API 1
                     |
                     v
                  Frontend
```

---

# 15. Tecnologías utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Jakarta Validation
- OpenFeign
- H2 Database
- AES-256
- BCrypt
- React / Next.js
- Bootstrap
- Maven
- Postman
- Docker

---

# 16. Endpoints principales

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/api/v1/user` | Validar login |
| `POST` | `/api/v1/operation` | Registrar una operación |
| `GET` | `/api/v1/operations` | Consultar operaciones con paginación |
| `PATCH` | `/api/v1/operation` | Cancelar una operación |

---

# 17. Requisitos funcionales

La aplicación deberá permitir:

- Validar los datos de entrada.
- Manejar errores mediante `@RestControllerAdvice`.
- Cifrar el secreto desde el frontend utilizando AES-256.
- Descifrar el secreto en el backend.
- Comunicar las dos APIs.
- Persistir las operaciones mediante JPA.
- Generar referencias numéricas de 6 dígitos.
- Registrar operaciones con estatus `Aprobada`.
- Autenticar usuarios utilizando BCrypt.
- Cancelar operaciones mediante `PATCH`.
- Actualizar el estatus utilizando `@Query`.
- Consultar operaciones mediante paginación.
- Ordenar los resultados por diferentes campos.
- Mostrar las respuestas de las operaciones en el frontend.
