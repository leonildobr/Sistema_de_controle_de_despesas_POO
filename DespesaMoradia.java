import java.time.LocalDate;

public class DespesaMoradia extends Despesa {

    public DespesaMoradia(String nome, double valor, LocalDate dataVencimento) {
        super(nome, valor, dataVencimento);
    }

    public DespesaMoradia(int id, String nome, double valor, LocalDate dataVencimento,
                          LocalDate dataEmissao, boolean paga, LocalDate dataPagamento) {
        super(id, nome, valor, dataVencimento, dataEmissao, paga, dataPagamento);
    }

    @Override
    public String getTipoNome() {
        return "Moradia";
    }
}