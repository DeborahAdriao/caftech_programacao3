package br.com.caftech.factory;

import br.com.caftech.decorator.Bebida;
import br.com.caftech.decorator.bebidas.Cappuccino;
import br.com.caftech.singleton.CardapioSingleton;

public class CappuccinoFactory extends CafeteriaFactory {
    @Override
    public Bebida criarBebida() {
        double preco = CardapioSingleton.getInstancia().getPrecoItem("cappuccino");
        return new Cappuccino(preco);
    }
}