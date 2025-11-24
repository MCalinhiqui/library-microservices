package ao.com.mcali.service.impl;

import ao.com.mcali.clients.BookClient;
import ao.com.mcali.clients.UserClient;
import ao.com.mcali.domain.Loan;
import ao.com.mcali.domain.LoanStatus;
import ao.com.mcali.dto.BookDetailsDTO;
import ao.com.mcali.dto.LoanDTO;
import ao.com.mcali.dto.LoanRegisteredDTO;
import ao.com.mcali.dto.UserDTO;
import ao.com.mcali.exception.CodigoInvalidoException;
import ao.com.mcali.exception.LivroIndisponivelException;
import ao.com.mcali.exception.UsuarioIndisponivelException;
import ao.com.mcali.mapper.ILoanMapper;
import ao.com.mcali.repository.ILoanRepository;
import ao.com.mcali.service.ILoanService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@NoArgsConstructor
public class LoanService implements ILoanService {
    private  ILoanMapper mapper;
    private  BookClient bookClient;
    private  UserClient userClient;
    private  ILoanRepository repository;

    @Value("${loan.default-period-days:7}")
    private Long periodDays;

    @Autowired
    public LoanService(ILoanMapper mapper, BookClient bookClient, UserClient userClient, ILoanRepository repository) {
        this.mapper = mapper;
        this.bookClient = bookClient;
        this.userClient = userClient;
        this.repository = repository;
    }

    @Override
    public void emprestar(LoanDTO dto){
        Long codigoUsuario = dto.codigoUsuario();
        Long codigoLivro = dto.codigoLivro();

        UserDTO userDTO = userClient.buscarUsuario(codigoUsuario);
        BookDetailsDTO bookDetailsDTO = bookClient.buscarLivro(codigoLivro);

        validarDTOs(userDTO, bookDetailsDTO);

        Loan loan = mapper.toEntity(dto);
        loan.setDataLimiteDevolucao(loan.getDataEmprestimo().plusDays(periodDays));

        repository.save(loan);
        bookClient.atualizarStatus(BookDetailsDTO.BookStatus.EMPRESTADO, codigoLivro);
    }

    @Override
    public List<LoanRegisteredDTO> consultarTodos() {
        List<Loan> list = repository.findAll();
        return mapper.toRegisteredDto(list);
    }

    @Override
    public LoanRegisteredDTO consultar(Long codigoEmprestimo) {
        validarCodigo(codigoEmprestimo);
        Loan loan = repository.findByCodigo(codigoEmprestimo).orElseThrow(() -> new EntityNotFoundException("Emprestimo não encontrado!!"));
        return mapper.toRegisteredDto(loan);
    }

    @Override
    public List<LoanRegisteredDTO> consultarComFiltro(LoanStatus status, Long codigoUsuario, Long codigoLivro){
        List<Loan> list = repository.findAll();
        List<LoanRegisteredDTO> dtos = mapper.toRegisteredDto(list);

        if(status != null)
            dtos = dtos.stream().filter(loan -> loan.status().equals(status)).toList();
        if(codigoUsuario != null && codigoUsuario >= 1000)
            dtos = dtos.stream().filter(loan -> loan.codigoUsuario().equals(codigoUsuario)).toList();
        if(codigoLivro != null && codigoLivro >= 1000)
            dtos = dtos.stream().filter(loan -> loan.codigoLivro().equals(codigoLivro)).toList();

        return dtos;
    }

    @Override
    public LoanRegisteredDTO devolver(Long codigoEmprestimo) {
        validarCodigo(codigoEmprestimo);
        Loan loan = repository.findByCodigo(codigoEmprestimo).orElseThrow(() -> new EntityNotFoundException("Emprestimo não encontrado!!"));

        if(loan.getStatus().equals(LoanStatus.INACTIVE))
            throw new UnsupportedOperationException("Impossível prosseguir, este emprestimo não está ativo!!");

        loan.setDataDevolucao(LocalDateTime.now());
        loan.setStatus(LoanStatus.INACTIVE);
        loan = repository.save(loan);

        bookClient.atualizarStatus(BookDetailsDTO.BookStatus.DISPONIVEL, loan.getCodigoLivro());

        return mapper.toRegisteredDto(loan);
    }

    @Override
    public void excluirEmprestimo(Long codigoEmprestimo) {
        validarCodigo(codigoEmprestimo);
        Loan loan = repository.findByCodigo(codigoEmprestimo).orElseThrow(() -> new EntityNotFoundException("Emprestimo não encontrado!!"));
        if(loan.getStatus().equals(LoanStatus.ACTIVE))
            bookClient.atualizarStatus(BookDetailsDTO.BookStatus.DISPONIVEL, loan.getCodigoLivro());
        repository.delete(loan);
    }

    private void validarCodigo(Long codigo) {
        if(codigo == null || codigo < 1000)
            throw new CodigoInvalidoException();
    }

    private void validarDTOs(UserDTO userDTO, BookDetailsDTO bookDetailsDTO){
        if(userDTO == null)
            throw new UsuarioIndisponivelException();
        if(bookDetailsDTO == null)
            throw new LivroIndisponivelException("Não foi possível processar os dados deste livro!!");

        boolean hasUserActiveLoan = !consultarComFiltro(LoanStatus.ACTIVE,userDTO.codigo(),null).isEmpty();
        if(hasUserActiveLoan)
            throw new UnsupportedOperationException("Este usuário já possui um emprestimo pendente!!");

        BookDetailsDTO.BookStatus status = bookDetailsDTO.status();
        if(status != BookDetailsDTO.BookStatus.DISPONIVEL)
            throw new LivroIndisponivelException("Impossível prosseguir. este livro está " + status.name().toLowerCase());
    }
}
