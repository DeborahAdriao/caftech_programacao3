package br.com.caftech.decorator.bebidas;
import br.com.caftech.decorator.Bebida;

public class CafeGourmet extends Bebida{
    private double precoBase;

    public CafeGourmet(double preco){
        this.descricao = "Café Gourmet";
        this.precoBase = preco;
    }
    @Override
    public double getCusto() {
        return this.precoBase;
    }
}
