# Docker ecjug demo
Url del repositorio: [https://github.com/petrubear/docker-ecjug-demo](https://github.com/petrubear/docker-ecjug-demo)

Los siguientes directorios tienen los archivos necesarios para ejecutar el laboratorio de inttroduccion a docker:

* **ubuntu-jdk8** - dockerfile para generar una imagen de ubuntu 16.04 con oracle java8, vim y maven
* **debian-jdk8** - dockerfile para generar una imagen de debian jessie con oracle java8, vim y maven
* **debian-extended** - dockerfile para generar el ejemplo simple de java y maven
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

# Fase de construccion

Para iniciar la contruccion de la imagen, acceder al directorio del archivo Dockerfile que se desea construir y ejecutar el comando *docker build*. Ejemplo:

```bash
cd <directorio_dockerfile>
docker build -t="usuario/nombre:version" .
```

Para cada uno de los ejemplos del laboratorio se adjunta el comando utilizado:

* **ubuntu-jdk8**

```bash
cd ubuntu-jdk8
docker build -t="petrubear/ubuntu-jdk8-dev:1.0.0" .
```

* **debian-jdk8** - dockerfile para generar una imagen de debian jessie con oracle java8, vim y maven

```bash
cd debian-jdk8
docker build -t="petrubear/debian-jdk8-dev:1.0.0" .
```

* **debian-extended**

```bash
cd debian-extended
docker build -t="petrubear/debian-extended:1.0.0" .
```

* **debian-extended-web**

```bash
cd debian-extended-web
docker build -t="petrubear/debian-extended-web" .
```
* **debian-karaf**
```bash
cd debian-karaf
docker build -t="petrubear/debian-jdk8-karaf:1.0.0" .
```

# Creacion de contenedores

A continuacion se lista el comando para arrancar un contenedor para cada imagen creada:

* **ubuntu-jdk8**

```bash
docker run --rm -t -i petrubear/ubuntu-jdk8-dev:1.0.0
```

* **debian-jdk8** - dockerfile para generar una imagen de debian jessie con oracle java8, vim y maven

```bash
docker run --rm -t -i petrubear/debian-jdk8-dev:1.0.0
```

* **debian-extended**

```bash
docker run --rm -t -i petrubear/debian-extended:1.0.0
```

* **debian-extended-web**

```bash
docker run --rm -p 8080:8080 --name webimg -t -i petrubear/debian-extended-web:1.0.0
```
* **debian-karaf**
```bash
docker run --name karaf -p 1099:1099 -p 8101:8101 -p 44444:44444 -v /Users/edison/Tmp/docker/deploy:/deploy -t -i petrubear/debian-jdk8-karaf:1.0.0
```

# Publicación

Una vez creado el usuario en dockerhub, ejecutar los siguientes comandos:

```bash
docker login
docker push 'usuario/nombre/version'
```

