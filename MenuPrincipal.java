import javax.swing.*;

public class MenuPrincipalGUI {

    // Array de botões para o menu principal
    private final String[] opcoes = {
        "Entrar despesa",
        "Anotar pagamento",
        "Listar despesas em aberto no período",
        "Listar despesas pagas no período",
        "gerenciar tipos de despesas",
        "gerenciar Usuários",
        "Sair"
    };

    // Construtor da classe MenuPrincipalGUI
    public MenuPrincipalGUI() {
        // configurações básicas da janela
        setTitle("Sistema de controle de despesas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);
        // cria o painel principal e define o layout
        JPanel PainelPrincipal = new JPanel(new GridLayout(opcoes.length, 1, 10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // loop de criação dos botões
        for (String opcao : opcoes) {
            JButton botao = new JButton(opcao);
            botao.setFont(new Font("Arial", Font.BOLD, 14));
            botao.addActionListener(this);
            PainelPrincipal.add(botao);
        }

        // adiciona o painel principal à janela
        add(PainelPrincipal, BorderLayout.CENTER);

        // torna a janela visível
        setVisible(true);

        // método para lidar com eventos de clique nos botões
        /*
        no momento apenas imprime a opção selecionada no terminal
        */
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        System.out.println("Opção selecionada: " + comando);

        if (comando.equals("Sair")) {
            System.out.println("Encerrando o sistema...");
            System.exit(0);
        }
    }

    /* método principal para inicializar o programa */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuPrincipalGUI();
            }
        });
    }
}