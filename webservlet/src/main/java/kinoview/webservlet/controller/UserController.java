package kinoview.webservlet.controller;

import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.entity.Genre;
import kinoview.commonjdbc.entity.User;
import kinoview.commonjdbc.entity.dto.FilmDTO;
import kinoview.commonjdbc.entity.dto.GenreDTO;
import kinoview.commonjdbc.service.FilmService;
import kinoview.commonjdbc.service.GenreService;
import kinoview.commonjdbc.service.MainService;
import kinoview.commonjdbc.service.UserService;
import kinoview.commonjdbc.util.LocalProperties;
import kinoview.commonjdbc.util.Validator;
import kinoview.webservlet.web.View;

import kinoview.commonjdbc.entity.dto.UserDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by alexfomin on 04.07.17.
 */

//todo: create java docs
@Controller
@RequestMapping(path = "/users")
public class UserController {
    Logger logger = Logger.getLogger(UserController.class);
    private static LocalProperties properties = new LocalProperties();
    private static final String FILMS_PER_PAGE = "film.filmsperpage";
    private static final String THUMBNAILS_NUMBER = "film.thumbnailsnumber";

    @Autowired
    private UserService userService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private MainService mainService;

    @Autowired
    GenreService genreService;

    @RequestMapping(method = GET, path = "/signUp")
    public ModelAndView signUp() {
        return new ModelAndView(View.SIGN_UP.getName());
    }

    @RequestMapping(method = POST, path = "/signUp")
    public ModelAndView signUp(@RequestParam String login, @RequestParam String password, @RequestParam String fName,
                               @RequestParam String lName, @RequestParam String email) {
        ModelAndView view = new ModelAndView(View.SIGN_UP_ERROR.getName());
        UserDTO userDTO = userService.save(login, password, fName, lName, email);
        if (userDTO != null) {
            view = new ModelAndView(View.LOGIN.getName());
            view.addObject("user", userDTO);
        }
        return view;
    }

    @RequestMapping(method = GET, path = "/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("userfName"))
                .findFirst()
                .ifPresent(presented -> request.setAttribute("userfName", presented.getValue()));
        return new ModelAndView(View.LOGIN.getName());
    }

    @RequestMapping(method = POST, path = "/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam String emailOrLogin, @RequestParam String password) {
        ModelAndView view = new ModelAndView(View.LOGIN_ERROR.getName());

        UserDTO userDTO = userService.login(emailOrLogin, password);

        if (userDTO != null) {
            view = new ModelAndView(View.MAIN.getName());
            view.addObject("user", userDTO);

            HttpSession session = request.getSession(true);
            session.setAttribute("userId", userDTO.getId());
            session.setAttribute("userfName", userDTO.getfName());
            session.setAttribute("login", userDTO.getLogin());

            int countOfFilms = filmService.countFilmsInDB();
            int filmsPerPage = Validator.validateInt(properties.get(FILMS_PER_PAGE));
            int lastPageNumber = countOfFilms / filmsPerPage;

            int page = 1;
            view.addAllObjects(mainService.getPagination(page, filmsPerPage, lastPageNumber));

            List<FilmDTO> rangeOfFilms = filmService.getFilms(page, filmsPerPage);

            Set<GenreDTO> allGenres = genreService.getAllGenres();

            view.addObject("allGenres", allGenres);
            view.addObject("films", rangeOfFilms);
            view.addObject("user", userDTO);

            int thumbNailsNumber = Validator.validateInt(properties.get(THUMBNAILS_NUMBER));
            List<FilmDTO> thumbnails = filmService.getThumbnails(countOfFilms, thumbNailsNumber);
            view.addObject("thumbnails", thumbnails);

            //todo: cookies
            Cookie cookie = new Cookie("userfName", userDTO.getfName());
            cookie.setMaxAge(180 * 24 * 60 * 60);
            response.addCookie(cookie);
        }
        return view;
    }

    //todo:
    @RequestMapping(method = POST, path = "/logout")
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView view = new ModelAndView(View.MAIN.getName());
        HttpSession session = request.getSession(true);
        session.setAttribute("login", null);

        int countOfFilms = filmService.countFilmsInDB();
        int filmsPerPage = Validator.validateInt(properties.get(FILMS_PER_PAGE));
        int lastPageNumber = countOfFilms / filmsPerPage;

        int page = 1;
        view.addAllObjects(mainService.getPagination(page, filmsPerPage, lastPageNumber));

        List<FilmDTO> rangeOfFilms = filmService.getFilms(page, filmsPerPage);
        Set<GenreDTO> allGenres = genreService.getAllGenres();

        view.addObject("allGenres", allGenres);
        view.addObject("films", rangeOfFilms);

        int thumbNailsNumber = Validator.validateInt(properties.get(THUMBNAILS_NUMBER));
        List<FilmDTO> thumbnails = filmService.getThumbnails(countOfFilms, thumbNailsNumber);
        view.addObject("thumbnails", thumbnails);

        return view;
    }

    @RequestMapping(method = GET, path = "/findId")
    public ModelAndView find(@RequestParam int id) {
        ModelAndView view = new ModelAndView(View.USER.getName());
        User user = userService.find(id);
        view.addObject("user", user);
        return view;
    }

    @RequestMapping(method = GET, path = "/findEmailOrLogin")
    public ModelAndView find(@RequestParam String emailOrLogin) {
        ModelAndView view = new ModelAndView(View.USER.getName());
        User user = userService.find(emailOrLogin);
        view.addObject("user", user);
        return view;
    }

    @RequestMapping(method = DELETE, path = "/delete")
    public ModelAndView delete(@RequestParam String emailOrLogin) {
        ModelAndView view = new ModelAndView(View.USER.getName());
        UserDTO userDTO = userService.delete(emailOrLogin);
        view.addObject("user", userDTO);
        return view;
    }

    @RequestMapping(method = POST, path = "/setStatus")
    public ModelAndView setStatus(@RequestParam String emailOrLogin, @RequestParam String status) {
        ModelAndView view = new ModelAndView(View.USER.getName());
        UserDTO userDTO = userService.setStatus(emailOrLogin, status);
        view.addObject("user", userDTO);
        return view;
    }
}
