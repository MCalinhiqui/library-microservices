package ao.com.mcali.exception;

public class UsuarioIndisponivelException extends RuntimeException {
    public UsuarioIndisponivelException(String message) {
        super(message);
    }

    public UsuarioIndisponivelException() {
        super("Não foi possível processar os dados deste usuário!");
    }
}
