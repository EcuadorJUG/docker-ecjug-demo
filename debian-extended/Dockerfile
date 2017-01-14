# Dockerfile para ejecutar aplicacion Java

# Pull base image
FROM petrubear/debian-jdk8-dev:1.0.0

# Directorio para las aplicaciones
WORKDIR /Application

# Copiar los recursos de la aplicacion
COPY pom.xml /Application/pom.xml
COPY src /Application/src

#Compilar la aplicacion y ejecutar
RUN ["mvn", "package"]

# Comando inicial
#CMD ["bash"]
CMD ["mvn", "exec:java"]

