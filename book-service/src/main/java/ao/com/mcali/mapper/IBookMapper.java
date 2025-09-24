package ao.com.mcali.mapper;
import ao.com.mcali.domain.Book;
import ao.com.mcali.dto.BookDTO;
import ao.com.mcali.dto.BookDetailsDTO;
import ao.com.mcali.dto.BookUpdateDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = IYearToIntegerMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IBookMapper{
    BookDTO toDTO(Book book);
    BookDetailsDTO toDetailsDTO(Book book);

    Book toDomain(BookDTO bookDTO);
    Book toDomain(BookDetailsDTO BookDetailsDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void domainFromBookUpdatedDTO(BookUpdateDTO dto, @MappingTarget Book book);

    List<BookDetailsDTO> toDTOList(List<Book> list);
    List<Book> toDomainList(List<BookDetailsDTO> list);

}
