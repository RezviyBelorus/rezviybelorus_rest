package kinoview.commonjdbc.service;

import kinoview.commonjdbc.dao.GenreDAO;
import kinoview.commonjdbc.entity.Genre;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by alexfomin on 07.07.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class GenreServiceTest {

    @Mock
    private GenreDAO genreDAO;

    @InjectMocks
    GenreService genreService;

    @Test
    public void shouldAddGenre() throws Exception {
        //given
        Genre genre = new Genre();
        genre.setGenreName("testGenre");
        genre.setGenreId(1);

        Mockito.when(genreDAO.find("testGenre")).thenReturn(null).thenReturn(genre);
        Mockito.when(genreDAO.save(genre)).thenReturn(true);

        //when
        Genre actual = genreService.addGenre("testGenre");

        //then
        Assert.assertNotNull(actual);
    }

    @Test
    public void shouldDeleteGenreByName() throws Exception {
        //given
        Mockito.when(genreDAO.delete("genreName")).thenReturn(true);

        //when
        boolean isDeleted = genreService.delete("genreName");

        //then
        Assert.assertTrue(isDeleted);
    }

    @Test
    public void shouldFindGenreName() throws Exception {
        //given
        Genre genre = new Genre();
        genre.setGenreName("genreName");
        Mockito.when(genreDAO.find("genreName")).thenReturn(genre);

        //when
        Genre actual = genreService.find("genreName");

        //then
        Assert.assertEquals(genre.getGenreName(), actual.getGenreName());
    }

    @Test
    public void shouldFindGenreId() throws Exception {
        //given
        Genre genre = new Genre();
        genre.setGenreId(1);

        Mockito.when(genreDAO.find(1)).thenReturn(genre);

        //when
        Genre actual = genreService.find(1);

        //then
        Assert.assertEquals(genre.getGenreId(), actual.getGenreId());
    }
}