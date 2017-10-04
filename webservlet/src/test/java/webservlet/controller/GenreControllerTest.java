package webservlet.controller;

import kinoview.commonjdbc.entity.Genre;
import kinoview.webservlet.controller.GenreController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import kinoview.commonjdbc.service.GenreService;

import kinoview.webservlet.web.View;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexfomin on 13.07.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class GenreControllerTest {

    @Mock
    GenreService genreService;

    @InjectMocks
    GenreController genreController;

    @Test
    public void shouldAddGenre() throws Exception {
        //given
        Genre genre = new Genre();
        Mockito.when(genreService.addGenre("genreName")).thenReturn(genre);

        //when
        ModelAndView actual = genreController.add("genreName");

        //then
        assertEquals(View.GENRE, actual.getView());
    }

    @Test
    public void shouldDeleteGenre() throws Exception {
        //given
        Mockito.when(genreService.delete("genreName")).thenReturn(true);

        //when
        ModelAndView actual = genreController.delete("genreName");

        //then
        assertEquals(View.GENRE, actual.getView());
    }

    @Test
    public void shouldFindGenre() throws Exception {
        //given
        Genre genre = new Genre();

        Mockito.when(genreService.find("genreName")).thenReturn(genre);

        //when
        ModelAndView actual = genreController.find("genreName");

        //then
        assertEquals(View.GENRE, actual.getView());
    }
}