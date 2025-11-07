# petshop-agendamento
Projeto acadêmico em console para CRUD e agendamento, aplicando conceitos de POO.

---
## Descrição

Projeto em Java organizado por pacotes (`main`, `controller`, `service`, `model`, `util`) feito para gerenciar pets e agendamentos de um petshop em modo console. O projeto usa arquivos simples para carregar raças (`data/racas.txt`) e gravar agendamentos (`data/agendamentos.txt`).

## Funcionalidades

* Cadastrar pets (nome, data de nascimento, peso, gênero, porte e raça)
* Listagem e seleção de raças carregadas de `data/racas.txt`
* Agendar serviços (banho / tosa) em horários pré-definidos
* Listar agendamentos existentes
* Cancelar agendamentos
* Gravar agendamentos em `data/agendamentos.txt`

## Tecnologias

* Java 17+ 
* Estruturado em pacotes seguindo o padrão MVC (sem uso de build tool específica)

---

## Estrutura do projeto (resumo)

```
src/
  main/ (package `main`)
  controller/
  model/
  service/
  util/
data/
  racas.txt
  agendamentos.txt (gerado)
```

### Pacotes e classes principais

* `main.Main` — ponto de entrada. Inicializa listas, carrega raças e inicia o menu.
* `controller.MenuController` — exibe o menu e faz roteamento das ações.
* `controller.PetController` — interage com o usuário para cadastrar pets.
* `controller.AgendaController` — lida com agendamentos (criar, listar, cancelar, gravar).
* `service.PetService` — lógica de domínio para criação/manutenção de pets.
* `service.AgendaService` — lógica de domínio para criação/manutenção de agendas e horários.
* `model.*` — modelos: `Pet`, `TipoPet`, `Raca`, `Servico`, `Horario`, `Agenda`, `PxS`.
* `util.RacaLoader` — carrega as raças a partir do arquivo `data/racas.txt`.

---

## Formato do arquivo de raças (`data/racas.txt`)

Cada linha deve seguir o padrão:

```
<especie>:<nome_da_raca>
```

Exemplos:

```
Gato:Persa
Gato:Siamês
Cachorro:Labrador
```

> Observação: `RacaLoader` faz `split(":")` e espera exatamente 2 partes (espécie e nome).

---

## Como compilar e executar (linha de comando)

> Com a pasta `src` na raiz do projeto com os pacotes (`main`, `controller`, ...).

1. Compile o projeto (isso gera os arquivos `.class` dentro da pasta `out`):

```bash
javac -d out src/main/Main.java
```

2. Execute a aplicação (a classe principal está no pacote `main`):

```bash
java -cp out main.Main
```
> Se preferir usar uma IDE (VSCode, IntelliJ ou Eclipse), basta importar o projeto e executar a classe `Main.java`.
---

## Uso / Regras de entrada importantes

* Data de nascimento: **dd/MM/yyyy** (ex.: `31/12/2018`). O `Pet.setDataNascimento` valida o formato e imprime mensagem caso inválido.
* Peso: número double (ex.: `7.5`). Validação atual: deve ser >= 1 e <= 350.
* Gênero: `M` ou `F` (aceito maiúsculo/minúsculo).
* Porte: `P` / `M` / `G`.
* Ao selecionar espécie o aplicativo mostra somente raças compatíveis (carregadas de `racas.txt`).
* Ao digitar opções numéricas (menus, escolhas), use apenas números — entrada inválida é tratada com mensagens no console.

---

## Exemplos de execução (fluxo)

1. Ao iniciar, escolha `1 - Cadastrar Pet`:

   * Informe nome, data (`dd/MM/yyyy`), peso, gênero, porte.
   * Selecione espécie: `1 - Gato` ou `2 - Cachorro`.
   * Escolha a raça a partir da lista exibida.

2. Depois, escolha `2 - Agendar Serviço`:

   * Selecione um pet cadastrado.
   * Escolha o serviço (banho/tosa).
   * Informe a data do agendamento no formato `DDMMAAAA` (ex.: `07112025`).
   * Selecione horário disponível.

3. Para salvar em arquivo, use `5 - Gravar no Arquivo de Agendamentos`.

---

