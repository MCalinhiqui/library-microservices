package ao.com.mcali.service;

import ao.com.mcali.domain.BookStatus;
import ao.com.mcali.dto.BookDTO;
import ao.com.mcali.dto.BookDetailsDTO;
import ao.com.mcali.dto.BookUpdateDTO;

import java.util.List;

public interface IBookService {
    void cadastrar(BookDTO bookDTO);
    List<BookDetailsDTO> buscarTodos();
    BookDetailsDTO buscarPorCodigo(Long codigo);
    List<BookDetailsDTO> buscarPorEstado(BookStatus status);
    BookDTO atualizar(BookUpdateDTO dtoForUpdating, Long codigo);
    void atualizarStatus(BookStatus status, Long codigo);
    void deletar(Long codigo);
}
