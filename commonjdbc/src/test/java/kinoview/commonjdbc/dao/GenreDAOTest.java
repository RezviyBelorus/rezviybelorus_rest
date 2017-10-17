package kinoview.commonjdbc.dao;

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

import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by alexfomin on 04.07.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:common-jdbc.xml")
@Transactional
@Rollback
public class GenreDAOTest {

    @Autowired
    private GenreDAO dao;
    private Genre genre;

    @Test
    public void save() throws Exception {
        genre = new Genre();
        genre.setGenreName("комедия");
        Genre genreInDB = dao.find(genre.getGenreName());
        if (genreInDB != null) {
            genre=genreInDB;
        }
        boolean saveResult = dao.save(genre);

        assertTrue(saveResult);
    }

    @Ignore("primary key")
    @Test
    public void shouldDeleteById() throws Exception {
        Genre genre = dao.find(8);
        boolean result = dao.delete(genre);
        assertTrue(result);
    }

    @Ignore("foreign key")
    @Test
    public void shouldDeleteByGenreName() throws Exception {
        Genre genre = dao.find("horror");
        boolean result = dao.delete(genre);
        assertTrue(result);
    }

    @Test
    public void shouldFindByID() throws Exception {
        Genre actual = dao.find(8);

        Assert.assertEquals(8, actual.getGenreId());
    }

    @Test
    public void shouldFindByGenreName() throws Exception {
        Genre actual = dao.find("семейный");
        Assert.assertEquals("семейный", actual.getGenreName());
    }

    @Test
    public void findAllGenres() throws Exception {
        Set<Genre> actual = dao.findAllGenres();
        assertNotNull(actual);
    }

    @Test
    public void findAllByFilm() throws Exception {
        Set<String> actual = dao.findAllByFilm(4008);
        assertNotNull(actual);
    }
}