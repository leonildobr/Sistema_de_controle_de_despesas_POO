import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorDespesas {

    private static final String ARQUIVO_DESPESAS = "despesas.txt";

    private static List<Despesa> listaDespesas = carregarDespesas();

    /**
     * Carrega as despesas do arquivo.
     * Esta é a mudança principal: ele usa Polimorfismo.
     */
    private static List<Despesa> carregarDespesas() {
        List<Despesa> despesas = new ArrayList<>();
        File file = new File(ARQUIVO_DESPESAS);
        int maxId = 0;

        if (!file.exists()) {
            System.out.println("LOG: Arquivo de despesas não encontrado. Criando um novo...");
            return despesas;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(",");
                if (partes.length == 8) {

                    String tipoNome = partes[0];
                    int id = Integer.parseInt(partes[1]);
                    String nome = partes[2];
                    double valor = Double.parseDouble(partes[3]);
                    LocalDate dataVenc = LocalDate.parse(partes[4]);
                    LocalDate dataEmissao = LocalDate.parse(partes[5]);
                    boolean paga = Boolean.parseBoolean(partes[6]);
                    LocalDate dataPag = partes[7].equals("null") ? null : LocalDate.parse(partes[7]);

                    Despesa d = null;

                    switch (tipoNome) {
                        case "Moradia":
                            d = new DespesaMoradia(id, nome, valor, dataVenc, dataEmissao, paga, dataPag);
                            break;
                        case "Alimentação":
                            d = new DespesaAlimentacao(id, nome, valor, dataVenc, dataEmissao, paga, dataPag);
                            break;
                        default:
                            System.err.println("Erro: Tipo de despesa desconhecido: " + tipoNome);
                            break;
                    }

                    if (d != null) {
                        despesas.add(d);
                        if (id > maxId) maxId = id;
                    }
                }
            }
            Despesa.setProximoId(maxId + 1);

        } catch (IOException | NumberFormatException | java.time.DateTimeException e) {
            System.err.println("Erro ao carregar despesas: " + e.getMessage());
        }
        return despesas;
    }

    private static void salvarDespesas() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_DESPESAS))) {
            for (Despesa d : listaDespesas) {
                bw.write(d.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar despesas: " + e.getMessage());
        }
    }

    public static void adicionarDespesa(Despesa d) {
        listaDespesas.add(d);
        salvarDespesas();
        System.out.println("LOG: Despesa adicionada e salva: " + d.getNome());
    }

    public static void editarDespesa(Despesa despesa, String nome, double valor, LocalDate dataVenc) {
        despesa.setNome(nome);
        despesa.setValor(valor);
        despesa.setDataVencimento(dataVenc);
        salvarDespesas();
        System.out.println("LOG: Despesa editada e salva: " + despesa.getNome());
    }

    public static void marcarDespesaComoPaga(Despesa despesa, LocalDate dataPagamento) {
        despesa.marcarComoPaga(dataPagamento);
        salvarDespesas();
        System.out.println("LOG: Despesa paga e salva: " + despesa.getNome());
    }

    public static List<Despesa> getTodasDespesasEmAberto() {
        return listaDespesas.stream()
                .filter(d -> !d.isPaga())
                .collect(Collectors.toList());
    }

    public static List<Despesa> getTodasDespesas() {
        return listaDespesas;
    }

    public static List<Despesa> getTodasDespesasPagas() {
        return listaDespesas.stream()
                .filter(Despesa::isPaga)
                .collect(Collectors.toList());
    }

    public static void excluirDespesa(Despesa despesa) {
        if (despesa != null) {
            listaDespesas.remove(despesa);
            salvarDespesas();
            System.out.println("LOG: Despesa removida e salva: " + despesa.getNome());
        }
    }
}