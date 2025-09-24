package ao.com.mcali.exception;

import jakarta.persistence.EntityNotFoundException;

public class UserNaoEncontradoException extends EntityNotFoundException {
    public UserNaoEncontradoException() {
        super("Este usuário não foi cadastrado!");
    }
    public UserNaoEncontradoException(String message) {
        super(message);
    }
}
