import java.time.LocalDate;

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
}