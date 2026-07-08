# selenium-restassured-cucumber-kiro-github-actions

![CI](https://github.com/aline-nory/selenium-restassured-cucumber-kiro-github-actions/actions/workflows/testes.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-8-orange?logo=java)
![Maven](https://img.shields.io/badge/Maven-3.9-blue?logo=apachemaven)
![Selenium](https://img.shields.io/badge/Selenium-3.141-green?logo=selenium)
![Cucumber](https://img.shields.io/badge/Cucumber-7.18-brightgreen?logo=cucumber)
![REST Assured](https://img.shields.io/badge/REST_Assured-4.5-teal)
![Allure](https://img.shields.io/badge/Report-Allure-orange)
![Kiro AI](https://img.shields.io/badge/Generated%20with-Kiro%20AI-blueviolet)

Projeto de automacao de testes **Web (UI)** e **API REST** com pipeline CI/CD. Desenvolvido com Kiro AI como portfolio de QA.

---

## Destaques do projeto

- **BDD em portugues** com Cucumber — cenarios legiveis para qualquer pessoa do time
- **Page Object Pattern** com heranca (`BasePage`) — codigo reutilizavel e facil de manter
- **Testes de UI** com Selenium WebDriver — validacao de fluxos no navegador
- **Testes de API** com REST Assured — cobertura completa dos verbos HTTP (GET, POST, PUT, DELETE)
- **Validacao de contrato** com JSON Schema — garante que a API respeita o formato esperado
- **Templates JSON externalizados** — payloads de request em arquivos `.json` separados do codigo
- **Separacao por camadas** — pages, steps, services, support (padrao enterprise)
- **CI/CD com GitHub Actions** — testes executados automaticamente a cada push
- **Allure Report** — relatorio interativo com graficos, historico e screenshots
- **Screenshots automaticas** — capturadas em todos os cenarios de UI (sucesso e falha)
- **Configuracao por ambiente** — troca entre DEV/HQA sem alterar codigo
- **Deteccao automatica de CI** — modo headless ativado automaticamente em servidores

---

## Stack

| Tecnologia | Versao | Funcao |
|---|---|---|
| Java | 8 | Linguagem |
| Maven | 3.9 | Build e dependencias |
| Selenium WebDriver | 3.141.59 | Automacao de UI |
| Cucumber | 7.18 | BDD em portugues |
| JUnit | 4.13 | Runner e assertions |
| REST Assured | 4.5.1 | Automacao de API REST |
| Allure Report | 2.24 | Relatorio interativo |
| GitHub Actions | - | Pipeline CI/CD |

---

## Estrutura do projeto

```
src/test/java/
├── pages/                          # Page Objects
│   ├── BasePage.java               # Classe base (acoes comuns)
│   └── LoginPage.java             # PO da tela de login
├── services/                       # Servicos de API
│   └── PostService.java           # Chamadas do endpoint /posts
├── steps/                          # Step Definitions
│   ├── LoginSteps.java            # Steps de UI
│   └── ApiPostsSteps.java        # Steps de API
├── support/
│   ├── communication/             # Comunicacao HTTP
│   │   └── RestApi.java           # Wrapper REST Assured
│   ├── environment/               # Configuracoes de ambiente
│   │   └── Environment.java      # Leitura do config.properties
│   ├── helpers/                   # Utilitarios
│   │   ├── LogUtils.java         # Log formatado
│   │   └── JsonTemplate.java     # Leitura de templates JSON
│   ├── hooks/                     # Hooks do Cucumber
│   │   ├── WebHooks.java         # Ciclo de vida WebDriver (@ui)
│   │   └── ApiHooks.java         # Setup de API (@api)
│   ├── testEvidence/              # Evidencias de teste
│   │   └── ScreenshotUtils.java  # Captura de screenshots
│   └── webDriver/                 # Fabrica de drivers
│       └── DriverFactory.java    # Criacao Chrome/headless
└── Runner.java                    # Runner principal

src/test/resources/
├── features/
│   ├── login.feature              # Cenarios de UI
│   └── api_posts.feature          # Cenarios de API
├── schemas/
│   └── post-schema.json           # Contrato JSON da API
└── template.api/
    ├── POST_CriarPost.json        # Payload de criacao
    └── PUT_AtualizarPost.json     # Payload de atualizacao

config.properties                  # Configuracoes por ambiente (raiz)
```

---

## Tags

| Tag | Tipo | Como rodar |
|---|---|---|
| `@ui` | Cenarios Web (Chrome) | `mvn test -Dcucumber.filter.tags="@ui"` |
| `@api` | Cenarios de API | `mvn test -Dcucumber.filter.tags="@api"` |
| `@smoke` | Validacao rapida | `mvn test -Dcucumber.filter.tags="@smoke"` |
| sem filtro | Regressao completa | `mvn test` |

---

## Como executar

```bash
mvn test                                        # todos os cenarios
mvn test -Dcucumber.filter.tags="@api"          # so API
mvn test -Dcucumber.filter.tags="@ui"           # so UI
mvn test -Dcucumber.filter.tags="@smoke"        # smoke
mvn test -Denvironment=HQA                      # outro ambiente
```

## Relatorios

```bash
mvn allure:serve                                # abre Allure no navegador
mvn allure:report                               # gera em target/site/
```

| Tipo | Local |
|---|---|
| Cucumber HTML | `target/cucumber-reports/cucumber.html` |
| Allure Report | `mvn allure:serve` (interativo) |
| GitHub Pages | Automatico via CI |

---

## Pipeline CI/CD

O GitHub Actions executa automaticamente em cada push para `main` ou `develop`.

1. Configura Java 8, Chrome e ChromeDriver
2. Executa `mvn test` com `CI=true` (headless automatico)
3. Gera e publica Allure Report no GitHub Pages
4. Publica relatorio Cucumber como artefato
5. Exibe resultado JUnit inline no GitHub

---

## Evidencias

- Screenshots capturadas em **todos** os cenarios `@ui` (sucesso e falha)
- Embutidas no Allure Report e no `cucumber.html`
- Cenarios com sucesso: screenshot do estado final da tela
- Cenarios com falha: screenshot do momento do erro

---

## Validacao de contrato (JSON Schema)

O cenario "Validar contrato do post" garante que a API retorna a estrutura esperada:

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
  "additionalProperties": false
}
```

Se a API mudar a estrutura da resposta, o teste quebra imediatamente.

---

## Gerado com Kiro AI

Projeto criado com [Kiro](https://kiro.dev), ambiente de desenvolvimento com IA.
