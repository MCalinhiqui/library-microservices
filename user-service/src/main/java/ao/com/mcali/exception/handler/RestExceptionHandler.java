package ao.com.mcali.exception.handler;

import ao.com.mcali.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> erros = new HashMap<>();
            ex.getFieldErrors().forEach(e -> erros.put(e.getField(),e.getDefaultMessage()));
            Map<String,Object> resposta = new HashMap<>();
            resposta.put("Erro","Falha de validação");
            resposta.put("Erro",erros);
            return ResponseEntity.badRequest().body(resposta);
    }



    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<String> hanlderDataIntegrityViolationException(DataIntegrityViolationException ex){
        return ResponseEntity.unprocessableEntity().body(ex.getMessage());
    }

    @ExceptionHandler(UserNaoEncontradoException.class)
    protected ResponseEntity<String> handlerLivroNaoEncontradoException(UserNaoEncontradoException ex){
        return ResponseEntity.notFound().eTag(ex.getMessage()).build();
    }

    @ExceptionHandler(CodigoInvalidoException.class)
    protected ResponseEntity<String> handlerCodigoInvalidoException(CodigoInvalidoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(EmailInvalidoException.class)
    protected ResponseEntity<String> handlerEmailInvalidoException(EmailInvalidoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(NumBIInvalidoException.class)
    protected ResponseEntity<String> handlerNumBIInvalidoException(NumBIInvalidoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UserInativoException.class)
    protected ResponseEntity<String> handlerUserCadastradoException(UserInativoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
