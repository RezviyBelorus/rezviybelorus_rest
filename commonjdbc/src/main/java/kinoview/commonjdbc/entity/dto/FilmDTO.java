package kinoview.commonjdbc.entity.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import kinoview.commonjdbc.entity.Country;
import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.entity.Genre;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by alexfomin on 05.07.17.
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FilmDTO implements Serializable {
    @JsonProperty
    private int id;
    @JsonProperty
    private String name;
    @JsonProperty
    private int releaseYear;
    @JsonProperty
    private String quality;
    @JsonProperty
    private String translation;
    @JsonProperty
    private String duration;
    @JsonProperty
    private float rating;
    @JsonProperty
    private LocalDateTime uploadDate;
    @JsonProperty
    private int status;

    @JsonProperty
    private Set<GenreDTO> allGenres = new HashSet<>();
    @JsonProperty
    private Set<CountryDTO> allCountries = new HashSet<>();
    @JsonProperty
    private List<FilmDTO> films;
    @JsonProperty
    private List<FilmDTO> thumbnails;
    @JsonProperty
    private Map<String, Object> pagination;

    public FilmDTO() {
    }

    public FilmDTO(Film film) {
        this.id = film.getId();
        this.name = film.getName();
        this.releaseYear = film.getReleaseYear();
        this.quality = film.getQuality();
        this.translation = film.getTranslation();
        this.duration = film.getDuration();
        this.rating = film.getRating();
        this.uploadDate = film.getUploadDate();
        this.status = film.getStatus();
        film.getGenres().forEach(genre -> allGenres.add(new GenreDTO(genre)));
        film.getCountries().forEach(country -> allCountries.add(new CountryDTO(country)));
    }

    public FilmDTO(List<FilmDTO> rangeOfFilms, List<FilmDTO> thumbnails, Set<GenreDTO> allGenres, Map<String, Object> pagination) {
        this.films = rangeOfFilms;
        this.thumbnails = thumbnails;
        this.allGenres = allGenres;
        this.pagination = pagination;
    }


    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<CountryDTO> getAllCountries() {
        return allCountries;
    }

    public void setAllCountries(Set<CountryDTO> allCountries) {
        this.allCountries = allCountries;
    }

    public Set<GenreDTO> getAllGenres() {
        return allGenres;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setAllGenres(Set<GenreDTO> allGenres) {
        this.allGenres = allGenres;
    }

    public List<FilmDTO> getFilms() {
        return films;
    }

    public void setFilms(List<FilmDTO> films) {
        this.films = films;
    }

    public List<FilmDTO> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<FilmDTO> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public Map<String, Object> getPagination() {
        return pagination;
    }

    public void setPagination(Map<String, Object> pagination) {
        this.pagination = pagination;
    }
}
