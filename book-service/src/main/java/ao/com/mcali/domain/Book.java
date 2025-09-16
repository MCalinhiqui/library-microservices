package ao.com.mcali.domain;

import java.time.Year;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Book.
 *
 * @author mcalinhiqui
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_book")
@Schema(description = "Representa um livro na biblioteca")
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Schema(description = "identificador unico do livro na bd", example = "1")
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    @Schema(description = "Codigo do livro", example = "1000")
    private Long codigo;

    @Column(name = "titulo", nullable = false)
    @Schema(description = "Título do livro", minLength = 5, maxLength = 100, example = "Romeu e Julieta")
    private String titulo;

    @Column(name = "anoDePublicacao", nullable = false)
    @Schema(description = "Ano de lançamento do livro", example = "1995")
    private Year anoDePublicacao;

    @Column(name = "autor", nullable = false)
    @Schema(description = "Nome do autor do livro", minLength = 5, maxLength = 50, example = "Jorge P. Junior")
    private String autor;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Schema(description = "Informação sobre a disponibilidade do livro",
            example = "Alugado", defaultValue = "INDISPONIVEL",
            allowableValues = {"INDISPONIVEL","DISPONIVEL","ALUGADO"})
    private Status status = Status.INDISPONIVEL;


    public enum Status{
        @Schema(description = "Livro fora de circulação")
        INDISPONIVEL,
        @Schema(description = "Livro disponível")
        DISPONIVEL,
        @Schema(description = "Livro emprestado por um cliente")
        EMPRESTADO
    }
}
