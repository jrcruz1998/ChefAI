# ChefAI

Sistema em Java que coleta ingredientes, restrições alimentares e tipo de refeição do usuário e gera sugestões de receitas usando um modelo de linguagem.

## Diagrama de classes

Principais classes:

- `Usuario`
  - atributos: nome (opcional), lista<Ingrediente>, restricao, tipoRefeicao
  - métodos: getters/setters, adicionarIngrediente(...)

- `Ingrediente`
  - atributos: nome, quantidade

- `Receita`
  - atributos: nome, lista<Ingrediente>, modoPreparo, tempo

- `SugestorBase` (abstrata)
  - método: List<Receita> sugerir(Usuario u)

- `SugestorRapido` / `SugestorSaudavel` / `SugestorGenerico`
  - herdam de `SugestorBase`
  - implementam `sugerir(Usuario u)`

- `PromptBuilder`
  - método estático: String criarPrompt(Usuario u, String tipo)

- `InputHelper`
  - métodos estáticos para ler linha e números do console

- `Main`
  - contém o método `public static void main(String[] args)`
  - controla o fluxo: lê dados do usuário e chama o `SugestorBase`

## Exemplos de uso

### Exemplo 1 – Receita rápida

Entrada no console:
- Ingrediente: arroz
- Quantidade: 200
- Ingrediente: frango
- Quantidade: 300
- Ingrediente: (ENTER para terminar)
- Restrição: nenhuma
- Tipo de Refeição: almoço
- Opção: 1 (rápido)

O sistema então envia esses dados para o LLM e exibe 3 receitas rápidas baseadas em arroz e frango.

### Exemplo 2 – Receita saudável

Entrada:
- Ingrediente: aveia
- Quantidade: 100
- Ingrediente: banana
- Quantidade: 2
- Ingrediente: (ENTER)
- Restrição: vegetariano
- Tipo de Refeição: café da manhã
- Opção: 2 (saudável)

O sistema gera 3 receitas saudáveis de café da manhã usando aveia e banana.


## Configuração da API

Este projeto usa uma API da Groq para gerar as receitas.

1. Crie um arquivo `config/api.properties` (se ainda não existir).
2. Adicione a linha:

   API_KEY=SEU_TOKEN_AQUI

3. Substitua `SEU_TOKEN_AQUI` pela chave de API que você possui.

Caso não configure a chave, o sistema não chamará a API corretamente e irá lançar erro de autenticação, mas o restante do código pode ser analisado normalmente.
