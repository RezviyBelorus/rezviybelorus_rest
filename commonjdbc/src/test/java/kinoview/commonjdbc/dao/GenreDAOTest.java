package kinoview.commonjdbc.dao;

import kinoview.commonjdbc.entity.Genre;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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


    @Before
    public void setUp() {

    }

    @Test
    public void save() throws Exception {
        genre = new Genre();
        genre.setGenreName("comedy");
        boolean saveResult = dao.save(genre);

        assertTrue(saveResult);
    }

    //todo: after normal savings
    @Test
    @Ignore
    public void saveFilmToGenre() throws Exception {
        int filmId = 1;
        List<Integer> genresId = new ArrayList<>();
        genresId.add(3);
        genresId.add(4);
        boolean result = dao.saveFilmToGenre(filmId, genresId);
        assertTrue(result);
    }

    @Test
    public void shouldDeleteById() throws Exception {
        int genreId = 7;
        boolean result = dao.delete(genreId);
        assertTrue(result);
    }

    @Test
    public void shouldDeleteByGenreName() throws Exception {
        boolean result = dao.delete("horror");
        assertTrue(result);
    }

    @Test
    public void shouldFindByID() throws Exception {
        Genre actual = dao.find(8);

        Assert.assertEquals(8, actual.getGenreId());
    }

    @Test
    public void shouldFindByGenreName() throws Exception {
        Genre actual = dao.find("family");
        Assert.assertEquals("family", actual.getGenreName());
    }

    @Test
    public void findAllGenres() throws Exception {
        List<Genre> actual = dao.findAllGenres();
        assertNotNull(actual);
    }

    //todo: after normal savings
    @Ignore
    @Test
    public void findAllByFilm() throws Exception {
        List<String> actual = dao.findAllByFilm(1);
        assertNotNull(actual);
    }

}