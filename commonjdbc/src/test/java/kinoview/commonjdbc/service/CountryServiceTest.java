package kinoview.commonjdbc.service;

import kinoview.commonjdbc.dao.CountryDAO;
import kinoview.commonjdbc.entity.Country;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by alexfomin on 07.07.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {

    @Mock
    CountryDAO countryDAO;

    @InjectMocks
    CountryService countryService;

    @Test
    public void shouldAddCountry() throws Exception {
        //given
        Country country = new Country();
        country.setCountryName("testCountry");

        Mockito.when(countryDAO.find("testCountry")).thenReturn(null).thenReturn(country);

        //when
        Country actual = countryService.addCountry("testCountry");

        //then
        Assert.assertEquals(country.getCountryName(), actual.getCountryName());
    }

    @Test
    public void shouldDeleteCountryByName() throws Exception {
        //given
        Mockito.when(countryDAO.delete("countryName")).thenReturn(true);

        //when
        boolean isDeleted = countryService.delete("countryName");

        //then
        Assert.assertTrue(isDeleted);
    }

    @Test
    public void shouldFindByCountryName() throws Exception {
        //given
        Country country = new Country();
        country.setCountryName("testCountry");

        Mockito.when(countryDAO.find("testCountry")).thenReturn(country);

        //when
        Country actual = countryService.find("testCountry");

        //then
        Assert.assertEquals(country.getCountryName(), actual.getCountryName());
    }

    @Test
    public void shouldFindByCountryId() throws Exception {
        //given
        Country country = new Country();
        country.setCountryId(1);

        Mockito.when(countryDAO.find(1)).thenReturn(country);

        //when
        Country actual = countryService.find(1);

        //then
        Assert.assertEquals(country.getCountryId(), actual.getCountryId());
    }
}