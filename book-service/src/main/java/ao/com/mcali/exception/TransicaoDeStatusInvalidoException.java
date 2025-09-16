package ao.com.mcali.exception;

public class TransicaoDeStatusInvalidoException extends RuntimeException {
    public TransicaoDeStatusInvalidoException() {
        super();
    }
    public TransicaoDeStatusInvalidoException(String s) {
        super(s);
    }
}
