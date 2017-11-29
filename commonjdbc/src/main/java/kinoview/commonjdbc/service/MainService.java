package kinoview.commonjdbc.service;

import kinoview.commonjdbc.entity.dto.FilmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {
    private static final String CLASS_PAGE_ITEM = "page-item";
    private static final String CLASS_PAGE_ITEM_DISABLED = "page-item disabled";
    private static final String HIDDEN = "hidden";
    private static final int MAX_BEFORE_OR_AFTER_PAGES = 3;


    @Autowired
    GenreService genreService;

    @Autowired
    FilmService filmService;

    @Autowired
    CountryService countryService;

    //todo: переделать, чтобы был другой вид
    public Map<String, Object> getPagination(int currentPage, int filmsPerPage, int numberOfPageButtons) {
        Map<String, Object> paginationParams = new HashMap<>();

        if (numberOfPageButtons < 2) {
            paginationParams.put("pagination", "hidden");
            return paginationParams;
        }

        if (currentPage - 3 <= 0) {
            paginationParams.put("pageOneisHidden", "hidden");
        } else {
            paginationParams.put("pageOneValue", currentPage - 3);
        }

        if (currentPage - 2 <= 0) {
            paginationParams.put("pageTwoIsHidden", "hidden");
        } else {
            paginationParams.put("pageTwoValue", currentPage - 2);
        }

        if (currentPage - 1 <= 0) {
            paginationParams.put("pageThreeIsHidden", "hidden");
        } else {
            paginationParams.put("pageThreeValue", currentPage - 1);
        }

        if (currentPage + 1 > numberOfPageButtons) {
            paginationParams.put("pageFourIsHidden", "hidden");
        } else {
            paginationParams.put("pageFourValue", currentPage + 1);
        }

        if (currentPage + 2 > numberOfPageButtons) {
            paginationParams.put("pageFiveIsHidden", "hidden");
        } else {
            paginationParams.put("pageFiveValue", currentPage + 2);
        }

        if (currentPage + 3 > numberOfPageButtons) {
            paginationParams.put("pageSixIsHidden", "hidden");
        } else {
            paginationParams.put("pageSixValue", currentPage + 3);
        }

        if (currentPage == 1) {
            paginationParams.put("firstPage", "disabled");
        } else {
            paginationParams.put("firstPageValue", 1);
        }
        if (currentPage == numberOfPageButtons) {
            paginationParams.put("lastPage", "disabled");
        } else {
            paginationParams.put("lastPageValue", numberOfPageButtons);
        }
        if ((currentPage - 1) <= 0) {
            paginationParams.put("previousPage", "disabled");
        } else {
            paginationParams.put("previousPageValue", currentPage - 1);
        }
        if ((currentPage + 1) > numberOfPageButtons) {
            paginationParams.put("nextPage", "disabled");
        } else {
            paginationParams.put("nextPageValue", currentPage + 1);
        }
        paginationParams.put("currentPage", currentPage);

        return paginationParams;
    }

    //todo: incomplete version
    public Map<String, Object> getPaginationNEW(int page, int countOfFilms, int filmsPerPage) {
        Map<String, Object> pagination = new HashMap<>();
        if (countOfFilms < filmsPerPage) {
            pagination.put("pagination", null);
            return pagination;
        }

        if (page * filmsPerPage < countOfFilms) {
            List<Integer> pages = new ArrayList<>();
            for (int i = 1; i < 4; i++) {
                pages.add(page * filmsPerPage + i);
            }
            int previous = 3;
            while (page > 0 && previous > 0) {
                pages.add(0, --page);
            }
        }

        pagination.put("pages", page);
        return pagination;
    }

    public Map<String, Object> getSearchingResult(String searchingParam, int page, int filmsPerPage) {
        Map<String, Object> result = new HashMap<>();

        List<FilmDTO> films = filmService.findByGenre(searchingParam, page, filmsPerPage);
        if (films.size()>0) {
            int countFilmsByGenre = filmService.countFilmByGenre(searchingParam);
            putParams(searchingParam, page, filmsPerPage, result, films, countFilmsByGenre);
            return result;
        }

        films = filmService.findByCountry(searchingParam, page, filmsPerPage);
        if (films.size()>0) {
            int countFilmsByCountry = filmService.countFilmsByCountry(searchingParam);
            putParams(searchingParam, page, filmsPerPage, result, films, countFilmsByCountry);
            return result;
        }

        films = filmService.findFilmsByQuality(searchingParam, page, filmsPerPage);
        if (films.size() > 0) {
            int countFilmsByQuality = filmService.countFilmsByQuality(searchingParam);
            putParams(searchingParam, page, filmsPerPage, result, films, countFilmsByQuality);
            return result;
        }

        films = filmService.findFilmsByTranslation(searchingParam, page, filmsPerPage);
        if (films.size() > 0) {
            int countFilmsByTranslation = filmService.countFilmsByTranslation(searchingParam);
            putParams(searchingParam, page, filmsPerPage, result, films, countFilmsByTranslation);
            return result;
        }

        Integer releaseYear = null;
        try {
            releaseYear = Integer.parseInt(searchingParam);
        } catch (NumberFormatException e) {
            //NOP
        }

        if(releaseYear!=null) {
            films = filmService.findFilmsByReleaseYear(releaseYear, page, filmsPerPage);
            int countFilmsByReleaseYear = filmService.countFilmsByReleaseYear(Integer.valueOf(searchingParam));
            putParams(searchingParam, page, filmsPerPage, result, films, countFilmsByReleaseYear);
            return result;
        }

        films = filmService.findFilmsByNameLike(searchingParam, page, filmsPerPage);
        int countFilmsByNameLike = filmService.countFilmsByNameLike(searchingParam);
        putParams(searchingParam, page, filmsPerPage, result, films, countFilmsByNameLike);
        return result;
    }

    private void putParams(String searchingParam, int page, int filmsPerPage, Map<String, Object> result, List<FilmDTO> films, int countFilmsByGenre) {
        int lastPageNumber = countFilmsByGenre / filmsPerPage;
        result.putAll(getPagination(page, filmsPerPage, lastPageNumber));
        result.put("films", films);
        result.put("searchingParam", searchingParam);
    }
}
