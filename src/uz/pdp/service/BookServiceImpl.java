package uz.pdp.service;

import uz.pdp.entity.Book;
import uz.pdp.entity.Genre;
import uz.pdp.payload.BookDTO;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    private static BookServiceImpl instance;
    private static Lock lock = new ReentrantLock();
    public List<Book> books = Collections.synchronizedList(new ArrayList<>());

    private BookServiceImpl() {
    }

    public static BookServiceImpl getInstance() {
        if (Objects.isNull(instance)) {
            lock.lock();
            if (Objects.isNull(instance))
                instance = new BookServiceImpl();
            lock.unlock();
        }
        return instance;
    }

    @Override
    public List<BookDTO> getByGenre(Integer genreId) {
        return books.stream().filter(book -> book.getGenre().getId().equals(genreId)).map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public BookDTO getById(Integer id) {
        Optional<Book> first = books.stream().filter(book -> book.getId().equals(id)).findFirst();
        Book book = first.orElseThrow(() -> new RuntimeException("Book is not found with id: " + id));
        return toDTO(book);
    }

    @Override
    public BookDTO add(BookDTO bookDTO) {
        int id = books.size() + 1;
        List<Genre> genres = GenreServiceImpl.getInstance().genres;
        Optional<Genre> genreOptional = genres.stream().filter(genre -> genre.getId().equals(bookDTO.getGenreId())).findFirst();
        Genre genre = genreOptional.orElseThrow(() -> new RuntimeException("xato"));
        Book book=new Book(id,bookDTO.getName(), bookDTO.getAuthor(),
                bookDTO.getWrittenDate(), bookDTO.getFilePath(), bookDTO.getPageCount(),genre);
        books.add(book);
        bookDTO.setId(id);
        return bookDTO;
    }

    @Override
    public BookDTO edit(Integer id, BookDTO book) {
        Optional<Book> first = books.stream().filter(book1 -> book1.getId().equals(book.getId())).findFirst();
        Book book1 = first.orElseThrow();
        book1.setName(book.getName());
        book1.setAuthor(book.getAuthor());
        book1.setFilePath(book.getFilePath());
        book1.setPageCount(book.getPageCount());
        book1.setWrittenDate(book.getWrittenDate());

        GenreServiceImpl genreService=GenreServiceImpl.getInstance();
        Optional<Genre> genreOptional = genreService.genres.stream().filter(genre -> genre.getId().equals(book.getGenreId())).findFirst();
        Genre genre = genreOptional.orElseThrow();
        book1.setGenre(genre);
        return toDTO(book1);
    }

    @Override
    public boolean delete(Integer id) {
        books.removeIf(book -> book.getId().equals(id));
        return true;
    }

    @Override
    public String read(Integer id) {
        Optional<Book> first = books.stream().filter(book -> book.getId().equals(id)).findFirst();
        Book book = first.orElseThrow();
        Path path = Paths.get(book.getFilePath());
        String str;
        try (InputStream in=new FileInputStream(path.toFile())){
            byte[] bytes = in.readAllBytes();
            str=new String(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }

    @Override
    public boolean serialize() {
        try (ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream("db/salom.txt"))){
            objectOutputStream.writeObject(books);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deserialize() {
        try (ObjectInputStream objectInputStream=new ObjectInputStream(new FileInputStream("db/salom.txt"))){
            books= (List<Book>) objectInputStream.readObject();
            return true;
        } catch (IOException | ClassNotFoundException e) {
             e.printStackTrace();
             return false;
        }
    }
    public BookDTO toDTO(Book book){
        BookDTO bookDTO =new BookDTO(book.getId(), book.getName(), book.getAuthor(),
        book.getWrittenDate(), book.getFilePath(), book.getPageCount(), book.getGenre().getId());
        return bookDTO;
    }
}
