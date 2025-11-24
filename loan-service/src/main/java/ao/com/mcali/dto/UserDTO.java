package ao.com.mcali.dto;

public record UserDTO(
        Long codigo,
        String nome,
        String sobrenome,
        String email,
        String numBI
) {
}
