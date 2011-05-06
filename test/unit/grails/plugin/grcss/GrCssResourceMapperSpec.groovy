package grails.plugin.grcss

import spock.lang.Unroll;
import grails.plugin.grcss.exception.UndefinedCssVariableException;
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
        "#define border_radius 5px"       | [ "border_radius": "5px" ]  | ""
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
        [ fill_color: "#fafafa" ]                    | '    background-color: ${fill_color};'                 | "    background-color: #fafafa;"
        [ "fill-color": "#fafafa" ]                  | '    background-color: ${fill-color};'                 | "    background-color: #fafafa;"
        [ fillColor: "#fafafa", textColor: "#333" ]  | 'background-color: ${fillColor}; color: ${textColor}'  | "background-color: #fafafa; color: #333"
    }
    
    def "Should fail on undefined variables"() {
        when:
        mapper.insertVariables('color: ${ugga-bugga}')
        
        then:
        UndefinedCssVariableException ex = thrown()
        ex.variableName == "ugga-bugga"
        ex.lineNumber == null // will be filled out later
        ex.filename == null
    }
    
    def "Line number and file name should be filled out for GrCssExceptions"() {
        when:
        mapper.processCssLine('color: ${not-defined}', "main.css", 10)
        
        then:
        UndefinedCssVariableException ex = thrown()
        ex.message == "Undefiend CSS variable 'not-defined' on line 10 in main.css"
        ex.filename == "main.css"
        ex.lineNumber == 10
    }
    
}
