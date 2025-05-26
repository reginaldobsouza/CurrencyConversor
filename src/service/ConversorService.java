package service;

import api.APIClient;
import model.Conversao;
import org.json.JSONObject;

public class ConversorService {
    public static Conversao converter(String moedaBase, String moedaDestino, double valor) {
        JSONObject dadosCambio = APIClient.obterTaxaCambio(moedaBase);

        if (dadosCambio != null && dadosCambio.has("rates")) {
            double taxa = dadosCambio.getJSONObject("rates").optDouble(moedaDestino, -1);
            if (taxa > 0) {
                double valorConvertido = valor * taxa;
                return new Conversao(moedaBase, moedaDestino, valor, valorConvertido);
            }
        }
        System.out.println("Erro: Moeda não encontrada.");
        return null;
    }
}
