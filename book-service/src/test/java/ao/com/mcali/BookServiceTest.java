package ao.com.mcali;

import ao.com.mcali.domain.Book;
import ao.com.mcali.dto.BookDTO;
import ao.com.mcali.dto.BookUpdatedDTO;
import ao.com.mcali.exception.CodigoInvalidoException;
import ao.com.mcali.mapper.IBookMapper;
import ao.com.mcali.repository.IBookRepository;
import ao.com.mcali.service.IBookService;
import ao.com.mcali.service.impl.BookService;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    IBookRepository repository;

    @Mock
    IBookMapper mapper;

    @InjectMocks
    IBookService service = new BookService(mapper, repository);

    Faker faker = new Faker();
    List<BookDTO> criarBookDTO(int qtd){
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            BookDTO book = BookDTO.builder()
                    .codigo(faker.number().numberBetween(1000L, 9999L))
                    .titulo("teste")
                    .autor("testeAutor")
                    .anoDePublicacao(2026)
                    .build();
            books.add(book);
        }
        return books;
    }
    List<Book> criarBook(int qtd){
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            Book book = Book.builder()
                    .id(12L)
                    .codigo(faker.number().numberBetween(0, 9999L))
                    .titulo("teste")
                    .autor("testeAutor")
                    .anoDePublicacao(Year.of(2026))
                    .status(Book.Status.INDISPONIVEL)
                    .build();
            books.add(book);
        }
        return books;
    }

    @Test
    void cadastrar(){
        BookDTO bookDTO = criarBookDTO(1).get(0);
        Book book = mapper.toDomain(bookDTO);

        when(mapper.toDomain(bookDTO)).thenReturn(book);
        when(repository.save(book)).thenReturn(book);

        service.cadastrar(bookDTO);
        verify(repository,times(1)).save(book);
    }

    @Test
    void buscarTodos(){
        List<BookDTO> dtoList = criarBookDTO(5);
        List<Book> list = mapper.toDomainList(dtoList);

        when(repository.findAll()).thenReturn(list);
        when(mapper.toDTOList(list)).thenReturn(dtoList);
        List<BookDTO> booksCadastrados = service.buscarTodos();

        verify(repository,times(1)).findAll();
        Assertions.assertFalse(booksCadastrados.isEmpty());
        System.out.println(booksCadastrados);
    }

    @Test
    void buscarPorCodigo(){
        BookDTO bookDTO = criarBookDTO(1).get(0);
        Book book = Book.builder()
                .titulo(bookDTO.titulo())
                .codigo(bookDTO.codigo())
                .autor(bookDTO.autor())
                .anoDePublicacao(Year.of(bookDTO.anoDePublicacao()))
                .build();

        when(repository.findByCodigo(bookDTO.codigo())).thenReturn(Optional.of(book));
        when(mapper.toDTO(book)).thenReturn(bookDTO);
        BookDTO bookCadastrado = service.buscarPorCodigo(bookDTO.codigo());

        verify(repository,times(1)).findByCodigo(bookDTO.codigo());
        Assertions.assertNotNull(bookCadastrado);
        Assertions.assertEquals(bookDTO.codigo(), bookCadastrado.codigo());
    }

    @Test
    void consultarPorCodigoComLançamentoDeExceçãoCodigoInvalidoException(){
        Assertions.assertThrows(CodigoInvalidoException.class, () -> service.buscarPorCodigo(-352L));
    }

    @Test
    void consultarPorEstado(){
        List<BookDTO> dtoList = criarBookDTO(5);
        List<Book> list = mapper.toDomainList(dtoList);

        when(repository.findByStatus(Book.Status.INDISPONIVEL)).thenReturn(list);
        when(mapper.toDTOList(list)).thenReturn(dtoList);
        List<BookDTO> books = service.buscarPorEstado(Book.Status.INDISPONIVEL);

        verify(repository,times(1)).findByStatus(Book.Status.INDISPONIVEL);

        Assertions.assertFalse(books.isEmpty());
        System.out.println(books);
    }

    @Test
    void atualizarLivro(){
        BookUpdatedDTO dtoForUpdating = new BookUpdatedDTO(
                "Atualizado"
                ,"AutorAtualizado"
                ,2025);

        Book book = Book.builder()
                .id(12L)
                .codigo(1111L)
                .titulo("sdfsf")
                .autor("sdfsdf")
                .anoDePublicacao(Year.of(2002))
                .status(Book.Status.DISPONIVEL)
                .build();

        Book bookUpdated = Book.builder()
                .id(12L)
                .codigo(1111L)
                .titulo(dtoForUpdating.titulo())
                .autor(dtoForUpdating.autor())
                .anoDePublicacao(Year.of(dtoForUpdating.anoDePublicacao()))
                .status(Book.Status.DISPONIVEL)
                .build();

        BookDTO bookDTO = BookDTO.builder()
                .codigo(1111L)
                .titulo(dtoForUpdating.titulo())
                .autor(dtoForUpdating.autor())
                .anoDePublicacao(dtoForUpdating.anoDePublicacao())
                .build();


        Long codigo = 1111L;

        when(repository.findByCodigo(codigo)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(bookUpdated);
        when(mapper.toDTO(bookUpdated)).thenReturn(bookDTO);

        BookDTO bookAtualizado = service.atualizar(dtoForUpdating, codigo);

        verify(repository,times(1)).findByCodigo(codigo);
        verify(repository,times(1)).save(book);

        Assertions.assertNotNull(bookUpdated);
        Assertions.assertEquals(bookAtualizado.titulo(),dtoForUpdating.titulo());
        Assertions.assertEquals(bookAtualizado.autor(),dtoForUpdating.autor());
        Assertions.assertEquals(bookAtualizado.anoDePublicacao(),dtoForUpdating.anoDePublicacao());
    }

    @Test
    void atualizarStatus(){
        Book.Status status =  Book.Status.INDISPONIVEL;
        Long codigo = 1234L;

        Book book = Book.builder()
                .id(12L)
                .codigo(codigo)
                .titulo("sdfsf")
                .autor("sdfsdf")
                .anoDePublicacao(Year.of(2002))
                .status(Book.Status.DISPONIVEL)
                .build();

        when(repository.findByCodigo(codigo)).thenReturn(Optional.of(book));
        when(repository.save(book)).thenReturn(book);

        service.atualizarStatus(status,codigo);

        verify(repository,times(1)).findByCodigo(codigo);
        verify(repository,times(1)).save(book);
    }

    @Test
    void deletar(){
        Book book = Book.builder()
                .titulo(faker.name().title())
                .codigo(1234L)
                .autor(faker.name().maleFirstName()+" "+faker.name().lastName())
                .anoDePublicacao(Year.of(2020))
                .status(Book.Status.INDISPONIVEL)
                .build();

        when(repository.findByCodigo(book.getCodigo())).thenReturn(Optional.of(book));
        doNothing().when(repository).delete(book);

        service.deletar(book.getCodigo());

        verify(repository,times(1)).findByCodigo(book.getCodigo());
        verify(repository,times(1)).delete(book);
    }
}
