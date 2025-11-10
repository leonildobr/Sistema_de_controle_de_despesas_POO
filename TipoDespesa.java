import java.util.Objects;

public class TipoDespesa {
    private static int proximoId = 1;
    private int id;
    private String nome;

    public TipoDespesa(String nome) {
        this.id = proximoId++; // Usa o contador estático
        this.nome = nome;
    }

    public TipoDespesa(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }

    public String toFileString() {
        // Formato: ID,Nome
        return id + "," + nome;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoDespesa that = (TipoDespesa) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}