import java.time.LocalDate;

public class DespesaAlimentacao extends Despesa {

    public DespesaAlimentacao(String nome, double valor, LocalDate dataVencimento) {
        super(nome, valor, dataVencimento);
    }

    public DespesaAlimentacao(int id, String nome, double valor, LocalDate dataVencimento,
                              LocalDate dataEmissao, boolean paga, LocalDate dataPagamento) {
        super(id, nome, valor, dataVencimento, dataEmissao, paga, dataPagamento);
    }

    @Override
    public String getTipoNome() {
        return "Alimentação";
    }
}