import java.time.LocalDate;
import java.util.Objects;

public abstract class Despesa implements Pagavel {

    private static int proximoId = 1;

    protected int id;
    protected String nome;
    protected double valor;
    protected LocalDate dataVencimento;
    protected LocalDate dataEmissao;
    protected LocalDate dataPagamento;
    protected boolean paga;


    public abstract String getTipoNome();

    public Despesa(String nome, double valor, LocalDate dataVencimento) {
        this.id = proximoId++;
        this.nome = nome;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.dataEmissao = LocalDate.now();
        this.paga = false;
        this.dataPagamento = null;
    }

    public Despesa(int id, String nome, double valor, LocalDate dataVencimento,
                   LocalDate dataEmissao, boolean paga, LocalDate dataPagamento) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
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
                getTipoNome(), // <-- MUDANÇA PRINCIPAL AQUI
                String.valueOf(id),
                nome,
                String.valueOf(valor),
                dataVencimento.toString(),
                dataEmissao.toString(),
                String.valueOf(paga),
                dataPagStr
        );
    }

    @Override
    public void marcarComoPaga(LocalDate dataPagamento) {
        this.paga = true;
        this.dataPagamento = dataPagamento;
    }

    @Override
    public boolean isPaga() {
        return paga;
    }

    @Override
    public double getValor() {
        return valor;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public void setValor(double valor) { this.valor = valor; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public LocalDate getDataPagamento() { return dataPagamento; }

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