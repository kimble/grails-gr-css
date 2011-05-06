package grails.plugin.grcss.exception

/**
 * Thrown when we find a reference to a undefined CSS variable. 
 * @author Kim A. Betti
 */
class UndefinedCssVariableException extends GrCssException {

    final String variableName

    public UndefinedCssVariableException(String variableName) {
        super("Undefiend CSS variable '$variableName'".toString())
        this.variableName = variableName
    }

}