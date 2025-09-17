package ao.com.mcali.mapper;
import ao.com.mcali.domain.Book;
import ao.com.mcali.dto.BookDTO;
import ao.com.mcali.dto.BookDTOForUpdating;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = IYearToIntegerMapper.class)
public interface IBookMapper{
    BookDTO toDTO(Book book);
    Book toDomain(BookDTO bookDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void domainFromBookUpdatedDTO(BookDTOForUpdating dto, @MappingTarget Book book);

    List<BookDTO> toDTOList(List<Book> list);
    List<Book> toDomainList(List<BookDTO> list);

}
