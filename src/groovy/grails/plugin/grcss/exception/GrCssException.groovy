package grails.plugin.grcss.exception

/**
 * Base class for CSS related exceptions
 * that might occur during mapping / processing. 
 * @author Kim A. Betti
 */
abstract class GrCssException extends RuntimeException {

    public GrCssException(String message, Throwable cause) {
        super(message, cause)
    }

    public GrCssException(String message) {
        super(message)
    }

}