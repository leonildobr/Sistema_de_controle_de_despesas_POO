import java.time.LocalDate;
import java.util.Objects;

public class Despesa {
    private static int proximoId = 1;
    private int id;
    private String nome;
    private double valor;
    private LocalDate dataVencimento;
    private LocalDate dataEmissao;
    private LocalDate dataPagamento;
    private TipoDespesa tipo;
    private boolean paga;

    public Despesa(String nome, double valor, LocalDate dataVencimento, TipoDespesa tipo) {
        this.id = proximoId++;
        this.nome = nome;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.dataEmissao = LocalDate.now();
        this.paga = false;
        this.dataPagamento = null;
    }

    public Despesa(int id, String nome, double valor, LocalDate dataVencimento, TipoDespesa tipo,
                   LocalDate dataEmissao, boolean paga, LocalDate dataPagamento) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.tipo = tipo;
        this.dataEmissao = dataEmissao;
        this.paga = paga;
        this.dataPagamento = dataPagamento;
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }

    public String toFileString() {
        String dataPagStr = (dataPagamento == null) ? "null" : dataPagamento.toString();

        return String.join(",",
                String.valueOf(id),
                nome,
                String.valueOf(valor),
                dataVencimento.toString(),
                String.valueOf(tipo.getId()),
                dataEmissao.toString(),
                String.valueOf(paga),
                dataPagStr
        );
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public TipoDespesa getTipo() { return tipo; }
    public void setTipo(TipoDespesa tipo) { this.tipo = tipo; }
    public boolean isPaga() { return paga; }

    public void marcarComoPaga(LocalDate dataPagamento) {
        this.paga = true;
        this.dataPagamento = dataPagamento;
    }

    @Override
    public String toString() {
        return String.format("ID %d: %s (R$ %.2f)", this.id, this.nome, this.valor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Despesa despesa = (Despesa) o;
        return id == despesa.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}