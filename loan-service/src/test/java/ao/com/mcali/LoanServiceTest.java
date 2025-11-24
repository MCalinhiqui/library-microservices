package ao.com.mcali;

import ao.com.mcali.clients.BookClient;
import ao.com.mcali.clients.UserClient;
import ao.com.mcali.domain.Loan;
import ao.com.mcali.domain.LoanStatus;
import ao.com.mcali.dto.BookDetailsDTO;
import ao.com.mcali.dto.LoanDTO;
import ao.com.mcali.dto.LoanRegisteredDTO;
import ao.com.mcali.dto.UserDTO;
import ao.com.mcali.mapper.ILoanMapper;
import ao.com.mcali.repository.ILoanRepository;
import ao.com.mcali.service.ILoanService;
import ao.com.mcali.service.impl.LoanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest{
    @Mock
    ILoanMapper mapper;
    @Mock
    BookClient bookClient;
    @Mock
    UserClient userClient;
    @Mock()
    ILoanRepository repository;

    @InjectMocks
    ILoanService service = new LoanService();

    @Test
    void realizarEmprestimoTest(){
         Loan loan = Loan.builder()
                .id(1L)
                .codigo(1111L)
                .codigoUsuario(2222L)
                .codigoLivro(3333L)
                .build();

         LoanDTO loanDTO = LoanDTO.builder()
                .codigo(loan.getCodigo())
                .codigoUsuario(loan.getCodigoUsuario())
                .codigoLivro(loan.getCodigoLivro()).build();

         UserDTO userDTO = new UserDTO(loanDTO.codigoUsuario(), "Marcos","Mateus", "assaasd", "aasdaads");
         BookDetailsDTO bookDTO = new BookDetailsDTO(loanDTO.codigoLivro(), "Romeu e J", 2020,"sdadsa", BookDetailsDTO.BookStatus.DISPONIVEL);

        Mockito.when(userClient.buscarUsuario(loanDTO.codigoUsuario())).thenReturn(userDTO);
        Mockito.when(bookClient.buscarLivro(loanDTO.codigoLivro())).thenReturn(bookDTO);
        Mockito.when(mapper.toEntity(loanDTO)).thenReturn(loan);
        Mockito.when(repository.save(loan)).thenReturn(loan);
        Mockito.doNothing().when(bookClient).atualizarStatus(BookDetailsDTO.BookStatus.EMPRESTADO, loan.getCodigoLivro());

        service.emprestar(loanDTO);
        Mockito.verify(repository).save(loan);
    }

    @Test
    void consultarComFiltroTest(){
        List<Loan> loanList = new ArrayList<>();
        Loan loan;
        loan = Loan.builder().id(1L).codigo(1111L).codigoUsuario(2222L).codigoLivro(3333L).build();
        loanList.add(loan);
        loan = Loan.builder().id(1L).codigo(1111L).codigoUsuario(2223L).codigoLivro(3333L).build();
        loanList.add(loan);
        loan = Loan.builder().id(1L).codigo(1111L).codigoUsuario(2222L).codigoLivro(4444L).status(LoanStatus.INACTIVE).build();
        loanList.add(loan);

        List<LoanRegisteredDTO> dtoList = new ArrayList<>();
        LoanRegisteredDTO dto;
        dto = LoanRegisteredDTO.builder().codigo(1111L).codigoUsuario(2222L).codigoLivro(3333L).status(LoanStatus.ACTIVE).build();
        dtoList.add(dto);
        dto = LoanRegisteredDTO.builder().codigo(1111L).codigoUsuario(2223L).codigoLivro(4444L).status(LoanStatus.ACTIVE).build();
        dtoList.add(dto);
        dto = LoanRegisteredDTO.builder().codigo(1111L).codigoUsuario(2222L).codigoLivro(3333L).status(LoanStatus.INACTIVE).build();
        dtoList.add(dto);

        Mockito.when(repository.findAll()).thenReturn(loanList);
        Mockito.when(mapper.toRegisteredDto(loanList)).thenReturn(dtoList);

        List<LoanRegisteredDTO> list = service.consultarComFiltro(null,null,null);
        Assertions.assertEquals(3,list.size());

        list = service.consultarComFiltro(LoanStatus.INACTIVE,null,null);
        Assertions.assertEquals(1,list.size());

        list = service.consultarComFiltro(LoanStatus.ACTIVE,null,3333L);
        Assertions.assertEquals(1,list.size());

        list = service.consultarComFiltro(null,2223L,null);
        Assertions.assertEquals(1,list.size());

        list = service.consultarComFiltro(null,2222L,null);
        Assertions.assertEquals(2,list.size());

        Mockito.verify(repository,Mockito.times(5)).findAll();
    }
}
