package kinoview.commonjdbc.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MainService {
    private static final String CLASS_PAGE_ITEM = "page-item";
    private static final String CLASS_PAGE_ITEM_DISABLED = "page-item disabled";
    private static final String HIDDEN = "hidden";
    private static final int MAX_BEFORE_OR_AFTER_PAGES = 3;

    //todo: переделать, чтобы был другой вид
    public Map<String, Object> getPagination(int currentPage, int filmsPerPage, Integer numberOfPageButtons) {
        Map<String, Object> paginationParams = new HashMap<>();

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
            paginationParams.put("previousPageValue", currentPage-1);
        }
        if ((currentPage + 1) > numberOfPageButtons) {
            paginationParams.put("nextPage", "disabled");
        } else {
            paginationParams.put("nextPageValue", currentPage+1);
        }
        paginationParams.put("currentPage", currentPage);

        return paginationParams;
    }
}
