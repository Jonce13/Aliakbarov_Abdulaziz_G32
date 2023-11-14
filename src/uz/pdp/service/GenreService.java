package uz.pdp.service;

import uz.pdp.entity.Genre;
import uz.pdp.payload.GenreDTO;

import java.util.List;

public interface GenreService {
    List<GenreDTO> all();
    GenreDTO add(GenreDTO genreDTO);
    GenreDTO edit(Integer id,GenreDTO genreDTO);
    boolean delete(Integer id);
}
