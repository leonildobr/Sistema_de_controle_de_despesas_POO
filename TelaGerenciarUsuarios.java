import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaGerenciarUsuarios extends JDialog implements ActionListener, ListSelectionListener {

    private JList<Usuario> listaUsuariosUI;
    private DefaultListModel<Usuario> modeloLista;
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnSalvar;
    private JButton btnNovo;
    private JButton btnExcluir;

    private Usuario usuarioSelecionado = null;

    public TelaGerenciarUsuarios() {
        setTitle("6. Gerenciar Usuários");
        setSize(550, 350);
        setLocationRelativeTo(null);
        setModal(true);
        setLayout(new BorderLayout(10, 10));

        modeloLista = new DefaultListModel<>();
        listaUsuariosUI = new JList<>(modeloLista);
        listaUsuariosUI.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaUsuariosUI.addListSelectionListener(this);

        JScrollPane scrollLista = new JScrollPane(listaUsuariosUI);
        scrollLista.setBorder(BorderFactory.createTitledBorder("Usuários"));
        add(scrollLista, BorderLayout.CENTER);

        JPanel painelForm = new JPanel(new BorderLayout(10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(0, 1, 5, 5));
        painelCampos.add(new JLabel("Login:"));
        txtLogin = new JTextField();
        painelCampos.add(txtLogin);

        painelCampos.add(new JLabel("Senha (deixe em branco para não alterar):"));
        txtSenha = new JPasswordField();
        painelCampos.add(txtSenha);

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
        btnExcluir.setEnabled(false);
        painelBotoesForm.add(btnExcluir);

        painelForm.add(painelBotoesForm, BorderLayout.SOUTH);
        add(painelForm, BorderLayout.EAST);

        atualizarLista();
    }

    private void atualizarLista() {
        modeloLista.clear();
        for (Usuario user : GerenciadorUsuarios.getUsuarios()) {
            modeloLista.addElement(user);
        }
    }

    private void modoAdicionar() {
        usuarioSelecionado = null;
        listaUsuariosUI.clearSelection();
        txtLogin.setText("");
        txtSenha.setText("");
        btnSalvar.setText("Adicionar");
        btnExcluir.setEnabled(false);
        txtLogin.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNovo) {
            modoAdicionar();
        } else if (e.getSource() == btnSalvar) {
            salvar();
        } else if (e.getSource() == btnExcluir) {
            excluir();
        }
    }

    private void salvar() {
        String login = txtLogin.getText().trim();
        String senha = new String(txtSenha.getPassword());

        if (login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O login não pode estar vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuarioSelecionado == null) {
            if (senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "A senha é obrigatória para novos usuários.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                GerenciadorUsuarios.adicionarUsuario(login, senha);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            GerenciadorUsuarios.editarUsuario(usuarioSelecionado, login, senha);
        }

        atualizarLista();
        modoAdicionar();
    }

    private void excluir() {
        if (usuarioSelecionado == null) return;

        if (GerenciadorUsuarios.getUsuarios().size() <= 1) {
            JOptionPane.showMessageDialog(this, "Não é possível excluir o último usuário do sistema.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o usuário '" + usuarioSelecionado.getLogin() + "'?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (resposta == JOptionPane.YES_OPTION) {
            GerenciadorUsuarios.excluirUsuario(usuarioSelecionado);
            atualizarLista();
            modoAdicionar();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            usuarioSelecionado = listaUsuariosUI.getSelectedValue();

            if (usuarioSelecionado != null) {
                txtLogin.setText(usuarioSelecionado.getLogin());
                txtSenha.setText("");
                btnSalvar.setText("Editar");
                btnExcluir.setEnabled(true);
            }
        }
    }
}