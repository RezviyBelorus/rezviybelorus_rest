package kinoview.webservlet.controller;


import kinoview.commonjdbc.entity.dto.FilmDTO;
import kinoview.commonjdbc.entity.dto.GenreDTO;
import kinoview.commonjdbc.service.FilmService;
import kinoview.commonjdbc.service.GenreService;
import kinoview.commonjdbc.service.MainService;
import kinoview.commonjdbc.util.LocalProperties;
import kinoview.commonjdbc.util.Validator;

import kinoview.webservlet.web.View;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class MainController {
    private static LocalProperties properties = new LocalProperties();
    private static final String FILMS_PER_PAGE = "film.filmsperpage";
    private static final String THUMBNAILS_NUMBER = "film.thumbnailsnumber";

    @Autowired
    FilmService filmService;
    @Autowired
    MainService mainService;

    @Autowired
    GenreService genreService;

    private static final String HELLO = "Hello, ";

    @RequestMapping(method = GET, path = "/main")
    public ModelAndView loadMain(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {

        ModelAndView view = new ModelAndView(View.MAIN.getName());
        int countOfFilms = filmService.countFilmsInDB();
        int filmsPerPage = Validator.validateInt(properties.get(FILMS_PER_PAGE));
        int lastPageNumber = countOfFilms / filmsPerPage;

        view.addAllObjects(mainService.getPagination(page, filmsPerPage, lastPageNumber));

        List<FilmDTO> rangeOfFilms = filmService.getFilms(page, filmsPerPage);
        Set<GenreDTO> allGenres = genreService.getAllGenres();

        view.addObject("films", rangeOfFilms);
        view.addObject("allGenres", allGenres);

        int thumbNailsNumber = Validator.validateInt(properties.get(THUMBNAILS_NUMBER));
        List<FilmDTO> thumbnails = filmService.getThumbnails(countOfFilms, thumbNailsNumber);
        view.addObject("thumbnails", thumbnails);

        return view;
    }

    @RequestMapping(method = GET, path = "/search")
    public ModelAndView findFilmByGenre(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                        @RequestParam String genreName) {
        ModelAndView view = new ModelAndView(View.SEARCH_RESULT.getName());
        int filmsPerPage = Validator.validateInt(properties.get(FILMS_PER_PAGE));



        int countOfFilms = filmService.countFilmByGenre(genreName);

        int lastPageNumber = countOfFilms / filmsPerPage;

        view.addAllObjects(mainService.getPagination(page, filmsPerPage, lastPageNumber));

        Set<GenreDTO> allGenres = genreService.getAllGenres();

        List<FilmDTO> filmsByGenre = filmService.findByGenre(genreName, page, filmsPerPage);
        view.addObject("films", filmsByGenre);
        view.addObject("allGenres", allGenres);

        int thumbNailsNumber = Validator.validateInt(properties.get(THUMBNAILS_NUMBER));
        List<FilmDTO> thumbnails = filmService.getThumbnails(countOfFilms, thumbNailsNumber);
        view.addObject("thumbnails", thumbnails);

        return view;
    }

    @RequestMapping("/*")
    public ModelAndView notFound() {
        return new ModelAndView(View.NOT_FOUND.getName());
    }

//    @GetMapping(path = "/main", produces = "application/json")
//    public FilmDTO getMain(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
//        int countOfFilms = filmService.countFilmsInDB();
//        int filmsPerPage = Validator.validateInt(properties.get(FILMS_PER_PAGE));
//        int lastPageNumber = countOfFilms / filmsPerPage;
//
//        Map<String, Object> pagination = mainService.getPagination(page, filmsPerPage, lastPageNumber);
//
//        List<FilmDTO> rangeOfFilms = filmService.getFilms(page, filmsPerPage);
//
//        Set<GenreDTO> allGenres = genreService.getAllGenres();
//
//        int thumbNailsNumber = Validator.validateInt(properties.get(THUMBNAILS_NUMBER));
//        List<FilmDTO> thumbnails = filmService.getThumbnails(countOfFilms, thumbNailsNumber);
//
//        rangeOfFilms = null;
//        thumbnails = null;
//
//        FilmDTO filmDTO = new FilmDTO(rangeOfFilms, thumbnails, allGenres, pagination);
//
//        return filmDTO;
//    }
}
