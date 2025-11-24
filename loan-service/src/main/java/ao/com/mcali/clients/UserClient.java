package ao.com.mcali.clients;

import ao.com.mcali.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-client", url = "${user-service.url}")
public interface UserClient {
    @GetMapping("/users/{codigo}")
    UserDTO buscarUsuario(@PathVariable("codigo") Long codigo);
}
