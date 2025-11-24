package ao.com.mcali.controller;

import ao.com.mcali.domain.LoanStatus;
import ao.com.mcali.dto.LoanDTO;
import ao.com.mcali.dto.LoanRegisteredDTO;
import ao.com.mcali.service.ILoanService;
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
@RequestMapping("/loans")
@AllArgsConstructor
@Tag(name = "Empréstimo", description = "Operações relacionadas aos empréstimos!!")
public class LoanController{

    private ILoanService service;

    @PostMapping
    @Operation(summary = "Realizar empréstimo",
            description = "Cria um empréstimo, registra e retorna a uri do empréstimo")
    @ApiResponse(responseCode = "200", description = "Requisição efectuada!")
    @ApiResponse(responseCode = "201", description = "Empréstimo registrado. O cabeçalho location contem a URI do empréstimo")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @ApiResponse(responseCode = "422", description = "Já existe um empréstimo com o mesmo codigo")
    @ApiResponse(responseCode = "500", description = "Erro ao buscar livro e/ou usuário")
    ResponseEntity<Void> realizarEmprestimo(@RequestBody @Valid LoanDTO loanDTO) throws URISyntaxException {
        service.emprestar(loanDTO);
        return ResponseEntity.created(new URI("/loans/"+loanDTO.codigo())).build();
    }

    @GetMapping
    @Operation(summary = "Listar todos os empréstimos",
            description = "Retorna a lista de todos os empréstimos registrados, podendo ser filtrada por status, codigo do livro e/ou codigo do usuário via query param")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso", content = @Content(schema = @Schema()))
    ResponseEntity<List<LoanRegisteredDTO>> consultar(
            @RequestParam(required = false) LoanStatus status,
            @RequestParam(required = false) Long codigoUsuario,
            @RequestParam(required = false) Long codigoLivro)
    {
        List<LoanRegisteredDTO> list;
        if(status != null || codigoUsuario != null || codigoLivro != null)
            list = service.consultarComFiltro(status,codigoUsuario,codigoLivro);
        else
            list = service.consultarTodos();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Buscar empréstimo por código",
            description = "Busca um empréstimo com base nem seu código")
    @ApiResponse(responseCode = "200", description = "Empréstimo Encontrado", content = @Content(schema = @Schema(implementation = LoanRegisteredDTO.class)))
    @ApiResponse(responseCode = "400", description = "Código inválido fornecido", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content(schema = @Schema()))
    ResponseEntity<LoanRegisteredDTO> consultar(@PathVariable("codigo") long codigoEmprestimo)
    {
        LoanRegisteredDTO loan = service.consultar(codigoEmprestimo);
        return ResponseEntity.ok(loan);
    }

    @PatchMapping("/{codigo}")
    @Operation(summary = "Realizar devolução",
            description = "Alterar o status de um emspréstimo para inactivo e marca o livro como disponível")
    @ApiResponse(responseCode = "200", description = "Devolução realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Código inválido fornecido", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content(schema = @Schema()))
    ResponseEntity<LoanRegisteredDTO> devolver(@PathVariable("codigo") long codigoEmprestimo)
    {
        LoanRegisteredDTO loan = service.devolver(codigoEmprestimo);
        return ResponseEntity.ok(loan);
    }

    @DeleteMapping("/{codigo}")
    @Operation(summary = "Deletar um empréstimo",
            description = "Efectua a exclusão do registro de um empréstimo")
    @ApiResponse(responseCode = "204", description = "Empréstimo deletado com sucesso")
    @ApiResponse(responseCode = "400", description = "Código inválido fornecido")
    @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    ResponseEntity<Void> excluir(@PathVariable("codigo") long codigoEmprestimo)
    {
        service.excluirEmprestimo(codigoEmprestimo);
        return ResponseEntity.ok().build();
    }
}
