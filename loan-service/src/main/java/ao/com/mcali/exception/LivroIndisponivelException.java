package ao.com.mcali.exception;

public class LivroIndisponivelException extends RuntimeException{
    public LivroIndisponivelException() {
        super("Este livro não está disponível!!");
    }
    public LivroIndisponivelException(String message) {
        super(message);
    }
}
