package ao.com.mcali;

import ao.com.mcali.domain.BookStatus;
import ao.com.mcali.dto.BookDTO;
import ao.com.mcali.dto.BookDetailsDTO;
import ao.com.mcali.dto.BookUpdateDTO;
import ao.com.mcali.exception.CodigoInvalidoException;
import ao.com.mcali.exception.LivroNaoEncontradoException;
import ao.com.mcali.service.IBookService;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    IBookService service;

    Faker faker = new Faker();
    List<BookDTO> criarBookDTO(int qtd){
        List<BookDTO> books = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            BookDTO book = BookDTO.builder()
                    .codigo(faker.number().numberBetween(0, 9999L))
                    .titulo("teste")
                    .autor("testeAutor")
                    .anoDePublicacao(2026)
                    .build();
            books.add(book);
        }
        return books;
    }

    @Test
    void cadastrar(){
        BookDTO bookDTO = criarBookDTO(1).get(0);
        service.cadastrar(bookDTO);
    }

    @Test
    void buscarTodos(){
        List<BookDTO> dtoList = criarBookDTO(5);
        dtoList.forEach(b -> service.cadastrar(b));

        List<BookDetailsDTO> booksCadastrados = service.buscarTodos();

        Assertions.assertFalse(booksCadastrados.isEmpty());
        System.out.println(booksCadastrados);
    }

    @Test
    void buscarPorCodigo(){
        BookDTO bookDTO = criarBookDTO(1).get(0);
        service.cadastrar(bookDTO);

        BookDetailsDTO bookCadastrado = service.buscarPorCodigo(bookDTO.codigo());

        Assertions.assertNotNull(bookCadastrado);
        Assertions.assertEquals(bookDTO.titulo(), bookCadastrado.titulo());
    }

    @Test
    void consultarPorCodigoComLançamentoDeExceçãoCodigoInvalidoException(){
        Assertions.assertThrows(CodigoInvalidoException.class, () -> service.buscarPorCodigo(-352L));
    }

    @Test
    void consultarPorCodigoComLançamentoDeExceçãoLivroNaoEncontradoException(){
        Assertions.assertThrows(LivroNaoEncontradoException.class, () -> service.buscarPorCodigo(352L));
    }

    @Test
    void consultarPorEstado(){
        List<BookDTO> dtoList = criarBookDTO(5);
        dtoList.forEach(b -> service.cadastrar(b));

        List<BookDetailsDTO> books = service.buscarPorEstado(BookStatus.INDISPONIVEL);

        Assertions.assertFalse(books.isEmpty());
        System.out.println(books);
    }

    @Test
    void atualizarLivro(){
        BookUpdateDTO dtoForUpdating = new BookUpdateDTO(
                 "Atualizado"
                ,"AutorAtualizado"
                ,2025);

        BookDTO bookDTO = criarBookDTO(1).get(0);
        service.cadastrar(bookDTO);

        bookDTO = service.atualizar(dtoForUpdating, bookDTO.codigo());

        Assertions.assertEquals(bookDTO.titulo(),dtoForUpdating.titulo());
        Assertions.assertEquals(bookDTO.autor(),dtoForUpdating.autor());
        Assertions.assertEquals(bookDTO.anoDePublicacao(),dtoForUpdating.anoDePublicacao());
    }

    @Test
    void atualizarEstadoDoLivro(){
        BookDTO bookDTO = criarBookDTO(1).get(0);
        service.cadastrar(bookDTO);
        service.atualizarStatus(BookStatus.DISPONIVEL, bookDTO.codigo());
    }

    @Test
    void deletar(){
        BookDTO bookDTO = criarBookDTO(1).get(0);
        service.cadastrar(bookDTO);
        service.deletar(bookDTO.codigo());
    }
}
