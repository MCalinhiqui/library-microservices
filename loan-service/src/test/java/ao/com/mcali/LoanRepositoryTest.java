package ao.com.mcali;

import ao.com.mcali.domain.Loan;
import ao.com.mcali.repository.ILoanRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class LoanRepositoryTest {
    @Autowired
    ILoanRepository repository;

    Loan createLoan1(){
        return Loan.builder()
                .codigo(1111L)
                .codigoLivro(5555L)
                .codigoUsuario(6666L)
                .build();
    }

    @Test
    void findByCodigo(){
        repository.save(createLoan1());
        Optional<Loan> loan = repository.findByCodigo(1111L);
        Assertions.assertTrue(loan.isPresent());
        Assertions.assertEquals(5555L,loan.get().getCodigoLivro());
    }
}
