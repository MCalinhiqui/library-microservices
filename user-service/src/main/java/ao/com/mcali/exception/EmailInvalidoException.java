package ao.com.mcali.exception;

public class EmailInvalidoException extends RuntimeException {
    public EmailInvalidoException() {
        super("O email fornecido não é válido!");
    }
    public EmailInvalidoException(String message) {
        super(message);
    }
}
