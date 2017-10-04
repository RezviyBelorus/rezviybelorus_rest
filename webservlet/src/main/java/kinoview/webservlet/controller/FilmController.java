package kinoview.webservlet.controller;

import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.service.FilmService;
import kinoview.commonjdbc.service.UserService;
import kinoview.webservlet.web.View;

import kinoview.commonjdbc.entity.dto.FilmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;

/**
 * Created by alexfomin on 04.07.17.
 */
@Controller
@RequestMapping(value = "/films")
public class FilmController {

    @Autowired
    private FilmService filmService;
    @Autowired
    private UserService userService;

    @RequestMapping(method = POST, path = "/save")
    public ModelAndView save(@RequestParam String filmName, @RequestParam String releaseYear,
                             @RequestParam String quality, @RequestParam String translation,
                             @RequestParam String duration, @RequestParam String rating,
                             @RequestParam String imgLink, @RequestParam String watchLink,
                             @RequestParam String shortStory, @RequestParam int kinogoPage,
                             @RequestParam String genres, @RequestParam String countries) {
        ModelAndView view = new ModelAndView(View.FILM.getName());
        FilmDTO film = (FilmDTO) filmService.save(filmName, releaseYear, quality, translation, duration, rating,
                imgLink, watchLink, shortStory, kinogoPage, genres, countries);
        view.addObject("film", film);
        return view;
    }

    @RequestMapping(method = POST, path = "/saveBatch")
    public ModelAndView saveBatch(@RequestParam List<Film> films) {
        ModelAndView view = new ModelAndView(View.MAIN.getName());
        List<Film> savedFilms = filmService.saveBatch(films);
        view.addObject("films", savedFilms);
        return view;
    }

    @RequestMapping(method = DELETE, path = "/delete")
    public ModelAndView delete(@RequestParam String filmName) {
        ModelAndView view = new ModelAndView(View.FILM.getName());
        FilmDTO isDeleted = filmService.delete(filmName);
        view.addObject("isDeleted", isDeleted);
        return view;
    }

    @RequestMapping(method = GET, path = "/find")
    public ModelAndView find(@RequestParam String filmName) {
        ModelAndView view = new ModelAndView(View.FILM.getName());
        FilmDTO film = filmService.find(filmName);
        view.addObject("film", film);

        return view;
    }

    @RequestMapping(method = POST, path = "/addToFavorites")
    public ModelAndView addToFavorites(@RequestParam String userLoginOrEmail, @RequestParam String filmName) {
        ModelAndView view = new ModelAndView(View.FILM.getName());
        boolean isAdded = filmService.addToFavorites(userLoginOrEmail, filmName);
        view.addObject("isAdded", isAdded);

        return view;
    }

    @RequestMapping(method = POST, path = "/setStatus")
    public ModelAndView setStatus(@RequestParam String filmName, @RequestParam String status) {
        ModelAndView view = new ModelAndView(View.FILM.getName());
        FilmDTO filmDTO = filmService.setStatus(filmName, status);
        view.addObject("film", filmDTO);

        return view;
    }

    @RequestMapping(method = DELETE, path = "/clearTableFilms")
    public ModelAndView clearTableFilms() {
        filmService.clearTableFilms();
        return new ModelAndView(View.FILM.getName());
    }
}
