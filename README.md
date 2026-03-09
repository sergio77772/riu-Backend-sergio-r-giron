# Hotel Search Service

API REST desarrollada con **Spring Boot 3** y **Java 21** que permite registrar búsquedas de hoteles y consultar cuántas veces se ha realizado una búsqueda idéntica.

Las búsquedas se procesan de forma asíncrona utilizando **Kafka**. Para la persistencia se usa **H2 en memoria** por defecto, aunque también es posible ejecutar la aplicación con **Oracle XE** si se quiere simular un entorno más cercano a producción.

---

# Cómo levantar el proyecto

El proyecto puede ejecutarse fácilmente con **Docker Compose**, por lo que no es necesario tener Java ni Gradle instalados en la máquina.

## 1. Clonar el repositorio

```bash
git clone https://github.com/sergio77772/riu-Backend-sergio-r-giron.git
cd riu-Backend-sergio-r-giron
```

## 2. Levantar el proyecto (H2 por defecto)

```bash
docker-compose up --build
```

Este comando:

- Compila la aplicación dentro del contenedor
- Levanta Zookeeper
- Levanta Kafka
- Levanta la aplicación Spring Boot utilizando H2 en memoria

Una vez iniciado, la API queda disponible en:

```
http://localhost:8080
```

## 3. Detener los servicios

```bash
docker-compose down
```

---

# Base de datos

La aplicación soporta dos bases de datos utilizando **Spring Profiles**.

| Perfil | Base de datos | Uso |
|------|------|------|
| h2 | H2 en memoria | Perfil por defecto, rápido para pruebas |
| oracle | Oracle XE | Para un entorno más cercano a producción |

## Usar Oracle XE

Para ejecutar el proyecto con Oracle se deben realizar los siguientes pasos:

1. En el archivo [docker-compose.yml](cci:7://file:///c:/Users/sergi/Documents/GitHub/riu-Backend-sergio-r-giron/docker-compose.yml:0:0-0:0) descomentar el servicio `oracle`
2. Descomentar la dependencia `oracle: condition: service_healthy`
3. Cambiar

```yaml
SPRING_PROFILES_ACTIVE: h2
```

por

```yaml
SPRING_PROFILES_ACTIVE: oracle
```

Luego volver a ejecutar:

```bash
docker-compose up --build
```

Oracle XE descarga aproximadamente **1.5 GB** y puede tardar **hasta 90 segundos** en inicializar.

---

# Documentación de la API

La documentación Swagger está disponible en:

```
http://localhost:8080/swagger-ui/index.html
```

Desde esa interfaz se pueden probar todos los endpoints.

---

# Endpoints

## POST /search

Registra una búsqueda de hotel y la envía al topic de Kafka:

```
hotel_availability_searches
```

### Request

```json
{
  "hotelId": "1234aBc",
  "checkIn": "29/12/2023",
  "checkOut": "31/12/2023",
  "ages": [30, 29, 1, 3]
}
```

### Response

```json
{
  "searchId": "550e8400-e29b-41d4-a716-446655440000"
}
```

---

## GET /count

Permite consultar cuántas búsquedas idénticas existen a partir de un `searchId`.

```
GET /count?searchId={searchId}
```

El orden de las edades influye en el resultado. Por ejemplo:

```
[30, 1] es diferente de [1, 30]
```

### Response

```json
{
  "searchId": "550e8400-e29b-41d4-a716-446655440000",
  "search": {
    "hotelId": "1234aBc",
    "checkIn": "29/12/2023",
    "checkOut": "31/12/2023",
    "ages": [30, 29, 1, 3]
  },
  "count": 5
}
```

---

# Tests y cobertura

Para ejecutar los tests:

```bash
./gradlew test
```

Para generar el reporte de cobertura con JaCoCo:

```bash
./gradlew test jacocoTestReport
```

El reporte HTML se genera en:

```
build/reports/jacoco/test/html/index.html
```

Cobertura actual del proyecto:

- 97% líneas
- 100% branches
- 97% métodos

El build falla automáticamente si la cobertura baja del **80%**.

---

# Arquitectura

El proyecto sigue una **arquitectura hexagonal**, separando dominio, casos de uso y adaptadores.

```text
src/main/java/com/sergio/hotelsearch/
├── domain
│   ├── model
│   └── port
├── application
│   └── usecase
└── adapter
    ├── input
    │   └── rest
    └── output
        ├── kafka
        └── persistence
```

Esto permite desacoplar la lógica de negocio de tecnologías externas como Kafka, la base de datos o la capa REST.

---

# Stack tecnológico

| Tecnología | Versión |
|------|------|
| Java | 21 |
| Spring Boot | 3 |
| Apache Kafka | Spring Kafka |
| H2 | Base de datos en memoria |
| Oracle XE | 21c (opcional) |
| OpenAPI / Swagger | springdoc |
| JUnit | 5 |
| Mockito | Spring Boot Test |
| JaCoCo | cobertura de tests |
