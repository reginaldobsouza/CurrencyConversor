package model;

public class Conversao {
    private String moedaBase;
    private String moedaDestino;
    private double valorOriginal;
    private double valorConvertido;

    public Conversao(String moedaBase, String moedaDestino, double valorOriginal, double valorConvertido) {
        this.moedaBase = moedaBase;
        this.moedaDestino = moedaDestino;
        this.valorOriginal = valorOriginal;
        this.valorConvertido = valorConvertido;
    }

    public String getMoedaBase() {
        return moedaBase;
    }

    public String getMoedaDestino() {
        return moedaDestino;
    }

    public double getValorOriginal() {
        return valorOriginal;
    }

    public double getValorConvertido() {
        return valorConvertido;
    }

    @Override
    public String toString() {
        return String.format("Valor %.2f %s equivale a %.2f %s", valorOriginal, moedaBase, valorConvertido, moedaDestino);
    }
}
