package ao.com.mcali.service;

import ao.com.mcali.domain.Book;
import ao.com.mcali.dto.BookDTO;
import ao.com.mcali.dto.BookDTOForUpdating;

import java.util.List;

public interface IBookService {
    void cadastrar(BookDTO bookDTO);
    List<BookDTO> buscarTodos();
    BookDTO buscarPorCodigo(Long codigo);
    List<BookDTO> buscarPorEstado(Book.Status status);
    BookDTO atualizar(BookDTOForUpdating dtoForUpdating, Long codigo);
    void atualizarStatus(Book.Status status, Long codigo);
    void deletar(Long codigo);
}
