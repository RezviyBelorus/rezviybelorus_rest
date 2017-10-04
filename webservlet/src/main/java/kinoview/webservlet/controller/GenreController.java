package kinoview.webservlet.controller;

import kinoview.commonjdbc.entity.Genre;
import kinoview.commonjdbc.service.GenreService;
import kinoview.webservlet.web.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by alexfomin on 06.07.17.
 */
@Controller
@RequestMapping(path = "/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @RequestMapping(method = POST, path = "/add")
    public ModelAndView add(String genreName) {
        ModelAndView view = new ModelAndView(View.GENRE.getName());
        Genre genre = genreService.addGenre(genreName);
        view.addObject("genre", genre);

        return view;
    }

    @RequestMapping(method = DELETE, path = "/delete")
    public ModelAndView delete(String genreName) {
        ModelAndView view = new ModelAndView(View.GENRE.getName());
        boolean delete = genreService.delete(genreName);
        view.addObject("isDeleted", delete);

        return view;
    }

    @RequestMapping(method = GET, path = "/find")
    public ModelAndView find(String genreName) {
        ModelAndView view = new ModelAndView(View.GENRE.getName());
        Genre genre = genreService.find(genreName);
        view.addObject("genre", genre);

        return view;
    }
}
