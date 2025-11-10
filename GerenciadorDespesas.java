import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GerenciadorDespesas {

    private static final String ARQUIVO_DESPESAS = "despesas.txt";

    private static List<TipoDespesa> listaTipos = GerenciadorDeArquivos.carregarTipos();
    private static List<Despesa> listaDespesas = carregarDespesas();

    private static List<Despesa> carregarDespesas() {
        List<Despesa> despesas = new ArrayList<>();
        File file = new File(ARQUIVO_DESPESAS);
        int maxId = 0;

        if (!file.exists()) {
            System.out.println("LOG: Arquivo de despesas não encontrado. Criando um novo...");
            // Não precisa criar dados de exemplo, pois o MenuPrincipal fará isso
            return despesas; // Retorna lista vazia
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(",");
                if (partes.length == 8) {
                    int id = Integer.parseInt(partes[0]);
                    String nome = partes[1];
                    double valor = Double.parseDouble(partes[2]);
                    LocalDate dataVenc = LocalDate.parse(partes[3]);
                    int tipoId = Integer.parseInt(partes[4]);
                    LocalDate dataEmissao = LocalDate.parse(partes[5]);
                    boolean paga = Boolean.parseBoolean(partes[6]);
                    LocalDate dataPag = partes[7].equals("null") ? null : LocalDate.parse(partes[7]);

                    // Busca o objeto TipoDespesa correspondente ao ID
                    TipoDespesa tipo = buscarTipoPorId(tipoId);

                    if (tipo != null) {
                        Despesa d = new Despesa(id, nome, valor, dataVenc, tipo, dataEmissao, paga, dataPag);
                        despesas.add(d);
                        if (id > maxId) maxId = id;
                    } else {
                        System.err.println("Erro: Tipo de despesa com ID " + tipoId + " não encontrado para a despesa " + nome);
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

    private static TipoDespesa buscarTipoPorId(int id) {
        for (TipoDespesa tipo : listaTipos) {
            if (tipo.getId() == id) {
                return tipo;
            }
        }
        return listaTipos.stream().findFirst().orElse(null);
    }

    public static List<TipoDespesa> getTiposDespesa() {
        return listaTipos;
    }

    public static void adicionarTipoDespesa(String nome) {
        TipoDespesa novoTipo = new TipoDespesa(nome);
        listaTipos.add(novoTipo);
        GerenciadorDeArquivos.salvarTipos(listaTipos);
        System.out.println("LOG: Tipo de despesa adicionado: " + nome);
    }

    public static void editarTipoDespesa(TipoDespesa tipo, String novoNome) {
        tipo.setNome(novoNome);
        GerenciadorDeArquivos.salvarTipos(listaTipos);
        System.out.println("LOG: Tipo de despesa editado para: " + novoNome);
    }

    public static boolean excluirTipoDespesa(TipoDespesa tipo) {
        boolean emUso = listaDespesas.stream()
                .anyMatch(despesa -> despesa.getTipo().equals(tipo));

        if (emUso) {
            System.out.println("LOG: Tentativa de excluir tipo '" + tipo.getNome() + "', mas está em uso.");
            return false;
        }

        listaTipos.remove(tipo);
        GerenciadorDeArquivos.salvarTipos(listaTipos);
        System.out.println("LOG: Tipo de despesa removido: " + tipo.getNome());
        return true; // Excluído com sucesso
    }

    public static void adicionarDespesa(Despesa d) {
        listaDespesas.add(d);
        salvarDespesas();
        System.out.println("LOG: Despesa adicionada e salva: " + d.getNome());
    }

    public static void editarDespesa(Despesa despesa, String nome, double valor, LocalDate dataVenc, TipoDespesa tipo) {
        despesa.setNome(nome);
        despesa.setValor(valor);
        despesa.setDataVencimento(dataVenc);
        despesa.setTipo(tipo);
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
                .filter(d -> !d.isPaga()) // Filtra apenas as NÃO PAGAS
                .collect(Collectors.toList());
    }

    public static List<Despesa> getTodasDespesas() {
        return listaDespesas; // Retorna a lista completa
    }

    public static List<Despesa> getTodasDespesasPagas() {
        return listaDespesas.stream()
                .filter(d -> d.isPaga()) // Filtra apenas as PAGAS
                .collect(Collectors.toList());
    }

    public static void excluirDespesa(Despesa despesa) {
        if (despesa != null) {
            listaDespesas.remove(despesa);
            System.out.println("LOG: Despesa removida: " + despesa.getNome());
        }
    }
}