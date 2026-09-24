# DevShowcase API — Etapa Final

API REST em Java 17 + Spring Boot com regras de negócio, tratamento global de erros, Swagger/OpenAPI, PostgreSQL e preparação para deploy no Render.

## Endpoints

- POST `/api/profiles`
- GET `/api/profiles/{id}`
- POST `/api/technologies`
- GET `/api/technologies`
- POST `/api/projects`
- GET `/api/projects?technology=Java&page=0&size=10`
- POST `/api/projects/{id}/feedbacks`
- PUT `/api/projects/{id}/upvote`

## Exemplos para o Postman

### Feedback válido
POST `/api/projects/1/feedbacks`

```json
{"rating":5,"comment":"Projeto muito bom e bem organizado."}
```

### Erro 400
POST `/api/projects/1/feedbacks`

```json
{"rating":8,"comment":"Nota inválida."}
```

### Erro 404
POST `/api/projects/99999/feedbacks`

```json
{"rating":5,"comment":"Teste de projeto inexistente."}
```

### Upvote
PUT `/api/projects/1/upvote`

### Filtro e paginação
GET `/api/projects?technology=Java&page=0&size=10`

## Swagger

- `/swagger-ui.html`
- `/v3/api-docs`

## Desenvolvimento local

O projeto usa H2 por padrão (`dev`). Acesse o console em `/h2-console`.

## Produção

O perfil `prod` usa PostgreSQL por variáveis de ambiente:

- `SPRING_PROFILES_ACTIVE=prod`
- `DB_HOST`
- `DB_PORT`
- `DB_NAME`
- `DB_USER`
- `DB_PASSWORD`

Nunca coloque credenciais reais no GitHub.
