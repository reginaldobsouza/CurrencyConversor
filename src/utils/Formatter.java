package utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Formatter {
    // Usar um Locale específico para garantir consistência na formatação.
    // Locale.US usa '.' como separador decimal e ',' como agrupador.
    // new Locale("pt", "BR") usa ',' como separador decimal e '.' como agrupador.
    private static final DecimalFormatSymbols SYMBOLS = new DecimalFormatSymbols(new Locale("pt", "BR"));
    private static final DecimalFormat df = new DecimalFormat("#,##0.00", SYMBOLS);

    // Construtor privado para impedir a instanciação desta classe utilitária
    private Formatter() {
        throw new IllegalStateException("Classe utilitária não deve ser instanciada.");
    }

    public static String formatarValor(double valor) {
        return df.format(valor);
    }

    // Método alternativo para usar no toString de Conversao, se desejado
    public static String formatarConversao(String moedaBase, String moedaDestino, double valorOriginal, double valorConvertido) {
        return String.format("Valor %s %s equivale a %s %s",
                formatarValor(valorOriginal), moedaBase.toUpperCase(),
                formatarValor(valorConvertido), moedaDestino.toUpperCase());
    }
}