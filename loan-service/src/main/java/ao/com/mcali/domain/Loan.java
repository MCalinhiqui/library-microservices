package ao.com.mcali.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_loan", indexes = @Index(name = "idx_loan", columnList = "codigo,codigoUsuario,codigoLivro,status"))
public class Loan{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long codigo;

    @Column(nullable = false)
    private Long codigoUsuario;

    @Column(nullable = false)
    private Long codigoLivro;

    @Builder.Default
    private LocalDateTime dataEmprestimo = LocalDateTime.now();

    private LocalDateTime dataDevolucao;

    private LocalDateTime dataLimiteDevolucao;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.ACTIVE;
}
