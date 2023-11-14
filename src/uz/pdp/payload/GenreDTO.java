package uz.pdp.payload;

public class GenreDTO {
    private String name;
    private Integer id;

    @Override
    public String toString() {
        return "GenreDTO{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public GenreDTO(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
