package ao.com.mcali;

import ao.com.mcali.domain.Book;
import ao.com.mcali.repository.IBookRepository;

import net.datafaker.Faker;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Year;

import java.util.*;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    IBookRepository bookRepository;

    Faker faker = new Faker();

    List<Book> criarBook(int qtd){
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < qtd; i++) {
            Book book = Book.builder()
                    .codigo(faker.number().numberBetween(0, 9999L))
                    .titulo("teste")
                    .autor("testeAutor")
                    .anoDePublicacao(Year.of(2026))
                    .build();
            books.add(book);
        }
        return books;
    }

    @Test
    void cadastrar(){
        Book book = criarBook(1).get(0);

        Book registeredbook = bookRepository.save(book);
        Assertions.assertNotNull(registeredbook);
        Assertions.assertEquals(book.getCodigo(),registeredbook.getCodigo());
    }

    @Test
    void consultarPorCodigo(){
        Book book = criarBook(1).get(0);
        bookRepository.save(book);

        Optional<Book> registeredbook = bookRepository.findByCodigo(book.getCodigo());
        Assertions.assertNotNull(registeredbook.get());
        Assertions.assertEquals(book.getTitulo(),registeredbook.get().getTitulo());
    }

    @Test
    void consultarPorEstado(){
        List<Book> livros = criarBook(10);
        bookRepository.saveAll(livros::iterator);

        List<Book> livrosCadastrados = bookRepository.findByStatus(Book.Status.EMPRESTADO);
        Assertions.assertTrue(livrosCadastrados.isEmpty());

        livrosCadastrados = bookRepository.findByStatus(Book.Status.INDISPONIVEL);
        Assertions.assertEquals(livrosCadastrados.size(), livros.size());
        for (Book b : livrosCadastrados) {
            System.out.println(b.getCodigo()+" "+b.getStatus());
        }
    }
}
