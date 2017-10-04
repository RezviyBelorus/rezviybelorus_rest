package kinoview.commonjdbc.entity.dto;

import kinoview.commonjdbc.entity.Film;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexfomin on 05.07.17.
 */
public class FilmDTO {
    private int id;
    private String name;
    private int releaseYear;
    private String quality;
    private String translation;
    private String duration;
    private float rating;
    private LocalDateTime uploadDate;
    private int status;

    private List<String> genres = new ArrayList<>();
    private List<String> countries = new ArrayList<>();


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
        this.genres = film.getGenres();
        this.countries = film.getCountries();
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

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
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

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
