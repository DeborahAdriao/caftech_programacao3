package br.com.caftech.factory;
import br.com.caftech.decorator.Bebida;
import br.com.caftech.decorator.bebidas.Mocca;
import br.com.caftech.singleton.CardapioSingleton;


public class MoccaFactory extends CafeteriaFactory {
    @Override
    public Bebida criarBebida(){
        double preco = CardapioSingleton.getInstancia().getPrecoItem("Mocca");
        return new Mocca(preco);

    }
}
