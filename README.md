# Bookstore API - Sistema de Gerenciamento de Livraria

## 🔍 Visão Geral

API RESTful para gerenciamento de uma livraria online, permitindo o cadastro de usuários, gerenciamento de livros e
controle de empréstimos.

## Modelo dos dados

### 👤 Cliente

1. `username`: Nome de usuário (deve ser único)
2. `email`: Endereço de e-mail (deve ser único)
3. `password`: Senha (mínimo de 8 caracteres)

### 📘 Livro

1. `title`: Título do livro
2. `description`: Descrição do conteúdo
3. `author`
4. `price`
5. `stock`

### 🔄 Empréstimo

1. `id`: identificador único de empréstimo
2. `user`: usuario associado ao emprestimo do livro
3. `book`: livro associado ao emprestimo do usuario
4. `created_date`: Data de registro do empréstimo
5. `last_modified`: Ultima atualização do registro
6. `loanDate`: Data do emprestimo do livro
7. `dueDate`: Data do vencimento do livro
8. `returnDate`: Data em que o livro foi devolvido

## 📜 Regras de Negócio

### 👥 Cadastro de usuário

1. `username`: deve ser único
2. `email`: deve ser único
3. `password`: deve conter no mínimo 8 caracteres

### 📗 Cadastro de Livro

1. `title`:entre 5 e 50 caracteres
2. `description`: não pode ser vazia e deve ser maior que 10 e menor ou igual a 255
3. `author`: não pode ser vazio e deve ser maior que 10 e menor ou igual a 255
4. `price`: não pode ser vazio e deve ser maior que 0

### 📚 Empréstimo de Livro

1. Um usuário pode emprestar livros disponíveis em estoque
2. A data de vencimento deve ser posterior à data do empréstimo
3. O livro deve ser marcado como devolvido ao registrar a dataDevolucao
4. Não é permitido emprestar o mesmo livro para múltiplos usuários simultaneamente