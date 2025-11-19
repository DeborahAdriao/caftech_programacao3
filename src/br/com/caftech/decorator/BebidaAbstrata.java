package br.com.caftech.decorator;

public abstract class BebidaAbstrata {
    protected String descricao = "Bebida Desconhecida";

    public String getDescricao() {
        return descricao;
    }
    public abstract double getCusto();
}
