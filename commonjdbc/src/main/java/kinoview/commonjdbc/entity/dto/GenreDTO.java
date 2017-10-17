package kinoview.commonjdbc.entity.dto;

import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.entity.Genre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GenreDTO implements Serializable {
    private int genreId;

    private String genreName;

    private List<Film> films = new ArrayList<>();

    public GenreDTO() {
    }

    public GenreDTO(Genre genre) {
        this.genreId = genre.getGenreId();
        this.genreName = genre.getGenreName();
        this.films = genre.getFilms();
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenreDTO genreDTO = (GenreDTO) o;

        return genreName != null ? genreName.equals(genreDTO.genreName) : genreDTO.genreName == null;
    }

    @Override
    public int hashCode() {
        return genreName != null ? genreName.hashCode() : 0;
    }
}
