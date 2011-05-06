package grails.plugin.grcss.exception

/**
 * Wraps the other GrCssException adding
 * information about the file / line causing the problem. 
 * @author Kim A. Betti
 */
class SourceAwareGrCssException extends RuntimeException {

    final String filename
    final Integer lineNumber

    public SourceAwareGrCssException(String message, String filename, Integer lineNumber, GrCssException cssException) {
        super(message, cssException);
        assert cssException instanceof GrCssException
        this.filename = filename;
        this.lineNumber = lineNumber;
    }    
    
}
