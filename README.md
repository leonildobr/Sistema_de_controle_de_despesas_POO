# Sistema_de_controle_de_despesas_POO
Projeto final das aulas de Programação Orientada a Objetos, um sistema completo de gestão de despesas pessoais construído em Java Swing.

## Sobre o Projeto

Este projeto é a implementação de um Sistema de Controle de Despesas completo. O que começou como um protótipo MVP (Produto Mínimo Viável) evoluiu para uma aplicação robusta que não apenas gerencia despesas, mas também implementa conceitos avançados de Programação Orientada a Objetos, como Herança, Polimorfismo e persistência de dados em arquivos.

O sistema permite que o usuário gerencie múltiplas contas de usuário, cadastre despesas categorizadas (como Moradia, Alimentação, etc.), anote pagamentos e liste suas finanças com filtros.

---

## Funcionalidades Principais

* **CRUD de Despesas:** Crie, edite e exclua despesas.
* **Sistema de Pagamentos:** Anote pagamentos para despesas específicas, movendo-as de "Em Aberto" para "Pagas".
* **Listagem e Filtros:** Uma tela de gerenciamento centralizada permite listar todas as despesas e filtrá-las por status (Pagas/Em Aberto) ou por Categoria (Moradia, Alimentação, etc.).
* **Gerenciamento de Usuários:** CRUD completo de usuários, permitindo adicionar novos, editar ou excluir existentes.
* **Segurança:** As senhas dos usuários são armazenadas de forma segura usando um hash **SHA-256**. Em nenhum momento a senha original é salva em texto.
* **Persistência de Dados:** Todos os dados (usuários e despesas) são salvos em arquivos `.txt` na raiz do projeto, garantindo que as informações não sejam perdidas ao fechar o programa.

---

## Arquitetura e Conceitos de POO

O maior objetivo deste projeto foi aplicar conceitos-chave de Programação Orientada a Objetos. A arquitetura foi refatorada de um modelo simples de Composição para um design mais avançado usando Herança e Interfaces:

* **Classes e Herança:** A classe `Despesa` é `abstract` e serve como um molde. Classes concretas como `DespesaMoradia` e `DespesaAlimentacao` **herdam** dela, compartilhando comportamento comum.
    * **Interfaces e Polimorfismo:** A `interface Pagavel` define um contrato para tudo que pode ser pago. As classes de Despesa a implementam. O `GerenciadorDespesas` utiliza Polimorfismo ao gerenciar uma `List<Despesa>`, que pode conter qualquer objeto que *seja uma* Despesa (como `DespesaMoradia`).
* **Sobrecarga e Sobrescrita:** As classes de modelo (como `Usuario` e as `Despesas`) usam **construtores sobrecarregados** (um para criar novos objetos, outro para carregar do arquivo). Métodos como `toString()` e `getTipoNome()` são **sobrescritos** (`@Override`) para se adequarem a cada classe.
* **Métodos e Atributos Estáticos:** As classes "Gerenciadoras" (`GerenciadorDespesas`, `GerenciadorUsuarios`) são compostas inteiramente de métodos e atributos `static`, agindo como serviços centralizados para o acesso e persistência de dados.
* **Encapsulamento:** Todos os atributos das classes de modelo são `private` ou `protected`, com acesso controlado por getters e setters.

---

## Como Executar

Este projeto usa apenas bibliotecas padrão do Java (Swing).

1.  **Pré-requisito:** Ter o JDK (Java Development Kit) 8 ou superior instalado.
2.  **Clone o repositório:**
    ```bash
    git clone [URL-DO-SEU-REPOSITÓRIO-AQUI]
    ```
3.  **Entre na pasta, compile os arquivos e execute:**
    ```bash
    cd [NOME-DA-PASTA-DO-PROJETO]
    javac *.java
    java MenuPrincipal
    ```
4.  O menu principal do sistema será exibido.

---

## Arquivos de Dados Gerados

Ao executar o sistema pela primeira vez, ele criará os seguintes arquivos de texto na raiz do projeto para armazenar os dados:

* `despesas.txt`: Armazena todas as despesas cadastradas, usando um formato CSV que inclui o tipo da classe (ex: "Moradia") para recriação polimórfica.
* `usuarios.txt`: Armazena os usuários, incluindo o login e o hash SHA-256 da senha.

**Nota:** O plano original incluía um `tipos_despesa.txt`. Este arquivo não é mais usado, pois a lógica de "Tipos" foi substituída pela arquitetura de Herança (onde cada tipo é uma Classe).
