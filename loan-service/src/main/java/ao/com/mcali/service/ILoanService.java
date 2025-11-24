package ao.com.mcali.service;

import ao.com.mcali.domain.LoanStatus;
import ao.com.mcali.dto.LoanDTO;
import ao.com.mcali.dto.LoanRegisteredDTO;

import java.util.List;

public interface ILoanService {
    void emprestar(LoanDTO dto);
    LoanRegisteredDTO consultar(Long codigoEmprestimo);
    List<LoanRegisteredDTO> consultarTodos();
    List<LoanRegisteredDTO> consultarComFiltro(LoanStatus status, Long codigoUsuario, Long codigoLivro);
    LoanRegisteredDTO devolver(Long codigoEmprestimo);
    void excluirEmprestimo(Long codigoEmprestimo);
}
