package kinoview.commonjdbc.service;

import kinoview.commonjdbc.dao.CountryDAO;
import kinoview.commonjdbc.entity.Country;
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
    public Country addCountry(String countryName) {
        Country country = countryDAO.find(countryName);

        if(country==null) {
            country = new Country();
            country.setCountryName(countryName);
            countryDAO.save(country);
            return countryDAO.find(countryName);
        }
        return null;
    }

    @Transactional
    public boolean delete(String countryName) {
        boolean isDeleted = countryDAO.delete(countryName);
        if(isDeleted) {
            return true;
        }
        return false;
    }

    public Country find(String countryName) {
        return countryDAO.find(countryName);
    }

    public Country find(int countryId){
        return countryDAO.find(countryId);
    }
}
