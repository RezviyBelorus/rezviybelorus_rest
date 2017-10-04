package webservlet.controller;

import kinoview.commonjdbc.entity.Country;
import kinoview.webservlet.controller.CountryController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import kinoview.commonjdbc.service.CountryService;

import kinoview.webservlet.web.View;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexfomin on 13.07.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {
    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @Test
    public void shouldAddCountry() throws Exception {
        //given
        Country country = new Country();
        country.setCountryName("testCountry");

        Mockito.when(countryService.addCountry("testCountry")).thenReturn(country);

        //when
        ModelAndView actual = countryController.add("testCountry");

        //then
        assertEquals(View.COUNTRY, actual.getView());
    }

    @Test
    public void shouldDeleteCountry() throws Exception {
        //given
        Country country = new Country();
        country.setCountryName("testCountry");

        Mockito.when(countryService.delete("testCountry")).thenReturn(true);

        //when
        ModelAndView actual = countryController.delete("testCountry");

        //then
        assertEquals(View.COUNTRY, actual.getView());
    }

    @Test
    public void shouldFindCountry() throws Exception {
        //given
        Country country = new Country();
        country.setCountryName("testCountry");

        Mockito.when(countryService.find("testCountry")).thenReturn(country);

        //when
        ModelAndView actual = countryController.find("testCountry");

        //then
        assertEquals(View.COUNTRY, actual.getView());
    }
}