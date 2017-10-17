package kinoview.commonjdbc.dao;

import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.exception.IllegalRequestException;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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

    @Autowired
    SessionFactory sessionFactory;

    Logger logger = Logger.getLogger(this.getClass());

    private String UPDATE_STATUS_BY_FILM_NAME_QUERY = "UPDATE films SET status = ? WHERE film_name = ?";

    private String ADD_TO_FAVORITES_QUERY = "INSERT INTO favorites_by_user VALUES (?, ?)";

    private String SET_FOREIGN_KEY_CHECKS_QUERY = "SET FOREIGN_KEY_CHECKS = ?";
    private String TRUNCATE_TABLE_FILMS_QUERY = "TRUNCATE TABLE films";

    private String SELECT_COUNT_OF_FILMS_QUERY = "SELECT COUNT(*) FROM films";

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

    public boolean clearDatabase() {
//        template.update(TRUNCATE_TABLE_FILMS_QUERY);
        sessionFactory.getCurrentSession().createQuery("delete from Film").executeUpdate();
        return true;
    }
}
