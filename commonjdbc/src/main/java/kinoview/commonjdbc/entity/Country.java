package kinoview.commonjdbc.entity;

import java.util.List;

/**
 * Created by alexfomin on 06.07.17.
 */
public class Country {
    private int countryId;
    private String countryName;
    private List<String> countries;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }
}
