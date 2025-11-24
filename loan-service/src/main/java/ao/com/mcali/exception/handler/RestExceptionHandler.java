package ao.com.mcali.exception.handler;

import ao.com.mcali.exception.CodigoInvalidoException;
import ao.com.mcali.exception.LivroIndisponivelException;
import ao.com.mcali.exception.UsuarioIndisponivelException;
import feign.Feign;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
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

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String,String> erros = new HashMap<>();
        ex.getFieldErrors().forEach(e -> erros.put(e.getField(),e.getDefaultMessage()));
        Map<String,Object> resposta = new HashMap<>();
        resposta.put("Causa","Erro de validação");
        resposta.put("Erros",erros);
        return ResponseEntity.badRequest().body(resposta);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<String> hanlderDataIntegrityViolationException(DataIntegrityViolationException ex){
        return ResponseEntity.unprocessableEntity().body(ex.getMessage());
    }

    @ExceptionHandler(CodigoInvalidoException.class)
    ResponseEntity<String> handlerCodigoInvalidoException(CodigoInvalidoException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(UsuarioIndisponivelException.class)
    ResponseEntity<String> handlerUsuarioIndisponivelException(UsuarioIndisponivelException ex){
        return ResponseEntity.of(Optional.of(ex.getMessage()));
    }

    @ExceptionHandler(LivroIndisponivelException.class)
    ResponseEntity<String> handlerLivroIndisponivelException(LivroIndisponivelException ex){
        return ResponseEntity.of(Optional.of(ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<String> handlerEntityNotFoundException(EntityNotFoundException ex){
        return ResponseEntity.notFound().eTag(ex.getMessage()).build();
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    ResponseEntity<String> handlerUnsupportedOperationException(UnsupportedOperationException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    ResponseEntity<String> handlerSQLException(SQLException ex){
        return ResponseEntity.of(Optional.of(ex.getMessage()));
    }

    @ExceptionHandler(FeignException.class)
    ResponseEntity<String> handlerFeignException(FeignException ex){
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
