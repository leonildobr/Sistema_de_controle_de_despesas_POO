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
            // "gerenciar tipos de despesas",
            "gerenciar Usuários",
            "Sair"
    };

    public MenuPrincipal() {
        setTitle("Sistema de controle de despesas (Versão 2.0 - Herança)");
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
                    !opcao.equals("Listar despesas pagas no período") &&                    !opcao.equals("Anotar pagamento") &&
                    // !opcao.equals("gerenciar tipos de despesas") &&
                    !opcao.equals("Anotar pagamento") &&
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
        if (!GerenciadorDespesas.getTodasDespesas().isEmpty()) {
            System.out.println("LOG: Dados já carregados do arquivo 'despesas.txt'.");
            return;
        }

        System.out.println("LOG: Populando dados de exemplo (primeira execução)...");

        Despesa d1 = new DespesaMoradia("Conta de Luz", 150.75, LocalDate.now().plusDays(5));
        Despesa d2 = new DespesaAlimentacao("Supermercado", 450.00, LocalDate.now().plusDays(2));
        Despesa d3 = new DespesaMoradia("Aluguel", 1200.00, LocalDate.now().plusDays(10));

        GerenciadorDespesas.adicionarDespesa(d1);
        GerenciadorDespesas.adicionarDespesa(d2);
        GerenciadorDespesas.adicionarDespesa(d3);

        Despesa d4 = new DespesaMoradia("Internet (Mês Passado)", 99.90, LocalDate.now().minusDays(20));
        d4.marcarComoPaga(LocalDate.now().minusDays(19)); // Marca como paga ANTES de salvar
        GerenciadorDespesas.adicionarDespesa(d4); // Salva o objeto já pago no arquivo

        System.out.println("LOG: Dados de exemplo populados e salvos.");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("Entrar despesa")) {
            new TelaCadastroDespesa().setVisible(true);

        } else if (comando.equals("Listar despesas em aberto no período")) {
            new TelaListarDespesas("Em Aberto").setVisible(true);

        } else if (comando.equals("Listar despesas pagas no período")) {
            new TelaListarDespesas("Pagas").setVisible(true);

        } else if (comando.equals("Anotar pagamento")) {
            new TelaAnotarPagamento().setVisible(true);

        /* } else if (comando.equals("gerenciar tipos de despesas")) {
            new TelaGerenciarTipos().setVisible(true);
        }
        */

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