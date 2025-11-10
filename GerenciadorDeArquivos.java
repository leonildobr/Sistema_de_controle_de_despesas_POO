import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDeArquivos {

    private static final String ARQUIVO_TIPOS = "tipos_despesa.txt";

    public static List<TipoDespesa> carregarTipos() {
        List<TipoDespesa> tipos = new ArrayList<>();
        File file = new File(ARQUIVO_TIPOS);
        int maxId = 0;

        if (!file.exists()) {
            System.out.println("LOG: Arquivo de tipos não encontrado. Criando um novo...");
            tipos.add(new TipoDespesa("Moradia"));
            tipos.add(new TipoDespesa("Alimentação"));
            tipos.add(new TipoDespesa("Transporte"));
            tipos.add(new TipoDespesa("Lazer"));
            tipos.add(new TipoDespesa("Outros"));
            salvarTipos(tipos); // Salva os dados padrão
            return tipos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(",");
                if (partes.length == 2) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    tipos.add(new TipoDespesa(id, nome)); // Usa o construtor de ID

                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
            TipoDespesa.setProximoId(maxId + 1);

        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar tipos de despesa: " + e.getMessage());
            return new ArrayList<>();
        }

        return tipos;
    }

    public static void salvarTipos(List<TipoDespesa> tipos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_TIPOS))) {
            for (TipoDespesa tipo : tipos) {
                bw.write(tipo.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar tipos de despesa: " + e.getMessage());
        }
    }
}