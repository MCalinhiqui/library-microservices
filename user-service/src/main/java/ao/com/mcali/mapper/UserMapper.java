package ao.com.mcali.mapper;

import ao.com.mcali.domain.User;
import ao.com.mcali.dto.UserDTO;
import ao.com.mcali.dto.UserUpdatedDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO userDTO);
    UserDTO toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(UserUpdatedDTO updatedDTO, @MappingTarget User user);

    List<UserDTO> toDTOList(List<User> list);
    List<User> toDomainList(List<UserDTO> list);
}