package api;

import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class APIClient {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static JSONObject obterTaxaCambio(String moedaBase) {
        try {
            URL url = new URL(API_URL + moedaBase);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Falha na conexão: Código HTTP " + conn.getResponseCode());
            }

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            return new JSONObject(response.toString());
        } catch (Exception e) {
            System.out.println("Erro ao obter taxas de câmbio: " + e.getMessage());
            return null;
        }
    }
}
