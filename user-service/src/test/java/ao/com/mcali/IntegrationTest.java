package ao.com.mcali;

import ao.com.mcali.dto.UserDTO;
import ao.com.mcali.dto.UserUpdatedDTO;
import ao.com.mcali.service.IUserService;
import net.datafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class IntegrationTest {

    @Autowired
    private IUserService service;
    private final Faker faker =  new Faker();

    UserUpdatedDTO createUserUpdated(){
        return UserUpdatedDTO.builder()
                .nome("Atualizado")
                .sobrenome("u1ewew")
                .email("ffsdf@gmail.com")
                .build();
    }

    private  List<UserDTO> dtoList() {
        List<UserDTO> dtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int codigo = 1000+i;
            UserDTO user = UserDTO.builder()
                    .codigo((long)codigo)
                    .nome(faker.name().firstName())
                    .sobrenome((faker.name().lastName()))
                    .numBI("123456789AB12"+i)
                    .email("mnhg@gmail.com"+i)
                    .build();
            dtos.add(user);
        }

        return dtos;
    }

    @BeforeEach
    void cadastrar(){
        List<UserDTO> dtos = dtoList();
        dtos.forEach(dto -> service.cadastrar(dto));
        Assertions.assertEquals(10,service.consultarTodos().size());
    }

    @AfterEach
    void deletar(){
        service.consultarTodos().forEach( u -> service.deletar(u.codigo()));
    }

    @Test
    void consultarTodosTest(){
        List<UserDTO> users = service.consultarTodos();
        Assertions.assertNotNull(users);
        Assertions.assertEquals(10,users.size());
    }

    @Test
    void consultarPorCodigoTest(){
        long codigo = 1001L;
        UserDTO user = service.consultarPorCodigo(codigo);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(codigo,user.codigo());
    }

    @Test
    void atualizarTest(){
        long codigo = 1001L;
        UserUpdatedDTO updatedDTO = createUserUpdated();

        UserDTO userUpdated = service.atualizarUsuario(updatedDTO,codigo);

        Assertions.assertNotNull(userUpdated);
        Assertions.assertEquals(codigo,userUpdated.codigo());
        Assertions.assertEquals(updatedDTO.nome(),userUpdated.nome());
        Assertions.assertEquals("123456789AB121",userUpdated.numBI());
    }

    @Test
    void removerTest(){
        service.consultarTodos().forEach(dto -> service.remover(dto.codigo()));
        Assertions.assertEquals(0,service.consultarAtivos().size());
    }

}
