import javax.swing.*;
import java.awt.*;

public class TelaCadastroDespesa extends JDialog {

    public TelaCadastroDespesa() {
        setTitle("1. Entrar Despesa (MVP)");
        setSize(400, 300);
        setLocationRelativeTo(null); // Centraliza na tela
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Aqui será o formulário de cadastro.", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        add(label, BorderLayout.CENTER);

        setVisible(true);

        // 1. Adicionar JTextFields para "Nome" e "Valor".
        // 2. Adicionar um JComboBox para "Tipo" (usando GerenciadorDespesas.getTiposDespesa()).
        // 3. Adicionar um JDatePicker (biblioteca externa) ou campos para data.
        // 4. Adicionar um botão "Salvar" que cria um objeto Despesa e o envia para
        //    GerenciadorDespesas.adicionarDespesa()
    }
}