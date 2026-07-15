# language: pt
@ui
Funcionalidade: Lista de Empregados
  Como um administrador do sistema
  Quero visualizar e buscar empregados na lista
  Para gerenciar os registros de funcionários

  Contexto:
    Dado que estou logado como administrador
    E estou na página de lista de empregados

  @smoke
  Cenário: Visualizar tabela de empregados
    Então devo ver a tabela de empregados com resultados

  Cenário: Buscar empregado por nome existente
    Quando busco pelo nome do empregado ""
    E clico no botão de busca
    Então devo ver a tabela de empregados com resultados

  Cenário: Buscar empregado inexistente
    Quando busco pelo nome do empregado "UsuarioQueNaoExiste999"
    E clico no botão de busca
    Então devo ver a mensagem "No Records Found"

  Cenário: Navegar para adicionar novo empregado
    Quando clico no botão de adicionar empregado
    Então devo ser redirecionado para a página de adicionar empregado

  Cenário: Editar empregado da lista
    Quando clico no botão de editar do primeiro empregado
    Então devo ser redirecionado para a página de detalhes do empregado
