package ao.com.mcali.mapper;

import ao.com.mcali.domain.Loan;
import ao.com.mcali.dto.LoanDTO;
import ao.com.mcali.dto.LoanRegisteredDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ILoanMapper {

    Loan toEntity(LoanDTO loanDTO);
    LoanDTO toDto(Loan loan);
    default LoanRegisteredDTO toRegisteredDto(Loan loan){
        if ( loan == null ) {
            return null;
        }

        LoanRegisteredDTO.LoanRegisteredDTOBuilder loanRegisteredDTO = LoanRegisteredDTO.builder();

        loanRegisteredDTO.codigo( loan.getCodigo() );
        loanRegisteredDTO.codigoUsuario( loan.getCodigoUsuario() );
        loanRegisteredDTO.codigoLivro( loan.getCodigoLivro() );
        loanRegisteredDTO.dataEmprestimo( loan.getDataEmprestimo() );
        loanRegisteredDTO.dataDevolucao( loan.getDataDevolucao() );
        loanRegisteredDTO.dataLimiteDevolucao( loan.getDataLimiteDevolucao() );
        loanRegisteredDTO.atrasado(LocalDateTime.now().isAfter(loan.getDataLimiteDevolucao()));
        loanRegisteredDTO.status( loan.getStatus() );

        if(loan.getDataDevolucao() != null)
            loanRegisteredDTO.atrasado(loan.getDataDevolucao().isAfter(loan.getDataLimiteDevolucao()));

        return loanRegisteredDTO.build();
    }
    List<LoanRegisteredDTO> toRegisteredDto(List<Loan> loan);
}