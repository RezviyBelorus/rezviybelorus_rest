package kinoview.commonjdbc.dao;

import kinoview.commonjdbc.entity.Genre;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexfomin on 03.07.17.
 */
@Repository
public class GenreDAO extends AbstractDAO {
    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate template;

    private String SAVE_GENRE_QUERY = "INSERT INTO genre_type (genre_name) VALUES (?)";

    private String DELETE_GENRE_BY_ID_QUERY = "DELETE FROM genre_type WHERE genre_id = ?";

    private String DELETE_GENRE_BY_NAME_QUERY = "DELETE FROM genre_type WHERE genre_name = ?";

    private String SELECT_GENRE_BY_ID_QUERY = "SELECT genre_id, genre_name FROM genre_type WHERE genre_id = ?";

    private String SELECT_GENRE_BY_GENRE_NAME_QUERY = "SELECT genre_id, genre_name FROM genre_type WHERE genre_name = ?";

    private String SELECT_ALL_GENRES_BY_FILM_QUERY = "SELECT n.genre_name FROM genre_type AS n " +
            "INNER JOIN films_to_genres AS f ON n.genre_id = f.genre_id WHERE f.film_id=?";

    private String SELECT_ALL_GENRES_QUERY = "SELECT genre_id, genre_name FROM genre_type";

    private String INSERT_FILMS_TO_GENRES_QUERY = "INSERT INTO films_to_genres VALUES (?, ?)";


    @Transactional
    public boolean save(Genre genre) {
        template.update(SAVE_GENRE_QUERY, genre.getGenreName());
        return true;
    }

    public boolean saveFilmToGenre(int filmId, List<Integer> genresId) {
        template.batchUpdate(INSERT_FILMS_TO_GENRES_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int genreId = genresId.get(i);
                ps.setInt(1, filmId);
                ps.setInt(2, genreId);
            }

            @Override
            public int getBatchSize() {
                return genresId.size();
            }
        });
        return true;
    }

    public boolean delete(int id) {
        template.update(DELETE_GENRE_BY_ID_QUERY, id);
        return true;
    }

    public boolean delete(String genreName) {
        template.update(DELETE_GENRE_BY_NAME_QUERY, genreName);
        return true;
    }

    public Genre find(int id) {
        return template.queryForObject(SELECT_GENRE_BY_ID_QUERY, new RowMapper<Genre>() {
            @Override
            public Genre mapRow(ResultSet rs, int i) throws SQLException {
                Genre genre = new Genre();
                genre.setGenreId(rs.getInt(1));
                genre.setGenreName(rs.getString(2));
                return genre;
            }
        }, id);

    }

    public Genre find(String genreName) {
        return template.queryForObject(SELECT_GENRE_BY_GENRE_NAME_QUERY, new RowMapper<Genre>() {
            @Override
            public Genre mapRow(ResultSet rs, int i) throws SQLException {
                Genre genre = new Genre();
                genre.setGenreId(rs.getInt(1));
                genre.setGenreName(rs.getString(2));
                return genre;
            }
        }, genreName);
    }

    public List<Genre> findAllGenres(){
        SqlRowSet rowSet = template.queryForRowSet(SELECT_ALL_GENRES_QUERY);
        List<Genre> genres = new ArrayList<>();
        while (rowSet.next()) {
            Genre genre = new Genre();
            genre.setGenreId(rowSet.getInt(1));
            genre.setGenreName(rowSet.getString(2));
            genres.add(genre);
        }
        return genres;
    }

    public List<String> findAllByFilm(int filmId) {
        SqlRowSet rs = template.queryForRowSet(SELECT_ALL_GENRES_BY_FILM_QUERY, filmId);
        List<String> genres = new ArrayList<>();
        while (rs.next()){
         genres.add(rs.getString(1));
        }
        return genres;
    }

}
