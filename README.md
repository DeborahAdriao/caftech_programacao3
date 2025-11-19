#  Projeto CafTech

Sistema de terminal de vendas para uma cafeteria, desenvolvido em Java como projeto acadêmico. O objetivo principal é aplicar e demonstrar o funcionamento em conjunto de três padrões de projeto clássicos: 
**Singleton**, **Factory Method** e **Decorator**.

O sistema simula o atendimento em um caixa, permitindo:
* Escolher uma bebida base (como Expresso, Latte, etc.).
* Adicionar múltiplos ingredientes extras (como Baunilha, Chocolate).
* Calcular o preço final, aplicando uma taxa de 5% para pagamentos no cartão.
* Salvar e consultar o histórico de todas as vendas realizadas.

---

##  Padrões de Projeto Aplicados

A arquitetura do projeto foi construída em torno destes três padrões:

### 1. Padrão Decorator
* **Onde:** Pacote `br.com.caftech.decorator`.
* **Como:** Usado para montar a bebida. A classe abstrata `Bebida` é o componente base. Classes como `CafeExpresso` são os componentes concretos.
Classes como `Baunilha` e `Chocolate` são os "Decorators" que "embrulham" a bebida, somando seu próprio custo e alterando a descrição final.

### 2. Padrão Factory Method
* **Onde:** Pacote `br.com.caftech.factory`.
* **Como:** Usado para desacoplar a criação das bebidas. O `MenuAtendimento` não cria o café diretamente; ele pede a uma fábrica (ex: `CafeExpressoFactory`).
Essa fábrica é a única responsável por consultar o `CardapioSingleton`, pegar o preço correto e "montar" o objeto `new CafeExpresso(preco)`.

### 3. Padrão Singleton
* **Onde:** Pacote `br.com.caftech.singleton`.
* **Como:** Usado para garantir uma instância única de gerenciadores centrais:
    * **`CardapioSingleton`**: É a única fonte de verdade para todos os preços de produtos. Ele persiste (salva/carrega) o menu no arquivo `cardapio.dat`.
    * **`CaixaFinanceiroSingleton`**: É o único responsável por gerenciar o histórico de vendas. Ele salva (persiste) todos os objetos `Pedido` no arquivo `pedidos.dat`.

---

##  Tecnologias
* **Linguagem:** Java
* **Persistência:** Serialização de objetos Java (`ObjectOutputStream` / `ObjectInputStream`).
* **Interface:** Aplicação de Console (`Scanner`).
