package kinoview.commonjdbc.dao;

import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.exception.IllegalRequestException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by alexfomin on 02.07.17.
 */
@Repository
public class FilmDAO extends AbstractDAO {

    @Autowired
    private JdbcTemplate template;

    Logger logger = Logger.getLogger(this.getClass());

    private String SAVE_FILM_QUERY = "INSERT INTO films (film_name, release_year, quality, translation, " +
            "length, rating, upload_date, status, img_link, watch_link, short_story, kinogo_page) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

    private String DELETE_BY_ID_QUARY = "DELETE FROM films WHERE film_id = ?";

    private String DELETE_BY_FILM_NAME_QUARY = "DELETE FROM films WHERE film_name = ?";

    private String SELECT_FILM_BY_ID_QUERY = "SELECT film_id, film_name, release_year, quality," +
            "translation, length, rating, upload_date, status, img_link, watch_link, short_story, kinogo_page FROM films WHERE film_id = ?";

    private String SELECT_FILM_BY_FILM_NAME_QUERY = "SELECT film_id, film_name, release_year, quality," +
            "translation, length, rating, upload_date, status, img_link, watch_link, short_story, kinogo_page FROM films WHERE film_name = ?";

    private String SELECT_ALL_FILMS_QUERY = "SELECT film_id, film_name, release_year, quality," +
            "translation, length, rating, upload_date, status, img_link, watch_link, short_story, kinogo_page FROM films";

    private String INSERT_FILMS_TO_GENRES = "INSERT INTO films_to_genres VALUES (?, ?)";
    private String INSERT_FILMS_TO_COUNTRIES = "INSERT INTO films_to_countries VALUES (?, ?)";

    private String UPDATE_STATUS_BY_FILM_NAME_QUERY = "UPDATE films SET status = ? WHERE film_name = ?";

    private String UPDATE_FILMS_QUERY = "UPDATE films SET quality = ?, translation = ? " +
            "WHERE film_name = ? AND release_year = ? AND short_story = ?";

    private String ADD_TO_FAVORITES_QUERY = "INSERT INTO favorites_by_user VALUES (?, ?)";

    private String SET_FOREIGN_KEY_CHECKS_QUERY = "SET FOREIGN_KEY_CHECKS = ?";
    private String TRUNCATE_TABLE_FILMS_QUERY = "TRUNCATE TABLE films";

    private String SELECT_COUNT_OF_FILMS_QUERY = "SELECT COUNT(*) FROM films";

    private String SELECT_RANGE_OF_FILMS_QUERY = "SELECT film_id, film_name, release_year, quality, translation, " +
            "length, rating, upload_date, status, img_link, watch_link, short_story, kinogo_page FROM films LIMIT ?, ?";


    public boolean save(Film film) {
        template.update(SAVE_FILM_QUERY, film.getName(), film.getReleaseYear(), film.getQuality(),
                film.getTranslation(), film.getDuration(), film.getRating(),
                Timestamp.valueOf(film.getUploadDate()), film.getStatus(), film.getImgLink(), film.getWatchLink(),
                film.getShortStory(), film.getKinogoPage());
        return true;
    }

    public boolean saveBatch(List<Film> films) {
        template.batchUpdate(SAVE_FILM_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Film film = films.get(i);
                ps.setString(1, film.getName());
                ps.setInt(2, film.getReleaseYear());
                ps.setString(3, film.getQuality());
                ps.setString(4, film.getTranslation());
                ps.setString(5, film.getDuration());
                ps.setFloat(6, film.getRating());
                ps.setTimestamp(7, Timestamp.valueOf(film.getUploadDate()));
                ps.setInt(8, film.getStatus());
                ps.setString(9, film.getImgLink());
                ps.setString(10, film.getWatchLink());
                ps.setString(11, film.getShortStory());
                ps.setInt(12, film.getKinogoPage());
            }

            @Override
            public int getBatchSize() {
                return films.size();
            }
        });
        return true;
    }

    public boolean updateFilm(Film film) {
        template.update(UPDATE_FILMS_QUERY, film.getQuality(), film.getTranslation(), film.getName(),
                film.getReleaseYear(), film.getShortStory());
        return true;
    }

    public boolean updateBatchFilms(List<Film> films) {
        template.batchUpdate(UPDATE_FILMS_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Film film = films.get(i);
                ps.setString(1, film.getQuality());
                ps.setString(2, film.getTranslation());
                ps.setString(3, film.getName());
                ps.setInt(4, film.getReleaseYear());
                ps.setString(5, film.getShortStory());
            }

            @Override
            public int getBatchSize() {
                return films.size();
            }
        });
        return true;
    }

    public int countFilmsInDB() {
        int count = template.queryForObject(SELECT_COUNT_OF_FILMS_QUERY, Integer.class);
        return count;
    }

    public boolean delete(int id) {
        template.update(DELETE_BY_ID_QUARY, id);
        return true;
    }

    public boolean delete(String filmName) {
        template.update(DELETE_BY_FILM_NAME_QUARY, filmName);
        return true;
    }

    public List<Film> findRange(int offset, int limit) {
        SqlRowSet rs = template.queryForRowSet(SELECT_RANGE_OF_FILMS_QUERY, offset, limit);
        List<Film> films = new ArrayList<>();
        Film film;
        while (rs.next()) {
            film = new Film();
            film.setId(rs.getInt(1));
            film.setName(rs.getString(2));
            film.setReleaseYear(rs.getInt(3));
            film.setQuality(rs.getString(4));
            film.setTranslation(rs.getString(5));
            film.setDuration(rs.getString(6));
            film.setRating(rs.getFloat(7));
            film.setUploadDate(rs.getTimestamp(8).toLocalDateTime());
            film.setStatus(rs.getInt(9));
            film.setImgLink(rs.getString(10));
            film.setWatchLink(rs.getString(11));
            film.setShortStory(rs.getString(12));
            film.setKinogoPage(rs.getInt(13));
            films.add(film);
        }
        return films;
    }

    public Film find(int id) {
        Film film = template.queryForObject(SELECT_FILM_BY_ID_QUERY, new RowMapper<Film>() {
            @Override
            public Film mapRow(ResultSet rs, int i) throws SQLException {
                return getFilm(rs);
            }
        }, id);
        return film;
    }

    public Film find(String filmName) {
        Film film = template.queryForObject(SELECT_FILM_BY_FILM_NAME_QUERY, new RowMapper<Film>() {
            @Override
            public Film mapRow(ResultSet rs, int i) throws SQLException {
                return getFilm(rs);
            }
        }, filmName);
        return film;
    }



    public List<Film> findLoadedFilms() {
        List<Film> films = template.query(SELECT_ALL_FILMS_QUERY, new RowMapper<Film>() {
            @Override
            public Film mapRow(ResultSet rs, int i) throws SQLException {
                return getFilm(rs);
            }
        });
        return films;
    }

    public boolean setStatus(String filmName, int status) {
        template.update(UPDATE_STATUS_BY_FILM_NAME_QUERY, status, filmName);
        return true;
    }

    public boolean addFilmToFavorites(int userId, int filmId) {
        template.update(ADD_TO_FAVORITES_QUERY, userId, filmId);
        return true;
    }

    public boolean setForeignKeyChecks(int param) {
        template.update(SET_FOREIGN_KEY_CHECKS_QUERY, param);
        return true;
    }

    public boolean clearDatabase() {
        template.update(TRUNCATE_TABLE_FILMS_QUERY);
        return true;
    }
    private Film getFilm(ResultSet rs) throws SQLException {
        Film film = new Film();
        film.setId(rs.getInt(1));
        film.setName(rs.getString(2));
        film.setReleaseYear(rs.getInt(3));
        film.setQuality(rs.getString(4));
        film.setTranslation(rs.getString(5));
        film.setDuration(rs.getString(6));
        film.setRating(rs.getFloat(7));
        film.setUploadDate(rs.getTimestamp(8).toLocalDateTime());
        film.setStatus(rs.getInt(9));
        film.setImgLink(rs.getString(10));
        film.setWatchLink(rs.getString(11));
        film.setShortStory(rs.getString(12));
        film.setKinogoPage(rs.getInt(13));
        return film;
    }

}
