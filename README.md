# selenium-restassured-cucumber-kiro-github-actions

![CI](https://github.com/aline-nory/selenium-restassured-cucumber-kiro-github-actions/actions/workflows/testes.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-8-orange?logo=java)
![Maven](https://img.shields.io/badge/Maven-3.9-blue?logo=apachemaven)
![Selenium](https://img.shields.io/badge/Selenium-3.141-green?logo=selenium)
![Cucumber](https://img.shields.io/badge/Cucumber-7.18-brightgreen?logo=cucumber)
![REST Assured](https://img.shields.io/badge/REST_Assured-4.5-teal)
![Allure](https://img.shields.io/badge/Report-Allure-orange)
![Kiro AI](https://img.shields.io/badge/Generated%20with-Kiro%20AI-blueviolet)

Framework de automacao de testes **Web (UI)** e **API REST** com pipeline CI/CD, desenvolvido com Kiro AI como portfolio de QA.

---

## Destaques do projeto

- **Arquitetura em camadas** — Pages, Steps, Services, Hooks e Config com responsabilidades bem definidas
- **Explicit waits** em toda interacao com o DOM — estabilidade em aplicacoes assincronas
- **Validacao de carregamento** em cada Page Object antes de interagir
- **Driver isolado por cenario** — independencia total entre execucoes
- **Retry automatico** de cenarios falhados (1x) — resiliencia contra instabilidade de rede
- **Semantica BDD** — `@Dado` prepara, `@Quando` executa, `@Entao` valida
- **Validacao de contrato** com JSON Schema
- **Payloads externalizados** em arquivos `.json`
- **Pipeline CI/CD** com GitHub Actions — headless automatico, Allure no GitHub Pages
- **Configuracao por ambiente** com override via variavel de ambiente
- **WebDriverManager** — resolve chromedriver automaticamente
- **Custom agents Kiro** — `qa-scaffold` gera artefatos, `framework-reviewer` audita arquitetura
---

## Stack

| Tecnologia | Versao | Funcao |
|---|---|---|
| Java | 8 | Linguagem |
| Maven | 3.9 | Build e dependencias |
| Selenium WebDriver | 3.141.59 | Automacao de UI |
| WebDriverManager | 5.8.0 | Resolucao automatica do chromedriver |
| Cucumber | 7.18 | BDD em portugues |
| JUnit | 4.13 | Runner e assertions |
| REST Assured | 4.5.1 | Automacao de API REST |
| Allure Report | 2.24 | Relatorio interativo |
| PicoContainer | 7.18 | Injecao de dependencia por cenario |
| SLF4J + Logback | 1.7/1.2 | Logging estruturado |
| Jackson Databind | 2.15.2 | Leitura/edicao de payloads JSON |
| GitHub Actions | - | Pipeline CI/CD |

---

## Estrutura do projeto

```
src/test/java/
├── runners/
│   └── TestRunner.java              # Ponto de entrada Cucumber + JUnit
├── steps/
│   ├── ui/
│   │   ├── LoginSteps.java          # Steps de login e autenticacao
│   │   └── EmployeeListSteps.java   # Steps da lista de empregados
│   └── api/
│       └── PostSteps.java           # Steps de API
├── pages/
│   ├── base/
│   │   └── BasePage.java            # Classe abstrata (acoes comuns + waits)
│   ├── login/
│   │   └── LoginPage.java           # Page Object do login
│   ├── dashboard/
│   │   └── DashboardPage.java       # Page Object do dashboard (pos-login)
│   └── employeelist/
│       └── EmployeeListPage.java    # Page Object da lista de empregados
├── api/
│   ├── clients/
│   │   └── RestClient.java          # Cliente HTTP com Allure attachments
│   └── services/
│       └── PostService.java         # Logica de negocio /posts
├── hooks/
│   ├── UiHooks.java                 # Ciclo de vida WebDriver (@ui)
│   └── ApiHooks.java                # Setup de API (@api)
├── config/
│   ├── Environment.java             # Gerenciamento de ambiente + siteRoot
│   └── ConfigReader.java            # Leitura de .properties + env var override
├── drivers/
│   ├── DriverFactory.java           # Criacao do Chrome (WebDriverManager)
│   └── DriverManager.java           # ThreadLocal para paralelismo
├── utils/
│   ├── LogUtils.java                # Logging SLF4J
│   ├── JsonUtils.java               # Leitura/edicao de payloads JSON
│   └── ScreenshotUtils.java         # Captura de evidencias
└── exceptions/
    └── FrameworkException.java       # Excecao customizada do framework

src/test/resources/
├── features/
│   ├── ui/
│   │   ├── login.feature            # Cenarios de login (OrangeHRM)
│   │   └── employee-list.feature    # Cenarios da lista de empregados
│   └── api/
│       └── posts.feature            # Cenarios de API (JSONPlaceholder)
├── environments/
│   ├── dev.properties               # Configuracao DEV
│   └── hml.properties               # Configuracao HML
├── payloads/posts/
│   ├── create-post.json             # Body de criacao
│   └── update-post.json             # Body de atualizacao
├── schemas/
│   └── post-schema.json             # Contrato JSON da API
└── logback.xml                      # Configuracao de logging

.kiro/agents/
├── qa-scaffold.md                   # Agent para gerar Page Objects/Steps/Features
└── framework-reviewer.md            # Agent para auditar arquitetura do framework
```

---

## Cenarios de teste

### UI — Login (OrangeHRM Demo)

| Cenario | Tag |
|---------|-----|
| Login com credenciais validas | `@smoke` |
| Login com senha incorreta | - |
| Login com credenciais invalidas (Scenario Outline, 2 combinacoes) | - |

### UI — Employee List (OrangeHRM Demo)

| Cenario | Tag |
|---------|-----|
| Visualizar tabela de empregados | `@smoke` |
| Buscar empregado (com resultados) | - |
| Buscar empregado inexistente (No Records Found) | - |
| Navegar para adicionar novo empregado | - |
| Editar empregado da lista | - |

### API — Posts (JSONPlaceholder)

| Cenario | Tag |
|---------|-----|
| `GET /posts` — listar todos | `@smoke` |
| `GET /posts/{id}` — buscar por ID | `@smoke` |
| `GET /posts?userId=1` — filtrar por usuario | - |
| `POST /posts` — criar novo post | - |
| `PUT /posts/{id}` — atualizar post | - |
| `DELETE /posts/{id}` — deletar post | - |
| `GET /posts/9999` — recurso inexistente (404) | - |
| Validacao de contrato (JSON Schema) | `@smoke` |

**Total: 17 cenarios** (8 API + 5 Employee List + 4 Login)

---

## Tags

| Tag | Escopo | Comando |
|---|---|---|
| `@ui` | Cenarios Web (Chrome) | `mvn test -Dcucumber.filter.tags="@ui"` |
| `@api` | Cenarios de API | `mvn test -Dcucumber.filter.tags="@api"` |
| `@smoke` | Validacao rapida (5 cenarios) | `mvn test -Dcucumber.filter.tags="@smoke"` |
| sem filtro | Regressao completa (17 cenarios) | `mvn test` |

---

## Pre-requisitos locais

- **Java 8** e **Maven 3.9+** instalados
- **Google Chrome** instalado (qualquer versao recente)
- O **ChromeDriver** e resolvido automaticamente via WebDriverManager — nao precisa baixar manualmente
- Em CI (`CI=true`), o Chrome roda headless automaticamente
- Testes `@api` nao precisam de Chrome

---

## Como executar

```bash
mvn test                                        # todos os cenarios (17)
mvn test -Dcucumber.filter.tags="@api"          # so API (8)
mvn test -Dcucumber.filter.tags="@ui"           # so UI (9)
mvn test -Dcucumber.filter.tags="@smoke"        # smoke (5)
mvn test -Denvironment=hml                      # outro ambiente
```

Cenarios com falha sao reexecutados automaticamente uma vez antes de reportar falha definitiva — mitiga instabilidade transitoria dos servicos publicos (JSONPlaceholder, OrangeHRM demo).

---

## Relatorios

```bash
mvn allure:serve                                # abre Allure no navegador
mvn allure:report                               # gera em target/site/
```

| Tipo | Local |
|---|---|
| Cucumber HTML | `target/cucumber-reports/cucumber.html` |
| Allure Report | `mvn allure:serve` (interativo) |
| Log de execucao | `target/test-execution.log` |
| GitHub Pages | Automatico via CI (push para main) |

---

## Pipeline CI/CD

O GitHub Actions executa automaticamente em cada push para `main`/`develop` e em pull requests para `main`, em dois jobs separados (principio do menor privilegio):

**Job `testes`** (permissao read-only + `checks: write`)
1. Configura Java 8 e instala Chrome + ChromeDriver compativel
2. Executa `mvn test` com `CI=true` (headless automatico)
3. Publica resultados JUnit inline no GitHub e artefatos Allure

**Job `publicar-relatorio`** (permissao de escrita — so roda em push para `main`)
4. Gera e publica o Allure Report no GitHub Pages

Acoes de terceiros sao fixadas em versoes imutaveis (nunca `@master`), e o job de publicacao nunca roda em pull requests.

---

## Configuracao por ambiente

```bash
mvn test                       # usa dev.properties (padrao)
mvn test -Denvironment=hml     # usa hml.properties
```

Arquivos em `src/test/resources/environments/`. Qualquer chave pode ser sobrescrita por variavel de ambiente sem tocar nos arquivos: `senha.admin` vira `SENHA_ADMIN` (ver `ConfigReader`).

As credenciais no repositorio sao as credenciais publicas do [demo OrangeHRM](https://opensource-demo.orangehrmlive.com/) — nao sao segredos reais. Em um projeto real, use GitHub Secrets ou um gerenciador de segredos (Vault, AWS Secrets Manager).

---

## Validacao de contrato (JSON Schema)

```json
{
  "type": "object",
  "required": ["userId", "id", "title", "body"],
  "properties": {
    "userId": { "type": "integer", "minimum": 1 },
    "id": { "type": "integer", "minimum": 1 },
    "title": { "type": "string", "minLength": 1 },
    "body": { "type": "string", "minLength": 1 }
  },
  "additionalProperties": true
}
```

Se a API mudar a estrutura da resposta, o teste detecta a quebra de contrato imediatamente.

---

## Gerado com Kiro AI

Projeto criado com [Kiro](https://kiro.dev), ambiente de desenvolvimento com IA. Os custom agents (`qa-scaffold` e `framework-reviewer`) em `.kiro/agents/` demonstram como IA pode ser integrada ao fluxo de desenvolvimento de testes.
