# Evaluaci-nPlataformasEspeciales
Evaluación Plataformas Especiales 2026 

Se debe realizar la siguiente aplicación basada en API’s para procesar los datos de una
transacción
como se muestra a continuación:
La primer API consume el siguiente JSON
{
operacion”:”venta”,
“importe”: “100.00”,
“cliente”:”Angel”,
“secreto”:”jejdjw134&3#$$”
}
La cual deberá de cumplir con los siguientes puntos:
-Validar los atributos del JSON utilizando alguno(s) de los siguientes métodos:
-@Valid, @Regex, @RestControllerAdvice, para el atributo operación se validará que sean
caracteres, para el atributo importe se valida que sea de tipo moneda y para cliente que
sean caracteres, la longitud queda a criterio del desarrollador.
-El atributo clave se calcula con el algoritmo AES 256 (enviado desde el front), por lo que al
llegar al API, se descifra con la clave secreta que asignamos.
-Si el formato del JSON es válido se deberá de mandar por medio de @OpenFeign,
RestTemplate o
Apache Client a la segunda API La segunda API deberá de cumplir con lo siguiente:
-Se almacenarán los datos recibidos utilizando SpringData mediante JPARepository.
La estructura de la base de datos es la siguiente:
PK, operación, importe, cliente, referencia, estatus, secreto La base de datos será H2 (base
de datos en memoria, se configura en el pom.xml y application properties.
Una vez guardada la información se generará una referencia numérica aleatoria de 6
dígitos, en la columna secreto se guardará la palabra secreto sin cifrar, en el
estatus indicaremos la leyenda Aprobada, se le informará al primer API esta información
para generar un response como se muestra a continuación:
{
“id”: “2376”,
“estatus”: “Aprobada”,
“referencia”:”262737”,
“operacion”:”venta”
}
*El atributo id es la PK del registro almacenado.
Para poder enviar las peticiones al API se deberá de generar un front (página web)
utilizando
JavaScript, se puede utilizar Angular, Vue.js, React.
Ejemplo del front para login y registrar la información:
Ventana de Login
Textfield usuario:
Textfield password:
Para validar el password, creamos una tabla en base de datos en la cual tendremos una
columna
de usuario y password, este password se tiene que cifrar con el algoritmo Bcrypt
(previamente,
antes de iniciar sesión, podemos insertar esta información a la base con un script o con un
query)
para que al momento de iniciar sesión compare el usuario y password y de ser correctos
pasemos
a la siguiente ventana.
Ventana Registrar operación :
Textfield operación:
Textfield importe:
Textfield cliente:
Textfield secreto:
Button (Manda petición Post) Al recibir la respuesta del API se mostrará está en una
notificación.
*En el Textfield secreto se colocara cualquier palabra que deseemos y esta sea cifrada
desde el front hacia el backend.
*Se utilizará el método Patch para actualizar el estatus de una transacción “aprobada” a
“cancelada”, para esto solo se mandarán los campos id, referencia y estatus con el valor
“cancelar”
(Se puede mostrar desde Postman), se debe utilizar @Query
*Utilizar paginación para poder realizar la consulta de las transacciones que tenemos en la
base de datos, los parámetros mínimos requeridos para la paginación son : número de
página, número de registros por página, ordenamiento (por tipo de campo). Para esto se
requiere JPA.
