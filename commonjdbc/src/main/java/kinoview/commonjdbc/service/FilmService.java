package kinoview.commonjdbc.service;

import kinoview.commonjdbc.dao.CountryDAO;
import kinoview.commonjdbc.dao.FilmDAO;
import kinoview.commonjdbc.dao.GenreDAO;
import kinoview.commonjdbc.dao.UserDAO;
import kinoview.commonjdbc.entity.Country;
import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.entity.Genre;
import kinoview.commonjdbc.entity.dto.CountryDTO;
import kinoview.commonjdbc.entity.dto.FilmDTO;
import kinoview.commonjdbc.entity.dto.GenreDTO;
import kinoview.commonjdbc.exception.IllegalRequestException;
import kinoview.commonjdbc.util.Validator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.*;


/**
 * Created by alexfomin on 05.07.17.
 */
@Service
@Transactional
public class FilmService {
    private static final Logger logger = Logger.getLogger(FilmService.class);
    @Autowired
    private FilmDAO filmDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    GenreService genreService;

    @Autowired
    CountryService countryService;

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
            film.setGenres(createGenresSet(genres));
            film.setCountries(createCountriesSet(countries));
            filmDAO.save(film);

            film = filmDAO.find(filmName);
            return new FilmDTO(film);
        }
        return null;
    }

    private Set<Country> createCountriesSet(String countries) {
        String[] countriesArray = countries.split(",");
        Set<Country> countrySet = new HashSet<>();
        for (String countryName : countriesArray) {
            Country country = new Country();
            country.setCountryName(countryName);
            countrySet.add(country);
        }
        Set<Country> allCountries = countryDAO.findAllCountries();
        allCountries.retainAll(countrySet);
        allCountries.addAll(countrySet);
        countrySet = allCountries;
        return countrySet;
    }

    private Set<Genre> createGenresSet(String genres) {
        String[] genresArray = genres.split(",");
        Set<Genre> genresSet = new HashSet<>();
        for (String genreName : genresArray) {
            Genre genre = new Genre();
            genre.setGenreName(genreName);
            genresSet.add(genre);
        }
        Set<Genre> allGenres = genreDAO.findAllGenres();
        allGenres.retainAll(genresSet);
        allGenres.addAll(genresSet);
        genresSet = allGenres;
        return genresSet;
    }

    @Transactional
    public List<Film> saveBatch(List<Film> films) {
        List<Film> filmsToSave = new ArrayList<>();
        filmsToSave.addAll(films);
        List<Film> filmsInDB = filmDAO.findLoadedFilms();
        filmsToSave.removeAll(filmsInDB);
        boolean isSaved = false;

        filmsToSave.forEach(film -> {
            Set<Country> allCountries = countryDAO.findAllCountries();
            allCountries.retainAll(film.getCountries());
            allCountries.addAll(film.getCountries());
            film.setCountries(allCountries);

            Set<Genre> allGenres = genreDAO.findAllGenres();
            allGenres.retainAll(film.getGenres());
            allGenres.addAll(film.getGenres());
            film.setGenres(allGenres);
            filmDAO.save(film);
        });

        isSaved = true;
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
            return new FilmDTO(updatedFilm);
        }
        logger.error("Can't update film");
        throw new IllegalRequestException("");
    }

    @Transactional
    public List<FilmDTO> updateBatchFilms(List<Film> films) {
        List<Film> filmsToUpdate = new ArrayList<>();
        List<Film> filmsInDB = filmDAO.findLoadedFilms();
        List<FilmDTO> filmsDTO = new ArrayList<>();

        if (filmsInDB.size() == 0) {
            filmsToUpdate.forEach(film -> filmsDTO.add(new FilmDTO(film)));
            return filmsDTO;
        }
        for (Film potentialToUpdateFilm : films) {
            for (Film filmInDB : filmsInDB) {
                if (potentialToUpdateFilm.equals(filmInDB)
                        && potentialToUpdateFilm.getKinogoPage() < filmInDB.getKinogoPage()) {
                    potentialToUpdateFilm.setId(filmInDB.getId());
                    filmsToUpdate.add(potentialToUpdateFilm);
                }
            }
        }
        updateCountries(filmsToUpdate);
        updateGenres(filmsToUpdate);
        filmDAO.updateBatchFilms(filmsToUpdate);
        List<Film> updatedFilms = new ArrayList<>();
        filmsToUpdate.forEach(film -> {
            Film updatedFilm = filmDAO.find(film.getName());
            if (updatedFilm.getName() != null) {
//                updatedFilms.add(updatedFilm);
                filmsDTO.add(new FilmDTO(updatedFilm));
            }
        });
        return filmsDTO;
    }

    private void updateGenres(List<Film> filmsToUpdate) {
        Set<Genre> allGenres = genreDAO.findAllGenres();
        filmsToUpdate.forEach(film -> {
            Set<Genre> updatingGenres = new HashSet<>();
            updatingGenres.addAll(allGenres);
            updatingGenres.retainAll(film.getGenres());
            updatingGenres.addAll(film.getGenres());
            film.setGenres(updatingGenres);
        });
    }

    private void updateCountries(List<Film> filmsToUpdate) {
        Set<Country> allCountries = countryDAO.findAllCountries();
        filmsToUpdate.forEach(film -> {
            Set<Country> updatingCountries = new HashSet<>();
            updatingCountries.addAll(allCountries);
            updatingCountries.retainAll(film.getCountries());
            updatingCountries.addAll(film.getCountries());
            film.setCountries(updatingCountries);
        });
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

            return new FilmDTO(film);
        }
        return null;
    }

    public int countFilmsInDB() {
        return filmDAO.countFilmsInDB();
    }

    public List<FilmDTO> findRange(int offset, int limit) {
        List<Film> rangeOfFilms = filmDAO.findRange(offset, limit);
        List<FilmDTO> rangeOfFilmsDTO = new ArrayList<>();
        rangeOfFilms.forEach(film -> rangeOfFilmsDTO.add(new FilmDTO(film)));
        return rangeOfFilmsDTO;
    }

    public List<FilmDTO> findLoadedFilms() {
        List<Film> loadedFilms = filmDAO.findLoadedFilms();
        List<FilmDTO> loadedFilmsDTO = new ArrayList<>();
        loadedFilms.forEach(film -> loadedFilmsDTO.add(new FilmDTO(film)));
        return loadedFilmsDTO;
    }

    //todo: Set<Genre>
    public Set<String> findGenres(int filmId) {
        return genreDAO.findAllByFilm(filmId);
    }

    //todo: Set<Country>
    public Set<String> findCountries(int filmId) {
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

    public List<FilmDTO> getThumbnails(int countOfFilms, int thumbNailsNumber) {
        Random random = new Random();
        int min = 1;
        int max = countOfFilms - thumbNailsNumber;
        int i = min + random.nextInt(max - min);
        return findRange(i, thumbNailsNumber);
    }

    public List<FilmDTO> getFilms(int page, int filmsPerPage) {
        int offset = page * filmsPerPage - filmsPerPage;
        return findRange(offset, filmsPerPage);
    }

    public List<FilmDTO> findByGenre(String genreName, int page, int filmsPerpage) {
        int offset = (page - 1) * filmsPerpage;
        GenreDTO genreDTO = genreService.find(genreName);
        List<FilmDTO> filmDTOList = new ArrayList<>();
        List<Integer> filmIdByGenreLimit = filmDAO.findFilmsIdByGenreLimit(genreDTO.getGenreId(), offset, filmsPerpage);
        filmIdByGenreLimit.forEach(id -> {
            Film film = filmDAO.find(id);
            filmDTOList.add(new FilmDTO(film));
        });
        return filmDTOList;
    }

    public List<FilmDTO> findByCountry(String countryName, int page, int filmsPerPage) {
        int offset = (page - 1) * filmsPerPage;
        CountryDTO countryDTO = countryService.find(countryName);
        List<Integer> filmsIdByCountry = filmDAO.findFilmsIdByCountryLimit(countryDTO.getCountryId(), offset, filmsPerPage);
        List<FilmDTO> filmDTOList = new ArrayList<>();
        filmsIdByCountry.forEach(id -> {
            Film film = filmDAO.find(id);
            filmDTOList.add(new FilmDTO(film));
        });
        return filmDTOList;
    }

    public List<FilmDTO> findFilmsByQuality(String quality, int page, int filmsPerPage) {
        int offset = (page - 1) * filmsPerPage;
        List<Film> filmsByQuality = filmDAO.findFilmsByQuality(quality, offset, filmsPerPage);
        List<FilmDTO> filmDTOList = new ArrayList<>();
        filmsByQuality.forEach(film->{
            filmDTOList.add(new FilmDTO(film));
        });
        return filmDTOList;
    }

    public List<FilmDTO> findFilmsByTranslation(String translation, int page, int filmsPerPage) {
        int offset = page - 1 * filmsPerPage;
        List<Film> filmsByTranslation = filmDAO.findFilmsByTranslation(translation, offset, filmsPerPage);
        List<FilmDTO> filmDTOList = new ArrayList<>();
        filmsByTranslation.forEach(film->{
            filmDTOList.add(new FilmDTO(film));
        });
        return filmDTOList;
    }

    public List<FilmDTO> findFilmsByReleaseYear(int releaseYear, int page, int filmsPerPage) {
        int offset = page - 1 * filmsPerPage;
        List<Film> filmsByReleaseYear = filmDAO.findFilmsByReleaseYear(releaseYear, offset, filmsPerPage);
        List<FilmDTO> filmDTOList = new ArrayList<>();
        filmsByReleaseYear.forEach(film->{
            filmDTOList.add(new FilmDTO(film));
        });
        return filmDTOList;
    }

    public List<FilmDTO> findFilmsByNameLike(String nameLike, int page, int filmsPerPage) {
        int offset = page - 1 * filmsPerPage;
        List<Film> filmsByNameLike = filmDAO.findFilmsByNameLike(nameLike, offset, filmsPerPage);
        List<FilmDTO> filmDTOList = new ArrayList<>();
        filmsByNameLike.forEach(film->{
            filmDTOList.add(new FilmDTO(film));
        });
        return filmDTOList;
    }

    public int countFilmByGenre(String genreName) {
        GenreDTO genreDTO = genreService.find(genreName);
        return genreService.countOfFilmsByGenre(genreDTO.getGenreId());
    }

    public int countFilmsByCountry(String countryName) {
        CountryDTO countryDTO = countryService.find(countryName);
        return countryService.countOfFilmsByCountry(countryDTO.getCountryId());
    }

    public int countFilmsByQuality(String quality) {
        return filmDAO.countFilmsByQuality(quality);
    }

    public int countFilmsByTranslation(String translation) {
        return filmDAO.countFilmsByTranslation(translation);
    }

    public int countFilmsByReleaseYear(int releaseYear) {
        return filmDAO.countFilmsByReleaseYear(releaseYear);
    }

    public int countFilmsByNameLike(String nameLike) {
        return filmDAO.countFilmsByNameLike(nameLike);
    }
}
