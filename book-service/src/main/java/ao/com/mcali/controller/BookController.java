package ao.com.mcali.controller;

import ao.com.mcali.domain.Book;
import ao.com.mcali.dto.BookDTO;
import ao.com.mcali.dto.BookDTOForUpdating;
import ao.com.mcali.service.IBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Cadastrar um livro",
                description = "Cria um novo livro no sistema, cadastra e retorna a uri do livro cadastrado")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso. O cabeçalho location contem a URI do recurso criado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
                    @ApiResponse(responseCode = "409", description = "Já existe um livro com o mesmo codigo")})
    @PostMapping()
    ResponseEntity<Void> cadastrar(@RequestBody @Valid BookDTO bookDTO) throws URISyntaxException {
        service.cadastrar(bookDTO);
        return ResponseEntity.created(new URI("/books/"+bookDTO.codigo())).build();
    }

    @Operation(summary = "Listar todos os livros",
            description = "Retorna a lista de todos os livros cadastrados, podendo ser filtrada por status via query param")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "Lista restornada com sucesso", content = @Content(schema = @Schema())))
    @GetMapping()
    ResponseEntity<List<BookDTO>> buscarTodos(@RequestParam(required = false) Book.Status status){
        List<BookDTO> list;
        list = (status == null) ? service.buscarTodos() : service.buscarPorEstado(status);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Buscar livro por codigo",
            description = "Busca um livro com base no codigo fornecido")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Livro Encontrado", content = @Content(schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "400", description = "Código inválido fornecido", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content(schema = @Schema()))})
    @GetMapping("/{codigo}")
    ResponseEntity<BookDTO> buscarPorCodigo(@PathVariable Long codigo) {
        BookDTO book = service.buscarPorCodigo(codigo);
        return ResponseEntity.ok(book);
    }

    @Operation(summary = "Atualizar as informações de um livro",
            description = "Atualiza um ou mais campos de um livro existente")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso", content = @Content(schema = @Schema(implementation = BookDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidoS", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content(schema = @Schema()))})
    @PatchMapping("/{codigo}")
    ResponseEntity<BookDTO> atualizar(@RequestBody @Valid BookDTOForUpdating book, @PathVariable Long codigo) {
        BookDTO bookDTO = service.atualizar(book,codigo);
        return ResponseEntity.ok(bookDTO);
    }

    @Operation(summary = "Alterar o status de um livro",
            description = """
                    Atualiza o status de um livro \nde acordo com as seguintes regras de transição:
                    - INDISPONÍVEL -> DISPONIVEL
                    - DISPONÍVEL -> EMPRESTADO
                    - DISPONIVEL -> INDISPONIVEL
                    - EMPRESTADO -> DISPONIVEL
                    
                    Não é premitido transições inversas
                    """)
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Status do livro atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Código inválido fornecido"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "401", description = "Violação das regras de transição")})
    @PatchMapping("/{codigo}/{status}")
    ResponseEntity<Void> atualizar(@PathVariable("status") Book.Status status, @PathVariable("codigo") Long codigo) {
        service.atualizarStatus(status,codigo);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deletar um livro",
            description = "Atualiza o status de um livro existente")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Código inválido fornecido"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")})
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletar(Long codigo) {
        service.deletar(codigo);
        return ResponseEntity.noContent().build();
    }
}
