package br.com.caftech.decorator.bebidas;
import br.com.caftech.decorator.Bebida;

public class Mocca extends Bebida {
    private double precoBase;

    public Mocca (double preco){
        this.descricao = "Mocca";
        this.precoBase =  preco;
    }
    @Override
    public double getCusto (){
        return this.precoBase;

    }
}
