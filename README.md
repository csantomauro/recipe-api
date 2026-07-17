# Recipe API

A REST API for managing recipes, ingredients, and user ratings, built with Java, Spring Boot, and PostgreSQL. Supports dynamic search via JPA Specifications, combining filters by cuisine, title, and ingredient.

**Stack:** Java 17 · Spring Boot 3.5 · PostgreSQL · Spring Data JPA · JUnit 5 / Mockito · Docker

## Getting started

```bash
git clone https://github.com/yourusername/recipe-api.git
cd recipe-api
docker compose up -d
./mvnw spring-boot:run
```

API available at `http://localhost:8080`. Run tests with `./mvnw test`.

## Usage examples

**Create a user**
```bash
curl -X POST "http://localhost:8080/users?username=mario"
```

**Create a recipe**
```bash
curl -X POST http://localhost:8080/recipes \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Pizza Margherita",
    "cuisine": "Italian",
    "authorUsername": "mario",
    "ingredients": [
      { "ingredientName": "Flour", "quantity": 300, "unit": "g" },
      { "ingredientName": "Tomato", "quantity": 150, "unit": "g" }
    ]
  }'
```

**Search recipes** (filters are optional and combinable)
```bash
curl "http://localhost:8080/recipes/search?cuisine=Italian&ingredient=Tomato"
```

**Rate a recipe**
```bash
curl -X POST http://localhost:8080/recipes/1/ratings \
  -H "Content-Type: application/json" \
  -d '{ "username": "luca", "score": 5 }'
```

| Method | Endpoint | Description |
|---|---|---|
| POST | `/recipes` | Create a recipe |
| GET | `/recipes/{id}` | Get a recipe by id |
| GET | `/recipes/search` | Search by `cuisine`, `title`, `ingredient` |
| POST | `/recipes/{id}/ratings` | Rate a recipe (1-5) |