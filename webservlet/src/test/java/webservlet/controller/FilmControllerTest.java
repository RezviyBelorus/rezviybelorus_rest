package webservlet.controller;

import kinoview.commonjdbc.entity.Film;
import kinoview.webservlet.controller.FilmController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import kinoview.commonjdbc.service.FilmService;
import kinoview.webservlet.web.View;
import kinoview.commonjdbc.entity.dto.FilmDTO;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexfomin on 13.07.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class FilmControllerTest {
    @Mock
    private FilmService filmService;

    @InjectMocks
    private FilmController filmController;

    @Test
    public void shuoldUploadFilm() throws Exception {
        FilmDTO film = new FilmDTO(new Film());
        film.setName("testFilm");

        Mockito.when(filmService.save("testFilm", "1980", "1", "2",
                "12:02:01", "4", "123", "321", "shortStory",
                456,"genres", "countries")).thenReturn(film);

        //when
        ModelAndView actual = filmController.save("testFilm", "1980", "1",
                "2","12:02:01", "4", "123", "321",
                "shortStory", 456,"genres", "countries");

        //then
        assertEquals(View.FILM, actual.getView());
    }

    @Test
    public void shouldDelete() throws Exception {
        //given
        FilmDTO filmDTO = new FilmDTO(new Film());

        Mockito.when(filmService.delete("testFilm")).thenReturn(filmDTO);

        //when
        ModelAndView actual = filmController.delete("testFilm");

        //then
        assertEquals(View.FILM, actual.getView());
    }

    @Test
    public void shouldFindFilm() throws Exception {
        //given
        FilmDTO filmDTO = new FilmDTO(new Film());
        filmDTO.setName("testFilm");

        Mockito.when(filmService.find("testFilm")).thenReturn(filmDTO);

        //when
        ModelAndView actual = filmController.find("testFilm");

        //then
        assertEquals(View.FILM, actual.getView());
    }

    @Test
    public void shouldAddFilmToFavorites() throws Exception {
        //given
        Mockito.when(filmService.addToFavorites("login", "filmName")).thenReturn(true);

        //when
        ModelAndView actual = filmController.addToFavorites("login", "filmName");

        //then
        assertEquals(View.FILM, actual.getView());
    }

    @Test
    public void shouldSetStatus() throws Exception {
        //given
        FilmDTO filmDTO = new FilmDTO(new Film());
        Mockito.when(filmService.setStatus("filmName", "1")).thenReturn(filmDTO);

        //when
        ModelAndView actual = filmController.setStatus("filmName", "1");

        //then
        assertEquals(View.FILM, actual.getView());
    }

}