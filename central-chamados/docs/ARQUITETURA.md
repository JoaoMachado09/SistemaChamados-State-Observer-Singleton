# Arquitetura

## Inicialização e composição

`Main` inicializa a aplicação com `@SpringBootApplication`. A busca de componentes cobre `aplicacao` e `servicos`.
`Configuracao` fornece `PainelChamados` e a instância de `Parametros` como beans.
`ConsoleAplicacao` implementa `CommandLineRunner`: após a inicialização, abre o menu ou executa a demonstração.

## Serviços

`ChamadoService` mantém o cadastro em memória, atribui números e encaminha as ações para o contexto State.
`NotificacaoService` cadastra os interessados de cada chamado, conecta o painel e disponibiliza as mensagens produzidas pelo Observer.
Ambos são `@Service` e recebem suas dependências por construtores anotados com `@Autowired`.

## Domínio

`Chamado` mantém seu estado atual, histórico e observadores. `ChamadoEstado` define as operações, retornando `false` para aquelas não suportadas.
Os estados concretos sobrescrevem as operações válidas, alteram o contexto e retornam `true`.

O domínio não depende de classes do Spring. A injeção de dependências organiza os componentes de aplicação, mantendo os padrões testáveis isoladamente.

## Eventos e notificações

`setEstado` cria uma `MudancaStatus`, altera o estado, registra o histórico e chama `setChanged()` e `notifyObservers()`.
`Usuario.update` registra a mensagem e `PainelChamados.update` atualiza a visão do chamado.
Cada cadastro cria seu próprio solicitante e técnico, evitando misturar mensagens de chamados diferentes.

## Singleton

Cada classe de estado possui uma instância estática, sem dados mutáveis de chamados.
`Parametros` mantém o nome compartilhado usado nas notificações; o bean Spring retorna a instância obtida por `getInstance()`.
Os serviços têm uma instância por contexto Spring. Objetos de domínio continuam independentes e são criados por cadastro.

## Testabilidade

Os testes dos padrões são unitários e não inicializam o framework.
Os testes de serviço recebem dependências pelo construtor.
`SpringIntegracaoTest` verifica a criação dos beans, a injeção e o fluxo completo com State, Observer e Singleton.
A propriedade `app.console.enabled=false` impede leitura do teclado durante esses testes.

## Limites

A aplicação é sequencial e mantém seus dados em memória. Os serviços não fornecem controle de concorrência.
O setter público permite alteração técnica direta; o fluxo normal usa as operações de negócio.
Não há segurança de acesso, persistência ou entrega externa de mensagens.
