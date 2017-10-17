package kinoview.commonjdbc.service;

import kinoview.commonjdbc.dao.CountryDAO;
import kinoview.commonjdbc.dao.FilmDAO;
import kinoview.commonjdbc.dao.GenreDAO;
import kinoview.commonjdbc.dao.UserDAO;
import kinoview.commonjdbc.entity.Country;
import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.entity.Genre;
import kinoview.commonjdbc.entity.User;
import kinoview.commonjdbc.entity.dto.FilmDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by alexfomin on 07.07.17.
 */

@RunWith(MockitoJUnitRunner.class)
public class FilmServiceTest {


    @Mock
    FilmDAO filmDAO;
    @Mock
    GenreDAO genreDAO;
    @Mock
    CountryDAO countryDAO;

    @Mock
    UserDAO userDAO;

    @InjectMocks
    FilmService filmService;

    @Test
    public void shouldSaveFilm() throws Exception {
        //given
        Film film = new Film();
        film.setName("testFilm");

        Mockito.when(filmDAO.find("testFilm")).thenReturn(null).thenReturn(film);
        Mockito.when(filmDAO.save(film)).thenReturn(true);

        //when
        FilmDTO actual = filmService.save("testFilm", "2017", "HD", "RU",
                "01:34:13", "3.5", "1,2,3", "1,2,3", "shortStory",
                456, "genres", "countries");

        //then
        Assert.assertEquals(film.getName(), actual.getName());
    }

    @Test
    public void shouldDeleteFilm() throws Exception {
        //given
        Film film = new Film();
        film.setName("testFilm");
        film.setStatus(3);

        Mockito.when(filmDAO.setStatus("testFilm", 3)).thenReturn(true);
        Mockito.when(filmDAO.find("testFilm")).thenReturn(film);

        //when
        FilmDTO actual = filmService.delete("testFilm");

        //then
        Assert.assertEquals(film.getName(), actual.getName());
    }

    @Test
    public void shouldFindFilmByName() throws Exception {
        //given
        Film film = new Film();
        film.setId(33);
        film.setName("testFilm");
        film.setStatus(1);

        Set<String> genres = new HashSet<>();
        genres.add("comedy");

        Set<String> countries = new HashSet<>();
        countries.add("USA");

        Mockito.when(filmDAO.find("testFilm")).thenReturn(film);
        Mockito.when(genreDAO.findAllByFilm(33)).thenReturn(genres);
        Mockito.when(countryDAO.findAllByFilm(33)).thenReturn(countries);

        //when
        FilmDTO actual = filmService.find("testFilm");

        //then
        Assert.assertEquals(film.getName(), actual.getName());
    }

//    @Test
//    public void shouldFindGenres() throws Exception {
//        //given
//        ArrayList<String> genres = new ArrayList<>();
//        genres.add("comedy");
//
//        Mockito.when(genreDAO.findAllByFilm(33)).thenReturn(genres);
//
//        //when
//        Set<String> actual = filmService.findGenres(33);
//
//        //then
//        Assert.assertEquals(genres.get(0), actual.c);
//    }

//    @Test
//    public void shouldFindCountries() throws Exception {
//        //given
//        ArrayList<String> countries = new ArrayList<>();
//        countries.add("USA");
//
//        Mockito.when(countryDAO.findAllByFilm(33)).thenReturn(countries);
//
//        //when
//        Set<String> actual = filmService.findCountries(33);
//
//        //then
//        Assert.assertEquals(countries.get(0), actual.get(0));
//    }

    @Test
    public void shouldSetStatus() throws Exception {
        //given
        Film film = new Film();
        film.setName("testFilm");
        film.setStatus(1);

        Mockito.when(filmDAO.setStatus("testFilm", 1)).thenReturn(true);
        Mockito.when(filmDAO.find("testFilm")).thenReturn(film);

        //when
        FilmDTO actual = filmService.setStatus("testFilm", "1");

        //then
        Assert.assertEquals(film.getStatus(), actual.getStatus());
    }

    @Test
    public void shouldAddToFavorites() throws Exception {
        //given
        User user = new User();
        user.setId(1);

        Film film = new Film();
        film.setId(1);

        Mockito.when(userDAO.find("user")).thenReturn(user);
        Mockito.when(filmDAO.find("film")).thenReturn(film);
        Mockito.when(filmDAO.addFilmToFavorites(1, 1)).thenReturn(true);

        //when
        boolean isAdded = filmService.addToFavorites("user", "film");

        //then
        Assert.assertTrue(isAdded);
    }

    @Test
    public void getFilms() throws Exception {
        //given
        Film film = new Film();
        film.setName("filmFromDB");
        List<Film> films = new ArrayList<>();
        films.add(film);

        Mockito.when(filmDAO.findRange(0, 2)).thenReturn(films);

        //when
        List<FilmDTO> resultList = filmService.getFilms(0, 2);

        //then
        Assert.assertNotNull(resultList);
    }

    @Test
    public void findRange() throws Exception {
        //given
        Film film = new Film();
        film.setName("filmFromDB");
        List<Film> films = new ArrayList<>();
        films.add(film);

        Mockito.when(filmDAO.findRange(0, 1)).thenReturn(films);

        //when
        List<FilmDTO> resultList = filmService.findRange(0, 1);

        //then
        Assert.assertNotNull(resultList);
    }
}