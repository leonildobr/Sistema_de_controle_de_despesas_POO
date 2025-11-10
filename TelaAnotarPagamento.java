import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TelaAnotarPagamento extends JDialog implements ActionListener {

    private JComboBox<Despesa> cbDespesas;
    private JTextField txtDataPagamento;
    private JButton btnSalvar;
    private JButton btnCancelar;

    public TelaAnotarPagamento() {
        setTitle("2. Anotar Pagamento (Pós-MVP)");
        setSize(450, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setModal(true);

        List<Despesa> despesasAbertas = GerenciadorDespesas.getTodasDespesasEmAberto();

        if (despesasAbertas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Não há nenhuma despesa em aberto para pagar.",
                    "Nenhuma Despesa",
                    JOptionPane.INFORMATION_MESSAGE);

            SwingUtilities.invokeLater(() -> dispose());
            return;
        }

        JPanel painelFormulario = new JPanel(new GridLayout(0, 2, 10, 10));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painelFormulario.add(new JLabel("Selecione a Despesa:"));
        cbDespesas = new JComboBox<>(despesasAbertas.toArray(new Despesa[0]));
        painelFormulario.add(cbDespesas);

        painelFormulario.add(new JLabel("Data Pagamento (dd/MM/yyyy):"));
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtDataPagamento = new JTextField(LocalDate.now().format(formatador));
        painelFormulario.add(txtDataPagamento);

        add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        btnSalvar = new JButton("Salvar Pagamento");
        btnSalvar.addActionListener(this);
        painelBotoes.add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        painelBotoes.add(btnCancelar);

        add(painelBotoes, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalvar) {
            salvarPagamento();
        } else if (e.getSource() == btnCancelar) {
            dispose();
        }
    }

    private void salvarPagamento() {
        Despesa despesaSelecionada = (Despesa) cbDespesas.getSelectedItem();
        String dataStr = txtDataPagamento.getText();

        if (despesaSelecionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Nenhuma despesa foi selecionada.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            LocalDate dataPagamento = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            GerenciadorDespesas.marcarDespesaComoPaga(despesaSelecionada, dataPagamento);

            JOptionPane.showMessageDialog(this,
                    "Pagamento da despesa '" + despesaSelecionada.getNome() + "' anotado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "A data de pagamento deve estar no formato dd/MM/yyyy.",
                    "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}