package br.com.caftech.decorator.bebidas;
import br.com.caftech.decorator.Bebida;

public class CafeExpresso extends Bebida{
    private double precoBase;

    public CafeExpresso(double preco){
        this.descricao = "Café Expresso";
        this.precoBase = preco;
    }
    @Override
    public double getCusto(){
        return this.precoBase;
    }
}
