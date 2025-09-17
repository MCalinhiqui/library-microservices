package ao.com.mcali;

import ao.com.mcali.domain.Book;
import ao.com.mcali.dto.BookDTO;
import ao.com.mcali.dto.BookUpdatedDTO;
import ao.com.mcali.mapper.IBookMapper;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Year;

class BookMapperTest {

    private IBookMapper bookMapper = Mappers.getMapper(IBookMapper.class);
    Faker faker = new Faker();

    @Test
    void convertingDomainToDTO(){
        Book book = Book.builder()
                .titulo("Teste")
                .codigo(9992L)
                .anoDePublicacao(Year.of(2024))
                .autor("TesteAutor")
                .build();

        BookDTO bookDTO = bookMapper.toDTO(book);

        Assertions.assertNotNull(bookDTO);
        Assertions.assertEquals(bookDTO.codigo(),book.getCodigo());
        Assertions.assertEquals(bookDTO.titulo(),book.getTitulo());
        Assertions.assertEquals(bookDTO.anoDePublicacao(),book.getAnoDePublicacao().getValue());
        Assertions.assertEquals(bookDTO.autor(),book.getAutor());
    }

    @Test
    void convertingDTOToDomain(){
        BookDTO bookDTO = BookDTO.builder()
                .titulo("Teste")
                .codigo(9992L)
                .anoDePublicacao(2024)
                .autor("TesteAutor")
                .build();

        Book book = bookMapper.toDomain(bookDTO);

        Assertions.assertEquals(book.getCodigo(),bookDTO.codigo());
        Assertions.assertEquals(book.getTitulo(),bookDTO.titulo());
        Assertions.assertEquals(book.getAnoDePublicacao().getValue(),bookDTO.anoDePublicacao());
        Assertions.assertEquals(book.getAutor(),bookDTO.autor());
    }

    @Test
    void updatingDomainFromBookUpdatedDTO(){
        Book book = Book.builder()
                .titulo("Teste")
                .codigo(9992L)
                .anoDePublicacao(Year.of(2024))
                .autor("TesteAutor")
                .build();
        Integer ano = book.getAnoDePublicacao().getValue();
        String autor = book.getAutor();

        BookUpdatedDTO dtoForUpdating = new BookUpdatedDTO("atualizado",null,null);
        bookMapper.domainFromBookUpdatedDTO(dtoForUpdating,book);

        Assertions.assertEquals(dtoForUpdating.titulo(),book.getTitulo());
        Assertions.assertEquals(ano,book.getAnoDePublicacao().getValue());
        Assertions.assertEquals(autor,book.getAutor());
    }
}
