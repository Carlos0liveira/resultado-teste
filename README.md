Resultado Avaliação Back-End Elotech

Link da Aplicação no Heroku: https://resultado-teste.herokuapp.com/api/pessoas

Endpoints de Pessoa -> "api/pessoas"
  @PostMapping("/salvarpessoa")
    Requer um JSON com as seguintes informações 
      {
        Nome : String
        cpf : String
        DataNascimento : "DD/MM/YYY"
      }
      
  @PutMapping("{id}") 
    Para atualizar o cadastro de uma Pessoa
      Requer um JSON com as informações do cadastro de pessoa e tambem é necessário informar o id da pessoa via PathVariable
      
  @DeleteMapping("{id}")
    Exclui o cadastro de Pessoa da base de dados
      Requer apenas o id da Pessoa via PathVariable
      
  @GetMapping("{id}")
    Busca uma pessoa por por id atraves de PathVariable
      
  @GetMapping
    Retorna uma lista de Pessoas conforme filtros informados por RequestParam sendo eles cpf, nome ou vazio
   
Endpoints de Processos -> "api/processos"
  @PostMapping
      Salva um nomo processo e requer um JSON com as seguintes informações 
        {
          "numero": Long,
          "ano": integer,
          "pessoa": idPessoa
        }
      
  @PutMapping("{id}")
      Para atualizar o cadastro de um Processo
      Requer um JSON com as informações do cadastro de Processo e tambem é necessário informar o id do Processo via PathVariable

  @DeleteMapping("{id}")
      Exclui o cadastro de Processo da base de dados
       Requer apenas o id do Processo via PathVariable
      
  GetMapping("{id}")
    Busca Processo por por id atraves de PathVariable
      
  @GetMapping
    Retorna uma lista de Processos conforme filtros informados por RequestParam sendo eles numero, ano ou vazio  
      
      
      
      
      
      
      
      
      
      
      
      
      
