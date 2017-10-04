package kinoview.commonjdbc.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by alexfomin on 01.07.17.
 */

public class Film {
    private int id;
    private String name;
    private int releaseYear;

    private String quality;
    private String translation;
    private String duration;
    private float rating;
    private LocalDateTime uploadDate;
    private int status;
    private String imgLink;
    private String watchLink;
    private String shortStory;
    private int kinogoPage;

    private List<String> countries = new ArrayList<>();
    private List<String> genres = new ArrayList<>();

    private List<Integer> countrieId = new ArrayList<>();
    private List<Integer> genreId = new ArrayList<>();

    public int getKinogoPage() {
        return kinogoPage;
    }

    public void setKinogoPage(int kinogoPage) {
        this.kinogoPage = kinogoPage;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getWatchLink() {
        return watchLink;
    }

    public void setWatchLink(String watchLink) {
        this.watchLink = watchLink;
    }

    public String getShortStory() {
        return shortStory;
    }

    public void setShortStory(String shortStory) {
        this.shortStory = shortStory;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Integer> getCountrieId() {
        return countrieId;
    }

    public void setCountrieId(List<Integer> countrieId) {
        this.countrieId = countrieId;
    }

    public List<Integer> getGenreId() {
        return genreId;
    }

    public void setGenreId(List<Integer> genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object obj) {
        Film film = (Film) obj;
        if (this.getName().equals(film.getName())
                && this.getReleaseYear() == film.getReleaseYear()
                && this.getShortStory().equals(film.getShortStory())) {
            return true;
        }
        return false;
    }
}


