package ao.com.mcali.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
        INATIVO(0),
        ATIVO(1);
        private final Integer codigo;
}
