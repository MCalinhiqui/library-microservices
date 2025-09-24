package ao.com.mcali.service.impl;

import ao.com.mcali.domain.User;
import ao.com.mcali.domain.UserStatus;
import ao.com.mcali.dto.UserDTO;
import ao.com.mcali.dto.UserUpdatedDTO;
import ao.com.mcali.exception.*;
import ao.com.mcali.mapper.UserMapper;
import ao.com.mcali.repository.IUserRepository;
import ao.com.mcali.service.IUserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class UserService implements IUserService {
    IUserRepository repository;
    UserMapper mapper;
    private UserUpdatedDTO updatedDTO;

    @Autowired
    public UserService(IUserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void cadastrar(UserDTO dto){
        Optional<User> userInDatabase= repository.findByCodigo(dto.codigo());
        userInDatabase.ifPresent(u -> {
            throw new UserInativoException("Este usuário encontra-se inativo, consulte o administrador do sistema para reativar!");
        });
        User user = mapper.toEntity(dto);
        repository.save(user);
    }

    @Override
    public List<UserDTO> consultarTodos() {
        List<User> users = repository.findAll();
        return mapper.toDTOList(users);
    }

    @Override
    public List<UserDTO> consultarAtivos() {
        List<User> users = repository.findAll().stream()
                .filter(user -> user.getStatus().getCodigo().equals(1)).toList();
        return mapper.toDTOList(users);
    }

    @Override
    public UserDTO consultarPorCodigo(Long codigo) {
        validarCodigo(codigo);
        User user = repository.findByCodigo(codigo).orElseThrow(UserNaoEncontradoException::new);
        validarStatus(user.getStatus());
        return mapper.toDto(user);
    }

    @Override
    public UserDTO consultarPorEmail(String email) {
        validarEmail(email);
        User user = repository.findByEmail(email).orElseThrow(UserNaoEncontradoException::new);
        validarStatus(user.getStatus());
        return mapper.toDto(user);
    }

    @Override
    public UserDTO consultarPorNumBI(String numBI) {
        validarNumBI(numBI);
        User user = repository.findByNumBI(numBI).orElseThrow(UserNaoEncontradoException::new);
        validarStatus(user.getStatus());
        return mapper.toDto(user);
    }

    @Override
    public UserDTO atualizarUsuario(UserUpdatedDTO updatedDTO, Long codigo) {
        validarCodigo(codigo);
        User user = repository.findByCodigo(codigo).orElseThrow(UserNaoEncontradoException::new);
        validarStatus(user.getStatus());
        mapper.partialUpdate(updatedDTO, user);
        user = repository.save(user);
        return mapper.toDto(user);
    }

    @Override
    public void remover(Long codigo) {
        validarCodigo(codigo);
        User user = repository.findByCodigo(codigo).orElseThrow(UserNaoEncontradoException::new);
        validarStatus(user.getStatus());
        repository.softDelete(user);
    }

    @Override
    public void deletar(Long codigo) {
        validarCodigo(codigo);
        User user = repository.findByCodigo(codigo).orElseThrow(UserNaoEncontradoException::new);
        repository.delete(user);
    }

    private void validarStatus(UserStatus status) {
        if(status.getCodigo().equals(0))
            throw new UserInativoException();
    }

    private void validarNumBI(String numBI) {
        boolean isValid = numBI.matches("^\\d{9}[A-Z]{2}\\d{3}$");
        if(!isValid)
            throw new NumBIInvalidoException();
    }

    private void validarEmail(String email){
        boolean isValid = email.matches(".+@.+\\..+");
        if(!isValid)
            throw new EmailInvalidoException();

    }

    private void validarCodigo(Long codigo) {
        if(codigo == null || codigo < 1_000)
            throw new CodigoInvalidoException("O código fornecido não é válido!!");
    }
}
