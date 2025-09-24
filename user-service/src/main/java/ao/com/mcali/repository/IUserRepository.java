package ao.com.mcali.repository;

import ao.com.mcali.domain.User;
import ao.com.mcali.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCodigo(Long codigo);
    Optional<User> findByEmail(String email);
    Optional<User> findByNumBI(String numBI);
    default void softDelete(User user){
        user.setStatus(UserStatus.INATIVO);
        this.save(user);
    }
}
