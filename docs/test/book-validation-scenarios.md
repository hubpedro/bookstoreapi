# 📚 Book Validation - Mapa de Cenários de Teste

## 🎯 Estratégia de Validação

- **Camada de Domínio (Entidade `Book`)**: Validações de regra de negócio central (ex: estoque não negativo).
- **Camada de API (DTO `BookRequest`)**: Validações de integridade dos dados de entrada (ex: campos obrigatórios, formato de preço) :cite[1]:cite[2].
- **Camada de Serviço (`BookServiceImpl`)**: Validação da lógica de aplicação e propagação de exceções.

## 📋 Cenários de Validação por Camada

### 1. Validações da Entidade `Book` (Domínio/Regras de Negócio)

#### `validateStock`

| Cenário          | Entrada      | Comportamento Esperado          | Status         |
|:-----------------|:-------------|:--------------------------------|:---------------|
| Estoque válido   | `stock = 5`  | Retorna o valor (`5`)           | ✅ IMPLEMENTADO |
| Estoque zero     | `stock = 0`  | Lança `DomainValidateException` | ✅ IMPLEMENTADO |
| Estoque negativo | `stock = -1` | Lança `DomainValidateException` | ✅ IMPLEMENTADO |

#### `validatePrice`

| Cenário        | Entrada         | Comportamento Esperado          | Status           |
|:---------------|:----------------|:--------------------------------|:-----------------|
| Preço válido   | `price = 29.90` | Retorna o valor                 | 🚧 A IMPLEMENTAR |
| Preço zero     | `price = 0`     | Lança `DomainValidateException` | 🚧 A IMPLEMENTAR |
| Preço negativo | `price = -5.00` | Lança `DomainValidateException` | 🚧 A IMPLEMENTAR |

#### `validateTitle` e `validateAuthor`

| Cenário                         | Entrada                   | Comportamento Esperado          | Status           |
|:--------------------------------|:--------------------------|:--------------------------------|:-----------------|
| Título/Autor dentro dos limites | `title = "Título Válido"` | Retorna o valor                 | 🚧 A IMPLEMENTAR |
| Título/Autor vazio              | `title = ""`              | Lança `DomainValidateException` | 🚧 A IMPLEMENTAR |
| Título/Autor muito curto        | `title = "Oi"`            | Lança `DomainValidateException` | 🚧 A IMPLEMENTAR |

### 2. Validações do DTO `BookRequest` (Entrada da API)

**Anotações de Bean Validation a serem adicionadas** :cite[1]:cite[2]:

- `@NotBlank` para `title`, `author` e `description` (campos obrigatórios) :cite[2].
- `@Positive` para `price` (deve ser maior que zero) :cite[1]:cite[2].
- `@Min(0)` para `stock` (não pode ser negativo).

| Cenário                | Entrada (JSON)                           | Comportamento Esperado   | Status           |
|:-----------------------|:-----------------------------------------|:-------------------------|:-----------------|
| Dados válidos          | `{"title": "Foo", "author": "Bar", ...}` | Status 201 (Created)     | 🚧 A IMPLEMENTAR |
| Título em branco       | `{"title": ""}`                          | Status 400 (Bad Request) | 🚧 A IMPLEMENTAR |
| Preço negativo no JSON | `{"price": -1.0}`                        | Status 400 (Bad Request) | 🚧 A IMPLEMENTAR |

### 3. Validações de Serviço e Controlador

#### `BookService.create(BookRequest request)`

| Cenário          | Condição                           | Comportamento Esperado   | Status         |
|:-----------------|:-----------------------------------|:-------------------------|:---------------|
| Request válido   | Mapeamento bem-sucedido            | Retorna `Book` salvo     | ✅ PARCIAL      |
| Request inválido | `bookMapper.toData` retorna `null` | Lança `RuntimeException` | ✅ IMPLEMENTADO |

#### `BookController` (Endpoints da API)

| Cenário                     | Chamada HTTP                      | Comportamento Esperado               | Status           |
|:----------------------------|:----------------------------------|:-------------------------------------|:-----------------|
| Criação com sucesso         | `POST /books` com dados válidos   | Status 201, retorna `Book`           | 🚧 A IMPLEMENTAR |
| Criação com dados inválidos | `POST /books` com dados inválidos | Status 400, retorna detalhes do erro | 🚧 A IMPLEMENTAR |

## 🛠️ Implementação Técnica

### Dependência Necessária

```gradle
implementation 'org.springframework.boot:spring-boot-starter-validation' :cite[1]:cite[2]