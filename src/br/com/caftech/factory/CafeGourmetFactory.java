package br.com.caftech.factory;

import br.com.caftech.decorator.Bebida;
import br.com.caftech.decorator.bebidas.CafeGourmet;
import br.com.caftech.singleton.CardapioSingleton;

public class CafeGourmetFactory extends CafeteriaFactory {
    @Override
    public Bebida criarBebida() {
        double preco = CardapioSingleton.getInstancia().getPrecoItem("cafe gourmet");
        return new CafeGourmet(preco);
    }
}