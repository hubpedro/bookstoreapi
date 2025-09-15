Vou ajudar a criar um README.md detalhado para seu projeto. Aqui está uma estrutura completa:

# Bookstore API - Sistema de Gerenciamento de Livraria

## 📋 Visão Geral

API RESTful para gerenciamento de uma livraria online desenvolvida com Domain-Driven Design (DDD) simplificado e
arquitetura limpa.

## 🏗️ Estrutura do Projeto (DDD Simplificado)

```
src
└── main
    └── java
        └── com
            └── hubpedro
                └── bookstoreapi
                    ├── application          # Camada de aplicação
                    │   ├── dto              # Objetos de transferência de dados
                    │   ├── service          # Serviços de aplicação
                    │   └── event            # Eventos de aplicação
                    ├── domain               # Coração do domínio
                    │   ├── model            # Entidades e agregados
                    │   ├── repository       # Interfaces de repositório
                    │   ├── service          # Serviços de domínio
                    │   └── events           # Eventos de domínio
                    ├── infra                # Infraestrutura
                    │   ├── persistence      # Implementações de persistência
                    │   └── configuration    # Configurações técnicas
                    └── interfaces           # Adaptadores de entrada/saída
                        └── rest             # Controladores REST
```

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Hibernate Validator
- Maven
- H2 Database
- PostgreSQL
- Docker

## 🚀 Funcionalidades

### Sprint 1: Cadastro Básico e Gestão de Livros

- [ ] Cadastro de livros com ISBN, título, autor, preço e estoque
- [ ] Categorização de livros por gênero
- [ ] Busca de livros por título/autor/categoria
- [ ] Atualização de estoque
- [ ] Soft delete de livros

### Sprint 2: Gestão de Clientes e Pedidos

- [ ] Cadastro de clientes com endereço
- [ ] Sistema de autenticação básica
- [ ] Carrinho de compras
- [ ] Processamento de pedidos
- [ ] Histórico de pedidos por cliente

### Sprint 3: Sistema de Recomendações

- [ ] Recomendações baseadas em histórico de compras
- [ ] Livros mais vendidos por categoria
- [ ] Sistema de avaliações e reviews

### Sprint 4: Funcionalidades Avançadas

- [ ] Integração com gateways de pagamento
- [ ] Sistema de cupons de desconto
- [ ] Notificações por e-mail
- [ ] Relatórios de vendas

## 📋 Regras de Negócio Principais

### Domínio de Livros

- ISBN deve ser válido (formato 13 ou 10 dígitos)
- Preço não pode ser negativo
- Estoque mínimo de 0 unidades
- Título obrigatório (mínimo 2 caracteres)

### Domínio de Pedidos

- Pedido deve ter pelo menos 1 item
- Valor total não pode ser negativo
- Status do pedido: PENDENTE, PROCESSANDO, ENVIADO, ENTREGUE, CANCELADO

### Domínio de Clientes

- Email deve ser único e válido
- CPF/CNPJ válido conforme regras da Receita Federal
- Endereço deve conter CEP válido

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para detalhes.

---

