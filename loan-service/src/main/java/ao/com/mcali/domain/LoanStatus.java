package ao.com.mcali.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum LoanStatus {
    ACTIVE,
    INACTIVE
}
