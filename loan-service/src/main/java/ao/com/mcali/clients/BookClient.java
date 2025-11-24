package ao.com.mcali.clients;

import ao.com.mcali.config.FeignConfig;
import ao.com.mcali.dto.BookDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service", url = "${book-service.url}", configuration = FeignConfig.class)
public interface BookClient{
    @GetMapping("/books/{codigo}")
    BookDetailsDTO buscarLivro(@PathVariable(name = "codigo") Long codigo);

    @PatchMapping("/books/{codigo}/{status}")
    void atualizarStatus(@PathVariable("status") BookDetailsDTO.BookStatus status, @PathVariable("codigo") Long codigo);
}
