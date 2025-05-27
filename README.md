# CurrencyConversor

Conversor de moedas simples em Java, utilizando consumo de API para obter taxas de câmbio em tempo real. O projeto é construído com Maven, configurado para Java 17, e inclui testes unitários para garantir a qualidade do serviço de conversão.

## Funcionalidades

-   Conversão entre diferentes moedas utilizando taxas atualizadas.
-   Consumo da API [ExchangeRate-API](https://www.exchangerate-api.com/) para buscar as taxas.
-   Interface de linha de comando (CLI) interativa e amigável.
-   Validação de entrada robusta para códigos de moeda (formato de 3 letras maiúsculas) e valor a ser convertido (não negativo).
-   Tratamento de erros aprimorado utilizando `Optional` para lidar com falhas na API, moedas não encontradas ou dados inválidos.
-   Formatação de saída dos valores monetários para o padrão brasileiro (ex: 1.234,56).
-   Testes unitários abrangentes com JUnit 5 e Mockito para o serviço de conversão.
-   Configurado para compilar e rodar com Java 17 (ou superior).

## Pré-requisitos

-   Java 17 ou superior (o projeto está configurado para Java 17 no `pom.xml`).
-   Maven instalado e configurado no PATH do sistema.

## Como executar

1.  **Clone o repositório ou baixe os arquivos do projeto.**

2.  **Navegue até o diretório raiz do projeto** (onde o arquivo `pom.xml` está localizado) pelo seu terminal.

3.  **Compile o projeto com Maven:**
    Este comando irá baixar as dependências e compilar o código-fonte.

4.  **Execute os testes unitários (recomendado):**

5.  **Execute o programa principal:**
    O `Main.java` está no pacote default.

(Mensagens de erro específicas podem aparecer no console via System.err dependendo da natureza da falha no serviço ou API).


Observações

-   •É necessário ter uma conexão ativa com a internet para que a API de taxas de câmbio funcione corretamente.
-   •Utilize códigos de moeda válidos e reconhecidos internacionalmente (ex: USD, BRL, EUR, JPY, GBP). A API pode não suportar todas as moedas existentes.
-   •O projeto utiliza System.err para logar mensagens de erro internas das classes APIClient e ConversorService, o que pode ser útil para depuração.
-
- Licença
- 
- MIT   

