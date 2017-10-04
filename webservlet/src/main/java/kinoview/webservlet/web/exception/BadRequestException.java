package kinoview.webservlet.web.exception;

/**
 * Created by alexfomin on 14.07.17.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

