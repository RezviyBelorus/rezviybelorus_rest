package kinoview.commonjdbc.service;

import kinoview.commonjdbc.entity.Country;
import kinoview.commonjdbc.entity.Film;
import kinoview.commonjdbc.entity.Genre;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import kinoview.commonjdbc.util.LocalProperties;
import kinoview.commonjdbc.util.Validator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class KinogoPageParser {
    private static Logger logger = Logger.getLogger(KinogoPageParser.class);
    private static LocalProperties properties = new LocalProperties();

    private static final String DEFAULT_FILM_STATUS = "film.defaultstatus";

    private static final String YEAR = "Год выпуска: ";
    private static final String COUNTRY = " Страна: ";
    private static final String GENRE = " Жанр: ";
    private static final String QUALITY = " Качество: ";
    private static final String TRANSLATION = " Перевод: ";
    private static final String DURATION = " Продолжительность: ";
    private static final String PREMIERE = " Премьера \\(РФ\\): ";

    private static Pattern pattern;
    private static Matcher matcher;

    public static List<Film> parseFilms(Document document, int kinogoPage) throws IOException {

        List<Film> films = new ArrayList<>();

        try {
            List<String> filmsNames = getFilmsName(document);
            List<String> filmsDescription = getFilmsDescription(document);
            List<String> rating = getRating(document);
            List<String> imgLink = getImgLink(document);
            List<String> watchLink = getWatchLink(document);

            films = fillListByFilms(filmsNames, filmsDescription, rating, imgLink, watchLink, kinogoPage);
        } catch (RuntimeException e) {
            logger.error("KinogoParser: can't parse the page: " + kinogoPage);
        }
        return films;
    }

    private static List<String> getImgLink(Document document) {
        List<String> imgLink = new ArrayList<>();
        Elements shortmigClass = document.select(".shortimg");
        Elements imgSelector = shortmigClass.select("img");
        for (Element element : imgSelector) {
            String stringLink = "http://kinogo.club" + element.attr("src");
            imgLink.add(stringLink);
        }
        return imgLink;
    }

    private static List<String> getWatchLink(Document document) {
        List<String> watchLink = new ArrayList<>();
        Elements zagolovkiClass = document.select(".zagolovki");
        for (Element aClass : zagolovkiClass) {
            watchLink.add(aClass.select("a").attr("href"));
        }
        return watchLink;
    }

    private static List<String> getRating(Document document) {
        List<String> rating = new ArrayList<>();
        Elements ratingAttr = document.select(".current-rating");
        for (Element element : ratingAttr) {
            rating.add(element.text());
        }
        return rating;
    }

    private static List<String> getFilmsDescription(Document document) {
        List<String> filmsDescription = new ArrayList<>();
        Elements shortmigClass = document.select(".shortimg");
        for (Element element : shortmigClass) {
            filmsDescription.add(element.text());
        }
        return filmsDescription;
    }

    private static List<String> getFilmsName(Document document) throws UnsupportedEncodingException {
        List<String> filmsNames = new ArrayList<>();
        Elements filmTitles = document.select("h2");
        for (Element title : filmTitles) {
            filmsNames.add(title.text());
        }
        return filmsNames;
    }

    private static List<Film> fillListByFilms(List<String> filmsName, List<String>
            filmsDescription, List<String> rating, List<String> imgLink, List<String> watchLink, int kinogoPage) {
        List<Film> films = new ArrayList<>();
        for (int i = 0; i < filmsDescription.size(); i++) {
            Film film = new Film();

            film.setName(filmsName.get(i));
            film.setReleaseYear(Validator.validateInt(getReleaseYear(filmsDescription.get(i))));
            film.setCountries(getCountries(filmsDescription.get(i)));
            film.setGenres(getGenries(filmsDescription.get(i)));
            film.setQuality(getQuality(filmsDescription.get(i)));
            film.setTranslation(getTranslation(filmsDescription.get(i)));
            film.setDuration(getDuration(filmsDescription.get(i)));
            film.setShortStory(getShortStory(filmsDescription.get(i)));
            film.setRating(Validator.validateFloat(rating.get(i)));
            film.setStatus(Validator.validateInt(properties.get(DEFAULT_FILM_STATUS)));
            film.setUploadDate(LocalDateTime.now());
            film.setImgLink(imgLink.get(i));
            film.setWatchLink(watchLink.get(i));
            film.setKinogoPage(kinogoPage);

            films.add(film);
        }
        return films;
    }

    private static String getShortStory(String filmDescription) {

        int begin = 0;
        pattern = Pattern.compile(YEAR);
        matcher = pattern.matcher(filmDescription);

        int end = 0;
        if (matcher.find()) {
            end = matcher.start();
        }
        String shortStory = filmDescription.substring(begin, end);

        return shortStory;
    }

    private static String getReleaseYear(String filmDescription) {
        pattern = Pattern.compile(YEAR);
        matcher = pattern.matcher(filmDescription);
        int begin = 0;

        if (matcher.find()) {
            begin = matcher.end();
        }

        pattern = Pattern.compile(COUNTRY);
        matcher = pattern.matcher(filmDescription);
        int end = 0;
        if (matcher.find()) {
            end = matcher.start();
        }

        String year = filmDescription.substring(begin, end);

        return year;
    }

    private static Set<Country> getCountries(String filmDescription) {
        pattern = Pattern.compile(COUNTRY);
        matcher = pattern.matcher(filmDescription);
        int begin = 0;

        if (matcher.find()) {
            begin = matcher.end();
        }

        pattern = Pattern.compile(GENRE);
        matcher = pattern.matcher(filmDescription);
        int end = 0;

        if (matcher.find()) {
            end = matcher.start();
        }

        String countries = filmDescription.substring(begin, end);
        String[] byCountry = countries.split(", ");
        Set<Country> countryList = new HashSet<>();
        for (String s : byCountry) {
            Country country = new Country();
            country.setCountryName(s);
            countryList.add(country);
        }
        return countryList;
    }

    private static Set<Genre> getGenries(String filmDescription) {
        pattern = Pattern.compile(GENRE);
        matcher = pattern.matcher(filmDescription);
        int begin = 0;

        if (matcher.find()) {
            begin = matcher.end();
        }

        pattern = Pattern.compile(QUALITY);
        matcher = pattern.matcher(filmDescription);
        int end = 0;

        if (matcher.find()) {
            end = matcher.start();
        }

        String genries = filmDescription.substring(begin, end);
        String[] byGenre = genries.split(", ");

        Set<Genre> genreList = new HashSet<>();
        for (String g : byGenre) {
            Genre genre = new Genre();
            genre.setGenreName(g.toLowerCase());
            genreList.add(genre);
        }

        return genreList;
    }

    private static String getQuality(String filmDescription) {
        pattern = Pattern.compile(QUALITY);
        matcher = pattern.matcher(filmDescription);
        int begin = 0;

        if (matcher.find()) {
            begin = matcher.end();
        }

        pattern = Pattern.compile(TRANSLATION);
        matcher = pattern.matcher(filmDescription);
        int end = 0;

        if (matcher.find()) {
            end = matcher.start();
        }

        String quality = filmDescription.substring(begin, end);
        return quality;
    }

    private static String getTranslation(String filmDescription) {
        pattern = Pattern.compile(TRANSLATION);
        matcher = pattern.matcher(filmDescription);
        int begin = 0;

        if (matcher.find()) {
            begin = matcher.end();
        }

        pattern = Pattern.compile(DURATION);
        matcher = pattern.matcher(filmDescription);
        int end = 0;

        if (matcher.find()) {
            end = matcher.start();
        }

        String translation = filmDescription.substring(begin, end);
        if (translation.contains("Оригинальное название:")) {
            String[] translationTrimmed = translation.split("Оригинальное название:");
            return translationTrimmed[0];
        }
        return translation;
    }

    private static String getDuration(String filmDescription) {
        pattern = Pattern.compile(DURATION);
        matcher = pattern.matcher(filmDescription);
        int begin = 0;

        if (matcher.find()) {
            begin = matcher.end();
        }

        pattern = Pattern.compile(PREMIERE);
        matcher = pattern.matcher(filmDescription);
        int end = 0;

        if (matcher.find()) {
            end = matcher.start();
        }

        String duration = filmDescription.substring(begin, end);
        if (duration.startsWith("~")) {
            String durationTrimmed = duration.substring(1, duration.length()-1);
            return durationTrimmed;
        }
        return duration;
    }
}
