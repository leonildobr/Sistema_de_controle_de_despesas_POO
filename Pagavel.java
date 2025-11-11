import java.time.LocalDate;

public interface Pagavel {

    void marcarComoPaga(LocalDate dataPagamento);

    boolean isPaga();

    double getValor();
}