package service;

import api.APIClient;
import model.Conversao;
import org.json.JSONObject;

import java.util.Optional;

public class ConversorService {

    public static Optional<Conversao> converter(String moedaBase, String moedaDestino, double valor) {
        // Validação básica dos parâmetros
        if (moedaBase == null || moedaBase.trim().isEmpty() ||
                moedaDestino == null || moedaDestino.trim().isEmpty()) {
            System.err.println("Erro de serviço: Moeda base e/ou destino não podem ser nulas ou vazias.");
            return Optional.empty();
        }
        if (valor < 0) {
            System.err.println("Erro de serviço: Valor para conversão não pode ser negativo.");
            return Optional.empty();
        }

        String baseUpper = moedaBase.trim().toUpperCase();
        String destinoUpper = moedaDestino.trim().toUpperCase();

        // Se as moedas forem iguais, a taxa é 1 e o valor convertido é o mesmo.
        if (baseUpper.equals(destinoUpper)) {
            return Optional.of(new Conversao(baseUpper, destinoUpper, valor, valor));
        }

        Optional<JSONObject> dadosCambioOpt = APIClient.obterTaxaCambio(baseUpper);

        // Usando flatMap para processar o Optional retornado pela API
        return dadosCambioOpt.flatMap(dadosCambio -> {
            if (!dadosCambio.has("rates")) {
                System.err.println("Erro de serviço: Resposta da API não contém o objeto 'rates'.");
                return Optional.empty();
            }
            JSONObject rates = dadosCambio.getJSONObject("rates");

            if (!rates.has(destinoUpper)) {
                System.err.println("Erro de serviço: Moeda destino '" + destinoUpper + "' não encontrada nas taxas da API para a base '" + baseUpper + "'.");
                return Optional.empty();
            }

            // optDouble retorna 0.0 se a chave não existir ou não for um número,
            // mas já verificamos a existência da chave.
            // O segundo argumento é o valor padrão se a chave não existir ou não for conversível.
            double taxa = rates.optDouble(destinoUpper, -1.0);

            if (taxa <= 0) { // Taxas de câmbio devem ser positivas
                System.err.println("Erro de serviço: Taxa de câmbio inválida (<= 0) para '" + destinoUpper + "'.");
                return Optional.empty();
            }

            double valorConvertido = valor * taxa;
            return Optional.of(new Conversao(baseUpper, destinoUpper, valor, valorConvertido));
        });
        // Se dadosCambioOpt for empty(), o flatMap não é executado e um Optional.empty() é retornado.
    }
}