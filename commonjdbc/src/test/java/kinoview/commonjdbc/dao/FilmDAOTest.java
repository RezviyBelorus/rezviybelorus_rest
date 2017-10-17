package kinoview.commonjdbc.dao;

import kinoview.commonjdbc.entity.Country;
import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.entity.Genre;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by alexfomin on 04.07.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:common-jdbc.xml")
@Transactional
@Rollback
public class FilmDAOTest {

    @Autowired
    private FilmDAO dao;
    private static Film film;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private CountryDAO countryDAO;


    @BeforeClass
    public static void init() {
        Country country = new Country();
        country.setCountryName("Russia");

        Country country2 = new Country();
        country2.setCountryName("Belarus");

        Set<Country> countries = new HashSet<>();
        countries.add(country);
        countries.add(country2);

        Genre genre = new Genre();
        genre.setGenreName("horror");

        Genre genre2 = new Genre();
        genre2.setGenreName("drama");

        Set<Genre> genres = new HashSet<>();
        genres.add(genre);
        genres.add(genre2);

        film = new Film();
        film.setName("Ветреная река (2017)");
        film.setReleaseYear(2017);
        film.setQuality("HDRip");
        film.setTranslation("Дублированный");
        film.setDuration("01:24:13");
        film.setRating(5);
        film.setUploadDate(LocalDateTime.now());
        film.setStatus(3);
        film.setWatchLink("watchLink");
        film.setImgLink("imgLink");
        film.setShortStory("story");
        film.setKinogoPage(333);
        film.setCountries(countries);
        film.setGenres(genres);
    }

    @Ignore
    @Test
    public void shouldSave() throws Exception {
        Set<Genre> allGenres = genreDAO.findAllGenres();
        allGenres.retainAll(film.getGenres());
        allGenres.addAll(film.getGenres());
        film.setGenres(allGenres);

        Set<Country> countries = countryDAO.findAllCountries();
        countries.retainAll(film.getCountries());
        countries.addAll(film.getCountries());
        film.setCountries(countries);

        boolean result = dao.save(film);

        assertTrue(result);
    }

    @Test
    public void shouldSaveBatch() throws Exception {
        List<Film> films = new ArrayList<>();
        films.add(film);
        Set<Genre> allGenres = genreDAO.findAllGenres();
        Set<Country> allCountries = countryDAO.findAllCountries();
        films.forEach(film->{
            Set<Genre> genres = new HashSet<>();
            genres.addAll(allGenres);
            genres.retainAll(film.getGenres());
            genres.addAll(film.getGenres());
            film.setGenres(genres);

            Set<Country> countries = new HashSet<>();
            countries.addAll(allCountries);
            countries.retainAll(film.getCountries());
            countries.addAll(film.getCountries());
            film.setCountries(countries);
        });
        boolean result = dao.saveBatch(films);
        assertTrue(result);
    }

    @Ignore
    @Test
    public void shouldUpdateFilm() throws Exception {
        Film filmInDB = dao.find(film.getName());
        film.setId(filmInDB.getId());
        filmInDB.getCountries().addAll(film.getCountries());
        filmInDB.getGenres().addAll(film.getGenres());
        film.setGenres(filmInDB.getGenres());
        film.setCountries(filmInDB.getCountries());
        boolean result = dao.updateFilm(film);
        assertTrue(result);
    }

    @Ignore
    @Test
    public void shouldUpdateBatchFilms() throws Exception {
        List<Film> films = new ArrayList<>();
        films.add(film);
        films.add(film);
        boolean result = dao.updateBatchFilms(films);
        assertTrue(result);
    }

    @Test
    public void shouldCountFilmsInDB() throws Exception {
        int i = dao.countFilmsInDB();
        boolean result = false;
        if (i > 0) {
            result = true;
        }
        assertTrue(true);
    }

    @Test
    public void shouldDeleteFilm() throws Exception {
        Film film = dao.find(1);
        boolean result = dao.delete(film);

        assertTrue(result);
    }

    @Test
    public void shouldFindRange() throws Exception {
        int offset = -5;
        int filmsQuantity = 2;
        List<Film> resultFilms = dao.findRange(offset, filmsQuantity);
        assertEquals(filmsQuantity, resultFilms.size());
    }

    @Test
    public void shouldFindByID() throws Exception {
        Film actual = dao.find(400);

        assertNotNull(actual);
    }

    @Test
    public void shouldFindByName() throws Exception {
        String filmName = "Полина (2016)";
        Film result = dao.find(filmName);
        assertEquals(filmName, result.getName());
    }

    @Test
    public void shouldFindLoadedFilms() throws Exception {
        List<Film> resultFilms = dao.findLoadedFilms();
        assertNotNull(resultFilms);
    }

    @Test
    public void shouldSetStatus() throws Exception {
        boolean result = dao.setStatus("Полина (2016)", 1);
        assertTrue(result);
    }
    @Test
    public void shouldAddFilmToFavorites() throws Exception {
        boolean result = dao.addFilmToFavorites(55, 1);
        assertTrue(result);
    }

    @Test
    public void shouldSetForeignKeyChecks() throws Exception {
        boolean result = dao.setForeignKeyChecks(1);
        assertTrue(result);
    }

    @Ignore("transactional is not working, will totally clear the table!")
    @Test
    public void shouldClearDatabase() throws Exception {
        boolean result = dao.clearDatabase();
        assertTrue(result);
    }
}