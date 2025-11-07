package br.com.caftech.factory;

import br.com.caftech.decorator.Bebida;
import br.com.caftech.decorator.bebidas.Latte;
import br.com.caftech.singleton.CardapioSingleton;

public class LatteFactory extends CafeteriaFactory {
    @Override
    public Bebida criarBebida() {
        double preco = CardapioSingleton.getInstancia().getPrecoItem("latte");
        return new Latte(preco);
    }
}