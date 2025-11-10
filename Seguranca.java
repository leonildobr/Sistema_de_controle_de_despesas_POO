import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Seguranca {


    public static String hashSenha(String senhaPlana) {
        if (senhaPlana == null || senhaPlana.isEmpty()) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senhaPlana.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular hash da senha", e);
        }
    }
}