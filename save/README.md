# 1. API de procesamiento de operaciones

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

## 2. Base de datos H2

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

## 3. Registro de la operación

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

## 4. Response de la operación

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

## 5. Levantar un contenedor Docker con la API de procesamiento de operaciones

Crear un contenedor Docker que levante la API y permita recibir peticiones desde el frontend.

Ejecutar el siguiente comando para construir la imagen Docker:
```bash
    docker build -t api:1.0 .
```

Ejecutar el siguiente comando para levantar el contenedor Docker:
```bash
    docker run -p 8081:8081 api:1.0
```