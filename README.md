# AvaliacaoAgendamentoFinanceiro

# Sistema de Agendamento de Transferências Financeiras

## Visão Geral
Aplicação para solucionar o teste de agendamento de transferências financeiras, permitindo ao usuário calcular taxas e visualizar um extrato das transferências agendadas. Desenvolvido com Spring Boot e Vue.js para acesso web via desktops, também apresenta potencial para ser acessado em dispositivos variados, pois foi construído visando não só os padrões de desenvolvimento mas também a responsividade.

## Tecnologias Utilizadas
- **Back-end:** Spring Boot
- **Front-end:** Vue.js 
- **Banco de dados:** MySQL

## Configuração e Execução
Passos para a execução da aplicação
1. Clonar o repositório.
2. Configurar o `application.properties` do back-end com os dados de conexão ao banco.
3. Executar o back-end com `mvn spring-boot:run`.
4. Iniciar o front-end com `npm install` e `npm run serve`.
5. Acessar `http://localhost:8080`.

## Responsividade
A aplicação foi construída com um layout arrojado e facilmente adaptável. Pode ser acessada por dispositivos como Galaxy Ultra, iPhone 12 Pro Max e desktops via navegador web, com potencial para aprimoramento em adaptatividade, seguindo as normas SPA.

## Banco de Dados
O script de criação do banco de dados está disponível, facilitando a configuração inicial.

#Melhorias: 
Criar cadastro de entidade com faixas de dias onde os dados de taxa podem ser configuráveis
