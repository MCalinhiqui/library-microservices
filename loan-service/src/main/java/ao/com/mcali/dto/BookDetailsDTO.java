package ao.com.mcali.dto;

public record BookDetailsDTO(
    Long codigo,
    String titulo,
    Integer anoDePublicacao,
    String autor,
    BookStatus status
)
{
    public enum BookStatus {
        INDISPONIVEL,
        DISPONIVEL,
        EMPRESTADO
    }
}
