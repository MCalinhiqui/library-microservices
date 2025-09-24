package ao.com.mcali.controller;

import ao.com.mcali.dto.UserDTO;
import ao.com.mcali.dto.UserUpdatedDTO;
import ao.com.mcali.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Operações relacionadas aos usuários")
public class UserController {
    IUserService service;

    @PostMapping()
    @Operation(summary = "Cadastrar usuário", description = "Cria um novo usuário, cadastra na base de dados e retorna a uri do usuário cadastrado!")
    @ApiResponse(responseCode = "400", useReturnTypeSchema = true, description = "Dados inválidos fornecidos")
    @ApiResponse(responseCode = "201", useReturnTypeSchema = true, description = "Usuário cadastrado com sucesso, o cabeçalho location contem a URI do usuario criado!")
    @ApiResponse(responseCode = "422", useReturnTypeSchema = true, description = "Usuário não cadastrado por duplicação de dados!")
    ResponseEntity<Void> cadastrar(@RequestBody @Valid UserDTO user) throws URISyntaxException {
        service.cadastrar(user);
        return ResponseEntity.created(new URI("/users/"+user.codigo())).build();
    }


    @GetMapping()
    @Operation(summary = "Buscar todos os usuários", description = "Retorna uma lista de todos os usuários da biblioteca")
    @ApiResponse(responseCode = "200", description = "Retorna todos os usuários da biblioteca, caso não haja nada é exibido", content = @Content(schema = @Schema()))
    ResponseEntity<List<UserDTO>> buscar(){
        List<UserDTO> list = service.consultarTodos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/ativos")
    @Operation(summary = "Buscar todos os usuários ativos", description = "Retorna uma lista de todos os usuários ativos da biblioteca")
    @ApiResponse(responseCode = "200", description = "Retorna todos os usuários ativos da biblioteca, caso não haja nada é exibido", content = @Content(schema = @Schema()))
    ResponseEntity<List<UserDTO>> buscarAtivos(){
        List<UserDTO> list = service.consultarAtivos();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Buscar um usuário pelo codigo", description = "Busca um usuário na base de dados com base no cṕdigo informado")
    @ApiResponse(responseCode = "200", useReturnTypeSchema = true, description = "Retorna as informações do usuário")
    @ApiResponse(responseCode = "400", description = "Código inválido", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema()))
    ResponseEntity<UserDTO> buscarPorCodigo(@PathVariable Long codigo){
        UserDTO user = service.consultarPorCodigo(codigo);
        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/{codigo}")
    @Operation(summary = "Atualizar os dados de um usuário", description = "Realiza atualização total ou parcial das informações de um usuário e retorna as informações deste usuário")
    @ApiResponse(responseCode = "200", useReturnTypeSchema = true, description = "Dados atualizados com sucesso!")
    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema()), description = "Código ou informações inválidas")
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema()), description = "Usuário não encontrado")
    ResponseEntity<UserDTO> atualizar(@PathVariable Long codigo, @RequestBody @Valid UserUpdatedDTO updatedDTO){
        UserDTO user = service.atualizarUsuario(updatedDTO, codigo);
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{codigo}")
    @Operation(summary = "Removar um usuario",
            description = "Marca um usuário como inativo, inviabilizando qualquer operação para este usuário")
    @ApiResponse(responseCode = "204", description = "Usuário removido!!")
    @ApiResponse(responseCode = "400", description = "Código inválido fornecido")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    public ResponseEntity<Void> deletar(@PathVariable Long codigo) {
        service.remover(codigo);
        return ResponseEntity.noContent().build();
    }
}
