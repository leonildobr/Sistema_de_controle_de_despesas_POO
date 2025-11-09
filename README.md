# Sistema_de_controle_de_despesas_POO
Projeto final das aulas de Programação Orientada a Objetos

## Como Executar o Projeto (MVP)

Este projeto usa apenas bibliotecas padrão do Java (Swing).

1.  **Pré-requisito:** Ter o JDK (Java Development Kit) 8 ou superior instalado.
2.  **Clone o repositório:**
    ```bash
    git clone [URL-DO-SEU-REPOSITÓRIO-AQUI]
    ```
3.  **Entre na pasta e compile os arquivos:**
    ```bash
    cd [NOME-DA-PASTA-DO-PROJETO]
    javac *.java
    ```
4.  **Execute a classe principal:**
    ```bash
    java MenuPrincipal
    ```
5.  O menu principal do sistema será exibido.

## Changelog
# Classe Menu principal
Executa um menu simples com todas as opções do sistema atravéz de botões.
Cada botão exibe um texto no terminal de saída com informações que cada botão terá após o projeto completo.

## Prova de Conceito (PoC)
A Prova de Conceito (PoC) inicial deste projeto é o arquivo MenuPrincipal.java.

Ele valida a viabilidade técnica de usar a biblioteca Java Swing para construir a interface gráfica principal do sistema. O PoC demonstra um menu de navegação funcional, com múltiplos botões que respondem a eventos de clique, estabelecendo a base para a interação do usuário com as futuras funcionalidades.

## Separação de Prioridades
# Prioridade Alta (Essencial para o MVP)
Entrar despesa: A função principal do software. Sem a capacidade de inserir dados, nenhuma outra função de controle ou relatório pode existir.
Listar despesas em aberto no período: Permite ao usuário visualizar o que foi inserido e o que constitui suas dívidas atuais. Esta é a contrapartida essencial da entrada de dados.
Sair: Função básica de usabilidade para fechar a aplicação. (Já implementada).
# Prioridade Média (Importante, Pós-MVP)
Anotar pagamento: Completa o fluxo de uma despesa (de "em aberto" para "paga").
Listar despesas pagas no período: Relatório importante para o histórico e análise, mas secundário à visualização de dívidas pendentes.
gerenciar tipos de despesas: Melhora a organização, mas para o MVP, os tipos de despesa (ex: "Moradia", "Lazer") podem ser pré-definidos no código sem uma tela de gerenciamento.
# Prioridade Baixa (Complexidade Alta / Futuras Versões)
gerenciar Usuários: Introduz a necessidade de login, senhas, sessões e permissões, o que foge do escopo inicial.

## Projeto do MVP (Minimum Viable Product)
# Com base nas prioridades altas, o MVP (Produto Mínimo Viável) será a primeira versão realmente usável do sistema e se concentrará em duas ações principais:
O usuário pode cadastrar uma nova despesa.
O usuário pode listar todas as despesas em aberto.
Passos Técnicos para o MVP
# Modelagem (Model):
Criar a classe de entidade Despesa.java (com atributos como nome, valor, dataVencimento, paga).
Criar uma classe TipoDespesa.java (com id, nome).
# Armazenamento (Controller/Service):
Criar uma classe GerenciadorDespesas.java que simulará um banco de dados.
Esta classe terá um ArrayList<Despesa> estático para armazenar as despesas em memória durante a execução do programa.
# Telas (View):
Criar a tela TelaCadastroDespesa.java (um JFrame ou JDialog) com formulário (JTextField, JComboBox) para preencher os dados de uma Despesa.
Criar a tela TelaListarDespesas.java (usando um JTable ou JTextArea) para exibir as despesas salvas no GerenciadorDespesas.
# Integração:
Modificar o MenuPrincipal.java para que os botões "Entrar despesa" e "Listar despesas em aberto" abram as novas telas criadas, em vez de apenas imprimir no console.
