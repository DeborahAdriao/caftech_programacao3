package br.com.caftech.decorator.bebidas;
import br.com.caftech.decorator.Bebida;

public class Latte extends Bebida{
    private double precoBase;

    public Latte(double preco){
        this.descricao = "Latte";
        this.precoBase = preco;
    }
    @Override
    public double getCusto() {
        return this.precoBase;
    }
}
