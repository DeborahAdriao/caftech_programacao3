package br.com.caftech.decorator;

public abstract class Bebida {
    protected String descricao = "Bebida Desconhecida";

    public String getDescricao() {
        return descricao;
    }
    public abstract double getCusto();
}
