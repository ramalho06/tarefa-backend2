# Refatoração e Separação de Responsabilidades

Implementação da atividade prática "Refatoração e Separação de Responsabilidades"
(5 exercícios, Spring Boot, Back-End Avançado — Prof. Jonas Bernardino).

O projeto veio vazio (apenas o esqueleto do Spring Boot), então cada exercício foi
implementado como um pacote independente e executável em
`org.example.projbd2.exercicioN`, reproduzindo o cenário "problema" descrito no PDF
e, em seguida, a versão refatorada aplicando SOLID. Isso permite comparar
diretamente o "antes" (documentado no Javadoc de cada orquestrador/entidade) com o
"depois" (o código em si).

## Como executar

```bash
./mvnw test      # roda os 17 testes (17/17 passam), incluindo o contexto Spring completo
./mvnw spring-boot:run
```

## Infraestrutura compartilhada

Antes de qualquer exercício, foi criada uma base comum reaproveitada pelos cinco
módulos, para que cada Controller possa de fato se limitar à fronteira HTTP sem
repetir tratamento de erro:

- [`pom.xml`](pom.xml) — adicionadas as dependências `spring-boot-starter-web` (o
  projeto original não tinha nenhuma, então não existiam nem `@RestController` nem
  `ResponseEntity`) e `spring-boot-starter-validation` (Bean Validation, usada no
  Exercício 1).
- [`common/exception/RegraDeNegocioException.java`](src/main/java/org/example/projbd2/common/exception/RegraDeNegocioException.java) —
  superclasse de toda exceção de regra de negócio (→ HTTP 400).
- [`common/exception/RecursoNaoEncontradoException.java`](src/main/java/org/example/projbd2/common/exception/RecursoNaoEncontradoException.java) —
  exceção para buscas por id que falham (→ HTTP 404).
- [`common/web/ApiExceptionHandler.java`](src/main/java/org/example/projbd2/common/web/ApiExceptionHandler.java) —
  `@RestControllerAdvice` único que traduz essas exceções (e falhas de
  `@Valid`) em respostas HTTP, para todos os Controllers.

---

## Questão 1 — Cadastro de cliente: o Controller faz demais

**Problema:** o `Controller` validava campos, checava e-mail duplicado, definia o
estado inicial do cliente (`ativo`, `dataCadastro`) e persistia — tudo em um único
método.

**Arquivos:**
- [`exercicio1/Cliente.java`](src/main/java/org/example/projbd2/exercicio1/Cliente.java) —
  entidade. Ganhou o método `definirEstadoInicial()`: o próprio cliente passa a
  garantir seu invariante ("todo cliente novo nasce ativo, com data de cadastro"),
  em vez de depender de quem o cria lembrar de fazer isso. Validação de forma
  (`@NotBlank`, `@Email`) fica declarada nos próprios campos.
- [`exercicio1/EmailJaCadastradoException.java`](src/main/java/org/example/projbd2/exercicio1/EmailJaCadastradoException.java) —
  erro de negócio específico, em vez de um `ResponseEntity.badRequest()` construído
  manualmente.
- [`exercicio1/ClienteRepository.java`](src/main/java/org/example/projbd2/exercicio1/ClienteRepository.java) /
  [`InMemoryClienteRepository.java`](src/main/java/org/example/projbd2/exercicio1/InMemoryClienteRepository.java) —
  abstração de persistência (a lógica de negócio depende da interface, não de uma
  implementação concreta).
- [`exercicio1/ClienteCadastroService.java`](src/main/java/org/example/projbd2/exercicio1/ClienteCadastroService.java) —
  **novo**: o caso de uso "cadastrar cliente". Única responsabilidade: aplicar a
  regra de e-mail único e coordenar a persistência.
- [`exercicio1/ClienteController.java`](src/main/java/org/example/projbd2/exercicio1/ClienteController.java) —
  reduzido a fronteira HTTP: recebe a requisição validada, delega ao serviço,
  devolve a resposta.

**SOLID aplicado:** SRP (Controller = HTTP, Service = regra de negócio, Entidade =
seu próprio invariante) e DIP (Service depende de `ClienteRepository`, a
interface, não de uma implementação).

**Testes:** [`ClienteCadastroServiceTest.java`](src/test/java/org/example/projbd2/exercicio1/ClienteCadastroServiceTest.java).

---

## Questão 2 — Pedido: o Service virou uma God Class

**Problema:** `PedidoService.criarPedido` buscava cliente e validava se estava
ativo, buscava cada produto, validava e baixava estoque, calculava subtotal e
total, definia status, salvava o pedido e disparava e-mail — nove passos
diferentes num só método.

**Arquivos:**
- [`exercicio2/Produto.java`](src/main/java/org/example/projbd2/exercicio2/Produto.java) —
  ganhou `baixarEstoque(quantidade)`: o produto protege seu próprio invariante
  (estoque nunca fica negativo), em vez de a validação e a subtração ficarem soltas
  num laço externo.
- [`exercicio2/ItemPedido.java`](src/main/java/org/example/projbd2/exercicio2/ItemPedido.java) —
  ganhou `calcularSubtotal()`: o item calcula seu próprio valor.
- [`exercicio2/ValidadorClientePedido.java`](src/main/java/org/example/projbd2/exercicio2/ValidadorClientePedido.java) —
  **novo**: única responsabilidade — decidir se um cliente pode fazer pedidos.
- [`exercicio2/EstoqueService.java`](src/main/java/org/example/projbd2/exercicio2/EstoqueService.java) —
  **novo**: aplica a baixa de estoque de uma lista de itens e persiste.
- [`exercicio2/CalculadoraPedido.java`](src/main/java/org/example/projbd2/exercicio2/CalculadoraPedido.java) —
  **novo**: soma os subtotais dos itens; sem tocar em repositório, é trivial de
  testar isoladamente.
- [`exercicio2/EmailService.java`](src/main/java/org/example/projbd2/exercicio2/EmailService.java) /
  [`LogEmailService.java`](src/main/java/org/example/projbd2/exercicio2/LogEmailService.java) —
  abstração para o envio de e-mail (DIP).
- [`exercicio2/CriarPedidoService.java`](src/main/java/org/example/projbd2/exercicio2/CriarPedidoService.java) —
  o que restou do Service original: um **orquestrador** fino que só chama, em
  sequência, os colaboradores acima. Não contém mais nenhuma regra própria.
- [`Cliente.java`](src/main/java/org/example/projbd2/exercicio2/Cliente.java),
  [`Pedido.java`](src/main/java/org/example/projbd2/exercicio2/Pedido.java) e os
  repositórios (`ClienteRepository`, `ProdutoRepository`, `PedidoRepository` +
  implementações em memória) — modelos/persistência de apoio.

**SOLID aplicado:** SRP (cada colaborador tem um único motivo para mudar: regra do
cliente, regra de estoque, cálculo, notificação, persistência) e DIP
(`CriarPedidoService` depende só de interfaces/colaboradores injetados).

**Testes:** [`CriarPedidoServiceTest.java`](src/test/java/org/example/projbd2/exercicio2/CriarPedidoServiceTest.java).

---

## Questão 3 — Frete grátis: a mesma regra em três lugares

**Problema:** a condição "frete grátis se total ≥ 300" estava copiada em três
lugares (`PedidoController`, `CarrinhoService`, `CheckoutService`). A mudança de
política (grátis acima de R$ 500, e depois diferenciada por tipo de cliente)
exigiria lembrar de alterar os três pontos.

**Arquivos:**
- [`exercicio3/PoliticaFrete.java`](src/main/java/org/example/projbd2/exercicio3/PoliticaFrete.java) —
  **novo**: interface (Strategy) — único ponto de verdade da regra "quando o
  pedido tem frete grátis".
- [`exercicio3/FretePadraoPolicy.java`](src/main/java/org/example/projbd2/exercicio3/FretePadraoPolicy.java) —
  regra vigente para clientes comuns (grátis ≥ R$ 500).
- [`exercicio3/FretePremiumPolicy.java`](src/main/java/org/example/projbd2/exercicio3/FretePremiumPolicy.java) —
  **desafio adicional**: clientes PREMIUM têm frete grátis a partir de R$ 200.
- [`exercicio3/PoliticaFreteResolver.java`](src/main/java/org/example/projbd2/exercicio3/PoliticaFreteResolver.java) —
  encontra a política aplicável a um `TipoCliente` sem nenhum `if/switch` — Spring
  injeta todas as políticas cadastradas e o resolver pergunta a cada uma se é
  aplicável.
- [`exercicio3/PedidoFreteService.java`](src/main/java/org/example/projbd2/exercicio3/PedidoFreteService.java) —
  **novo**: o ponto conceitual único de responsabilidade pedido no exercício.
- [`exercicio3/PedidoController.java`](src/main/java/org/example/projbd2/exercicio3/PedidoController.java),
  [`CarrinhoService.java`](src/main/java/org/example/projbd2/exercicio3/CarrinhoService.java),
  [`CheckoutService.java`](src/main/java/org/example/projbd2/exercicio3/CheckoutService.java) —
  os três pontos que antes duplicavam a regra; agora cada um tem uma única linha
  delegando a `PedidoFreteService`.
- [`TipoCliente.java`](src/main/java/org/example/projbd2/exercicio3/TipoCliente.java),
  [`Cliente.java`](src/main/java/org/example/projbd2/exercicio3/Cliente.java),
  [`Pedido.java`](src/main/java/org/example/projbd2/exercicio3/Pedido.java) —
  modelos de apoio.

**SOLID aplicado:** principalmente **OCP** — adicionar um novo tipo de cliente com
sua própria regra de frete significa criar uma nova classe `PoliticaFrete`; nenhum
código existente (`PedidoFreteService`, `PoliticaFreteResolver` ou os três pontos
de chamada) precisa ser modificado. Também SRP (regra de frete isolada em um único
serviço).

**Testes:** [`PedidoFreteServiceTest.java`](src/test/java/org/example/projbd2/exercicio3/PedidoFreteServiceTest.java)
(cobre cliente comum abaixo/acima de R$ 500 e cliente premium acima de R$ 200).

---

## Questão 4 — Cancelamento de pedido: fluxo com efeitos colaterais

**Problema:** o endpoint de cancelamento validava o status do pedido, persistia o
novo status, devolvia estoque item a item, estornava pagamento e enviava
notificação — tudo dentro do método do `Controller`.

**Arquivos:**
- [`exercicio4/StatusPedido.java`](src/main/java/org/example/projbd2/exercicio4/StatusPedido.java) —
  **novo** enum (o código original usava strings soltas para o status).
- [`exercicio4/Pedido.java`](src/main/java/org/example/projbd2/exercicio4/Pedido.java) —
  ganhou o método `cancelar()`: o próprio pedido passa a proteger sua máquina de
  estados (só ele decide se pode ir para `CANCELADO`), preservando exatamente as
  mensagens de erro originais ("Pedido já enviado" / "Pedido já entregue").
- [`exercicio4/Produto.java`](src/main/java/org/example/projbd2/exercicio4/Produto.java) —
  ganhou `devolverEstoque(quantidade)`.
- [`exercicio4/EstoqueService.java`](src/main/java/org/example/projbd2/exercicio4/EstoqueService.java) —
  **novo**: devolve ao estoque os itens de um pedido cancelado.
- [`exercicio4/PagamentoService.java`](src/main/java/org/example/projbd2/exercicio4/PagamentoService.java) /
  [`NotificacaoService.java`](src/main/java/org/example/projbd2/exercicio4/NotificacaoService.java)
  (+ implementações `Log...`) — abstrações (DIP) para os sistemas externos de
  pagamento e notificação.
- [`exercicio4/CancelarPedidoService.java`](src/main/java/org/example/projbd2/exercicio4/CancelarPedidoService.java) —
  **novo**: orquestrador do caso de uso — busca o pedido, pede para ele se
  cancelar, e então coordena estoque, pagamento e notificação, nessa ordem.
- [`exercicio4/PedidoController.java`](src/main/java/org/example/projbd2/exercicio4/PedidoController.java) —
  reduzido a fronteira HTTP.

**SOLID aplicado:** SRP (a regra de estado vive no `Pedido`; cada efeito colateral
tem seu próprio colaborador) e DIP (o orquestrador depende de interfaces de
pagamento/notificação, não de implementações concretas).

**Testes:** [`CancelarPedidoServiceTest.java`](src/test/java/org/example/projbd2/exercicio4/CancelarPedidoServiceTest.java)
(cobre cancelamento válido com devolução de estoque/estorno/notificação, pedido
enviado, pedido entregue e pedido inexistente).

---

## Questão 5 — Checkout: diagnóstico e refatoração arquitetural

**Problema (o mais amplo dos cinco):** `CheckoutService.finalizar` validava
usuário, resolvia e validava cupom, calculava total, aplicava desconto, calculava
frete, processava pagamento, criava o pedido, gerava nota fiscal, enviava e-mail e
registrava auditoria — nove responsabilidades em um único método.

**Arquivos:**
- [`exercicio5/UsuarioValidator.java`](src/main/java/org/example/projbd2/exercicio5/UsuarioValidator.java) —
  **novo**: busca o usuário e garante que está apto a comprar.
- [`exercicio5/CupomService.java`](src/main/java/org/example/projbd2/exercicio5/CupomService.java) —
  **novo**: resolve, valida e aplica o desconto de um cupom (o cálculo do desconto
  em si continua no próprio `Cupom.calcularDesconto`, como já era no original).
- [`exercicio5/CalculadoraCheckoutService.java`](src/main/java/org/example/projbd2/exercicio5/CalculadoraCheckoutService.java) —
  **novo**: soma os itens do checkout, sem descontos ou frete.
- [`exercicio5/FreteService.java`](src/main/java/org/example/projbd2/exercicio5/FreteService.java),
  [`PagamentoService.java`](src/main/java/org/example/projbd2/exercicio5/PagamentoService.java),
  [`NotaFiscalService.java`](src/main/java/org/example/projbd2/exercicio5/NotaFiscalService.java),
  [`EmailService.java`](src/main/java/org/example/projbd2/exercicio5/EmailService.java),
  [`AuditoriaService.java`](src/main/java/org/example/projbd2/exercicio5/AuditoriaService.java)
  (+ implementações de apoio) — cada sistema externo por trás de uma interface
  própria (DIP).
- [`exercicio5/PedidoService.java`](src/main/java/org/example/projbd2/exercicio5/PedidoService.java) —
  **novo**: cria e persiste o pedido resultante do checkout.
- [`exercicio5/PosVendaService.java`](src/main/java/org/example/projbd2/exercicio5/PosVendaService.java) —
  **novo**: agrupa nota fiscal + e-mail + auditoria, as três ações que sempre
  acontecem juntas após a criação do pedido — um pequeno orquestrador dentro do
  orquestrador maior, mostrando que "orquestrar não significa fazer tudo".
- [`exercicio5/CheckoutOrchestratorService.java`](src/main/java/org/example/projbd2/exercicio5/CheckoutOrchestratorService.java) —
  o que restou do `CheckoutService` original: apenas a sequência de chamadas aos
  sete colaboradores acima, sem nenhuma regra de negócio própria.
- Modelos/DTOs de apoio: [`Usuario.java`](src/main/java/org/example/projbd2/exercicio5/Usuario.java),
  [`Cupom.java`](src/main/java/org/example/projbd2/exercicio5/Cupom.java),
  [`Endereco.java`](src/main/java/org/example/projbd2/exercicio5/Endereco.java),
  [`ItemCheckout.java`](src/main/java/org/example/projbd2/exercicio5/ItemCheckout.java),
  [`CheckoutRequest.java`](src/main/java/org/example/projbd2/exercicio5/CheckoutRequest.java),
  [`Pedido.java`](src/main/java/org/example/projbd2/exercicio5/Pedido.java) e os
  repositórios em memória.

**SOLID aplicado:** SRP levado ao limite proporcional (nove responsabilidades →
sete colaboradores + um orquestrador + um sub-orquestrador de pós-venda) e DIP em
toda a borda com sistemas externos (pagamento, frete, nota fiscal, e-mail,
auditoria) — o orquestrador não conhece nenhum detalhe de como essas integrações
funcionam.

**Testes:** [`CheckoutOrchestratorServiceTest.java`](src/test/java/org/example/projbd2/exercicio5/CheckoutOrchestratorServiceTest.java)
(cobre checkout sem cupom, desconto aplicado antes do frete, usuário inativo e
cupom inválido).

---

## Notas gerais

- Todos os repositórios são implementações em memória (`InMemory...Repository`),
  já que o objetivo da atividade é arquitetural, não persistência real — numa
  aplicação real seriam substituídos por adaptadores Spring Data JPA sem alterar
  nenhuma outra camada, pois todo o resto depende apenas das interfaces de
  repositório.
- Nomes de bean explícitos (ex.: `@Service("ex2EstoqueService")`) foram
  adicionados onde exercícios diferentes reaproveitam o mesmo nome de classe
  (`EstoqueService`, `PedidoController`, etc.), já que os cinco módulos coexistem
  no mesmo contexto Spring.
- `./mvnw test` roda 17 testes (JUnit 5 + Mockito + AssertJ), cobrindo os
  "requisitos mínimos" listados em cada exercício do PDF, incluindo o teste
  `contextLoads` que garante que a aplicação sobe com os cinco módulos juntos.
