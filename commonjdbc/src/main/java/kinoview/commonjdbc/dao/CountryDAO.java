package kinoview.commonjdbc.dao;

import kinoview.commonjdbc.entity.Country;
import kinoview.commonjdbc.exception.IllegalRequestException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexfomin on 06.07.17.
 */
@Repository
public class CountryDAO extends AbstractDAO {
    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private JdbcTemplate template;

    private String SAVE_COUNTRY_QUERY = "INSERT INTO countries (country_name) VALUES (?)";

    private String DELETE_COUNTRY_BY_NAME_QUERY = "DELETE FROM countries WHERE country_name = ?";

    private String DELETE_COUNTRY_BY_ID_QUERY = "DELETE FROM countries WHERE country_id = ?";

    private String SELECT_COUNTRY_BY_NAME_QUERY = "SELECT * FROM countries WHERE country_name = ?";

    private String SELECT_COUNTRY_BY_ID_QUERY = "SELECT * FROM countries WHERE country_id = ?";

    private String INSERT_FILM_TO_COUNTRIES_QUERY = "INSERT INTO films_to_countries VALUES (?,?)";

    private String SELECT_ALL_COUNTRIES_BY_FILM_QUERY = "SELECT c.country_name FROM countries AS c " +
            "INNER JOIN films_to_countries AS f ON c.country_id = f.country_id WHERE f.film_id = ?";

    private String SELECT_ALL_COUNTRIES_QUERY = "SELECT country_id, country_name FROM countries";

    public boolean save(Country country) {

        template.update(SAVE_COUNTRY_QUERY, country.getCountryName());
        return true;
    }

    public boolean saveFilmToCountries(int filmId, List<Integer> countriesId) {
        template.batchUpdate(INSERT_FILM_TO_COUNTRIES_QUERY, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int countryId = countriesId.get(i);
                ps.setInt(1, filmId);
                ps.setInt(2, countryId);
            }

            @Override
            public int getBatchSize() {
                return countriesId.size();
            }
        });
        return true;
    }

    public boolean delete(int id) {
        template.update(DELETE_COUNTRY_BY_ID_QUERY, id);
        return true;
    }

    public boolean delete(String countryName) {
        template.update(DELETE_COUNTRY_BY_NAME_QUERY, countryName);
        return true;
    }

    public Country find(int id) {
        return template.queryForObject(SELECT_COUNTRY_BY_ID_QUERY, new RowMapper<Country>() {
            @Override
            public Country mapRow(ResultSet rs, int i) throws SQLException {
                Country country = new Country();
                country.setCountryId(rs.getInt(1));
                country.setCountryName(rs.getString(2));
                return country;
            }
        }, id);
    }

    public Country find(String countryName) {
        return template.queryForObject(SELECT_COUNTRY_BY_NAME_QUERY, new RowMapper<Country>() {
            @Override
            public Country mapRow(ResultSet rs, int i) throws SQLException {
                Country country = new Country();
                country.setCountryId(rs.getInt(1));
                country.setCountryName(rs.getString(2));
                return country;
            }
        }, countryName);
    }

    public List<Country> findAllCountries() {
        SqlRowSet rs = template.queryForRowSet(SELECT_ALL_COUNTRIES_QUERY);
        List<Country> countries = new ArrayList<>();
        while (rs.next()) {
            Country country = new Country();
            country.setCountryId(rs.getInt(1));
            country.setCountryName(rs.getString(2));
            countries.add(country);
        }
        return countries;
    }

    public List<String> findAllByFilm(int filmId) {
        SqlRowSet rs = template.queryForRowSet(SELECT_ALL_COUNTRIES_BY_FILM_QUERY, filmId);
        List<String> countriesNames = new ArrayList<>();
        while (rs.next()) {
            countriesNames.add(rs.getString(1));
        }
        return countriesNames;
    }
}
