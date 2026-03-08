# Hotel Search Service

API REST desarrollada con **Spring Boot 3** y **Java 21** que permite registrar búsquedas de hoteles y consultar cuántas veces se ha realizado una búsqueda idéntica.

Para el procesamiento asíncrono se utiliza **Kafka**, mientras que la persistencia se realiza en una base **H2 en memoria**.

---

## Cómo levantar el proyecto

El proyecto está preparado para ejecutarse usando **Docker Compose**, por lo que no es necesario tener Java o Gradle instalados localmente.

### 1. Clonar el repositorio

```bash
git clone https://github.com/sergio77772/riu-Backend-sergio-r-giron.git
cd riu-Backend-sergio-r-giron
```

### 2. Levantar los servicios

```bash
docker-compose up --build
```

Este comando:

* compila la aplicación dentro de Docker
* levanta Zookeeper
* levanta Kafka
* levanta la aplicación Spring Boot

Una vez iniciado, la API estará disponible en:

```
http://localhost:8080
```

### 3. Detener los servicios

```bash
docker-compose down
```

---

## Documentación de la API

La documentación está disponible mediante **Swagger UI**.

```
http://localhost:8080/swagger-ui/index.html
```

---

## Endpoints

### POST `/search`

Registra una búsqueda de hotel y la envía al topic de Kafka `hotel_availability_searches`.

Ejemplo de request:

```json
{
  "hotelId": "1234aBc",
  "checkIn": "29/12/2023",
  "checkOut": "31/12/2023",
  "ages": [30, 29, 1, 3]
}
```

Respuesta:

```json
{
  "searchId": "550e8400-e29b-41d4-a716-446655440000"
}
```

---

### GET `/search/count?searchId={searchId}`

Devuelve la cantidad de búsquedas iguales a la identificada por el `searchId`.

Se considera que dos búsquedas son iguales si coinciden:

* `hotelId`
* `checkIn`
* `checkOut`
* `ages` (incluyendo el orden)

Ejemplo:

```
GET /search/count?searchId=550e8400-e29b-41d4-a716-446655440000
```

Respuesta:

```json
{
  "count": 5
}
```

---

## Tests

Para ejecutar los tests:

```bash
./gradlew test
```

También es posible ejecutarlos dentro del contenedor:

```bash
docker-compose run --rm app ./gradlew test
```

---

## Cobertura de tests

El proyecto utiliza **JaCoCo** para medir cobertura.

Generar el reporte:

```bash
./gradlew test jacocoTestReport
```

El reporte HTML se genera en:

```
build/reports/jacoco/test/html/index.html
```

---

## Arquitectura

El proyecto sigue el patrón **Arquitectura Hexagonal (Ports & Adapters)**.

```
src/main/java/com/sergio/hotelsearch/
├── domain/
│   ├── model/
│   └── port/
├── application/
│   └── usecase/
└── adapter/
    ├── input/
    │   └── rest/
    └── output/
        ├── kafka/
        └── persistence/
```

Esto permite separar claramente:

* lógica de dominio
* casos de uso
* adaptadores externos (HTTP, Kafka, base de datos)

---

## Tecnologías utilizadas

* Java 21
* Spring Boot 3.5
* Apache Kafka
* H2 Database
* Springdoc OpenAPI
* JUnit 5
* Mockito
* JaCoCo
