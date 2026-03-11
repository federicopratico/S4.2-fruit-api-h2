FROM eclipse-temurin:21-jdk AS builder

# Imposta directory di lavoro nel container
WORKDIR /app

# Copia wrapper Maven (per build senza Maven installato localmente)
COPY mvnw .
COPY .mvn .mvn

# Copia file configurazione progetto
COPY pom.xml .

# Scarica dipendenze (layer cachato se pom.xml non cambia)
RUN ./mvnw dependency:go-offline

# Copia codice sorgente
COPY src src

# Compila applicazione e genera JAR (salta i test per velocità)
RUN ./mvnw clean package -DskipTests

# ============================================
# STAGE 2: RUNTIME
# ============================================
# Usa immagine leggera con solo JRE (no JDK)
FROM eclipse-temurin:21-jre

# Imposta directory di lavoro
WORKDIR /app

# Copia SOLO il JAR dallo stage builder (non tutto il resto!)
COPY --from=builder /app/target/*.jar app.jar

# Espone porta 8080 (o 9000 se usi quella)
EXPOSE 9000

# Comando per avviare l'applicazione
ENTRYPOINT ["java", "-jar", "app.jar"]