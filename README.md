# CurrencyConversor

Conversor de moedas simples em Java, utilizando consumo de API para obter taxas de câmbio em tempo real.

## Funcionalidades

- Conversão entre diferentes moedas utilizando taxas atualizadas.
- Consumo da API [ExchangeRate-API](https://www.exchangerate-api.com/).
- Interface de linha de comando simples.

## Pré-requisitos

- Java 8 ou superior
- [Maven](https://maven.apache.org/) instalado

## Como executar

1. Clone o repositório ou baixe os arquivos.
2. Compile o projeto com Maven:

   ```sh
   mvn clean compile
   ```

3. Execute o programa principal:

   ```sh
   mvn exec:java -Dexec.mainClass=Main -Dexec.classpathScope=compile
   ```

   > **Obs:** O arquivo `Main.java` está no pacote default (`src\Main.java`). Se você mover para um pacote, ajuste o parâmetro `-Dexec.mainClass` conforme o novo pacote.

## Estrutura do Projeto

```
CurrencyConversor/
 ├── pom.xml
 ├── src/
 │    ├── api/
 │    │    └── APIClient.java
 │    ├── model/
 │    │    ├── Conversao.java
 │    │    └── Moeda.java
 │    ├── service/
 │    │    └── ConversorService.java
 │    ├── utils/
 │    │    └── Formatter.java
 │    └── Main.java
```

## Exemplo de uso

```
=== Conversor de Moedas ===
Digite a moeda base (ex: USD): USD
Digite a moeda destino (ex: BRL): BRL
Digite o valor a ser convertido: 100
Valor 100.00 USD equivale a 500.00 BRL
```

## Observações

- Certifique-se de estar conectado à internet para que a API funcione corretamente.
- Utilize códigos de moeda válidos (ex: USD, BRL, EUR).

## Licença

MIT
