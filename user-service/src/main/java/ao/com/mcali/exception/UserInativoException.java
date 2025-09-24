package ao.com.mcali.exception;

public class UserInativoException extends RuntimeException {
    public UserInativoException() {
        super("Este usuário está inativo!!!");
    }
    public UserInativoException(String msg) {
        super(msg);
    }
}
