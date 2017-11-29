package kinoview.webservlet.controller;

import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.entity.dto.GenreDTO;
import kinoview.commonjdbc.service.FilmService;
import kinoview.commonjdbc.service.GenreService;
import kinoview.commonjdbc.service.MainService;
import kinoview.commonjdbc.service.UserService;
import kinoview.commonjdbc.util.LocalProperties;
import kinoview.commonjdbc.util.Validator;
import kinoview.webservlet.web.View;

import kinoview.commonjdbc.entity.dto.FilmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;
import java.util.Set;

/**
 * Created by alexfomin on 04.07.17.
 */
@Controller
@RequestMapping(value = "/films")
public class FilmController {
    private static LocalProperties properties = new LocalProperties();
    private static final String FILMS_PER_PAGE = "film.filmsperpage";
    private static final String THUMBNAILS_NUMBER = "film.thumbnailsnumber";

    @Autowired
    private FilmService filmService;
    @Autowired
    private UserService userService;

    @Autowired
    MainService mainService;

    @Autowired
    GenreService genreService;

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

    @RequestMapping(method = GET, path = "/search")
    public ModelAndView findFilmByGenre(@RequestParam String searchingParam, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        ModelAndView view = new ModelAndView(View.SEARCH_RESULT.getName());

        int filmsPerPage = Validator.validateInt(properties.get(FILMS_PER_PAGE));

//        int countFilmsByGenre = filmService.countOfFilmByGenre(searchingParam);
        int countFilmsInDB = filmService.countFilmsInDB();
//        int lastPageNumber = countFilmsByGenre / filmsPerPage;

//        view.addAllObjects(mainService.getPaginationNEW(page, countFilmsByGenre, filmsPerPage));
//        view.addAllObjects(mainService.getPagination(page, filmsPerPage, lastPageNumber));
        view.addAllObjects(mainService.getSearchingResult(searchingParam, page, filmsPerPage));
//        List<FilmDTO> filmsByGenre = filmService.findByGenre(searchingParam, page, filmsPerPage);
        Set<GenreDTO> allGenres = genreService.getAllGenres();

//        view.addObject("searchParam", searchingParam);
//        view.addObject("films", filmsByGenre);
        view.addObject("allGenres", allGenres);

        int thumbNailsNumber = Validator.validateInt(properties.get(THUMBNAILS_NUMBER));
        List<FilmDTO> thumbnails = filmService.getThumbnails(countFilmsInDB, thumbNailsNumber);
        view.addObject("thumbnails", thumbnails);

        return view;
    }
}
