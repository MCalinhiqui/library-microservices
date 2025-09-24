package ao.com.mcali;

import ao.com.mcali.domain.User;
import ao.com.mcali.domain.UserStatus;
import ao.com.mcali.dto.UserDTO;
import ao.com.mcali.dto.UserUpdatedDTO;
import ao.com.mcali.exception.CodigoInvalidoException;
import ao.com.mcali.exception.UserNaoEncontradoException;
import ao.com.mcali.mapper.UserMapper;
import ao.com.mcali.repository.IUserRepository;
import ao.com.mcali.service.IUserService;
import ao.com.mcali.service.impl.UserService;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest{

    Faker faker = new Faker();

    @Mock
    IUserRepository repository;

    @Mock
    UserMapper mapper;

    @InjectMocks
    IUserService service = new UserService();

    UserDTO createDTO(User user){
        return UserDTO.builder()
                .codigo(user.getCodigo())
                .nome(user.getNome())
                .sobrenome(user.getSobrenome())
                .numBI(user.getNumBI())
                .email(user.getEmail())
                .build();
    }

    User createUser(){
        return User.builder()
                .codigo(1L)
                .codigo(1234L)
                .nome("user")
                .sobrenome("u1")
                .numBI("123456789AB123")
                .email("ffsdfsd@gmail.com")
                .build();
    }

    UserUpdatedDTO createUserUpdated(){
        return UserUpdatedDTO.builder()
                .nome("Atualizado")
                .sobrenome("u1")
                .email("ffsdf@gmail.com")
                .build();
    }

    private  List<User> userList() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = User.builder()
                    .id((long)i)
                    .codigo(faker.number().randomNumber(4))
                    .nome(faker.name().firstName())
                    .sobrenome((faker.name().lastName()))
                    .numBI(faker.examplify("sdgsdgsg"))
                    .email(faker.examplify("mnhg@gmail.com"))
                    .build();
            users.add(user);
        }

        return users;
    }

    private  List<UserDTO> dtoList(List<User> users) {
        List<UserDTO> dtos = new ArrayList<>();

        for (User user: users) {
            UserDTO dto = UserDTO.builder()
                    .codigo(user.getCodigo())
                    .nome(user.getNome())
                    .sobrenome(user.getSobrenome())
                    .numBI(user.getNumBI())
                    .email(user.getEmail())
                    .build();
            dtos.add(dto);
        }

        return dtos;
    }

    @Test
    void cadastrarTest(){
        User user = createUser();
        UserDTO dto = createDTO(user);

        when(mapper.toEntity(dto)).thenReturn(user);
        when(repository.save(user)).thenReturn(user);

        service.cadastrar(dto);
        verify(repository).save(user);
    }

    @Test
    void consultarTodosTest(){
        List<User> users = userList();
        List<UserDTO> dtos = dtoList(users);

        when(repository.findAll()).thenReturn(users);
        when(mapper.toDTOList(users)).thenReturn(dtos);

        List<UserDTO> userRegistereds = service.consultarTodos();

        verify(repository).findAll();
        Assertions.assertNotNull(userRegistereds);
        Assertions.assertEquals(users.size(), userRegistereds.size());
    }

    @Test
    void consultarAtivosTest(){
        List<User> users = userList().stream()
                .peek(user -> {
                    if(user.getId()%2 == 0)
                        user.setStatus(UserStatus.INATIVO);
                }).toList();

        List<User> activeUsers = users.stream()
                .filter(u -> u.getStatus().getCodigo().equals(1)).toList();

        List<UserDTO> dtos = dtoList(activeUsers);

        when(repository.findAll()).thenReturn(activeUsers);
        when(mapper.toDTOList(activeUsers)).thenReturn(dtos);

        List<UserDTO> userRegistereds = service.consultarAtivos();

        verify(repository).findAll();

        Assertions.assertNotNull(userRegistereds);
        Assertions.assertNotEquals(users.size(), userRegistereds.size());
    }

    @Test
    void consultarPorCodigoTest(){
        User user = createUser();
        UserDTO dto = createDTO(user);

        when(repository.findByCodigo(user.getCodigo())).thenReturn(Optional.of(user));
        when(mapper.toDto(user)).thenReturn(dto);

        UserDTO userRegistered = service.consultarPorCodigo(user.getCodigo());

        verify(repository).findByCodigo(user.getCodigo());
        Assertions.assertNotNull(userRegistered);
        Assertions.assertEquals(user.getNumBI(), userRegistered.numBI());
    }

    @Test
    void consultarPorCodigoComExcessaoDeCodigoInvalidoTest(){
        Assertions.assertThrows(CodigoInvalidoException.class, () -> service.consultarPorCodigo(12L));
    }

    @Test
    void consultarPorCodigoComExcessaoUsuarioNaoEncontradoTest(){
        when(repository.findByCodigo(1234L)).thenThrow(new UserNaoEncontradoException());
        Assertions.assertThrows(UserNaoEncontradoException.class, () -> repository.findByCodigo(1234L));
    }

    @Test
    void consultarPorCodigoComStatusInativoTest(){
        User user = createUser();
        user.setStatus(UserStatus.INATIVO);
        when(repository.findByCodigo(user.getCodigo())).thenReturn(Optional.of(user));
        Assertions.assertThrows(UserNaoEncontradoException.class, () -> service.consultarPorCodigo(user.getCodigo()));
    }

    @Test
    void consultarPorEmailTest(){
        User user = createUser();
        UserDTO dto = createDTO(user);

        when(repository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(mapper.toDto(user)).thenReturn(dto);

        UserDTO userRegistered = service.consultarPorEmail(user.getEmail());

        verify(repository).findByEmail(user.getEmail());
        Assertions.assertNotNull(userRegistered);
        Assertions.assertEquals(user.getCodigo(), userRegistered.codigo());
    }

    @Test
    void consultarPorNumBITest(){
        User user = createUser();
        UserDTO dto = createDTO(user);

        when(repository.findByNumBI(user.getNumBI())).thenReturn(Optional.of(user));
        when(mapper.toDto(user)).thenReturn(dto);

        UserDTO userRegistered = service.consultarPorNumBI(user.getNumBI());

        verify(repository).findByNumBI(user.getNumBI());
        Assertions.assertNotNull(userRegistered);
        Assertions.assertEquals(user.getCodigo(), userRegistered.codigo());
    }

    @Test
    void atualizarTest(){
        User user = createUser();
        UserUpdatedDTO updatedDTO = createUserUpdated();

        user.setNome(updatedDTO.nome());
        user.setSobrenome(updatedDTO.sobrenome());
        user.setEmail(updatedDTO.email());
        UserDTO dto = createDTO(user);

        when(repository.findByCodigo(user.getCodigo())).thenReturn(Optional.of(user));
        when(repository.save(user)).thenReturn(user);
        when(mapper.toDto(user)).thenReturn(dto);

        UserDTO userUpdated = service.atualizarUsuario(updatedDTO, user.getCodigo());

        verify(repository).findByCodigo(user.getCodigo());
        verify(repository).save(user);

        Assertions.assertEquals(updatedDTO.nome(),userUpdated.nome());
    }

    @Test
    void deletarTest(){
        User user = createUser();

        when(repository.findByCodigo(user.getCodigo())).thenReturn(Optional.of(user));
        doNothing().when(repository).softDelete(user);

        service.remover(user.getCodigo());

        verify(repository).softDelete(user);

    }
}
