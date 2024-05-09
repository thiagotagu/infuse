# Aplicação para Avaliação Técnica

Esta aplicação é destinada à avaliação técnica e apresenta uma série de funcionalidades desenvolvidas utilizando as seguintes tecnologias:

- **Spring Boot**: Framework Java para construção de aplicações web e serviços RESTful de forma rápida e fácil.
- **Java 17**: Versão mais recente da linguagem de programação Java, trazendo melhorias de desempenho e novos recursos.
- **H2 ou MySQL**: Bancos de dados utilizados para persistência dos dados. Originalmente, a aplicação está configurada para utilizar o H2, mas pode ser facilmente alterada para o MySQL.
- **Swagger UI**: Interface interativa para documentação de APIs, permitindo visualizar e testar os endpoints diretamente no navegador.

### Instruções de Uso

1. **Configuração do Banco de Dados**:
   - No arquivo `application.yml`, é possível configurar o banco de dados entre H2 e MySQL. Basta comentar/descomentar as seções apropriadas conforme a sua escolha.

2. **Carga Automática de Dados**:
   - A aplicação realiza uma carga automática de dados toda vez que é iniciada. Isso inclui a criação de 10 clientes aleatórios e 10 produtos aleatórios.

3. **Script de Criação da Tabela e Carga Inicial**:
   - Na raiz do projeto, há um script chamado `scriptBase.sql`. Este script contém as instruções SQL para a criação da tabela e a carga inicial de dados. Ele pode ser utilizado para inicializar o banco de dados manualmente.

4. **Documentação da API com Swagger UI**:
   - Após iniciar a aplicação, você pode acessar a documentação da API em `http://localhost:8080/swagger-ui.html`. Lá, você encontrará uma interface interativa para explorar todos os endpoints disponíveis, bem como informações detalhadas sobre cada um deles.

### Como Executar

Para executar a aplicação, basta clonar este repositório e executar o comando Maven `mvn spring-boot:run`. Certifique-se de ter o JDK 17 e o Maven instalados em sua máquina. Após a execução bem-sucedida, você pode acessar a aplicação em `http://localhost:8080`.

### Observações

- Certifique-se de revisar e atualizar as configurações de banco de dados, especialmente as credenciais de acesso, conforme necessário para o ambiente em que a aplicação será executada.

- Este README fornece uma visão geral básica da aplicação. Para obter mais detalhes sobre a estrutura do código, os endpoints REST disponíveis e outras informações relevantes, consulte a documentação de referência.

Este README é apenas um guia inicial para ajudar na compreensão e utilização da aplicação. Sinta-se à vontade para atualizá-lo e adicioná-lo com mais informações conforme necessário.
