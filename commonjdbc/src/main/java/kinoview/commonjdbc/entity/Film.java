package kinoview.commonjdbc.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by alexfomin on 01.07.17.
 */
@Entity
@Table(name = "films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private int id;

    @Column(name = "film_name")
    private String name;

    @Column(name = "release_year")
    private int releaseYear;

    @Column(name = "quality")
    private String quality;

    @Column(name = "translation")
    private String translation;

    @Column(name = "length")
    private String duration;

    @Column(name = "rating")
    private float rating;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @Column(name = "status")
    private int status;

    @Column(name = "img_link")
    private String imgLink;

    @Column(name = "watch_link")
    private String watchLink;

    @Column(name = "short_story")
    private String shortStory;

    @Column(name = "kinogo_page")
    private int kinogoPage;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "films_to_countries",
            joinColumns = @JoinColumn(name = "film_id"), inverseJoinColumns = @JoinColumn(name = "country_id"))
    private Set<Country> countries = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "films_to_genres",
            joinColumns = @JoinColumn(name = "film_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

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

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
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


