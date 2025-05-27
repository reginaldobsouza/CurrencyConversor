package model;

import java.util.Objects;

/**
 * Representa o resultado de uma operação de conversão de moeda.
 * Esta classe é imutável, garantindo que seus valores não sejam alterados após a criação.
 */
public class Conversao {
    private final String moedaBase;
    private final String moedaDestino;
    private final double valorOriginal;
    private final double valorConvertido;

    /**
     * Constrói uma nova instância de Conversao.
     *
     * @param moedaBase       O código da moeda de origem (ex: "USD"). Não pode ser nulo.
     * @param moedaDestino    O código da moeda de destino (ex: "BRL"). Não pode ser nulo.
     * @param valorOriginal   O valor original na moeda base. Deve ser não negativo.
     * @param valorConvertido O valor resultante da conversão na moeda destino.
     * @throws NullPointerException se {@code moedaBase} ou {@code moedaDestino} forem nulos.
     * @throws IllegalArgumentException se {@code valorOriginal} for negativo.
     */
    public Conversao(String moedaBase, String moedaDestino, double valorOriginal, double valorConvertido) {
        this.moedaBase = Objects.requireNonNull(moedaBase, "Moeda base não pode ser nula.").toUpperCase();
        this.moedaDestino = Objects.requireNonNull(moedaDestino, "Moeda destino não pode ser nula.").toUpperCase();

        if (valorOriginal < 0) {
            throw new IllegalArgumentException("Valor original não pode ser negativo.");
        }
        this.valorOriginal = valorOriginal;
        this.valorConvertido = valorConvertido;
    }

    /**
     * Retorna o código da moeda base.
     * @return O código da moeda base.
     */
    public String getMoedaBase() {
        return moedaBase;
    }

    /**
     * Retorna o código da moeda destino.
     * @return O código da moeda destino.
     */
    public String getMoedaDestino() {
        return moedaDestino;
    }

    /**
     * Retorna o valor original que foi convertido.
     * @return O valor original.
     */
    public double getValorOriginal() {
        return valorOriginal;
    }

    /**
     * Retorna o valor convertido para a moeda destino.
     * @return O valor convertido.
     */
    public double getValorConvertido() {
        return valorConvertido;
    }

    /**
     * Retorna uma representação em String do objeto Conversao,
     * formatada para fácil leitura.
     * Exemplo: "Valor 100.00 USD equivale a 500.00 BRL"
     * @return Uma string formatada representando a conversão.
     */
    @Override
    public String toString() {
        // A formatação de valores com duas casas decimais é feita diretamente aqui.
        return String.format("Valor %.2f %s equivale a %.2f %s",
                valorOriginal, moedaBase,
                valorConvertido, moedaDestino);
    }

    /**
     * Compara este objeto Conversao com outro objeto para igualdade.
     * Duas conversões são consideradas iguais se todos os seus campos (moedaBase, moedaDestino,
     * valorOriginal, valorConvertido) forem iguais.
     *
     * @param o O objeto a ser comparado.
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversao conversao = (Conversao) o;
        return Double.compare(conversao.valorOriginal, valorOriginal) == 0 &&
                Double.compare(conversao.valorConvertido, valorConvertido) == 0 &&
                Objects.equals(moedaBase, conversao.moedaBase) &&
                Objects.equals(moedaDestino, conversao.moedaDestino);
    }

    /**
     * Retorna um código hash para este objeto Conversao.
     * O código hash é baseado em todos os campos da conversão.
     *
     * @return O código hash do objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(moedaBase, moedaDestino, valorOriginal, valorConvertido);
    }
}