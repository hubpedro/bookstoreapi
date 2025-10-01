# 🧪 Estratégia de Testes - Regras Básicas

## ✅ TESTE ISSO:

- [ ] Validações de domínio (stock, preço, título)
- [ ] Regras de negócio (available = stock >= 1)
- [ ] Fluxos críticos (create, update com regras)

## ❌ NÃO TESTE ISSO:

- [ ] Getters/Setters simples
- [ ] Métodos que só delegam
- [ ] Configurações de framework

## 🎯 PRÓXIMOS TESTES PRIORITÁRIOS:

1. validateTitle - limites 5-50 caracteres
2. BookService.create - fluxo completo
3. BookService.findById - livro inexistente