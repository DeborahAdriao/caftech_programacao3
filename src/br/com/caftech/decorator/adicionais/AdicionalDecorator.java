package br.com.caftech.decorator.adicionais;
import br.com.caftech.decorator.Bebida;

public abstract class AdicionalDecorator extends Bebida {
    protected Bebida bebida;

    public AdicionalDecorator(Bebida bebida){
        this.bebida = bebida;
    }
    @Override
    public abstract String getDescricao();
}
