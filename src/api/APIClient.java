package api; // Correção do typo aqui


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Scanner;
// import java.util.logging.Level; // Descomente se for usar um Logger mais robusto
// import java.util.logging.Logger; // Descomente se for usar um Logger mais robusto

/**
 * Cliente responsável por realizar a comunicação com a API externa
 * para obter as taxas de câmbio.
 */
public class APIClient { // Estrutura correta como uma classe
    private static final String API_URL_BASE = "https://api.exchangerate-api.com/v4/latest/";
    // Considere adicionar um logger mais robusto (como SLF4J + Logback) em um projeto maior.
    // private static final Logger logger = Logger.getLogger(APIClient.class.getName());

    /**
     * Obtém as taxas de câmbio da API para uma determinada moeda base.
     *
     * @param moedaBase O código da moeda base (ex: "USD", "BRL").
     * @return Um {@link Optional} contendo o {@link JSONObject} com os dados da API em caso de sucesso,
     *         ou um {@link Optional#empty()} em caso de falha na comunicação, erro de parsing,
     *         ou se a moeda base for inválida.
     */
    public static Optional<JSONObject> obterTaxaCambio(String moedaBase) {
        if (moedaBase == null || moedaBase.trim().isEmpty()) {
            System.err.println("Erro [APIClient]: Moeda base não pode ser nula ou vazia.");
            // logger.warning("Moeda base nula ou vazia fornecida.");
            return Optional.empty();
        }

        HttpURLConnection conn = null;
        String urlString = API_URL_BASE + moedaBase.toUpperCase(); // Para usar na mensagem de erro e logging
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // Timeout de conexão em milissegundos (5 segundos)
            conn.setReadTimeout(5000);    // Timeout de leitura em milissegundos (5 segundos)
            conn.connect(); // Realiza a conexão de fato

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) { // HTTP_OK é 200, indica sucesso
                System.err.println("Falha na conexão com a API: Código HTTP " + responseCode + " para " + urlString);
                // logger.severe("Falha na conexão com a API: Código HTTP " + responseCode + " para " + urlString);
                return Optional.empty();
            }

            // Usa try-with-resources para garantir o fechamento do InputStream e do Scanner
            try (InputStream inputStream = conn.getInputStream();
                 Scanner scanner = new Scanner(inputStream, "UTF-8")) { // Especificar UTF-8 é uma boa prática para encoding
                // Configura o Scanner para ler todo o conteúdo do stream de uma vez
                scanner.useDelimiter("\\A");
                if (scanner.hasNext()) {
                    String responseBody = scanner.next();
                    return Optional.of(new JSONObject(responseBody));
                } else {
                    System.err.println("Resposta da API vazia para " + urlString);
                    // logger.warning("Resposta da API vazia para " + urlString);
                    return Optional.empty();
                }
            }

        } catch (MalformedURLException e) {
            System.err.println("Erro ao obter taxas de câmbio: URL mal formada - " + urlString + " (" + e.getMessage() + ")");
            // logger.log(Level.SEVERE, "URL mal formada: " + urlString, e);
            return Optional.empty();
        } catch (IOException e) {
            // Erros de I/O podem ocorrer durante a conexão, envio da requisição ou leitura da resposta
            System.err.println("Erro de I/O ao obter taxas de câmbio ("+ urlString +"): " + e.getMessage());
            // logger.log(Level.SEVERE, "Erro de I/O ao comunicar com a API para " + urlString, e);
            return Optional.empty();
        } catch (JSONException e) {
            // Erro ao tentar parsear a resposta da API que não está no formato JSON esperado
            System.err.println("Erro ao parsear JSON da API ("+ urlString +"): " + e.getMessage());
            // logger.log(Level.SEVERE, "Erro ao parsear JSON da API para " + urlString, e);
            return Optional.empty();
        } catch (Exception e) { // Captura genérica para erros inesperados não previstos acima
            System.err.println("Erro inesperado ao obter taxas de câmbio ("+ urlString +"): " + e.getMessage());
            // logger.log(Level.SEVERE, "Erro inesperado ao obter taxas de câmbio para " + urlString, e);
            return Optional.empty();
        } finally {
            if (conn != null) {
                conn.disconnect(); // Libera os recursos da conexão HTTP
            }
        }
    }
}