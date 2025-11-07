import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaListarDespesas extends JDialog {

    public TelaListarDespesas() {
        setTitle("3. Listar Despesas em Aberto (MVP)");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        List<Despesa> despesas = GerenciadorDespesas.getTodasDespesasEmAberto();

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // Ninguém pode editar
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Fonte boa para tabelas

        textArea.append("ID  | Vencimento | Valor      | Nome\n");
        textArea.append("--------------------------------------------------\n");

        if (despesas.isEmpty()) {
            textArea.append("Nenhuma despesa em aberto encontrada.");
        } else {
            for (Despesa d : despesas) {
                String linha = String.format("%-3d | %-10s | R$ %-7.2f | %s\n",
                        d.getId(),
                        d.getDataVencimento().toString(),
                        d.getValor(),
                        d.getNome()
                );
                textArea.append(linha);
            }
        }

        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setVisible(true);

        // 1. Idealmente, substituir o JTextArea por um JTable.
        // 2. Adicionar campos de data e um botão "Filtrar" para usar um período.
    }
}