package man.kuke.core;

/**
 * @author: kuke
 * @date: 2021/1/28 - 20:23
 * @description:
 */
public class NotSetResourcePathException extends RuntimeException {
    public NotSetResourcePathException() {
    }

    public NotSetResourcePathException(String message) {
        super(message);
    }

    public NotSetResourcePathException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSetResourcePathException(Throwable cause) {
        super(cause);
    }

    public NotSetResourcePathException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
