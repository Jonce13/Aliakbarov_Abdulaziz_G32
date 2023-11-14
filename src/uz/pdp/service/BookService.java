package uz.pdp.service;

import uz.pdp.payload.BookDTO;

import java.util.List;

public interface BookService {
    List<BookDTO> getByGenre(Integer genreId);
    BookDTO getById(Integer id);
    BookDTO add(BookDTO bookDTO);
    BookDTO edit(Integer id,BookDTO book);
    boolean delete(Integer id);
    String read(Integer id);
    boolean serialize();
    boolean deserialize();
}
