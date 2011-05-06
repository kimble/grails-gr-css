package grails.plugin.grcss

import spock.lang.Unroll;
import grails.plugin.spock.UnitSpec;
import static java.util.Collections.EMPTY_MAP

/**
 * 
 * @author Kim A. Betti
 */
class GrCssResourceMapperSpec extends UnitSpec {

    GrCssResourceMapper mapper = new GrCssResourceMapper()
    
    @Unroll("Should find #variables in #line")
    def "Should be able to pick up simple variable definitions"() {
        expect:
        mapper.pickUpAnyVariableDefinition(line) == returnValue
        mapper.cssVariables == variables
        
        where:
        line                              | variables                   | returnValue
        "#define radius 5px"              | [ radius: "5px" ]           | ""
        "#define border-radius 5px"       | [ "border-radius": "5px" ]  | ""
        "body {"                          | EMPTY_MAP                   | "body {"
        "background-color: red"           | EMPTY_MAP                   | "background-color: red"
    }
    
    @Unroll("#line should be transformed into #transformed")
    def "Should be able to insert variables"() {
        setup:
        mapper.cssVariables = variables
        
        expect:
        mapper.insertVariables(line) == transformed
        
        where:
        variables                                    | line                                                   | transformed
        [ fillColor: "#fafafa" ]                     | '    background-color: ${fillColor};'                  | "    background-color: #fafafa;"
        [ fillColor: "#fafafa", textColor: "#333" ]  | 'background-color: ${fillColor}; color: ${textColor}'  | "background-color: #fafafa; color: #333"
    }
    
    def "Should fail on undefined variables"() {
        
    }
    
}
