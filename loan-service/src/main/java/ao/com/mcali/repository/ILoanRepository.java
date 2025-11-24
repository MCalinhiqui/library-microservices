package ao.com.mcali.repository;

import ao.com.mcali.domain.Loan;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface ILoanRepository extends JpaRepository<Loan,Long>{
    Optional<Loan> findByCodigo(Long codigo);
}
