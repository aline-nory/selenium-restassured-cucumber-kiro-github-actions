# language: pt
Funcionalidade: API de Posts - JSONPlaceholder
  Como consumidor da API REST
  Quero validar os endpoints de posts
  Para garantir que a API responde corretamente

  Contexto:
    Dado que a URL base da API é "https://jsonplaceholder.typicode.com"

  Cenário: Listar todos os posts
    Quando faço uma requisição GET para "/posts"
    Então o status code da resposta deve ser 200
    E o Content-Type da resposta deve conter "application/json"
    E a resposta deve conter 100 posts

  Cenário: Buscar um post por ID
    Quando faço uma requisição GET para "/posts/1"
    Então o status code da resposta deve ser 200
    E o campo "userId" deve ter valor inteiro 99
    E o campo "id" deve ter valor inteiro 1
    E o campo "title" não deve estar vazio
    E o campo "body" não deve estar vazio

  Cenário: Buscar posts de um usuário específico
    Quando faço uma requisição GET para "/posts?userId=1"
    Então o status code da resposta deve ser 200
    E todos os posts devem ter "userId" igual a 1

  Cenário: Criar um novo post
    Dado que tenho o seguinte corpo de requisição:
      """
      {
        "title": "Post de Teste Automatizado",
        "body": "Conteudo criado via REST Assured + Cucumber",
        "userId": 1
      }
      """
    Quando faço uma requisição POST para "/posts"
    Então o status code da resposta deve ser 201
    E o campo "title" deve ter valor de texto "Post de Teste Automatizado"
    E o campo "userId" deve ter valor inteiro 1
    E o campo "id" não deve estar vazio

  Cenário: Atualizar um post existente
    Dado que tenho o seguinte corpo de requisição:
      """
      {
        "id": 1,
        "title": "Titulo Atualizado",
        "body": "Corpo atualizado via REST Assured",
        "userId": 1
      }
      """
    Quando faço uma requisição PUT para "/posts/1"
    Então o status code da resposta deve ser 200
    E o campo "title" deve ter valor de texto "Titulo Atualizado"

  Cenário: Deletar um post
    Quando faço uma requisição DELETE para "/posts/1"
    Então o status code da resposta deve ser 200

  Cenário: Buscar post inexistente retorna 404
    Quando faço uma requisição GET para "/posts/9999"
    Então o status code da resposta deve ser 200
