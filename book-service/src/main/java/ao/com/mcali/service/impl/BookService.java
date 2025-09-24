package ao.com.mcali.service.impl;

import ao.com.mcali.domain.Book;
import ao.com.mcali.domain.BookStatus;
import ao.com.mcali.dto.BookDTO;
import ao.com.mcali.dto.BookDetailsDTO;
import ao.com.mcali.dto.BookUpdateDTO;
import ao.com.mcali.exception.LivroNaoEncontradoException;
import ao.com.mcali.exception.CodigoInvalidoException;
import ao.com.mcali.exception.TransicaoDeStatusInvalidoException;
import ao.com.mcali.mapper.IBookMapper;
import ao.com.mcali.repository.IBookRepository;
import ao.com.mcali.service.IBookService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService implements IBookService{

    private IBookMapper mapper;
    private IBookRepository repository;


    @Override
    public void cadastrar(BookDTO bookDTO) {
        Book book = mapper.toDomain(bookDTO);
        repository.save(book);
    }

    @Override
    public List<BookDetailsDTO> buscarTodos() {
        List<Book> list = repository.findAll();
        return mapper.toDTOList(list);
    }

    @Override
    public BookDetailsDTO buscarPorCodigo(Long codigo) {
        validarCodigo(codigo);
        Book book = repository.findByCodigo(codigo).orElseThrow(LivroNaoEncontradoException::new);
        return mapper.toDetailsDTO(book);
    }

    @Override
    public List<BookDetailsDTO> buscarPorEstado(BookStatus status) {
        List<Book> listBook = repository.findByStatus(status);
        return mapper.toDTOList(listBook);
    }

    @Override
    public BookDTO atualizar(BookUpdateDTO dtoForUpdating, Long codigo) {
        validarCodigo(codigo);
        Book book = repository.findByCodigo(codigo).orElseThrow(LivroNaoEncontradoException::new);
        mapper.domainFromBookUpdatedDTO(dtoForUpdating,book);
        book = repository.save(book);
        return mapper.toDTO(book);
    }

    @Override
    public void atualizarStatus(BookStatus status, Long codigo) {
        validarCodigo(codigo);
        Book book = repository.findByCodigo(codigo).orElseThrow(() -> new LivroNaoEncontradoException("Não existe nenhum livro registrado com este codigo"));
        validarStatus(book.getStatus(),status);
        book.setStatus(status);
        repository.save(book);
    }

    @Override
    public void deletar(Long codigo) {
        validarCodigo(codigo);
        Book book = repository.findByCodigo(codigo).orElseThrow(() -> new LivroNaoEncontradoException("Não existe nenhum livro registrado com este codigo"));
        if(!(book.getStatus().equals(BookStatus.INDISPONIVEL)))
            throw new UnsupportedOperationException("Não é possível deletar um livro com status "+book.getStatus()+", altere o estado para indisponível!!");
        repository.delete(book);
    }

    private void validarStatus(BookStatus atual, BookStatus novo){
        boolean isValid = switch (atual){
            case INDISPONIVEL, EMPRESTADO -> novo == BookStatus.DISPONIVEL;
            case DISPONIVEL -> novo == BookStatus.EMPRESTADO || novo == BookStatus.INDISPONIVEL;
        };

        if(!isValid)
            throw new TransicaoDeStatusInvalidoException("Não é possível transitar de status "+atual+" para status "+novo);
    }

    private void validarCodigo(Long codigo) {
        if(codigo == null || codigo <= 999)
            throw new CodigoInvalidoException("O código fornecido não é válido");
    }
}
