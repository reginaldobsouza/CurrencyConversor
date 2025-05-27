ppackage api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Scanner;

public class APIClient {
    private static final String API_URL_BASE = "https://api.exchangerate-api.com/v4/latest/";
    // Considere adicionar um logger em um projeto maior:
    // private static final Logger logger = Logger.getLogger(APIClient.class.getName());

    public static Optional<JSONObject> obterTaxaCambio(String moedaBase) {
        if (moedaBase == null || moedaBase.trim().isEmpty()) {
            System.err.println("Erro: Moeda base não pode ser nula ou vazia.");
            // logger.warning("Moeda base nula ou vazia fornecida.");
            return Optional.empty();
        }

        HttpURLConnection conn = null;
        try {
            URL url = new URL(API_URL_BASE + moedaBase.toUpperCase());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // Timeout de conexão em milissegundos
            conn.setReadTimeout(5000);    // Timeout de leitura em milissegundos
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) { // HTTP_OK é 200
                System.err.println("Falha na conexão com a API: Código HTTP " + responseCode + " para " + url);
                // logger.severe("Falha na conexão com a API: Código HTTP " + responseCode + " para " + url);
                return Optional.empty();
            }

            try (InputStream inputStream = conn.getInputStream();
                 Scanner scanner = new Scanner(inputStream, "UTF-8")) { // Especificar UTF-8 é uma boa prática
                scanner.useDelimiter("\\A"); // Lê todo o stream de uma vez
                if (scanner.hasNext()) {
                    String responseBody = scanner.next();
                    return Optional.of(new JSONObject(responseBody));
                } else {
                    System.err.println("Resposta da API vazia para " + url);
                    // logger.warning("Resposta da API vazia para " + url);
                    return Optional.empty();
                }
            }

        } catch (MalformedURLException e) {
            System.err.println("Erro ao obter taxas de câmbio: URL mal formada - " + e.getMessage());
            // logger.log(Level.SEVERE, "URL mal formada: " + API_URL_BASE + moedaBase, e);
            return Optional.empty();
        } catch (IOException e) {
            System.err.println("Erro de I/O ao obter taxas de câmbio: " + e.getMessage());
            // logger.log(Level.SEVERE, "Erro de I/O ao comunicar com a API", e);
            return Optional.empty();
        } catch (JSONException e) {
            System.err.println("Erro ao parsear JSON da API: " + e.getMessage());
            // logger.log(Level.SEVERE, "Erro ao parsear JSON da API", e);
            return Optional.empty();
        } catch (Exception e) { // Captura genérica para erros inesperados
            System.err.println("Erro inesperado ao obter taxas de câmbio: " + e.getMessage());
            // logger.log(Level.SEVERE, "Erro inesperado ao obter taxas de câmbio", e);
            return Optional.empty();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}