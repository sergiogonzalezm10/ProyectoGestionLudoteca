========================================
   GESTIÓN DE LA LUDOTECA
   Sergio González
   1º DAM - Módulo: Programación
   Curso 2025-2026
========================================

--------------------------------------
IDE Y VERSIÓN DE JAVA
--------------------------------------
IDE: Eclipse IDE for Java Developers
Versión de Java: JavaSE-21

--------------------------------------
DEPENDENCIAS EXTERNAS
--------------------------------------
No se requieren dependencias externas.
El proyecto usa únicamente la librería
estándar de Java (JDK 21).

--------------------------------------
CÓMO COMPILAR Y EJECUTAR
--------------------------------------
1. Abrir Eclipse
2. Importar el proyecto:
   Archivo -> Import -> General ->
   Existing Projects into Workspace
   -> Seleccionar la carpeta raíz del
   proyecto -> Finish

3. Ejecutar la clase Main.java:
   Clic derecho sobre Main.java ->
   Run As -> Java Application

La aplicación cargará automáticamente
los datos desde la carpeta recursos/
al arrancar, y los guardará al salir
mediante la opción 0 del menú principal.

--------------------------------------
ESTRUCTURA DEL PROYECTO
--------------------------------------
src/
 ├── Main.java
 ├── modelo/        <- Clases de dominio
 ├── servicio/      <- Lógica de negocio
 ├── repositorio/   <- Persistencia
 ├── ui/            <- Menús de consola
 └── excepciones/   <- Excepciones propias

src/recursos/
 ├── juegos.csv
 ├── socios.csv
 ├── prestamos.csv
 └── torneos.csv

doc/               	<- Javadoc generado
UML_González.pdf    <- Diagrama UML
Memoria_González.pdf    <- Memoria técnica

--------------------------------------
NOTAS
--------------------------------------
- Los ficheros CSV se crean solos en
  src/recursos/ la primera vez que se
  ejecuta la aplicación.
- No se necesita configuración adicional.