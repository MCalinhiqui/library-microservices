package ao.com.mcali.converter;

import java.time.Year;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author mcalinhiqui
 */
@Configuration
@Converter(autoApply = true)
public class YearToIntegerConverter implements AttributeConverter<Year, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Year attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    @Override
    public Year convertToEntityAttribute(Integer dbData) {
        return dbData != null ? Year.of(dbData) : null;
    }

}
