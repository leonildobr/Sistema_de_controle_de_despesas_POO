import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

public class TelaCadastroDespesa extends JDialog implements ActionListener {

    private JTextField txtNome;
    private JTextField txtValor;
    private JTextField txtDataVencimento; // Formato "dd/MM/yyyy"
    private JComboBox<String> cbTipoDespesa;
    private JButton btnSalvar;
    private JButton btnCancelar;

    private Despesa despesaParaEditar;

    private final String[] TIPOS_DISPONIVEIS = {"Moradia", "Alimentação"};

    public TelaCadastroDespesa() {
        setTitle("1. Entrar Despesa (MVP)");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setModal(true);

        JPanel painelFormulario = new JPanel(new GridLayout(0, 2, 10, 10));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painelFormulario.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelFormulario.add(txtNome);

        painelFormulario.add(new JLabel("Valor (ex: 150.75):"));
        txtValor = new JTextField();
        painelFormulario.add(txtValor);

        painelFormulario.add(new JLabel("Vencimento (dd/MM/yyyy):"));
        txtDataVencimento = new JTextField();
        painelFormulario.add(txtDataVencimento);

        painelFormulario.add(new JLabel("Tipo:"));
        cbTipoDespesa = new JComboBox<>(TIPOS_DISPONIVEIS);
        painelFormulario.add(cbTipoDespesa);

        add(painelFormulario, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(this);
        painelBotoes.add(btnSalvar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        painelBotoes.add(btnCancelar);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    public TelaCadastroDespesa(Despesa despesaParaEditar) {
        this();
        setTitle("Editar Despesa");
        this.despesaParaEditar = despesaParaEditar;

        txtNome.setText(despesaParaEditar.getNome());
        txtValor.setText(String.format("%.2f", despesaParaEditar.getValor()).replace(",", "."));
        txtDataVencimento.setText(despesaParaEditar.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        for (int i = 0; i < cbTipoDespesa.getItemCount(); i++) {
            cbTipoDespesa.setSelectedItem(despesaParaEditar.getTipoNome());
            cbTipoDespesa.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalvar) {
            salvarDespesa();
        } else if (e.getSource() == btnCancelar) {
            dispose();
        }
    }

    private void salvarDespesa() {
        String nome = txtNome.getText();
        String valorStr = txtValor.getText();
        String dataStr = txtDataVencimento.getText();
        String tipoSelecionado = (String) cbTipoDespesa.getSelectedItem();

        if (nome.isEmpty() || valorStr.isEmpty() || dataStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos os campos devem ser preenchidos.",
                    "Erro de Validação",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double valor = Double.parseDouble(valorStr.replace(",", "."));
            LocalDate dataVencimento = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (despesaParaEditar == null) {
                // --- MODO CRIAÇÃO (com switch) ---
                Despesa novaDespesa = null;

                switch (tipoSelecionado) {
                    case "Moradia":
                        novaDespesa = new DespesaMoradia(nome, valor, dataVencimento);
                        break;
                    case "Alimentação":
                        novaDespesa = new DespesaAlimentacao(nome, valor, dataVencimento);
                        break;
                    // Adicione outros 'case' aqui
                }

                if (novaDespesa != null) {
                    GerenciadorDespesas.adicionarDespesa(novaDespesa);
                    JOptionPane.showMessageDialog(this, "Despesa '" + nome + "' salva com sucesso!");
                }

            } else {
                // --- MODO EDIÇÃO (chama a nova assinatura) ---
                GerenciadorDespesas.editarDespesa(despesaParaEditar, nome, valor, dataVencimento);
                JOptionPane.showMessageDialog(this, "Despesa '" + nome + "' atualizada com sucesso!");
            }

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "O campo 'Valor' deve ser um número válido (ex: 150.75).",
                    "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "O campo 'Vencimento' deve estar no formato dd/MM/yyyy.",
                    "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}