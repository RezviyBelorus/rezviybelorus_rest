package kinoview.commonjdbc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexfomin on 03.07.17.
 */
@Entity
@Table(name = "genre_type")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private int genreId;

    @Column(name = "genre_name")
    private String genreName;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private List<Film> films = new ArrayList<>();

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

        Genre genre = (Genre) o;

        return genreName != null ? genreName.equals(genre.genreName) : genre.genreName == null;
    }

    @Override
    public int hashCode() {
        return genreName != null ? genreName.hashCode() : 0;
    }
}

