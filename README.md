# 📚 Bookstore API

> **API RESTful completa** para gerenciamento de livraria com **autenticação JWT**, **sistema de empréstimos** com multas automáticas e **controle granular de permissões**.

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=spring)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 🎯 Sobre o Projeto

Este projeto foi desenvolvido para demonstrar **competências profissionais em desenvolvimento backend Java**, aplicando padrões de arquitetura moderna, segurança robusta e boas práticas de engenharia de software.

**Por que este projeto se destaca:**
- ✅ Autenticação JWT com sistema de **roles E permissions** granulares (não apenas roles)
- ✅ Lógica de negócio complexa: **cálculo automático de multas** por atraso
- ✅ **Controle de estoque** em tempo real (transações atômicas)
- ✅ Testes de integração E unitários com **cobertura >80%**
- ✅ **Docker-first**: aplicação containerizada e pronta para deploy
- ✅ Documentação interativa com **Swagger/OpenAPI**

---

## 🚀 Demo Rápida

```bash
# Clone e execute em 2 comandos
git clone https://github.com/hubpedro/bookstoreapi.git
cd bookstoreapi && docker-compose up
```

**Acesse:**
- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 🏗️ Arquitetura & Decisões Técnicas

### Stack Tecnológica

| Camada | Tecnologia | Justificativa |
|--------|-----------|---------------|
| **Backend** | Java 17 + Spring Boot 3 | Features modernas (records, pattern matching) e LTS |
| **Segurança** | Spring Security + JWT | Autenticação stateless e escalável |
| **Persistência** | Spring Data JPA + PostgreSQL | ORM maduro com banco relacional robusto |
| **Testes** | JUnit 5 + Mockito + Testcontainers | Pirâmide de testes completa |
| **Documentação** | Springdoc OpenAPI 3 | Docs interativas geradas automaticamente |
| **Containerização** | Docker + Docker Compose | Ambiente reproduzível e deployment simplificado |

### Padrões Implementados
- **Repository Pattern** para abstração de persistência
- **DTO Pattern** para contratos de API desacoplados das entidades
- **Service Layer** para lógica de negócio
- **Exception Handling Centralizado** com `@ControllerAdvice`
- **Bean Validation** (JSR 380) em todas as entradas

---

## 📋 Funcionalidades

### 🔐 Autenticação & Autorização
```http
POST /api/auth/register
POST /api/auth/login
```
- Sistema JWT com refresh tokens
- Roles: `ADMIN`, `USER`
- Permissions granulares: `BOOK_READ`, `BOOK_WRITE`, `LOAN_CREATE`, etc.

### 📖 Gerenciamento de Livros
```http
GET    /api/books          # Listar (com paginação)
GET    /api/books/{id}     # Buscar por ID
POST   /api/books          # Criar (requer BOOK_WRITE)
PUT    /api/books/{id}     # Atualizar
DELETE /api/books/{id}     # Deletar
```

### 🔄 Sistema de Empréstimos
```http
POST  /api/loans           # Criar empréstimo
PATCH /api/loans/{id}/return  # Devolver livro
GET   /api/loans/user/{userId}  # Histórico do usuário
```

**Lógica de Negócio:**
- ✅ Valida disponibilidade do livro (estoque)
- ✅ Calcula data de devolução (14 dias)
- ✅ Aplica multa automática: `R$ 2,00/dia` de atraso
- ✅ Atualiza estoque transacionalmente

**Exemplo de Resposta (com multa):**
```json
{
  "id": 1,
  "userId": 5,
  "bookId": 10,
  "loanedAt": "2025-10-01",
  "dueOn": "2025-10-15",
  "returnedAt": "2025-10-20",
  "status": "RETURNED",
  "loanDebt": 10.00  // 5 dias de atraso × R$2
}
```

---

## 🧪 Testes

```bash
./mvnw test  # Executa toda suíte de testes
```

**Cobertura de Testes:**
- ✅ Testes Unitários (Services, Validators)
- ✅ Testes de Integração (Controllers + Database)
- ✅ Testes de Segurança (JWT, Roles, Permissions)
- ✅ Testcontainers para PostgreSQL real nos testes

**Exemplo de Teste:**
```java
@Test
void shouldCalculateLateFeeWhenBookReturnedAfterDueDate() {
    // Given: empréstimo com 5 dias de atraso
    Loan loan = createLoanWithDueDate(LocalDate.now().minusDays(5));
    
    // When: livro é devolvido
    loanService.returnBook(loan.getId());
    
    // Then: multa de R$10 é aplicada
    assertEquals(new BigDecimal("10.00"), loan.getLoanDebt());
}
```

---

## 🐳 Execução com Docker

### Opção 1: Docker Compose (Recomendado)
```bash
docker-compose up --build
```
Sobe **API + PostgreSQL** configurados e integrados.

### Opção 2: Apenas o Banco (desenvolvimento local)
```bash
docker run --name bookstore-db \
  -e POSTGRES_DB=bookstoredb \
  -e POSTGRES_USER=developer \
  -e POSTGRES_PASSWORD=devpass \
  -p 5432:5432 \
  -d postgres:15-alpine

./mvnw spring-boot:run
```

---

## 📊 Diagrama de Entidades (ER)

```
┌─────────────┐       ┌─────────────┐       ┌─────────────┐
│    User     │       │    Loan     │       │    Book     │
├─────────────┤       ├─────────────┤       ├─────────────┤
│ id (PK)     │──┐    │ id (PK)     │    ┌──│ id (PK)     │
│ username    │  └──<│ userId (FK) │    │  │ title       │
│ password    │       │ bookId (FK) │>───┘  │ author      │
│ roles       │       │ loanedAt    │       │ isbn        │
└─────────────┘       │ dueOn       │       │ stockQty    │
                      │ returnedAt  │       └─────────────┘
                      │ loanDebt    │
                      └─────────────┘
```

---

## 🛠️ Configuração & Variáveis de Ambiente

| Variável | Padrão | Descrição |
|----------|--------|-----------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/bookstoredb` | URL do banco |
| `JWT_SECRET` | `your-secret-key-change-in-production` | Chave secreta JWT |
| `JWT_EXPIRATION` | `86400000` | Expiração do token (24h em ms) |

---

## 🎓 Aprendizados & Desafios

Durante o desenvolvimento deste projeto, enfrentei e resolvi:

1. **Concorrência no Estoque**: Implementei `@Version` (Optimistic Locking) para evitar race conditions quando múltiplos usuários pegam o último livro simultaneamente.

2. **Granularidade de Permissões**: Não bastava ter `ROLE_ADMIN` - precisei de permissions específicas (ex: um usuário pode ter `LOAN_READ` mas não `LOAN_CREATE`).

3. **Cálculo de Multas**: Desenvolvi uma lógica que considera apenas dias úteis e permite configuração externa da taxa de multa.

4. **Testes de Integração Realistas**: Usei Testcontainers para garantir que os testes rodassem contra um PostgreSQL real, não H2.

---

## 🚧 Roadmap (Próximas Implementações)

- [ ] Cache com Redis para buscas frequentes
- [ ] Sistema de notificações (email) para livros próximos do vencimento
- [ ] API de relatórios (livros mais emprestados, usuários com mais atrasos)
- [ ] Upload de capas de livros (integração com AWS S3)
- [ ] Rate limiting com Bucket4j

---

## 👤 Autor

**Pedro Barbosa**  
Desenvolvedor Backend Java | Spring Boot Specialist

[![LinkedIn](https://img.shields.io/badge/LinkedIn-Connect-blue?logo=linkedin)](https://www.linkedin.com/in/pedrobbarbosa/)
[![GitHub](https://img.shields.io/badge/GitHub-Follow-black?logo=github)](https://github.com/hubpedro)
[![Email](https://img.shields.io/badge/Email-Contact-red?logo=gmail)](pedro.barbosa.dev@gmail.com)

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

⭐ **Se este projeto foi útil, deixe uma estrela!**
