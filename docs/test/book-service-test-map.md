# 📚 Book Service - Mapa de Testes

## 🎯 Estratégia Geral

- Foco em regras de negócio, não em getters/setters
- Testar comportamentos, não implementações
- Priorizar validações críticas

## 📋 Cenários Mapeados - Técnica 5W2H

### ✅ Book.validateStock()

| Aspecto     | Descrição                            |
|-------------|--------------------------------------|
| **O QUÊ**   | Validação de estoque negativo e zero |
| **POR QUÊ** | Garantir integridade do domínio      |
| **COMO**    | @ParameterizedTest com ValueSource   |
| **QUANDO**  | Durante criação e atualização        |
| **ONDE**    | BookValidationTest.java              |

**Cenários:**

- ❌ [-1, -5, -10] → DomainValidateException
- ❌ 0 → DomainValidateException
- ✅ [1, 5, 100] → Sucesso

### 🔄 BookService.create()

| Aspecto     | Descrição                       |
|-------------|---------------------------------|
| **O QUÊ**   | Criação de livro com validações |
| **POR QUÊ** | Garantir fluxo completo         |
| **COMO**    | Mockito + AssertJ               |
| **QUANDO**  | API POST /books                 |

**Cenários:**

- ✅ Dados válidos → Livro criado
- ❌ Dados inválidos → Exceção apropriada
- 🔄 Validação propagada → Book.validateStock chamado

## 🚦 Status dos Testes

- [x] validateStock implementado
- [ ] validateTitle pendente
- [ ] create flow pendente
- [ ] update scenarios pendente