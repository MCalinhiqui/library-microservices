package ao.com.mcali.mapper;

import org.mapstruct.Mapper;

import java.time.Year;

@Mapper(componentModel = "spring")
public interface IYearToIntegerMapper {
    default Year toYear(Integer ano_lancamento){
        return ano_lancamento != null ? Year.of(ano_lancamento) : null;
    }
    default Integer toInteger(Year ano_lancamento){
        return ano_lancamento != null ? ano_lancamento.getValue() : null;
    }
}
