package br.com.caftech.decorator.bebidas;
import br.com.caftech.decorator.Bebida;

public class Cappuccino extends Bebida{
    private double precoBase;

    public Cappuccino(double preco){
        this.descricao = "Cappuccino";
        this.precoBase = preco;
    }
    @Override
    public double getCusto() {
        return this.precoBase;
    }
}
