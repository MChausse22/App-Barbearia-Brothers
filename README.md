<h1 align="center">Aplicativo Barbearia Brothers</h1>

## Descrição
O projeto foi desenvolvido para a disciplina de Engenharia de Software III durante o curso de Análise e Desenvolvimento de Sistemas na FATEC-SP, com o objetivo de informatizar o processo de agendamento de uma barbearia real.

Para isso, desenvolvemos um aplicativo para a plataforma Android e utilizamos ferramentas do Firebase para o banco de dados.

## Tabela de conteúdos
* [Features](#features)
* [Instruções de uso](#instruções-de-uso)
  * [Cadastro de usuário](#cadastro-de-usuário)
  * [Módulo Cliente](#módulo-cliente)
    * [Consulta de agendamentos](#consulta-de-agendamentos)
    * [Novo Agendamento](#novo-agendamento)
    * [Cancelar Agendamento](#cancelar-agendamento)
    * [Sign out](#sign-out)
  * [Módulo Barbeiro](#módulo-barbeiro)
    * [Consulta de agendamentos](#consulta-de-agendamentos-1)
    * [Finalizar atendimento](#finalizar-atendimento)
  * [Pré-requisitos](#pré-requisitos)
  * [Tecnologias](#tecnologias)
<!--Autor-->    

## Features
- Autenticação de usuário pelo FirebaseAuth
- Cadastro de usuário cliente
- Realizar novo agendamento
- Consultar agendamentos existentes

## Instruções de uso
O aplicativo contém 2 módulos. No módulo cliente, o usuário consulta ou realiza o agendamento dos serviços disponíveis.

Já no módulo barbeiro, o usuário consulta os serviços agendados para o barbeiro logado e pode finalizar os agendamentos.
- ### Cadastro de usuário
  #### Campos
  - **nome:** Campo não-nulo.
  - **e-mail:** Este campo deve ser preenchido no padrão de e-mail. ***Ex.:*** *example@gmail.com* | Campo não-nulo | Utilizado para autenticação
  - **password:** Este campo deve ser preenchido com no mínimo 6 caracteres | Utilizado para autenticação
  Caso o cadastro tenha sido bem-sucedido, o aplicativo entra na Home do módulo Cliente
- ### Módulo Cliente
  #### Consulta de agendamentos
  Na tela inicial está presente a consulta dos agendamentos realizados, onde o usuário pode filtrá-los de acordo com seu status: Confirmado, Finalizado e Cancelado.
  Os agendamentos são exibidos em ordem ascendente de data.
  #### Novo Agendamento
  Para realizar um novo agendamento, basta clicar no botão **"+"** que direciona para a tela de seleção de serviços.
  No momento, só é possível selecionar um serviço por vez.

  Feita a seleção, o sistema redireciona para o calendário. Nessa tela, indique o barbeiro e clique em uma data, assim os horários disponíveis serão exibidos abaixo do calendário.
  Toda vez que um barbeiro for alterado, deve-se clicar na data para atualizar a grade de horários.
  Após a confirmação do agendamento, o aplicativo redireciona o usuário para a Home.

  #### Cancelar Agendamento
  Para cancelar um agendamento, aperte no ícone de **"X"**.
  Para atualizar a página, selecione um filtro

  #### Sign out
  Clique no ícone localizado no canto superior esquerdo para desconectar-se.
  Se o usuário não for desconectado, continuará logado mesmo após o reinício do aplicativo.
- ### Módulo Barbeiro
  #### Consulta de agendamentos
  Na tela inicial está presente a consulta dos agendamentos feitos para o barbeiro logado. É possível filtrar de acordo com seu status: Confirmado, Finalizado e Cancelado.
  #### Finalizar atendimento
  Para marcar como finalizado, clique no ícone inserido no card
  Para atualizar a página, selecione um filtro

## Pré-requisitos
- Android 8.1 (Oreo)
- Apk para instalação está salvo neste repositório como ***app-beta.apk***

## Tecnologias
As seguintes ferramentas foram usadas na construção do projeto:
- [Android Studio](https://developer.android.com/studio)
- [Java](https://www.java.com/en/)
- [Firebase](https://firebase.google.com/)
