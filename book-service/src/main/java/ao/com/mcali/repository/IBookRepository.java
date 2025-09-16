package ao.com.mcali.repository;

import ao.com.mcali.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
    List<Book> findByStatus(Book.Status status);
    Optional<Book> findByCodigo(Long codigo);
}
