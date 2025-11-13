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
        System.out.println("\n---------------------------------");
        System.out.println("Seja bem vindo a cafeteria CaffeTech");
        System.out.println("---------------------------------");

        boolean executando = true;
        while (executando) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Registrar Novo Pedido");
            System.out.println("2. Listar Histórico de Vendas");
            System.out.println("0. Sair do Sistema");
            System.out.print("\nEscolha uma opção: ");

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
                System.out.println("Ainda não entendi... Por favor digite uma opção válida");
                scanner.next();
            }
        }

        System.out.println("\nSistema encerrado. Obrigado e volte sempre! <3");
        scanner.close();
    }

    // mini menu de criação de pedido
    //adicionei a parte do acrescimo de 5% aqui
    //adicionei uma parte de validaçao, pois estava aceitando palavras erraadas (ex: carpao)
    private void fazerNovoPedido() {
        System.out.println("\n--- NOVO PEDIDO ---");

        // escolha da base de bebida com o factory
        Bebida bebidaDoCliente = escolherBebidaBase();
        // escolha dos acicionais do decorator
        bebidaDoCliente = adicionarExtras(bebidaDoCliente);
        double valorBase = bebidaDoCliente.getCusto(); //custo base ANTES de adicionar

        System.out.println("\n--- Método de Pagamento ---");
        System.out.printf("(Valor base do pedido: R$ %.2f)\n", valorBase);//troquei para valorBase
        scanner.nextLine();

        //variáveis fora do loop
        String metodoPag;
        double valorFinal;

        //LOOP DE VALIDAÇÃO (para evitar "carpao")
        while (true) {
            System.out.print("Digite o método (Ex: Dinheiro, Cartão, Pix): ");
            //adicionei o .trim() (para tirar os " ") e o .toLowerCase() (para deixar tudo minusculo)
            metodoPag = scanner.nextLine().trim().toLowerCase();

            //verifica as opções válidas
            if (metodoPag.equalsIgnoreCase("cartao") || metodoPag.equalsIgnoreCase("cartão")) {

                //calcula a taxa e dá o aviso
                valorFinal = valorBase * 1.05; // 5% de taxa
                System.out.println("[AVISO] Pagamento no cartão possui 5% de acréscimo.");
                System.out.printf("[AVISO] Novo valor total: R$ %.2f\n", valorFinal);

                //CONFIRMAÇÃO SE A PESSOA VAI CONTINUAR COM ESSE METODO OU N
                System.out.print("\nDeseja confirmar o pagamento com cartão? (S/N): ");
                String confirmacao = scanner.nextLine().trim().toLowerCase();

                if (confirmacao.equalsIgnoreCase("s") || confirmacao.equalsIgnoreCase("sim")) {
                    break; // confirmou, saiu do loop.
                } else {
                    System.out.println("Pagamento com cartão cancelado. Por favor, escolha outro método.");
                    continue; // Volta para o início do "while(true)"
                }            } else if (metodoPag.equalsIgnoreCase("dinheiro") || metodoPag.equalsIgnoreCase("pix")) {
                // sem taxa
                valorFinal = valorBase;
                break; //sai do loop, entrada válida!
            } else {
                //erro, repete o loop
                System.err.println("Método '" + metodoPag + "' não reconhecido. Por favor, digite Cartão, Dinheiro ou Pix.");
            }
        }

        // registro do pedido no singleton
        System.out.println("\n[Sistema] Registrando seu pedido no caixa...");

        Pedido novoPedido = new Pedido(
                bebidaDoCliente.getDescricao(),
                valorFinal, // de bebidaDoCliente.getCusto() foi para valorFinal
                metodoPag
        );
        // registro do novo pedido e já lança o saldo atualizado no caixa
        caixa.registrarPedido(novoPedido);
    }

    // escolha da base de bebida
    private Bebida escolherBebidaBase() {
        System.out.println("\n--- Por favor escolha sua Bebida Base! ---");
        System.out.println("1: Café Expresso");
        System.out.println("2: Cappuccino");
        System.out.println("3: Latte");
        System.out.println("4. Café Gourmet");

        while (true) {
            System.out.print("\nDigite a opção (1-4): ");
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
                System.out.println("Ainda não entendi... Por favor digite os números válidos.");
                scanner.next(); // trata erro caso digite string ao invés de escolher num
            }
        }
    }

    private Bebida adicionarExtras(Bebida bebidaBase) {
        Bebida bebidaAtual = bebidaBase;
        System.out.println("\nQuer deixar seu pedido mais saboroso? Escolha algum adicional!");
        System.out.println("-> Caso esteja satisfeito digite 0");
        while (true) {
            System.out.printf("\n(Pedido atual: %s | Custo: R$ %.2f | Para finalizar digite 0)\n",
                    bebidaAtual.getDescricao(), bebidaAtual.getCusto());

            System.out.println("1: Baunilha");
            System.out.println("2: Canela");
            System.out.println("3: Chantily");
            System.out.println("4: Chocolate");
            System.out.println("0: Sair / Finalizar Pedido");
            System.out.print("\nDigite a opção (0-4): ");

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
                System.out.println("Não entendi... Por favor digite os números válidos.");
                scanner.next();
            }
        }
    }
}