
package br.com.caftech;

import br.com.caftech.decorator.Bebida;
import br.com.caftech.decorator.adicionais.*;
import br.com.caftech.factory.*;
import br.com.caftech.singleton.CaixaFinanceiroSingleton;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuAtendimento {

    private Scanner scanner;
    private CafeteriaFactory factory;

    public MenuAtendimento() {
        this.scanner = new Scanner(System.in);
    }

    // metodo principal do menu att
    public void iniciar() {
        System.out.println("Seja Bem Vindo ao CoffeTech");
        System.out.println("---------------------------------");

        try {
            // inicializa  a fabrica dos factories
            Bebida bebidaDoCliente = escolherBebidaBase();

            // começa a diconar os decoradores
            bebidaDoCliente = adicionarExtras(bebidaDoCliente);

            // finaliza o pedido
            System.out.println("\n=================================");
            System.out.println("PEDIDO FINALIZADO:");
            System.out.println("Descrição: " + bebidaDoCliente.getDescricao());

            // formatação de preco
            System.out.printf("Custo Total: R$ %.2f\n", bebidaDoCliente.getCusto());
            System.out.println("=================================");

            // singleton para registar a venda
            double valorFinal = bebidaDoCliente.getCusto();
            CaixaFinanceiroSingleton.getInstancia().registrarVenda(valorFinal);

            System.out.println("\nVenda registrada no caixa!");
            System.out.printf("Total atual do caixa: R$ %.2f\n",
                    CaixaFinanceiroSingleton.getInstancia().getSaldoTotal());

            System.out.println("\nObrigado por comprar conosco!");

        } catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado no sistema: " + e.getMessage());
        } finally {
            // Fecha o scanner ao final do programa
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    // mini menu para escolher a bebida
    private Bebida escolherBebidaBase() {
        System.out.println("--- Escolha a base do seu café ---");
        System.out.println("1: Café Expresso");
        System.out.println("2: Cappuccino");
        System.out.println("3: Latte");
        System.out.println("4: Café Gourmet");

        while (true) {
            System.out.print("Digite a opção (1-4): ");
            try {
                int escolha = scanner.nextInt();

                switch (escolha) {
                    case 1:
                        this.factory = new CafeExpressoFactory();
                        return factory.criarBebida();
                    case 2:
                        this.factory = new CappuccinoFactory();
                        return factory.criarBebida();
                    case 3:
                        this.factory = new LatteFactory();
                        return factory.criarBebida();
                    case 4:
                        this.factory = new CafeGourmetFactory();
                        return factory.criarBebida();
                    default:
                        // trata o erro de opção invalida
                        System.out.println("Opção inválida. Por favor, escolha um número entre 1 e 4.");
                }
            } catch (InputMismatchException e) {
                // Trata erro de digitação do pedido da base
                System.out.println("Ainda não consigo entende.. por favor digite apenas números");
                scanner.next();
            }
        }
    }

    // mini menu dos decoradores do café
    private Bebida adicionarExtras(Bebida bebidaBase) {
        Bebida bebidaAtual = bebidaBase;

        while (true) {
            System.out.println("\n--- Adicionar Extras ---");
            // Mostra o pedido atual
            System.out.printf("(Seu pedido: %s | Custo: R$ %.2f)\n",
                    bebidaAtual.getDescricao(), bebidaAtual.getCusto());

            System.out.println("1: Baunilha");
            System.out.println("2: Canela");
            System.out.println("3: Chantily");
            System.out.println("4: Chocolate");
            System.out.println("0: Sair / Finalizar Pedido");
            System.out.print("Digite a opção (0-4): ");

            try {
                int escolha = scanner.nextInt();

                switch (escolha) {
                    case 1:
                        bebidaAtual = new Baunilha(bebidaAtual);
                        System.out.println("Baunilha adicionada!");
                        break;
                    case 2:
                        bebidaAtual = new Canela(bebidaAtual);
                        System.out.println("Canela adicionada!");
                        break;
                    case 3:
                        bebidaAtual = new Chantily(bebidaAtual);
                        System.out.println("Chantily adicionado!");
                        break;
                    case 4:
                        bebidaAtual = new Chocolate(bebidaAtual);
                        System.out.println("Chocolate adicionado!");
                        break;
                    case 0:
                        // retorna após inserir os decoradores
                        return bebidaAtual;
                    default:
                        // trata erro caso escolha númeor que não está na opção
                        System.out.println("Opção inválida. Por favor, escolha um número entre 0 e 4.");
                }
            } catch (InputMismatchException e) {
                // Trata erro de digitação
                System.out.println("Ainda não consigo entende.. por favor digite apenas números");
                scanner.next();
            }
        }
    }
}