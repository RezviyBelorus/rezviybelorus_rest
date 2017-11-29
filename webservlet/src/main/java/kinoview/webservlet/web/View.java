package kinoview.webservlet.web;

/**
 * Created by alexfomin on 13.07.17.
 */
public enum View {
    LOGIN("login"),
    LOGIN_ERROR("login_error"),
    SIGN_UP("sign_up"),
    SIGN_UP_ERROR("sign_up_error"),
    MAIN("main"),
    ERROR("error"),
    NOT_FOUND("not_found"),
    USER("user"),
    FILM("film"),
    GENRE("genre"),
    COUNTRY("country"),
    SEARCH_RESULT("search_result");

    private String name;
    private String fullName;
    private static final String BASE_DIRECTORY = "/WEB-INF/view/";
    private static final String JSP_SUFFIX = ".jsp";

    View(String name) {
        this.name = name;
        this.fullName = BASE_DIRECTORY + name + JSP_SUFFIX;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public static String getBaseDirectory() {
        return BASE_DIRECTORY;
    }
}
