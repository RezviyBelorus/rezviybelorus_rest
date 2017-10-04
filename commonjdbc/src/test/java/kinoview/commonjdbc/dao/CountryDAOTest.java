package kinoview.commonjdbc.dao;

import kinoview.commonjdbc.entity.Country;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:common-jdbc.xml")
@Transactional
@Rollback
public class CountryDAOTest {
    @Autowired
    CountryDAO dao;

    @Test
    public void shouldSave() throws Exception {
        Country country = new Country();
        country.setCountryName("Hungary");
        boolean result = dao.save(country);
        assertTrue(result);
    }

    //todo: need normal savings
    @Ignore
    @Test
    public void shouldSaveFilmToCountries() throws Exception {
        int filmdId = 1;
        List<Integer> countriesId = new ArrayList<>();
        countriesId.add(1);
        countriesId.add(2);
        boolean result = dao.saveFilmToCountries(filmdId, countriesId);
        assertTrue(result);
    }

    @Ignore("primary key")
    @Test
    public void shouldDeleteById() throws Exception {
        boolean result = dao.delete(1);
        assertTrue(result);
    }

    @Ignore("primary key")
    @Test
    public void shouldDeleteByName() throws Exception {
        boolean result = dao.delete("USA");
        assertTrue(result);
    }

    @Test
    public void shouldFindById() throws Exception {
        Country country = dao.find(1);
        assertEquals(1, country.getCountryId());
    }

    @Test
    public void shouldFindByName() throws Exception {
        Country country = dao.find("USA");
        assertEquals("USA", country.getCountryName());
    }

    @Test
    public void shouldFindAllCountries() throws Exception {
        List<Country> actual = dao.findAllCountries();
        assertNotNull(actual);
    }

    @Test
    public void shouldFindAllByFilm() throws Exception {
        List<String> actual = dao.findAllByFilm(3);
        assertNotNull(actual);
    }
}