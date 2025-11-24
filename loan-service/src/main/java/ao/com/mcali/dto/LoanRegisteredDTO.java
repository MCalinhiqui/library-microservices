package ao.com.mcali.dto;

import ao.com.mcali.domain.LoanStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Schema(name = "Registered Loan", description = "Dados de um emprestimo cadastrado")
public record LoanRegisteredDTO (

        @JsonProperty
        @Schema(name = "Codigo do emprestimo", example = "1111")
        Long codigo,

        @JsonProperty
        @Schema(name = "Codigo do usuário", example = "1111")
        Long codigoUsuario,

        @JsonProperty
        @Schema(name = "Codigo do Livro", example = "1111")
        Long codigoLivro,

        @JsonProperty
        @Schema(name = "Data de empréstimo", description = "Periodo em que o livro foi emprestado", example = "2025-09-24 12:55")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:MM")
        LocalDateTime dataEmprestimo,

        @JsonProperty
        @Schema(name = "Data de devolução", description = "Periodo em que o livro foi devolvido", example = "2025-09-24 12:55")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:MM")
        LocalDateTime dataDevolucao,

        @JsonProperty
        @Schema(name = "Data limite para devolução", description = "Periodo em que o livro deve ser devolvido", example = "2025-09-24 12:55")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:MM")
        LocalDateTime dataLimiteDevolucao,

        @JsonProperty
        @Schema(name = "Atrasado", description = "Informa se este emprestimo ultrapassou o prazo de devolução!!", examples = {"true","false"})
        Boolean atrasado,

        @JsonProperty
        @Schema(name = "Status", description = "Status do empréstimo", examples = {"Active","Inactive","Delayed"})
        LoanStatus status

){
}
