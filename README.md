# Docker ecjug demo
Url del repositorio: [https://github.com/petrubear/docker-ecjug-demo](https://github.com/petrubear/docker-ecjug-demo)


Para convertir este documento a docx, ejecutar el comando:

```bash
 pandoc -o README.docx -f markdown -t docx README.md
```

Los siguientes directorios tienen los archivos necesarios para ejecutar el laboratorio de inttroduccion a docker:

* **ubuntu-jdk8** - dockerfile para generar una imagen de ubuntu 16.04 con oracle java8, vim y maven
* **debian-jdk8** - dockerfile para generar una imagen de debian jessie con oracle java8, vim y maven
* **debian-extended-web** - dockerfile para generar el ejemplo simple de tomcat
* **debian-karaf** - dockerfile para generar una imagen con java 8 y karaf

Como referencia, la documentación para crear una imagen Base se encuentra en: [https://docs.docker.com/engine/userguide/eng-image/baseimages/](https://docs.docker.com/engine/userguide/eng-image/baseimages/)


# Descripción del Dockerfile
El archivo Dockerfile tiene la forma:

```bash
#Comentario
INSTRUCCION argumentos
```

Las "instrucciones" más comunmente utilizadas son:

* **FROM**, es la primera instruccion del Dockerfile, indica la imagen inicial, ejemplo FROM ubuntu
* **COPY**, permite copiar archivos del contexto de construccion al sistema de archivos del contenedor, ejemplo COPY pom.xml /code
* **ENV**, permite configurar variables de entorno, ejemplo ENV JAVA_HOME=/opt/java/jdk9/
* **RUN**, ejecuta un comando, ejemplo RUN apt-get update
* **CMD**, comando que se ejecuta por defecto al iniciar un contenedor, ejemplo CMD ["bash"]
* **EXPOSE**, expone puertos donde el contenedor va a escuchar, ejemplo EXPOSE 8080

# Fase de construcción

Para iniciar la contruccion de la imagen, acceder al directorio del archivo Dockerfile que se desea construir y ejecutar el comando *docker build*. Ejemplo:

```bash
cd <directorio_dockerfile>
docker build -t="usuario/nombre:version" .
```

# Laboratorios

## Laboratorio #1 - Imagen ubuntu 16.04 - Oracle jdk8

**Paso 1:** Crear el archivo Dockerfile con las siguientes instrucciones:

```
# Dockerfile para Ubuntu/Oracle Java 8

# Pull base ubuntu image
FROM ubuntu:16.04

# Install Java 8
RUN \
  apt-get update && \
  apt-get install -y software-properties-common python-software-properties && \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer oracle-java8-set-default vim maven && \
  rm -rf /var/cache/oracle-jdk8-installer &&\
  apt-get clean  && \
  rm -rf /var/lib/apt/lists/*

# working directory
WORKDIR /data

# set JAVA_HOME
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# Define default command.
CMD ["bash"]
```

**Paso 2:** Ejecutar el comando de construccion en el directorio donde se encuentra el Dockerfile

```bash
cd ubuntu-jdk8
docker build -t="petrubear/ubuntu-jdk8-dev:1.0.0" .
```

**Paso 3:** Crear un contenedor de la imagen que acabamos de crear

```bash
docker run --rm -t -i petrubear/ubuntu-jdk8-dev:1.0.0
```

## Laboratorio #2 - Imagen debian jessie - Oracle jdk8

**Paso 1:** Crear el archivo Dockerfile con las siguientes instrucciones:

```
# Dockerfile para Debian/Oracle Java 8

# Pull base ubuntu image
FROM debian:jessie

# Install Java 8
# add webupd8 repository
RUN \
    echo "deb http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee /etc/apt/sources.list.d/webupd8team-java.list  && \
    echo "deb-src http://ppa.launchpad.net/webupd8team/java/ubuntu trusty main" | tee -a /etc/apt/sources.list.d/webupd8team-java.list  && \
    apt-key adv --keyserver keyserver.ubuntu.com --recv-keys EEA14886  && \
    apt-get update  && \
    echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections  && \
    echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections  && \
    DEBIAN_FRONTEND=noninteractive  apt-get install -y --force-yes oracle-java8-installer oracle-java8-set-default vim maven && \
    rm -rf /var/cache/oracle-jdk8-installer  && \
    apt-get clean  && \
    rm -rf /var/lib/apt/lists/*
# working directory
WORKDIR /data

# set JAVA_HOME
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

# Define default command.
#CMD ["zsh"]
CMD ["bash"]
```

**Paso 2:** Ejecutar el comando de construccion en el directorio donde se encuentra el Dockerfile

```bash
cd debian-jdk8
docker build -t="petrubear/debian-jdk8-dev:1.0.0" .
```

**Paso 3:** Crear un contenedor de la imagen que acabamos de crear

```bash
docker run --rm -t -i petrubear/debian-jdk8-dev:1.0.0
```


## Laboratorio #3 - Imagen debian jessie - Aplicacion Java

**Paso 1:** Crear el archivo Dockerfile con las siguientes instrucciones:

```
# Dockerfile para ejecutar aplicacion Java

# Pull base image
FROM petrubear/debian-jdk8-dev:1.0.0

# instalar extras
RUN apt-get update && apt-get install -y curl && apt-get clean
# Directorio para las aplicaciones
WORKDIR /Application

# Copiar los recursos de la aplicacion
COPY pom.xml /Application/pom.xml
COPY src /Application/src

#Compilar la aplicacion y ejecutar
RUN ["mvn", "package"]

# Comando inicial
CMD ["bash"]
#CMD ["mvn", "exec:java"]
```

**Paso 2:** Ejecutar el comando de construccion en el directorio donde se encuentra el Dockerfile

```bash
cd debian-extended-web
docker build -t="petrubear/debian-extended-web" .
```

**Paso 3:** Crear un contenedor de la imagen que acabamos de crear

```bash
docker run --rm -p 8080:8080 --name webimg -t -i petrubear/debian-extended-web:1.0.0
```

## Laboratorio #4 - Imagen debian jessie - Karaf

**Paso 1:** Crear el archivo Dockerfile con las siguientes instrucciones:

```
# Dockerfile para ejecutar aplicacion Java

# Pull base image
FROM petrubear/debian-jdk8-dev:1.0.0

# defino variable de entorno para la version de karaf
ENV KARAF_VERSION=4.0.8

# descarga karaf
RUN wget http://www-us.apache.org/dist/karaf/${KARAF_VERSION}/apache-karaf-${KARAF_VERSION}.tar.gz && \
    mkdir /opt/karaf &&\
    tar --strip-components=1 -C /opt/karaf -xzf apache-karaf-${KARAF_VERSION}.tar.gz && \
    rm apache-karaf-${KARAF_VERSION}.tar.gz && \
    mkdir /deploy && \
    sed -i 's/^\(felix\.fileinstall\.dir\s*=\s*\).*$/\1\/deploy/' /opt/karaf/etc/org.apache.felix.fileinstall-deploy.cfg

# exponer volumen
VOLUME ["/deploy"]

# exponer puertos necesarios
EXPOSE 1099 8101 44444

# entrypoint
ENTRYPOINT ["/opt/karaf/bin/karaf"]
```

**Paso 2:** Ejecutar el comando de construccion en el directorio donde se encuentra el Dockerfile

```bash
cd debian-karaf
docker build -t="petrubear/debian-jdk8-karaf:1.0.0" .
```

**Paso 3:** Crear un contenedor de la imagen que acabamos de crear

```bash
docker run --name karaf -p 1099:1099 -p 8101:8101 -p 44444:44444 -v /Users/edison/Tmp/docker/deploy:/deploy -t -i petrubear/debian-jdk8-karaf:1.0.0
```


# Publicación

Una vez creado el usuario en dockerhub, ejecutar los siguientes comandos:

```bash
docker login
docker push 'usuario/nombre/version'
```

