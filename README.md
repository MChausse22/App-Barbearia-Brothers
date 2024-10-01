<h1 align="center">Aplicativo Barbearia Brothers</h1>

## Descrição
O projeto foi desenvolvido para a disciplina de Engenharia de Software III durante o curso de Análise e Desenvolvimento de Sistemas na FATEC-SP, com o objetivo de informatizar o processo de agendamento de uma barbearia real.
Para isso, desenvolvemos um aplicativo para a plataforma Android. Seu banco de dados foi desenvolvido utilizando a ferramenta Firebase.

## Tabela de conteúdos

## Features
- Autenticação de usuário pelo FirebaseAuth
- Cadastro de usuário cliente
- Realizar novo agendamento
- Consultar agendamentos existentes

## Instruções de uso
O aplicativo contém 2 módulos. No módulo cliente, o usuário consulta ou realiza o agendamento dos serviços disponíveis.
Já no módulo barbeiro, o usuário consulta os serviços agendados para o barbeiro logado
- ### Cadastro de usuário
  #### Campos
  - **nome:** Campo não-nulo.
  - **e-mail:** Este campo deve ser preenchido no padrão de e-mail. ***Ex.:*** *example@gmail.com* | Campo não-nulo | Utilizado para autenticação
  - **password:** Este campo deve ser preenchido com no mínimo 6 caracteres | Utilizado para autenticação
