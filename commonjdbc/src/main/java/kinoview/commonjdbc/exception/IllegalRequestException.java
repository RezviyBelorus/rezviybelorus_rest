package kinoview.commonjdbc.exception;

/**
 * Created by alexfomin on 02.07.17.
 */
public class IllegalRequestException extends RuntimeException {
    public IllegalRequestException(String message) {
        super(message);
    }
}
