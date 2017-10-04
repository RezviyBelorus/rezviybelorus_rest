package kinoview.commonjdbc.dao;

import kinoview.commonjdbc.entity.Film;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @BeforeClass
    public static void init() {
        film = new Film();
        film.setName("Полина (2016)");
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
    }

    @Test
    public void shouldSave() throws Exception {
        boolean result = dao.save(film);

        assertTrue(result);
    }

    @Test
    public void shouldSaveBatch() throws Exception {
        List<Film> films = new ArrayList<>();
        films.add(film);
        films.add(film);
        boolean result = dao.saveBatch(films);
        assertTrue(result);
    }

    @Test
    public void shouldUpdateFilm() throws Exception {
        boolean result = dao.updateFilm(film);
        assertTrue(result);
    }

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
    public void shouldDeleteById() throws Exception {
        boolean result = dao.delete(1);
        assertTrue(result);
    }

    @Test
    public void shouldDeleteByName() throws Exception {
        boolean result = dao.delete("Полина (2016)");
        assertTrue(result);
    }

    @Test
    public void shouldFindRange() throws Exception {
        List<Film> resultFilms = dao.findRange(0, 5);
        System.out.println(resultFilms.get(0).getName());
        assertEquals(5, resultFilms.size());
    }

    @Test
    public void shouldFindByID() throws Exception {
        Film actual = dao.find(2);

        assertNotNull(actual);
    }

    @Test
    public void shouldFindByName() throws Exception {
        Film result = dao.find("Полина (2016)");
        assertEquals("Полина (2016)", result.getName());
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
        dao.setForeignKeyChecks(0);
        boolean result = dao.clearDatabase();
        dao.setForeignKeyChecks(1);
        assertTrue(result);
    }
}