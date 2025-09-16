package ao.com.mcali.exception;

import jakarta.persistence.EntityNotFoundException;

public class LivroNaoEncontradoException extends EntityNotFoundException {
    public LivroNaoEncontradoException() {
        super("Este livro não foi cadastrado!");
    }

    public LivroNaoEncontradoException(String message) {
        super(message);
    }
}
