package ao.com.mcali;

import ao.com.mcali.domain.User;
import ao.com.mcali.dto.UserDTO;
import ao.com.mcali.dto.UserUpdatedDTO;
import ao.com.mcali.mapper.UserMapper;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class UserMapperTests {

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    Faker faker = new Faker();

	@Test
	void EntityToDTO() {
        User user = User.builder()
                .id(1L)
                .codigo(1111L)
                .nome(faker.name().firstName())
                .sobrenome(faker.name().lastName())
                .email("example@example.com")
                .numBI("0808908ew9090f")
                .build();

        UserDTO dto = userMapper.toDto(user);

        Assertions.assertEquals(user.getCodigo(), dto.codigo());
        Assertions.assertEquals(user.getNome(), dto.nome());
        Assertions.assertEquals(user.getSobrenome(), dto.sobrenome());
        Assertions.assertEquals(user.getEmail(), dto.email());
        Assertions.assertEquals(user.getNumBI(), dto.numBI());
	}

    @Test
    void DTOToEntity() {
        UserDTO dto = UserDTO.builder()
                .codigo(1111L)
                .nome(faker.name().firstName())
                .sobrenome(faker.name().lastName())
                .email("example@example.com")
                .numBI("0808908ew9090f")
                .build();

        User user = userMapper.toEntity(dto);

        Assertions.assertEquals(dto.codigo(),user.getCodigo());
        Assertions.assertEquals(dto.nome(), user.getNome());
        Assertions.assertEquals(dto.sobrenome(), user.getSobrenome());
        Assertions.assertEquals(dto.email(), user.getEmail());
        Assertions.assertEquals(dto.numBI(), user.getNumBI());
        Assertions.assertEquals(1,user.getStatus().getCodigo());
    }

    @Test
    void partialUpdate() {
        User user = User.builder()
                .id(1L)
                .codigo(1111L)
                .nome(faker.name().firstName())
                .sobrenome(faker.name().lastName())
                .email("example@example.com")
                .numBI("0808908ew9090f")
                .build();

        UserUpdatedDTO dto = UserUpdatedDTO.builder()
                .nome(faker.name().firstName())
                .sobrenome(faker.name().lastName())
                .email("example@ex.com")
                .build();

        userMapper.partialUpdate(dto,user);


        Assertions.assertEquals(dto.nome(), user.getNome());
        Assertions.assertEquals(dto.sobrenome(), user.getSobrenome());
        Assertions.assertEquals(dto.email(), user.getEmail());
    }
}
