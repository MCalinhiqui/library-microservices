package ao.com.mcali.exception;

public class NumBIInvalidoException extends RuntimeException {
    public NumBIInvalidoException() {
        super("Numero de BI inválido!");
    }

    public NumBIInvalidoException(String message) {
        super(message);
    }
}
