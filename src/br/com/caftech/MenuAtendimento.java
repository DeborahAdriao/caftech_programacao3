package br.com.caftech;


import br.com.caftech.decorator.Bebida;
import br.com.caftech.decorator.adicionais.*;
import br.com.caftech.factory.*;
import br.com.caftech.singleton.CaixaFinanceiroSingleton;
import br.com.caftech.singleton.Pedido;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuAtendimento {

    private Scanner scanner;
    private CafeteriaFactory factory;
    //inicio do singleton
    private CaixaFinanceiroSingleton caixa;

    public MenuAtendimento() {
        this.scanner = new Scanner(System.in);
        this.caixa = CaixaFinanceiroSingleton.getInstancia();
    }

    //inicio do menu
    public void iniciar() {
        System.out.println("Seja bem vindo a cafeteria CoffeTech");
        System.out.println("---------------------------------");

        boolean executando = true;
        while (executando) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Registrar Novo Pedido");
            System.out.println("2. Listar Histórico de Vendas");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                int escolha = scanner.nextInt();
                switch (escolha) {
                    case 1:
                        // mini menu p criar o pedido
                        fazerNovoPedido();
                        break;
                    case 2:
                        // vai listar o historico de pedidos
                        caixa.listarHistorico();
                        break;
                    case 0:
                        executando = false;
                        break;
                    default:
                        System.out.println("Você escolheu uma opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ainda não entendi.... por favor digite uma opção válida");
                scanner.next();
            }
        }

        System.out.println("\nSistema encerrado. Obrigado e volte sempre!");
        scanner.close();
    }

    // mini menu de criação de pedido
    private void fazerNovoPedido() {
        System.out.println("\n--- NOVO PEDIDO ---");

        // escolha da base de bebida com o factory
        Bebida bebidaDoCliente = escolherBebidaBase();

        // escolha dos acicionais do decorator
        bebidaDoCliente = adicionarExtras(bebidaDoCliente);

        System.out.println("\n--- Método de Pagamento ---");
        System.out.printf("(Valor base do pedido: R$ %.2f)\n", bebidaDoCliente.getCusto());
        System.out.print("Digite o método (Ex: Dinheiro, Cartão, Pix): ");

        scanner.nextLine();
        String metodoPag = scanner.nextLine();

        // registro do pedido no singleton
        System.out.println("\n[Sistema] Registrando seu pedido no caixa...");

        Pedido novoPedido = new Pedido(
                bebidaDoCliente.getDescricao(),
                bebidaDoCliente.getCusto(),
                metodoPag
        );
        // registro do novo pedido e já lança o saldo atualizado no caixa
        caixa.registrarPedido(novoPedido);
    }


    // escolha da base de bebida
    private Bebida escolherBebidaBase() {
        System.out.println("--- Por favor escolha sua Bebida Base! ---");
        System.out.println("1: Café Expresso");
        System.out.println("2: Cappuccino");
        System.out.println("3: Latte");
        System.out.println("4. Café Gourmet");

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
                        System.out.println("Não entendi... por favor digite uma opção válida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ainda não entendi... por favor digite os números válidos.");
                scanner.next(); // trata erro caso digite string ao invés de escolher num
            }
        }
    }


    private Bebida adicionarExtras(Bebida bebidaBase) {
        Bebida bebidaAtual = bebidaBase;
        System.out.println("Quer deixar seu pedido mais saboroso? Escolha algum adicional! Caso esteja satisfeito digite 0");
        while (true) {
            System.out.printf("\n(Pedido atual: %s | Custo: R$ %.2f Para finalizar digite 0)\n",
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
                        return bebidaAtual;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Não entendi... por favor digite os números válidos.");
                scanner.next();
            }
        }
    }
}