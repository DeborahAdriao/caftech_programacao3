package br.com.caftech.singleton;

import java.io.Serializable;
import java.time.LocalDateTime;
// tive que deixar as classes pedido publicas para poder implementar ele no menu
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    private String descricaoBebida;
    private double valorTotal;
    private String metodoPagamento;
    private LocalDateTime dataHora;

    public Pedido(String descricaoBebida, double valorTotalFinal, String metodoPagamento){
        this.descricaoBebida = descricaoBebida;
        this.metodoPagamento = metodoPagamento;
        this.dataHora = LocalDateTime.now();

        //o calculo de acrescimo agora vai ficar no MENU - fazerNovoPedido
        this.valorTotal = valorTotalFinal;
    }

    public double getValorTotal(){
        return valorTotal;
    }

    // metodo para mostrar o historico
    @Override
    public String toString(){
        return String.format("[%s] %s | Método: %s | Total: R$ %.2f",
                dataHora.toLocalDate(),
                descricaoBebida,
                metodoPagamento,
                valorTotal);
    }
}

