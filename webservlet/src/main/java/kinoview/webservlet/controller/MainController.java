package kinoview.webservlet.controller;


import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.entity.Genre;
import kinoview.commonjdbc.entity.dto.FilmDTO;
import kinoview.commonjdbc.entity.dto.GenreDTO;
import kinoview.commonjdbc.service.FilmService;
import kinoview.commonjdbc.service.GenreService;
import kinoview.commonjdbc.service.MainService;
import kinoview.commonjdbc.util.LocalProperties;
import kinoview.commonjdbc.util.Validator;

import kinoview.webservlet.web.View;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
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

    //    @RequestMapping(method = GET, path = "/main")
//    public ModelAndView loadMain(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
    //todo: delete or not
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            Arrays.stream(cookies)
//                    .filter(cookie -> cookie.getName().equals("userfName"))
//                    .findFirst()
//                    .ifPresent(presented -> request.setAttribute("hello", HELLO + presented.getValue()));
//        }

//        ModelAndView view = new ModelAndView(View.MAIN.getName());
//        int countOfFilms = filmService.countFilmsInDB();
//        int filmsPerPage = Validator.validateInt(properties.get(FILMS_PER_PAGE));
//        int lastPageNumber = countOfFilms / filmsPerPage;
//
//        view.addAllObjects(mainService.getPagination(page, filmsPerPage, lastPageNumber));
//
//        List<FilmDTO> rangeOfFilms = filmService.getFilms(page, filmsPerPage);
//        Set<GenreDTO> allGenres = genreService.getAllGenres();
//
//        view.addObject("films", rangeOfFilms);
//        view.addObject("allGenres", allGenres);
//
//        int thumbNailsNumber = Validator.validateInt(properties.get(THUMBNAILS_NUMBER));
//        List<FilmDTO> thumbnails = filmService.getThumbnails(countOfFilms, thumbNailsNumber);
//        view.addObject("thumbnails", thumbnails);
//
//        return view;
//    }

    @RequestMapping("/*")
    public ModelAndView notFound() {
        return new ModelAndView(View.NOT_FOUND.getName());
    }

    @GetMapping(path = "/main", produces = "application/json")
    public FilmDTO getMain(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        int countOfFilms = filmService.countFilmsInDB();
        int filmsPerPage = Validator.validateInt(properties.get(FILMS_PER_PAGE));
        int lastPageNumber = countOfFilms / filmsPerPage;

        Map<String, Object> pagination = mainService.getPagination(page, filmsPerPage, lastPageNumber);

        List<FilmDTO> rangeOfFilms = filmService.getFilms(page, filmsPerPage);

        Set<GenreDTO> allGenres = genreService.getAllGenres();

        int thumbNailsNumber = Validator.validateInt(properties.get(THUMBNAILS_NUMBER));
        List<FilmDTO> thumbnails = filmService.getThumbnails(countOfFilms, thumbNailsNumber);

        rangeOfFilms = null;
        thumbnails = null;

        FilmDTO filmDTO = new FilmDTO(rangeOfFilms, thumbnails, allGenres, pagination);

        return filmDTO;
    }
}
