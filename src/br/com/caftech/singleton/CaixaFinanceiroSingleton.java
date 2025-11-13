package br.com.caftech.singleton;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CaixaFinanceiroSingleton {
    private static final CaixaFinanceiroSingleton instancia = new CaixaFinanceiroSingleton();
    private static final String ARQUIVO_PEDIDOS = "pedidos.dat";

    private List<Pedido> historicoPedidos;
    private double saldoTotal;

    private CaixaFinanceiroSingleton() {
        carregarHistorico();

        if (historicoPedidos == null) {
            historicoPedidos = new ArrayList<>();
        }
        recalcularSaldoTotal();
        System.out.printf("[Caixa] Caixa aberto. Saldo carregado: R$ %.2f (%d pedidos no histórico)\n",
                saldoTotal, historicoPedidos.size());
    }
    public static CaixaFinanceiroSingleton getInstancia() {
        return instancia;
    }
    // --- MÉTODOS DE NEGÓCIO (O que o Menu vai usar) ---

    public void listarHistorico() {
        System.out.println("\n--- Histórico de Vendas CafTech ---\n");

        if (historicoPedidos.isEmpty()) {
            System.out.println(" (Nenhuma venda registrada)");
        } else {
            // Itera na lista de pedidos e usa o metodo toString() de cada Pedido
            for (Pedido p : historicoPedidos) {
                System.out.println("  " + p.toString());
            }
        }
        System.out.printf("\n--- Saldo Total em Caixa: R$ %.2f ---\n", saldoTotal);
    }
    public void registrarPedido(Pedido pedido) {
        historicoPedidos.add(pedido);
        saldoTotal += pedido.getValorTotal();
        salvarHistorico();

        System.out.printf("\n[Caixa] Venda registrada! Novo saldo: R$ %.2f\n", saldoTotal);
    }
    // --- MÉTODOS INTERNOS (Persistência e Cálculo) ---

    private void salvarHistorico() {
        try (FileOutputStream fos = new FileOutputStream(ARQUIVO_PEDIDOS);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(historicoPedidos);

        } catch (IOException e) {
            System.err.println("[Caixa] Erro ao salvar histórico: " + e.getMessage());
        }
    }
    private void carregarHistorico() {
        try (FileInputStream fis = new FileInputStream(ARQUIVO_PEDIDOS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            historicoPedidos = (List<Pedido>) ois.readObject();
            System.out.println("[Caixa] Histórico de pedidos carregado de " + ARQUIVO_PEDIDOS);

        } catch (FileNotFoundException e) {
            // Isso é normal na primeira vez que o programa roda
            System.out.println("[Caixa] Arquivo " + ARQUIVO_PEDIDOS + " não encontrado. Um novo será criado.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[Caixa] Erro ao carregar histórico: " + e.getMessage());
        }
    }
    private void recalcularSaldoTotal() {
        this.saldoTotal = 0; // Zera o saldo
        // Soma o valor de cada pedido que foi carregado do arquivo
        for (Pedido p : historicoPedidos) {
            this.saldoTotal += p.getValorTotal();
        }
    }
}
