package kinoview.commonjdbc.entity;

import java.util.List;

/**
 * Created by alexfomin on 03.07.17.
 */
public class Genre {
    private int genreId;
    private String genreName;
    private List<String> genres;

    public List<String> getGenres() {return genres;}

    public void setGenres(String genre) {
        genres.add(genre);
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
}

