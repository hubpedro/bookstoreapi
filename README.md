# Bookstore API - API de Gerenciamento de Livraria

API RESTful completa construída com Spring Boot para gerenciar os recursos de uma livraria, incluindo livros, usuários e
empréstimos.

Este projeto foi desenvolvido como um portfólio para demonstrar competências em desenvolvimento backend Java,
implementando boas práticas de arquitetura, segurança, testes e containerização.

## Features

* **Gerenciamento de Livros (CRUD):** API completa para criar, ler, atualizar e deletar livros.
* **Autenticação e Autorização com JWT:** Sistema de segurança robusto usando Spring Security:
    * Registro (`/register`) e Login (`/login`) de usuários.
    * Geração e validação de JSON Web Tokens (JWT).
    * Proteção de endpoints baseada em Roles (Perfis) e Permissions (Permissões) granulares (ex: `ROLE_ADMIN`,
      `BOOK_WRITE`).
* **Sistema de Empréstimos:** Usuários podem pegar livros emprestados, e o sistema controla o estoque e as datas.
* **Gerenciamento de Usuários (CRUD):** Endpoints administrativos para gerenciar usuários.
* **Validação de Dados:** Tratamento de erros e validação de entrada (DTOs).
* **Documentação de API:** API documentada com Swagger (OpenAPI).

## Endpoints da API

### Empréstimos

#### Criar um Empréstimo

*   **POST** `/api/loans`

    Cria um novo empréstimo para um usuário e um livro.

    **Request Body:**

    ```json
    {
      "userId": 1,
      "bookId": 1
    }
    ```

    **Response:** `201 Created` com o objeto do empréstimo criado no corpo da resposta e o `Location` header com a URL para o novo recurso.

    ```json
    {
        "userId": 1,
        "id": 1,
        "bookId": 1,
        "createdAt": "2025-10-30",
        "lastModifiedAt": "2025-10-30",
        "loanedAt": "2025-10-30",
        "dueOn": "2025-11-13",
        "returnedAt": null,
        "status": "ACTIVE",
        "loanDebt": null
    }
    ```

#### Devolver um Livro

*   **PATCH** `/api/loans/{id}/return`

    Registra a devolução de um livro. Se o livro for devolvido após a data de vencimento, uma multa será calculada e adicionada ao campo `loanDebt`.

    **Response:** `200 OK` com o objeto do empréstimo atualizado.

    ```json
    {
        "userId": 1,
        "id": 1,
        "bookId": 1,
        "createdAt": "2025-10-30",
        "lastModifiedAt": "2025-10-31",
        "loanedAt": "2025-10-30",
        "dueOn": "2025-11-13",
        "returnedAt": "2025-10-31",
        "status": "RETURNED",
        "loanDebt": 0.00
    }
    ```

    Em caso de tentativa de devolução de um livro já devolvido, a API retornará `409 Conflict`.

## Tech Stack (Tecnologias Utilizadas)

* **Backend:**
    * Java 17
    * Spring Boot 3
    * Spring Security (com JWT)
    * Spring Data JPA (Hibernate)
* **Database:**
    * PostgreSQL (em produção/dev)
    * H2 (para testes)
* **Testes:**
    * JUnit 5
    * Mockito
    * `@WebMvcTest` e `@SpringBootTest`
* **DevOps & Ferramentas:**
    * Docker & Docker Compose
    * Maven
    * Swagger (Springdoc OpenAPI)

## Como Executar o Projeto

Existem duas formas de rodar a aplicação: via Docker (recomendado) ou manualmente.

### 1. Usando Docker (Recomendado)

O `docker-compose` irá subir a API Spring e o banco de dados PostgreSQL com um único comando.

1. Clone o repositório:
   ```bash
   git clone [https://github.com/seu-usuario/bookstoreapi.git](https://github.com/seu-usuario/bookstoreapi.git)
   cd bookstoreapi
   ```

2. Execute o Docker Compose:
   ```bash
   docker-compose up --build
   ```

A API estará disponível em `http://localhost:8080`.
O banco de dados estará acessível em `localhost:5432` (Username: `developer`, Password: `devpass`).

### 2. Executando Manualmente (Local)

1. **Inicie um Banco PostgreSQL:**
    * Você pode usar o Docker para subir apenas o banco:
        ```bash
        docker run --name spring-dev-db -e POSTGRES_DB=postgresdb -e POSTGRES_USER=developer -e POSTGRES_PASSWORD=devpass -p 5432:5432 -d postgres:15-alpine
        ```
    * As configurações de conexão estão em `src/main/resources/application.yml`.

2. **Execute a Aplicação Spring:**
    * Use o Maven Wrapper para rodar:
        ```bash
        ./mvnw spring-boot:run
        ```

## Como Executar os Testes

O projeto possui uma suíte de testes de unidade e integração. Para executá-los:

```bash
./mvnw test