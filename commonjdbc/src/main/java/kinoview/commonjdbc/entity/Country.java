package kinoview.commonjdbc.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexfomin on 06.07.17.
 */
@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private int countryId;

    @Column(name = "country_name")
    private String countryName;

    @ManyToMany(mappedBy = "countries", fetch = FetchType.LAZY)
    private List<Film> films = new ArrayList<>();

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

        Country country = (Country) o;

        return countryName != null ? countryName.equals(country.countryName) : country.countryName == null;
    }

    @Override
    public int hashCode() {
        return countryName != null ? countryName.hashCode() : 0;
    }
}
