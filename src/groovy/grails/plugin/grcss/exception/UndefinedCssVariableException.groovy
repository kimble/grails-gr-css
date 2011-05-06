package grails.plugin.grcss.exception

/**
 * Thrown when we find a reference to 
 * a undefined CSS variable. 
 * @author Kim A. Betti
 */
class UndefinedCssVariableException extends GrCssException {

    String variableName

    @Override
    public String getMessage() {
        "Undefiend CSS variable '$variableName' on line ${ lineNumber ?: 'UNKOWN' } in ${ filename ?: 'UNKOWN' }"
    }
    
}