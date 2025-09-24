package ao.com.mcali.domain;

import java.time.Year;

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
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private Long codigo;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "anoDePublicacao", nullable = false)
    private Year anoDePublicacao;

    @Column(name = "autor", nullable = false)
    private String autor;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status = BookStatus.INDISPONIVEL;
}
