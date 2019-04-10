# CAFE APP #

Componente de backend de servicios para la gestión de restaurantes y cafebar

### Tecnologías ###

* Java 8
* Spring Boot 2.0.5

### Verificación de código estático ###

El proyecto se encuentra configurado para realizar verificación de código estático. Para ejecutar la verificación se debe ejecutar:

> mvn clean compile checkstyle:checkstyle

En cada uno de los módulos dentro de la carpeta target/site/ se puede visualizar con un navegador web el archivo checkstyle.html el cual
contendra los resultados de la verificación de código estático.

### Generación de binarios ###

Para integrar y generar el archivo ejecutable se requiere tener instalado y configurado Apache Maven 3.3 o superior. 

Para generar el archivo .jar de Java se ejecuta el siguientes comando:

Desde la raiz del proyecto ejecutar:

> mvn clean compile install

Desde el modulo fae-backend-gglosas ejecutar:

> mvn package

El anterior comando en el modulo fae-backend-gglosas creara una carpeta target la cual contendra el archivo fae-backend-gglosas.jar

### Ejecución de los servicios ###

Para ejecutar el componente de servicio se requiere tener instalado Java 8.

Para ejecutar los servicios se ejecuta el siguiente comando en linux para dejar corriendo el proceso en segundo plano:

> nohup java -jar fae-backend-gglosas.jar &

### Documentación del API ###

Al ejecutar la aplicación se puede ver la documentación del API a traves de la siguiente dirección:

http://localhost:8081/fae/v1/swagger-ui.html

Adicionalmente se puede acceder a la API en formato OpenAPI (Swagger) a traves de la siguiente URL:

http://localhost:8081/fae/v1/v2/api-docs