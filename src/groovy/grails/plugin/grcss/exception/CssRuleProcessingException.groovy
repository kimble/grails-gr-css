package grails.plugin.grcss.exception

/**
 * Thrown when there's been a problem processing a rule
 * using one of the user provided processors. 
 * @author Kim A. Betti
 */
class CssRuleProcessingException extends GrCssException {

    public CssRuleProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

}
