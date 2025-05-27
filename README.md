# CurrencyConversor

Conversor de moedas em Java que utiliza uma API externa para obter taxas de câmbio em tempo real. O projeto é estruturado com Maven e inclui testes automatizados.

## Estrutura do Código

O projeto segue uma estrutura modular, separando responsabilidades em diferentes pacotes:

```
src/
 ├── api/           # Comunicação com APIs externas (ex: APIClient.java)
 ├── model/         # Modelos de domínio (ex: Conversao.java, Moeda.java)
 ├── service/       # Lógica de negócio (ex: ConversorService.java)
 ├── utils/         # Utilitários e helpers (ex: Formatter.java)
 ├── Main.java      # Classe principal (ponto de entrada CLI)
 └── test/          # Testes automatizados (ex: ConversorServiceTest.java)
```

- **api/**: Responsável por consumir APIs externas de câmbio.
- **model/**: Contém as classes que representam os dados do domínio.
- **service/**: Implementa a lógica de conversão de moedas.
- **utils/**: Funções utilitárias para formatação e outras operações auxiliares.
- **Main.java**: Interface de linha de comando para interação com o usuário.
- **test/**: Testes unitários utilizando JUnit e Mockito.

## Funcionalidades

- Conversão entre moedas com taxas atualizadas via [ExchangeRate-API](https://www.exchangerate-api.com/).
- Interface de linha de comando (CLI) interativa.
- Validação de códigos de moeda (3 letras maiúsculas) e valores não negativos.
- Tratamento robusto de erros e mensagens informativas.
- Saída formatada no padrão brasileiro.
- Testes unitários com JUnit 5 e Mockito.

## Pré-requisitos

- Java 17 ou superior.
- Maven instalado e configurado no PATH.

## Instalação e Execução

1. **Clone o repositório ou baixe os arquivos do projeto.**
2. **Acesse o diretório do projeto:**
   ```sh
   cd CurrencyConversor
   ```
3. **Compile o projeto:**
   ```sh
   mvn clean compile
   ```
4. **Execute os testes (opcional, mas recomendado):**
   ```sh
   mvn test
   ```
5. **Execute o programa:**
   ```sh
   mvn exec:java -Dexec.mainClass=Main
   ```
   > Caso o comando acima não funcione, verifique se o plugin `exec-maven-plugin` está configurado no `pom.xml` ou execute manualmente o JAR gerado.

## Observações

- É necessário conexão com a internet para obter as taxas de câmbio.
- Utilize códigos de moeda válidos (ex: USD, BRL, EUR, JPY).
- Mensagens de erro são exibidas no console para facilitar a depuração.

## Licença

MIT License. Consulte o arquivo LICENSE para mais detalhes.
