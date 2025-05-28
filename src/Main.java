import model.Conversao;
import service.ConversorService;
// import utils.Formatter; // Descomente se for usar o Formatter.formatarConversao

import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) { // try-with-resources
            System.out.println("=== Conversor de Moedas ===");

            String moedaBase = lerCodigoMoeda(scanner, "base");
            String moedaDestino = lerCodigoMoeda(scanner, "destino");
            double valor = lerValor(scanner);

            System.out.println("\nProcessando conversão...");
            Optional<Conversao> conversaoOpt = ConversorService.converter(moedaBase, moedaDestino, valor);

            if (conversaoOpt.isPresent()) {
                Conversao conversaoRealizada = conversaoOpt.get();
                System.out.println("\n--- Resultado da Conversão ---");
                System.out.println(conversaoRealizada); // Usa o toString() de Conversao
                // Alternativa usando o Formatter:
                // System.out.println(Formatter.formatarConversao(
                // conversaoRealizada.getMoedaBase(),
                // conversaoRealizada.getMoedaDestino(),
                // conversaoRealizada.getValorOriginal(),
                // conversaoRealizada.getValorConvertido()
                // ));
                System.out.println("----------------------------");
            } else {
                System.out.println("\nFalha ao realizar a conversão.");
                System.out.println("Possíveis causas:");
                System.out.println("- Código de moeda inválido ou não suportado pela API.");
                System.out.println("- Problema de conexão com a internet ou com o serviço de taxas de câmbio.");
                System.out.println("- Valor inserido inválido.");
                System.out.println("Por favor, verifique os dados e tente novamente.");
            }
        } // Scanner é fechado automaticamente aqui
        System.out.println("\nObrigado por usar o Conversor de Moedas!");
    }

    private static String lerCodigoMoeda(Scanner scanner, String tipoMoeda) {
        String codigo;
        while (true) {
            System.out.printf("Digite a moeda %s (ex: USD, BRL, EUR, ARS, COP - 3 letras maiúsculas): ", tipoMoeda);
            codigo = scanner.next().trim().toUpperCase();
            if (codigo.matches("^[A-Z]{3}$")) {
                return codigo;
            } else {
                System.out.println("Código de moeda inválido. Por favor, insira 3 letras maiúsculas.");
            }
        }
    }

    private static double lerValor(Scanner scanner) {
        double valor;
        while (true) {
            System.out.print("Digite o valor a ser convertido (deve ser um número não negativo): ");
            try {
                valor = scanner.nextDouble();
                if (valor >= 0) {
                    return valor;
                } else {
                    System.out.println("O valor não pode ser negativo. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número (ex: 100 ou 123.45).");
                scanner.next(); // Limpa a entrada inválida do buffer do scanner
            }
        }
    }
}
