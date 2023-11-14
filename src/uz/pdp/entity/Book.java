package uz.pdp.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {
    private Integer id;
    private String name;
    private String author;
    private LocalDate writtenDate;
    private String filePath;
    private Integer pageCount;
    private Genre genre;

    public Book(Integer id, String name, String author, LocalDate writtenDate, String filePath, Integer pageCount, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.writtenDate = writtenDate;
        this.filePath = filePath;
        this.pageCount = pageCount;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", writtenDate=" + writtenDate +
                ", filePath='" + filePath + '\'' +
                ", pageCount=" + pageCount +
                ", genre=" + genre +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getWrittenDate() {
        return writtenDate;
    }

    public void setWrittenDate(LocalDate writtenDate) {
        this.writtenDate = writtenDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
