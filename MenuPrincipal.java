import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class MenuPrincipal extends JFrame implements ActionListener {

    private final String[] opcoes = {
            "Entrar despesa",
            "Anotar pagamento",
            "Listar despesas em aberto no período",
            "Listar despesas pagas no período",
            "gerenciar tipos de despesas",
            "gerenciar Usuários",
            "Sair"
    };

    public MenuPrincipal() {
        setTitle("Sistema de controle de despesas (Versão MVP)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new GridLayout(opcoes.length, 1, 10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        for (String opcao : opcoes) {
            JButton botao = new JButton(opcao);
            botao.setFont(new Font("Arial", Font.BOLD, 14));
            botao.addActionListener(this);

            if (!opcao.equals("Entrar despesa") &&
                    !opcao.equals("Listar despesas em aberto no período") &&
                    !opcao.equals("Anotar pagamento") &&
                    !opcao.equals("gerenciar tipos de despesas") &&
                    !opcao.equals("gerenciar Usuários") &&
                    !opcao.equals("Sair")) {

                botao.setEnabled(false);
                botao.setToolTipText("Disponível nas próximas versões");
            }

            painelPrincipal.add(botao);
        }

        add(painelPrincipal, BorderLayout.CENTER);

        popularDadosDeExemplo();

        setVisible(true);
    }

    private void popularDadosDeExemplo() {
        System.out.println("LOG: Populando dados de exemplo para o MVP...");
        TipoDespesa moradia = GerenciadorDespesas.getTiposDespesa().get(0);
        TipoDespesa alimentacao = GerenciadorDespesas.getTiposDespesa().get(1);

        Despesa d1 = new Despesa("Conta de Luz", 150.75, LocalDate.now().plusDays(5), moradia);
        Despesa d2 = new Despesa("Supermercado", 450.00, LocalDate.now().plusDays(2), alimentacao);
        Despesa d3 = new Despesa("Aluguel", 1200.00, LocalDate.now().plusDays(10), moradia);

        GerenciadorDespesas.adicionarDespesa(d1);
        GerenciadorDespesas.adicionarDespesa(d2);
        GerenciadorDespesas.adicionarDespesa(d3);

        Despesa d4 = new Despesa("Internet (Mês Passado)", 99.90, LocalDate.now().minusDays(20), moradia);
        d4.marcarComoPaga(LocalDate.now().minusDays(19));
        GerenciadorDespesas.adicionarDespesa(d4);

        System.out.println("LOG: Dados de exemplo populados.");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("Entrar despesa")) {
            new TelaCadastroDespesa().setVisible(true);

        } else if (comando.equals("Listar despesas em aberto no período")) {
            new TelaListarDespesas().setVisible(true);

        } else if (comando.equals("Anotar pagamento")) {
            new TelaAnotarPagamento().setVisible(true);

        } else if (comando.equals("gerenciar tipos de despesas")) {
            new TelaGerenciarTipos().setVisible(true);

        } else if (comando.equals("gerenciar Usuários")) {
            new TelaGerenciarUsuarios().setVisible(true);

        } else if (comando.equals("Sair")) {
            System.out.println("Encerrando o sistema...");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuPrincipal();
            }
        });
    }
}