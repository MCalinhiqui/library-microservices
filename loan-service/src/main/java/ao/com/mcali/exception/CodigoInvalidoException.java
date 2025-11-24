package ao.com.mcali.exception;

public class CodigoInvalidoException extends RuntimeException {
    public CodigoInvalidoException() {
        super("O código fornecido não é válido!");
    }
    public CodigoInvalidoException(String message) {
        super(message);
    }
}
