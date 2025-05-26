// Remova ou ajuste o package para 'main' se desejar.
// package main;

import model.Conversao;
import service.ConversorService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Conversor de Moedas ===");
        System.out.print("Digite a moeda base (ex: USD): ");
        String moedaBase = scanner.next().toUpperCase();

        System.out.print("Digite a moeda destino (ex: BRL): ");
        String moedaDestino = scanner.next().toUpperCase();

        System.out.print("Digite o valor a ser convertido: ");
        double valor = scanner.nextDouble();

        Conversao conversao = ConversorService.converter(moedaBase, moedaDestino, valor);

        if (conversao != null) {
            System.out.println(conversao);
        } else {
            System.out.println("Erro ao obter a taxa de câmbio. Verifique os códigos de moeda.");
        }

        scanner.close();
    }
}
