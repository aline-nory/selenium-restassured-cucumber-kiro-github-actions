# language: pt
Funcionalidade: Login no sistema
  Como um usuário registrado
  Quero fazer login na aplicação
  Para acessar as funcionalidades do sistema

  Contexto:
    Dado que estou na página de login

  Cenário: Login com credenciais válidas
    Quando preencho o campo "username" com "admin"
    E preencho o campo "password" com "admin123"
    E clico no botão de login
    Então devo ser redirecionado para a página inicial

  Cenário: Login com senha incorreta
    Quando preencho o campo "username" com "admin"
    E preencho o campo "password" com "senhaErrada"
    E clico no botão de login
    Então devo ver a mensagem de erro "Invalid credentials"

  Esquema do Cenário: Login com múltiplas credenciais inválidas
    Quando preencho o campo "username" com "<usuario>"
    E preencho o campo "password" com "<senha>"
    E clico no botão de login
    Então devo ver a mensagem de erro "<mensagem>"

    Exemplos:
      | usuario       | senha      | mensagem             |
      | usuarioErrado | admin123   | Invalid credentials  |
      | wronguser     | wrongpass  | Invalid credentials  |
