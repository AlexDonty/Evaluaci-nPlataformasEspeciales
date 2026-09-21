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

## 3. Levantar un contenedor Docker con la API

Crear un contenedor Docker que levante la API y permita recibir peticiones desde el frontend.

Ejecutar el siguiente comando para construir la imagen Docker: 
```bash
    docker build -t api:1.0 .
```

Ejecutar el siguiente comando para levantar el contenedor Docker: 
```bash
    docker run -p 8080:8080 api:1.0
```