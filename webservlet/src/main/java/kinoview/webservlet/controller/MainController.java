package kinoview.webservlet.controller;

import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.service.FilmService;
import kinoview.commonjdbc.service.MainService;
import kinoview.commonjdbc.util.LocalProperties;
import kinoview.commonjdbc.util.Validator;

import kinoview.webservlet.web.View;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

@Controller
public class MainController {
    private static LocalProperties properties = new LocalProperties();
    private static final String FILMS_PER_PAGE = "film.filmsperpage";
    private static final String THUMBNAILS_NUMBER = "film.thumbnailsnumber";

    @Autowired
    FilmService filmService;
    @Autowired
    MainService mainService;

    private static final String HELLO = "Hello, ";

    @RequestMapping(method = GET, path = "/main")
    public ModelAndView loadMain(@RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        //todo: delete or not
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            Arrays.stream(cookies)
//                    .filter(cookie -> cookie.getName().equals("userfName"))
//                    .findFirst()
//                    .ifPresent(presented -> request.setAttribute("hello", HELLO + presented.getValue()));
//        }

        ModelAndView view = new ModelAndView(View.MAIN.getName());
        int countOfFilms = filmService.countFilmsInDB();
        int filmsPerPage = Validator.validateInt(properties.get(FILMS_PER_PAGE));
        int lastPageNumber = countOfFilms / filmsPerPage;

        view.addAllObjects(mainService.getPagination(page, filmsPerPage, lastPageNumber));

        List<Film> rangeOfFilms = filmService.getFilms(page, filmsPerPage);

        view.addObject("films", rangeOfFilms);

        int thumbNailsNumber = Validator.validateInt(properties.get(THUMBNAILS_NUMBER));
        List<Film> thumbnails = filmService.getThumbnails(countOfFilms, thumbNailsNumber);
        view.addObject("thumbnails", thumbnails);

        return view;
    }


    @RequestMapping("/*")
    public ModelAndView notFound() {
        return new ModelAndView(View.NOT_FOUND.getName());
    }
}
