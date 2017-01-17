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
