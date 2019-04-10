#Prueba de concepto - Motor de reglas de negocio
---

##1. Descripción
Motor de reglas de negocio con API Rest

##2. Definición de tecnologías

### 2.1. Herramientas usadas

|Herramienta|Nombre|Versión|
|:-|:-:|:-:|
|Gestor de dependencias| Maven | 3.5.0 |
|Motor de reglas|Drools|7.11.0|
|Base de datos|MySql|5.7.21|
|Sistema Operativo|Windows 7 Professional|64 bits|

Tabla 1. Definición de herramientas usadas

### 2.2. Dependencias

 - Base de datos
 - Java SE Development Kit 8 
 - Proyecto padre
 - Propiedades del proyecto padre

##3. Manual de uso

### 3.1. Importar

1. Descargar con Git o con un cliente Git el proyecto en un directorio local
2. Importar el proyecto en el workspace del IDE
3. Cree o abra el proyecto padre en el que tiene el motor como una dependencia
4. Implemente la interfaz FrontValidacion y agréguelo en el bean UtilidadesClases cuando lo inyecte en el contexto del proyecto padre
5. En la propiedades del proyecto padre inserte la propiedad _motorReglas.paqueteObjetoNegocio_ con el paquete en el que se encuentran los objeto de negocio del proyecto padre
6. Inyecte en el contexto del proyecto padre el bean MotorBeans

### 3.2. Compilar

1. Ejecutar el comandos _mvn clean install_
2. Ubicar el archivo JAR terimando con _jar-with-dependencies_ en la carpeta target del proyecto

### 3.3. Ejecutar

1. Agregar o remplazar la versión anterior del JAR en la dependencia de los proyectos padres
2. En la propiedades del proyecto padre inserte la propiedad _motorReglas.paqueteObjetoNegocio_ con el paquete en el que se encuentran los objeto de negocio del proyecto padre
3. Ejecutar el JAR del proyecto padre