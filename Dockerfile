# Usar Java 21 LTS (recomendado)
FROM openjdk:17-jdk-slim

# Instalar ferramentas úteis para desenvolvimento
RUN apt-get update && apt-get install -y \
    curl \
    vim \
    postgresql-client \
    && rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos de construção
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN chmod +x mvnw

# Baixar dependências (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Expor portas
EXPOSE 8080 5005

# Comando para desenvolvimento
CMD ./mvnw spring-boot:run \
    -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" \
    -Dspring-boot.run.profiles=dev