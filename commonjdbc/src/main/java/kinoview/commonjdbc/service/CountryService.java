package kinoview.commonjdbc.service;

import kinoview.commonjdbc.dao.CountryDAO;
import kinoview.commonjdbc.entity.Country;
import kinoview.commonjdbc.entity.dto.CountryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by alexfomin on 06.07.17.
 */
@Service
public class CountryService {
    @Autowired
    private CountryDAO countryDAO;

    @Transactional
    public CountryDTO addCountry(String countryName) {
        Country country = countryDAO.find(countryName);

        if(country==null) {
            country = new Country();
            country.setCountryName(countryName);
            countryDAO.save(country);
            return new CountryDTO(countryDAO.find(countryName));
        }
        return null;
    }

    @Transactional
    public boolean delete(String countryName) {
        Country country = countryDAO.find(countryName);
        boolean isDeleted = countryDAO.delete(country);
        if(isDeleted) {
            return true;
        }
        return false;
    }

    public CountryDTO find(String countryName) {
        return new CountryDTO(countryDAO.find(countryName));
    }

    public int countOfFilmsByCountry(int countryId) {
        return countryDAO.countFilmsByCountry(countryId);
    }

    public CountryDTO find(int countryId){
        return new CountryDTO(countryDAO.find(countryId));
    }
}
