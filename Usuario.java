import java.util.Objects;

public class Usuario {
    private static int proximoId = 1;
    private int id;
    private String login;
    private String senhaHash; // Nunca armazena a senha plana


    public Usuario(String login, String senhaPlana) {
        this.id = proximoId++;
        this.login = login;
        this.setSenha(senhaPlana); // Usa o método para gerar o hash
    }

    public Usuario(int id, String login, String senhaHash) {
        this.id = id;
        this.login = login;
        this.senhaHash = senhaHash;
    }

    public String toFileString() {
        return id + "," + login + "," + senhaHash;
    }

    public int getId() { return id; }
    public String getLogin() { return login; }

    public void setLogin(String login) { this.login = login; }


    public void setSenha(String senhaPlana) {
        this.senhaHash = Seguranca.hashSenha(senhaPlana);
    }

    public boolean verificarSenha(String senhaPlana) {
        String hashTentativa = Seguranca.hashSenha(senhaPlana);
        return hashTentativa.equals(this.senhaHash);
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }

    @Override
    public String toString() {
        return this.login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id == usuario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}