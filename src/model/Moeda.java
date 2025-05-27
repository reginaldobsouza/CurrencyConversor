package model;

import java.util.Objects;

public class Moeda {
    private final String codigo; // Imutável

    public Moeda(String codigo) {
        Objects.requireNonNull(codigo, "Código da moeda não pode ser nulo.");
        if (!codigo.matches("^[A-Z]{3}$")) {
            throw new IllegalArgumentException("Código da moeda inválido. Deve conter 3 letras maiúsculas (ex: USD, BRL).");
        }
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Moeda moeda = (Moeda) o;
        return codigo.equals(moeda.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo; // Simplesmente retorna o código para representação em string
    }
}