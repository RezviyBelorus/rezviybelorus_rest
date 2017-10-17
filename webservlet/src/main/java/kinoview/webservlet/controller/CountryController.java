package kinoview.webservlet.controller;

import kinoview.commonjdbc.entity.Country;
import kinoview.commonjdbc.entity.dto.CountryDTO;
import kinoview.commonjdbc.service.CountryService;
import kinoview.webservlet.web.View;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.*;


/**
 * Created by alexfomin on 06.07.17.
 */
@Controller
@RequestMapping(path = "/countries")
public class CountryController{
    @Autowired
    private CountryService countryService;

    @RequestMapping(method = POST, path = "/add")
    public ModelAndView add(@RequestParam String countryName) {
        ModelAndView view = new ModelAndView(View.COUNTRY.getName());
        CountryDTO country = countryService.addCountry(countryName);
        view.addObject("country", country);

        return view;
    }

    @RequestMapping(method = DELETE, path = "/delete")
    public ModelAndView delete(@RequestParam String countryName) {
        ModelAndView view = new ModelAndView(View.COUNTRY.getName());
        boolean isDeleted = countryService.delete(countryName);
        view.addObject("isDeleted", isDeleted);

        return view;
    }

    @RequestMapping(method = GET, path = "/find")
    public ModelAndView find(@RequestParam String countryName){
        ModelAndView view = new ModelAndView(View.COUNTRY.getName());
        CountryDTO country = countryService.find(countryName);
        view.addObject("country", country);

        return view;
    }
}
