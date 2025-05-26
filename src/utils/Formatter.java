package utils;

import java.text.DecimalFormat;

public class Formatter {
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");

    public static String formatarValor(double valor) {
        return df.format(valor);
    }
}