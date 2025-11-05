package br.com.caftech.factory;

import br.com.caftech.decorator.Bebida;
import br.com.caftech.decorator.bebidas.CafeExpresso;
import br.com.caftech.singleton.Cardapio;

public class CafeExpressoFactory extends CafeteriaFactory {
    @Override
    public Bebida criarBebida() {
        double preco = Cardapio.getInstancia().getPrecoItem("expresso");
        return new CafeExpresso(preco);
    }
}