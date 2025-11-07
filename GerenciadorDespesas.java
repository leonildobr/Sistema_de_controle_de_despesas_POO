import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorDespesas {

    private static List<Despesa> listaDespesas = new ArrayList<>();
    private static List<TipoDespesa> listaTipos = new ArrayList<>();

    static {
        listaTipos.add(new TipoDespesa("Moradia"));
        listaTipos.add(new TipoDespesa("Alimentação"));
        listaTipos.add(new TipoDespesa("Transporte"));
        listaTipos.add(new TipoDespesa("Lazer"));
        listaTipos.add(new TipoDespesa("Outros"));
    }

    public static List<TipoDespesa> getTiposDespesa() {
        return listaTipos;
    }

    public static void adicionarDespesa(Despesa d) {
        listaDespesas.add(d);
        System.out.println("LOG: Despesa adicionada: " + d.getNome());
    }

    public static List<Despesa> getTodasDespesasEmAberto() {
        return listaDespesas.stream()
                .filter(d -> !d.isPaga()) // Filtra apenas as NÃO PAGAS
                .collect(Collectors.toList());
    }
}