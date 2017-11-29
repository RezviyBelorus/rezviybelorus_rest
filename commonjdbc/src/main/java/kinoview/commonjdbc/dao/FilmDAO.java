package kinoview.commonjdbc.dao;

import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.util.JDBCFactory;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    SessionFactory sessionFactory;

    Logger logger = Logger.getLogger(this.getClass());

    private String UPDATE_STATUS_BY_FILM_NAME_QUERY = "UPDATE films SET status = ? WHERE film_name = ?";

    private String ADD_TO_FAVORITES_QUERY = "INSERT INTO favorites_by_user VALUES (?, ?)";

    private String SET_FOREIGN_KEY_CHECKS_QUERY = "SET FOREIGN_KEY_CHECKS = ?";
    private String TRUNCATE_TABLE_FILMS_QUERY = "TRUNCATE TABLE films";

    private String SELECT_COUNT_OF_FILMS_QUERY = "SELECT COUNT(*) FROM films";
    private String SELECT_COUNT_OF_FILMS_BY_QUALITY_QUERY = "SELECT COUNT(f.film_id) FROM films f WHERE f.quality=?";
    private String SELECT_COUNT_OF_FILMS_BY_TRANSLATION_QUERY = "SELECT COUNT(f.film_id) FROM films f WHERE f.translation=?";
    private String SELECT_COUNT_OF_FILMS_BY_RELEASE_YEAR_QUERY = "SELECT COUNT(f.film_id) FROM films f WHERE f.release_year=?";
    private String SELECT_COUNT_OF_FILMS_BY_NAME_LIKE_QUERY = "SELECT COUNT(f.film_id) FROM films f WHERE film_name LIKE ?";

    private String SELECT_FILM_BY_GENRE_QUERY = "SELECT f.film_id, f.film_name, f.release_year, f.quality, f.translation," +
            " f.length, f.rating, f.upload_date, f.status, f.img_link, f.watch_link, f.short_story, f.kinogo_page FROM films f" +
            " INNER JOIN (SELECT fg.film_id FROM films_to_genres fg INNER JOIN genre_type g ON fg.genre_id = g.genre_id AND fg.genre_id=?) t" +
            " ON f.film_id=t.film_id LIMIT ?,?";

    private String SELECT_FILMS_ID_BY_GENRE_LIMIT = "SELECT fg.film_id FROM films_to_genres fg INNER JOIN genre_type g ON fg.genre_id = g.genre_id AND fg.genre_id=? LIMIT ?, ?";
    private String SELECT_FILMS_ID_BY_COUNTRY_LIMIT = "SELECT fc.film_id FROM films_to_countries fc INNER JOIN countries c ON fc.country_id = c.country_id AND fc.country_id=? LIMIT ?,?";


    public boolean save(Film film) {
        sessionFactory.getCurrentSession().saveOrUpdate(film);
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().detach(film);
        return true;
    }

    public boolean saveBatch(List<Film> films) {
        int counter = 0;
        for (Film film : films) {
            sessionFactory.getCurrentSession().saveOrUpdate(film);
            counter++;
            if (counter % 100 == 0 || counter == films.size()) {
                sessionFactory.getCurrentSession().flush();
            }
        }
        return true;
    }

    public boolean updateFilm(Film film) {
        sessionFactory.getCurrentSession().update(film);
        return true;
    }

    public boolean updateBatchFilms(List<Film> films) {
        int counter = 0;
        for (Film film : films) {
            sessionFactory.getCurrentSession().update(film);
            counter++;
            if (counter % 100 == 0 || counter == films.size()) {
                sessionFactory.getCurrentSession().flush();
            }
        }
        return true;
    }

    public int countFilmsInDB() {
        int count = template.queryForObject(SELECT_COUNT_OF_FILMS_QUERY, Integer.class);
        return count;
    }

    public boolean delete(Film film) {
        sessionFactory.getCurrentSession().delete(film);
        return true;
    }

    public List<Film> findRange(int offset, int limit) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Film");
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public Film find(int id) {
        return sessionFactory.getCurrentSession().get(Film.class, id);
    }

    public Film find(String filmName) {
        Query query = sessionFactory.getCurrentSession()
                .createQuery("from Film where film_name=:filmname");
        query.setParameter("filmname", filmName);
        return (Film) query.uniqueResult();
    }

    public List<Film> findLoadedFilms() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Film");
        return query.getResultList();
    }

    public boolean setStatus(String filmName, int status) {
        template.update(UPDATE_STATUS_BY_FILM_NAME_QUERY, status, filmName);
        return true;
    }

    //todo:
    public boolean addFilmToFavorites(int userId, int filmId) {
        template.update(ADD_TO_FAVORITES_QUERY, userId, filmId);
        return true;
    }

    public boolean setForeignKeyChecks(int param) {
        template.update(SET_FOREIGN_KEY_CHECKS_QUERY, param);
        return true;
    }

    public List<Film> findByGenre(int genreId, int offset, int limit) {
        SqlRowSet rowSet = template.queryForRowSet(SELECT_FILM_BY_GENRE_QUERY, genreId, offset, limit);
        List<Film> films = new ArrayList<>();
        while (rowSet.next()) {
            Film film = new Film();
            film.setId(rowSet.getInt(1));
            film.setName(rowSet.getString(2));
            film.setReleaseYear(rowSet.getInt(3));
            film.setQuality(rowSet.getString(4));
            film.setTranslation(rowSet.getString(5));
            film.setDuration(rowSet.getString(6));
            film.setRating(rowSet.getFloat(7));
            film.setUploadDate(rowSet.getTimestamp(8).toLocalDateTime());
            film.setStatus(rowSet.getInt(9));
            film.setImgLink(rowSet.getString(10));
            film.setWatchLink(rowSet.getString(11));
            film.setShortStory(rowSet.getString(12));
            film.setKinogoPage(rowSet.getInt(13));
            films.add(film);
        }
        return films;
    }

    public List<Integer> findFilmsIdByGenreLimit(int genreId, int offset, int limit) {
        List<Integer> filmsId = new ArrayList<>();
        try (Connection connection = JDBCFactory.createConnection()) {
            PreparedStatement prs = connection.prepareStatement(SELECT_FILMS_ID_BY_GENRE_LIMIT);
            prs.setInt(1, genreId);
            prs.setInt(2, offset);
            prs.setInt(3, limit);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                int filmId = rs.getInt(1);
                filmsId.add(filmId);
            }

        } catch (SQLException e) {
            //NOP
        }
        return filmsId;
    }

    public List<Integer> findFilmsIdByCountryLimit(int countryId, int offset, int limit) {
        List<Integer> filmsId = new ArrayList<>();
        try (Connection connection = JDBCFactory.createConnection()) {
            PreparedStatement prs = connection.prepareStatement(SELECT_FILMS_ID_BY_COUNTRY_LIMIT);
            prs.setInt(1, countryId);
            prs.setInt(2, offset);
            prs.setInt(3, limit);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                filmsId.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            //NOP
        }
        return filmsId;
    }

    public List<Film> findFilmsByQuality(String quality, int offset, int limit) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Film WHERE quality=:quality");
        query.setParameter("quality", quality);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<Film> findFilmsByTranslation(String translation, int offset, int limit) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Film WHERE translation=:translation");
        query.setParameter("translation", translation);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<Film> findFilmsByReleaseYear(int releaseYear, int offset, int limit) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Film WHERE release_year=:releaseYear");
        query.setParameter("releaseYear", releaseYear);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public List<Film> findFilmsByNameLike(String nameLike, int offset, int limit) {
        String filmName = "%" + nameLike + "%";
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Film WHERE film_name LIKE :filmName");
        query.setParameter("filmName", filmName);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public int countFilmsByQuality(String quality) {
        int count = 0;
        try (Connection connection = JDBCFactory.createConnection()) {
            PreparedStatement prs = connection.prepareStatement(SELECT_COUNT_OF_FILMS_BY_QUALITY_QUERY);
            prs.setString(1, quality);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            //NOP
        }
        return count;
    }

    public int countFilmsByTranslation(String translation) {
        int count = 0;
        try (Connection connection = JDBCFactory.createConnection()) {
            PreparedStatement prs = connection.prepareStatement(SELECT_COUNT_OF_FILMS_BY_TRANSLATION_QUERY);
            prs.setString(1, translation);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            //NOP
        }
        return count;
    }

    public int countFilmsByNameLike(String nameLike) {
        int count = 0;
        try (Connection connection = JDBCFactory.createConnection()) {
            PreparedStatement prs = connection.prepareStatement(SELECT_COUNT_OF_FILMS_BY_NAME_LIKE_QUERY);
            prs.setString(1, "%" + nameLike + "%");
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            //NOP
        }
        return count;
    }

    public int countFilmsByReleaseYear(int releaseYear) {
        int count = 0;
        try (Connection connection = JDBCFactory.createConnection()) {
            PreparedStatement prs = connection.prepareStatement(SELECT_COUNT_OF_FILMS_BY_RELEASE_YEAR_QUERY);
            prs.setInt(1, releaseYear);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            //NOP
        }
        return count;
    }

    public boolean clearDatabase() {
//        template.update(TRUNCATE_TABLE_FILMS_QUERY);
        sessionFactory.getCurrentSession().createQuery("delete from Film").executeUpdate();
        return true;
    }
}
