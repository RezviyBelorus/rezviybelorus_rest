package kinoview.commonjdbc.entity.dto;

import kinoview.commonjdbc.entity.Country;
import kinoview.commonjdbc.entity.Film;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CountryDTO implements Serializable {
    private int countryId;

    private String countryName;

    private List<Film> films = new ArrayList<>();

    public CountryDTO() {
    }

    public CountryDTO(Country country) {
        this.countryId = country.getCountryId();
        this.countryName = country.getCountryName();
        this.films = country.getFilms();
    }

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

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryDTO that = (CountryDTO) o;

        return countryName != null ? countryName.equals(that.countryName) : that.countryName == null;
    }

    @Override
    public int hashCode() {
        return countryName != null ? countryName.hashCode() : 0;
    }

    @Override
    public String toString() {
        return countryName;
    }
}
