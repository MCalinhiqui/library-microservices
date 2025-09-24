package ao.com.mcali.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user", indexes = @Index(name = "idx_user_codigo", columnList = "codigo, email, num_bi"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long codigo;

    @Column(nullable = false, length = 20)
    private String nome;

    @Column(nullable = false, length = 20)
    private String sobrenome;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "num_bi", unique = true, nullable = false)
    private String numBI;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status = UserStatus.ATIVO;

}
