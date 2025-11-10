import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class TelaListarDespesas extends JDialog implements ActionListener, ListSelectionListener {

    private JComboBox<String> cbFiltroStatus;
    private JComboBox<TipoDespesa> cbFiltroCategoria;
    private JButton btnFiltrar;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnFechar;

    private List<Despesa> despesasExibidas;

    public TelaListarDespesas() {
        setTitle("3. Listar e Gerenciar Despesas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        JPanel painelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));

        painelFiltros.add(new JLabel("Status:"));
        cbFiltroStatus = new JComboBox<>(new String[]{"Todas", "Em Aberto", "Pagas"});
        painelFiltros.add(cbFiltroStatus);

        painelFiltros.add(new JLabel("Categoria:"));
        List<TipoDespesa> tipos = new ArrayList<>();
        tipos.add(new TipoDespesa("Todas")); // ID = 0 (implícito)
        tipos.addAll(GerenciadorDespesas.getTiposDespesa());
        cbFiltroCategoria = new JComboBox<>(tipos.toArray(new TipoDespesa[0]));
        painelFiltros.add(cbFiltroCategoria);

        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(this);
        painelFiltros.add(btnFiltrar);

        add(painelFiltros, BorderLayout.NORTH);

        String[] colunas = {"ID", "Nome", "Valor", "Vencimento", "Categoria", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Apenas 1 linha por vez
        tabela.getSelectionModel().addListSelectionListener(this); // Ouve eventos de seleção

        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);  // ID
        tabela.getColumnModel().getColumn(1).setPreferredWidth(250); // Nome
        tabela.getColumnModel().getColumn(2).setPreferredWidth(100); // Valor
        tabela.getColumnModel().getColumn(3).setPreferredWidth(100); // Vencimento

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));

        btnEditar = new JButton("Editar Despesa");
        btnEditar.addActionListener(this);
        btnEditar.setEnabled(false); // Começa desabilitado
        painelBotoes.add(btnEditar);

        btnExcluir = new JButton("Excluir Despesa");
        btnExcluir.addActionListener(this);
        btnExcluir.setEnabled(false); // Começa desabilitado
        painelBotoes.add(btnExcluir);

        painelBotoes.add(new JSeparator(SwingConstants.VERTICAL));

        btnFechar = new JButton("Voltar (Fechar)");
        btnFechar.addActionListener(this);
        painelBotoes.add(btnFechar);

        add(painelBotoes, BorderLayout.SOUTH);

        atualizarTabela();
    }

    private void atualizarTabela() {
        String status = (String) cbFiltroStatus.getSelectedItem();
        TipoDespesa categoria = (TipoDespesa) cbFiltroCategoria.getSelectedItem();

        List<Despesa> despesasBase;
        if (status.equals("Em Aberto")) {
            despesasBase = GerenciadorDespesas.getTodasDespesasEmAberto();
        } else if (status.equals("Pagas")) {
            despesasBase = GerenciadorDespesas.getTodasDespesasPagas();
        } else {
            despesasBase = GerenciadorDespesas.getTodasDespesas();
        }

        if (categoria != null && !categoria.getNome().equals("Todas")) {
            despesasExibidas = despesasBase.stream()
                    .filter(d -> d.getTipo().equals(categoria))
                    .collect(Collectors.toList());
        } else {
            despesasExibidas = despesasBase;
        }

        modeloTabela.setRowCount(0); // Limpa a tabela
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Despesa d : despesasExibidas) {
            Vector<Object> linha = new Vector<>();
            linha.add(d.getId());
            linha.add(d.getNome());
            linha.add(String.format("R$ %.2f", d.getValor()));
            linha.add(d.getDataVencimento().format(formatadorData));
            linha.add(d.getTipo().getNome());
            linha.add(d.isPaga() ? "Paga" : "Em Aberto");
            modeloTabela.addRow(linha);
        }
    }

    private Despesa getDespesaSelecionada() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) {
            return null;
        }
        return despesasExibidas.get(linhaSelecionada);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnFiltrar) {
            atualizarTabela();
        }
        else if (e.getSource() == btnFechar) {
            dispose();
        }
        else if (e.getSource() == btnEditar) {
            Despesa despesa = getDespesaSelecionada();
            if (despesa != null) {
                // Abre a tela de cadastro em MODO DE EDIÇÃO
                TelaCadastroDespesa telaEdicao = new TelaCadastroDespesa(despesa);
                telaEdicao.setVisible(true);

                // Após a tela de edição fechar, atualiza a tabela
                atualizarTabela();
            }
        }
        else if (e.getSource() == btnExcluir) {
            Despesa despesa = getDespesaSelecionada();
            if (despesa != null) {
                int resposta = JOptionPane.showConfirmDialog(
                        this,
                        "Tem certeza que deseja excluir a despesa:\n" + despesa.getNome() + "?",
                        "Confirmar Exclusão",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (resposta == JOptionPane.YES_OPTION) {
                    GerenciadorDespesas.excluirDespesa(despesa);
                    atualizarTabela(); // Atualiza a tabela
                }
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // Se e.getValueIsAdjusting() for true, o usuário ainda está clicando/arrastando
        if (!e.getValueIsAdjusting()) {
            boolean linhaSelecionada = (tabela.getSelectedRow() != -1);
            // Habilita ou desabilita os botões com base na seleção
            btnEditar.setEnabled(linhaSelecionada);
            btnExcluir.setEnabled(linhaSelecionada);
        }
    }
}