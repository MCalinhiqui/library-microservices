package ao.com.mcali.service;

import ao.com.mcali.dto.UserDTO;
import ao.com.mcali.dto.UserUpdatedDTO;

import java.util.List;

public interface IUserService {
    void cadastrar(UserDTO dto);
    List<UserDTO> consultarTodos();
    List<UserDTO> consultarAtivos();
    UserDTO consultarPorCodigo(Long codigo);
    UserDTO consultarPorEmail(String email);
    UserDTO consultarPorNumBI(String numBI);
    UserDTO atualizarUsuario(UserUpdatedDTO updatedDTO, Long codigo);
    void remover(Long codigo);
    void deletar(Long codigo);
}
