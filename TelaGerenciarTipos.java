import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaGerenciarTipos extends JDialog implements ActionListener, ListSelectionListener {

    private JList<TipoDespesa> listaTiposUI;
    private DefaultListModel<TipoDespesa> modeloLista;
    private JTextField txtNome;
    private JButton btnSalvar;
    private JButton btnNovo;
    private JButton btnExcluir;

    private TipoDespesa tipoSelecionado = null;

    public TelaGerenciarTipos() {
        setTitle("5. Gerenciar Tipos de Despesa");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        modeloLista = new DefaultListModel<>();
        listaTiposUI = new JList<>(modeloLista);
        listaTiposUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaTiposUI.addListSelectionListener(this);

        JScrollPane scrollLista = new JScrollPane(listaTiposUI);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Tipos Existentes"));
        add(scrollLista, BorderLayout.CENTER);

        JPanel painelForm = new JPanel(new BorderLayout(10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(0, 1, 5, 5));
        painelCampos.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelCampos.add(txtNome);
        painelForm.add(painelCampos, BorderLayout.NORTH);

        JPanel painelBotoesForm = new JPanel(new GridLayout(0, 1, 5, 5));
        btnNovo = new JButton("Novo (Limpar seleção)");
        btnNovo.addActionListener(this);
        painelBotoesForm.add(btnNovo);

        btnSalvar = new JButton("Adicionar");
        btnSalvar.addActionListener(this);
        painelBotoesForm.add(btnSalvar);

        btnExcluir = new JButton("Excluir Selecionado");
        btnExcluir.addActionListener(this);
        btnExcluir.setEnabled(false); // Começa desabilitado
        painelBotoesForm.add(btnExcluir);

        painelForm.add(painelBotoesForm, BorderLayout.SOUTH);
        add(painelForm, BorderLayout.EAST);

        atualizarLista(); // Carrega os dados na lista
    }

    private void atualizarLista() {
        modeloLista.clear();
        for (TipoDespesa tipo : GerenciadorDespesas.getTiposDespesa()) {
            modeloLista.addElement(tipo);
        }
    }

    private void modoAdicionar() {
        tipoSelecionado = null;
        listaTiposUI.clearSelection();
        txtNome.setText("");
        btnSalvar.setText("Adicionar");
        btnExcluir.setEnabled(false);
        txtNome.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNovo) {
            modoAdicionar();
        }
        else if (e.getSource() == btnSalvar) {
            salvar();
        }
        else if (e.getSource() == btnExcluir) {
            excluir();
        }
    }

    private void salvar() {
        String nome = txtNome.getText().trim();
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tipoSelecionado == null) {
            GerenciadorDespesas.adicionarTipoDespesa(nome);
        } else {
            GerenciadorDespesas.editarTipoDespesa(tipoSelecionado, nome);
        }

        atualizarLista();
        modoAdicionar();
    }

    private void excluir() {
        if (tipoSelecionado == null) return;

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o tipo '" + tipoSelecionado.getNome() + "'?\n",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (resposta == JOptionPane.YES_OPTION) {
            boolean sucesso = GerenciadorDespesas.excluirTipoDespesa(tipoSelecionado);
            if (!sucesso) {
                JOptionPane.showMessageDialog(this,
                        "Não foi possível excluir o tipo '" + tipoSelecionado.getNome() + "'.\n" +
                                "Ele já está sendo usado em alguma despesa.",
                        "Erro de Exclusão",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                atualizarLista();
                modoAdicionar();
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            tipoSelecionado = listaTiposUI.getSelectedValue();

            if (tipoSelecionado != null) {
                txtNome.setText(tipoSelecionado.getNome());
                btnSalvar.setText("Editar");
                btnExcluir.setEnabled(true);
            }
        }
    }
}