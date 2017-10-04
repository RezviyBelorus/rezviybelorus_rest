package kinoview.commonjdbc.service;

import kinoview.commonjdbc.dao.CountryDAO;
import kinoview.commonjdbc.dao.FilmDAO;
import kinoview.commonjdbc.dao.GenreDAO;
import kinoview.commonjdbc.dao.UserDAO;
import kinoview.commonjdbc.entity.Country;
import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.entity.Genre;
import kinoview.commonjdbc.entity.dto.FilmDTO;
import kinoview.commonjdbc.exception.IllegalRequestException;
import kinoview.commonjdbc.util.Validator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


/**
 * Created by alexfomin on 05.07.17.
 */
@Service
public class FilmService {
    private static final Logger logger = Logger.getLogger(FilmService.class);
    @Autowired
    private FilmDAO filmDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private CountryDAO countryDAO;

    @Transactional
    public FilmDTO save(String filmName, String releaseYear, String quality, String translation,
                        String duration, String rating, String imgLink, String watchLink, String shortStory,
                        int kinogoPage, String genres, String countries) {
        Film film = filmDAO.find(filmName);
        if (film == null) {
            film = new Film();
            film.setName(filmName);
            film.setReleaseYear(Validator.validateInt(releaseYear));
            film.setQuality(quality);
            film.setTranslation(translation);
            film.setDuration(duration);
            film.setRating(Validator.validateFloat(rating));
            film.setUploadDate(LocalDateTime.now());
            film.setStatus(1);
            film.setImgLink(imgLink);
            film.setWatchLink(watchLink);
            film.setShortStory(shortStory);
            film.setKinogoPage(kinogoPage);
            filmDAO.save(film);

            String[] byGenre = genres.split(", ");
            String[] byCountry = countries.split(", ");

            saveGenres(filmName, Arrays.asList(byGenre));
            saveCountries(filmName, Arrays.asList(byCountry));

            film = filmDAO.find(filmName);
            return new FilmDTO(film);
        }
        return null;
    }

    @Transactional
    public List<Film> saveBatch(List<Film> films) {
        List<Film> filmsToSave = new ArrayList<>();
        filmsToSave.addAll(films);
        List<Film> filmsInDB = filmDAO.findLoadedFilms();
        filmsToSave.removeAll(filmsInDB);
        boolean isSaved = filmDAO.saveBatch(filmsToSave);
        for (Film film : filmsToSave) {
            saveGenres(film.getName(), film.getGenres());
            saveCountries(film.getName(), film.getCountries());
        }
        if (isSaved) {
            return filmsToSave;
        }
        logger.error("Can't save batch of films");
        throw new IllegalRequestException("");
    }

    @Transactional
    public FilmDTO updateFilm(Film film) {
        Film dao = filmDAO.find(film.getName());
        if (film.getName() == dao.getName() && film.getReleaseYear() == dao.getReleaseYear() && film.getKinogoPage() < dao.getKinogoPage()) {
            filmDAO.updateFilm(film);
        }
        Film updatedFilm = filmDAO.find(film.getName());
        if (updatedFilm.getName() != null) {
            updatedFilm.setCountries(findCountries(updatedFilm.getId()));
            updatedFilm.setGenres(findGenres(updatedFilm.getId()));
            return new FilmDTO(updatedFilm);
        }
        logger.error("Can't update film");
        throw new IllegalRequestException("");
    }

    @Transactional
    public List<Film> updateBatchFilms(List<Film> films) {
        List<Film> filmsToUpdate = new ArrayList<>();
        List<Film> filmsInDB = filmDAO.findLoadedFilms();

        if (filmsInDB.size() == 0) {
            return filmsToUpdate;
        }
        for (Film potentialToUpdateFilm : films) {
            for (Film filmInDB : filmsInDB) {
                if (potentialToUpdateFilm.equals(filmInDB)
                        && potentialToUpdateFilm.getKinogoPage() < filmInDB.getKinogoPage()) {
                    filmsToUpdate.add(potentialToUpdateFilm);
                }
            }
        }
        filmDAO.updateBatchFilms(filmsToUpdate);
        List<Film> updatedFilms = new ArrayList<>();
        filmsToUpdate.forEach(film -> {
            Film updatedFilm = filmDAO.find(film.getName());
            if (updatedFilm.getName() != null) {
                updatedFilm.setCountries(findCountries(updatedFilm.getId()));
                updatedFilm.setGenres(findGenres(updatedFilm.getId()));
                updatedFilms.add(updatedFilm);
            }
        });
        return updatedFilms;
    }

    @Transactional
    public FilmDTO delete(String filmName) {
        boolean isDeleted = filmDAO.setStatus(filmName, FilmStatus.DELETED.getValue());
        if (isDeleted) {
            return new FilmDTO(filmDAO.find(filmName));
        }
        logger.error("Can't delete film");
        throw new IllegalRequestException("");
    }

    public FilmDTO find(String filmName) {
        Film film = filmDAO.find(filmName);
        if (film.getStatus() == FilmStatus.ACTIVE.getValue()) {

            film.setGenres(findGenres(film.getId()));
            film.setCountries(findCountries(film.getId()));

            return new FilmDTO(film);
        }
        return null;
    }

    public int countFilmsInDB() {
        return filmDAO.countFilmsInDB();
    }

    public List<Film> findRange(int offset, int limit) {
        List<Film> rangeOfFilms = filmDAO.findRange(offset, limit);
        rangeOfFilms.forEach(film -> {
                    film.setGenres(findGenres(film.getId()));
                    film.setCountries(findCountries(film.getId()));
                });
        return rangeOfFilms;
    }

    public List<Film> findLoadedFilms() {
        List<Film> loadedFilms = filmDAO.findLoadedFilms();
        for (Film loadedFilm : loadedFilms) {
            loadedFilm.setGenres(findGenres(loadedFilm.getId()));
            loadedFilm.setCountries(findCountries(loadedFilm.getId()));
        }
        return loadedFilms;
    }

    public List<String> findGenres(int filmId) {
        return genreDAO.findAllByFilm(filmId);
    }

    public List<String> findCountries(int filmId) {
        return countryDAO.findAllByFilm(filmId);
    }

    @Transactional
    public FilmDTO setStatus(String filmName, String status) {
        filmDAO.setStatus(filmName, Validator.validateInt(status));
        Film film = filmDAO.find(filmName);
        return new FilmDTO(film);
    }

    @Transactional
    public boolean addToFavorites(String userEmailOrLogin, String filmName) {
        int userId = userDAO.find(userEmailOrLogin).getId();
        int filmId = filmDAO.find(filmName).getId();
        boolean isAdded = filmDAO.addFilmToFavorites(userId, filmId);
        if (isAdded) {
            return true;
        }
        return false;
    }

    @Transactional
    public boolean clearTableFilms() {
        boolean isKeyChecksDisable = filmDAO.setForeignKeyChecks(0);
        if (isKeyChecksDisable) {
            filmDAO.clearDatabase();
            filmDAO.setForeignKeyChecks(1);
            return true;
        }
        return false;
    }

    private boolean saveGenres(String filmName, List<String> filmGenres) {
        Film film = filmDAO.find(filmName);
        int filmId = film.getId();

        List<Integer> genresId = new ArrayList<>();
        List<Genre> allGenres = genreDAO.findAllGenres();
        for (String filmGenre : filmGenres) {
            for (Genre genreInDB : allGenres) {
                if (filmGenre.equals(genreInDB.getGenreName())) {
                    genresId.add(genreInDB.getGenreId());
                }
            }
        }

        return genreDAO.saveFilmToGenre(filmId, genresId);
    }

    private boolean saveCountries(String filmName, List<String> countries) {
        int filmId = filmDAO.find(filmName).getId();
        List<Integer> countriesId = new ArrayList<>();
        List<Country> allCountries = countryDAO.findAllCountries();
        for (String country : countries) {
            for (Country countryInDB : allCountries) {
                if (country.equals(countryInDB.getCountryName())) {
                    countriesId.add(countryInDB.getCountryId());
                }
            }
        }
        return countryDAO.saveFilmToCountries(filmId, countriesId);
    }

    public List<Film> getThumbnails(int countOfFilms, int thumbNailsNumber) {
        Random random = new Random();
        int min = 1;
        int max = countOfFilms-thumbNailsNumber;
        int i = min + random.nextInt(max-min);
        return findRange(i, thumbNailsNumber);
    }

    public List<Film> getFilms(int page, int filmsPerPage){
        int offset = page*filmsPerPage-filmsPerPage;
        return findRange(offset, filmsPerPage);
    }
}
