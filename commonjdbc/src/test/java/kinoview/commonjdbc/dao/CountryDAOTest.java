package kinoview.commonjdbc.dao;

import kinoview.commonjdbc.entity.Country;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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


    @Ignore("primary key")
    @Test
    public void shouldDeleteById() throws Exception {
        Country country = dao.find(10);
        boolean result = dao.delete(country);
        assertTrue(result);
    }

    @Ignore("foreign key")
    @Test
    public void shouldDeleteByName() throws Exception {
        Country country = dao.find("USA");
        boolean result = dao.delete(country);
        assertTrue(result);
    }

    @Test
    public void shouldFindById() throws Exception {
        Country country = dao.find(1);
        assertEquals(1, country.getCountryId());
    }

    @Test
    public void shouldFindByName() throws Exception {
        Country country = dao.find("Россия");
        assertEquals("Россия", country.getCountryName());
    }

    @Test
    public void shouldFindAllCountries() throws Exception {
        Set<Country> actual = dao.findAllCountries();
        assertNotNull(actual);
    }

    @Test
    public void shouldFindAllByFilm() throws Exception {
        Set<String> actual = dao.findAllByFilm(1);
        assertNotNull(actual);
    }
}