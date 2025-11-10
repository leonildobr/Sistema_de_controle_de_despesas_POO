import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorUsuarios {

    private static final String ARQUIVO_USUARIOS = "usuarios.txt";
    private static List<Usuario> listaUsuarios = new ArrayList<>();

    static {
        carregarUsuarios();
    }

    private static void carregarUsuarios() {
        File file = new File(ARQUIVO_USUARIOS);
        int maxId = 0;

        if (!file.exists()) {
            System.out.println("LOG: Arquivo de usuários não encontrado. Criando um novo...");
            Usuario admin = new Usuario("admin", "admin");
            listaUsuarios.add(admin);
            salvarUsuarios();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(",");
                if (partes.length == 3) {
                    int id = Integer.parseInt(partes[0]);
                    String login = partes[1];
                    String hash = partes[2];
                    listaUsuarios.add(new Usuario(id, login, hash));

                    if (id > maxId) {
                        maxId = id;
                    }
                }
            }
            Usuario.setProximoId(maxId + 1);

        } catch (IOException | NumberFormatException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    private static void salvarUsuarios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            for (Usuario user : listaUsuarios) {
                bw.write(user.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    public static List<Usuario> getUsuarios() {
        return listaUsuarios;
    }

    public static void adicionarUsuario(String login, String senha) {
        if (login.isEmpty() || senha.isEmpty()) {
            throw new IllegalArgumentException("Login e senha não podem ser vazios.");
        }
        Usuario novo = new Usuario(login, senha); // O construtor já faz o hash
        listaUsuarios.add(novo);
        salvarUsuarios();
    }

    public static void editarUsuario(Usuario usuario, String novoLogin, String novaSenha) {
        usuario.setLogin(novoLogin);

        if (novaSenha != null && !novaSenha.isEmpty()) {
            usuario.setSenha(novaSenha); // O método já faz o hash
        }

        salvarUsuarios();
    }

    public static void excluirUsuario(Usuario usuario) {
        listaUsuarios.remove(usuario);
        salvarUsuarios();
    }
}