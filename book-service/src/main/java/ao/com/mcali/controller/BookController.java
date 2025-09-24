package ao.com.mcali.controller;

import ao.com.mcali.domain.BookStatus;
import ao.com.mcali.dto.BookDTO;
import ao.com.mcali.dto.BookDetailsDTO;
import ao.com.mcali.dto.BookUpdateDTO;
import ao.com.mcali.service.IBookService;
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
@RequestMapping("/books")
@Tag(name = "Livros", description = "Operaçõs relacionadas aos livros da biblioteca")
public class BookController{

    IBookService service;

    @PostMapping()
    @Operation(summary = "Cadastrar um livro",
                description = "Cria um novo livro, cadastra e retorna a uri do livro cadastrado")
    @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso. O cabeçalho location contem a URI do livro criado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    @ApiResponse(responseCode = "422", description = "Já existe um livro com o mesmo codigo")
    ResponseEntity<Void> cadastrar(@RequestBody @Valid BookDTO bookDTO) throws URISyntaxException {
        service.cadastrar(bookDTO);
        return ResponseEntity.created(new URI("/books/"+bookDTO.codigo())).build();
    }

    @GetMapping()
    @Operation(summary = "Listar todos os livros",
            description = "Retorna a lista de todos os livros cadastrados, podendo ser filtrada por status via query param")
    @ApiResponse(responseCode = "200", description = "Lista restornada com sucesso", content = @Content(schema = @Schema()))
    ResponseEntity<List<BookDetailsDTO>> buscarTodos(@RequestParam(required = false) BookStatus status){
        List<BookDetailsDTO> list;
        list = (status == null) ? service.buscarTodos() : service.buscarPorEstado(status);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{codigo}")
    @Operation(summary = "Buscar livro por codigo",
            description = "Busca um livro com base no codigo fornecido")
    @ApiResponse(responseCode = "200", description = "Livro Encontrado", content = @Content(schema = @Schema(implementation = BookDTO.class)))
    @ApiResponse(responseCode = "400", description = "Código inválido fornecido", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content(schema = @Schema()))
    ResponseEntity<BookDetailsDTO> buscarPorCodigo(@PathVariable Long codigo) {
        BookDetailsDTO book = service.buscarPorCodigo(codigo);
        return ResponseEntity.ok(book);
    }

    @PatchMapping("/{codigo}")
    @Operation(summary = "Atualizar as informações de um livro",
            description = "Atualiza um ou mais campos de um livro existente")
    @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso", content = @Content(schema = @Schema(implementation = BookDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidoS", content = @Content(schema = @Schema()))
    @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content(schema = @Schema()))
    ResponseEntity<BookDTO> atualizar(@RequestBody @Valid BookUpdateDTO book, @PathVariable Long codigo) {
        BookDTO bookDTO = service.atualizar(book,codigo);
        return ResponseEntity.ok(bookDTO);
    }

    @PatchMapping("/{codigo}/{status}")
    @Operation(summary = "Alterar o status de um livro",
            description = """
                    Atualiza o status de um livro \nde acordo com as seguintes regras de transição:
                    - INDISPONÍVEL -> DISPONIVEL
                    - DISPONÍVEL -> EMPRESTADO
                    - DISPONIVEL -> INDISPONIVEL
                    - EMPRESTADO -> DISPONIVEL
                    
                    Não é premitido transições inversas
                    """)
    @ApiResponse(responseCode = "200", description = "Status do livro atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Código inválido fornecido")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    @ApiResponse(responseCode = "401", description = "Violação das regras de transição")
    ResponseEntity<Void> atualizar(@PathVariable("status") BookStatus status, @PathVariable("codigo") Long codigo) {
        service.atualizarStatus(status,codigo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{codigo}")
    @Operation(summary = "Deletar um livro",
            description = "Atualiza o status de um livro existente")
    @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso")
    @ApiResponse(responseCode = "400", description = "Código inválido fornecido")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    public ResponseEntity<Void> deletar(@PathVariable Long codigo) {
        service.deletar(codigo);
        return ResponseEntity.noContent().build();
    }
}
