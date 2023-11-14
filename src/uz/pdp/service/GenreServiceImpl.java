package uz.pdp.service;

import uz.pdp.entity.Book;
import uz.pdp.entity.Genre;
import uz.pdp.payload.GenreDTO;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class GenreServiceImpl implements GenreService{
    private static GenreServiceImpl instance;
    private static Lock lock=new ReentrantLock();
    public List<Genre> genres = Collections.synchronizedList(new ArrayList<>());
    private GenreServiceImpl(){}
    public static GenreServiceImpl getInstance(){
        if(Objects.isNull(instance)){
            lock.lock();
            if(Objects.isNull(instance))
                instance=new GenreServiceImpl();
            lock.unlock();
        }
        return instance;
    }
    @Override
    public List<GenreDTO> all() {
        return genres.stream().map(genre -> new GenreDTO(genre.getName(),genre.getId())).collect(Collectors.toList());
    }

    @Override
    public GenreDTO add(GenreDTO genreDTO) {
        Optional<Integer> max = genres.stream().map(Genre::getId).max(Integer::compareTo);
        int id = max.isEmpty() ? 1 : max.get() + 1;
        Genre genre=new Genre(id,genreDTO.getName());
        genres.add(genre);
        genreDTO.setId(id);
        return genreDTO;

    }

    @Override
    public GenreDTO edit(Integer id,GenreDTO genreDTO) {
        Optional<Genre> genreOptional = genres.stream().filter(genre -> Objects.equals(id, genre.getId())).findFirst();
        Genre genre = genreOptional.orElseThrow(() -> new RuntimeException("Genre with this id not found: " + id));
        genre.setName(genreDTO.getName());
        genreDTO.setId(genre.getId());
        return genreDTO;
    }

    @Override
    public boolean delete(Integer id) {
        Optional<Genre> genreOptional = genres.stream().filter(genre -> Objects.equals(genre.getId(), id)).findFirst();
        Genre genre = genreOptional.orElseThrow(() -> new RuntimeException("Genre with this id not found: " + id));
        genres.remove(genre);
        return true;
    }
}
