package ao.com.mcali;

import ao.com.mcali.domain.User;
import ao.com.mcali.repository.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class IUserRepositoryTest {

    @Autowired
    IUserRepository repository;

    @BeforeEach
    void createUser(){
        User user = User.builder()
                .codigo(123L)
                .numBI("asadasd")
                .nome("Maskia")
                .sobrenome("shkashd")
                .email("czxczx")
                .build();

        repository.save(user);
    }

    @Test
    void buscarPorCodigo(){
        Optional<User> user = repository.findByCodigo(123L);

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals("czxczx", user.get().getEmail());
    }

    @Test
    void buscarPorEmail(){
        Optional<User> user = repository.findByEmail("czxczx");

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(123L, user.get().getCodigo());
    }

    @Test
    void buscarPorNumBI(){
        Optional<User> user = repository.findByNumBI("asadasd");

        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(123L, user.get().getCodigo());
    }
}
