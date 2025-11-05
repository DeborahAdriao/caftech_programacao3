package br.com.caftech.factory;

import br.com.caftech.decorator.Bebida;
import br.com.caftech.decorator.bebidas.Latte;
import br.com.caftech.singleton.Cardapio;

public class LatteFactory extends CafeteriaFactory {
    @Override
    public Bebida criarBebida() {
        double preco = Cardapio.getInstancia().getPrecoItem("cafe gourmet");
        return new Latte(preco);
    }
}