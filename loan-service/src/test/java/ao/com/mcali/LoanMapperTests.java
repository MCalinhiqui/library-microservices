package ao.com.mcali;

import ao.com.mcali.domain.Loan;
import ao.com.mcali.dto.LoanDTO;
import ao.com.mcali.dto.LoanRegisteredDTO;
import ao.com.mcali.mapper.ILoanMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class LoanMapperTests {

    ILoanMapper mapper = Mappers.getMapper(ILoanMapper.class);

	@Test
	void toEntity(){
        LoanDTO dto = LoanDTO.builder()
                .codigo(1111L)
                .codigoLivro(2222L)
                .codigoUsuario(3333L)
                .build();

        Loan loan = mapper.toEntity(dto);
        Assertions.assertEquals(dto.codigo(),loan.getCodigo());
	}

    @Test
    void toDto(){
        Loan loan = Loan.builder()
                .codigo(1111L)
                .codigoLivro(2222L)
                .codigoUsuario(3333L)
                .build();

        LoanDTO dto = mapper.toDto(loan);
        Assertions.assertEquals(loan.getCodigo(),dto.codigo());
    }

    @Test
    void toRegisteredDto(){
        Loan loan = Loan.builder()
                .codigo(1111L)
                .codigoLivro(2222L)
                .codigoUsuario(3333L)
                .build();

        LoanRegisteredDTO registeredDTO = mapper.toRegisteredDto(loan);
        Assertions.assertEquals(loan.getCodigo(),registeredDTO.codigo());
        Assertions.assertEquals(loan.getDataEmprestimo(), registeredDTO.dataEmprestimo());
    }

}
