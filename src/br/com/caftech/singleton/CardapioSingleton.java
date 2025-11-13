package br.com.caftech.singleton;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CardapioSingleton {

    private static final CardapioSingleton instancia = new CardapioSingleton();
    private static final String ARQUIVO_CARDAPIO = "cardapio.dat"; //nomde do arq
    private Map<String, Double> precosItens;

    private CardapioSingleton() { //(Singleton)
        carregarCardapio();
        if (precosItens == null) {
            System.out.println("[Cardapio] Nenhum arquivo encontrado. Criando cardápio padrão...");
            criarCardapioPadrao();
            // E salva esse cardápio padrão no arquivo
            salvarCardapio();
        }
    }
    public static CardapioSingleton getInstancia() { //acesso global
        return instancia;
    }
    private void carregarCardapio() {
        try (FileInputStream fis = new FileInputStream(ARQUIVO_CARDAPIO);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            // lê o mapa inteiro do arquivo
            precosItens = (Map<String, Double>) ois.readObject();
            System.out.println("[Cardapio] Cardápio carregado de " + ARQUIVO_CARDAPIO);

        } catch (FileNotFoundException e) {
            // para a primeira vez que o programa roda
            System.out.println("[Cardapio] Arquivo " + ARQUIVO_CARDAPIO + " não encontrado.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[Cardapio] Erro ao carregar cardápio: " + e.getMessage());
        }
    }
    private void salvarCardapio() {
        try (FileOutputStream fos = new FileOutputStream(ARQUIVO_CARDAPIO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            // salva o objeto 'precosItens' (o Mapa) inteiro no arquivo
            oos.writeObject(precosItens);
            System.out.println("[Cardapio] Cardápio padrão salvo em " + ARQUIVO_CARDAPIO);

        } catch (IOException e) {
            System.err.println("[Cardapio] Erro ao salvar cardápio: " + e.getMessage());
        }
    }
    private void criarCardapioPadrao() { //metodo de segurança, primeira inicilizaçao
        precosItens = new HashMap<>();

        precosItens.put("expresso", 7.00);
        precosItens.put("cafe gourmet", 13.00);
        precosItens.put("cappuccino", 9.00);
        precosItens.put("latte", 7.00);

        precosItens.put("baunilha", 0.50);
        precosItens.put("chocolate", 2.00);
        precosItens.put("chantily", 2.00);
        precosItens.put("canela", 0.50);
    }
    public double getPrecoItem(String nomeItem){ //metodo q o resto pode acessar
        String chave = nomeItem.toLowerCase().trim();
        double preco = precosItens.getOrDefault(chave, 0.0);

        if (preco == 0.0){
            System.err.println("[Cardapio] AVISO: Item '" + chave + "' não encontrado no cardápio.");        }
        return preco;
    }
}

