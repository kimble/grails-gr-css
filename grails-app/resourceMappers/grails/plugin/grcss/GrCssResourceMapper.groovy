package grails.plugin.grcss

import java.util.regex.Matcher;
import java.util.regex.Pattern

import org.grails.plugin.resource.ResourceMeta
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 
 * @author Kim A. Betti
 */
class GrCssResourceMapper {

    static final Logger log = LoggerFactory.getLogger(GrCssResourceMapper)
    
    int priority = 1
    
    /**
     * Variable for finding variable declarations. 
     * Example: #define name value
     */
    static Pattern DEFINE_PATTERN = ~/^\#define\s+([a-zA-Z0-9]+)\s+(.*)$/
 
    Map cssVariables = [:]   
    
    def map(ResourceMeta resource, ConfigObject config) {
        File file = resource.processedFile
        if (resource.processedFileExtension?.equalsIgnoreCase("css")) {
            log.debug "Processing {}", file.absolutePath
            String processed = processCssFile(file)
            println "--- output ---"
            println processed
        }
    }
    
    protected String processCssFile(File cssFile) {
        StringBuilder output = new StringBuilder(2000)
        cssFile.eachLine { String line ->
             println " -> $line"
             
             line = pickUpAnyVariableDefinition(line)
             line = insertVariables(line)
             
             output.append(line).append('\n')
        }
        
        return output.toString()
    }
    
    protected String pickUpAnyVariableDefinition(String line) { 
        Matcher matcher = line =~ /^\#define\s+([a-zA-Z0-9]+)\s+(.*)$/
        if (matcher.matches()) {
            String variableName = matcher[0][1]
            String value = matcher[0][2]
            if (cssVariables.containsKey(variableName)) {
                log.info "Replacing previous declaration of $variableName"
            }
            
            log.debug "Setting variable $variableName = $value"
            cssVariables[variableName] = value
            return ""
        } else {
            return line
        }
    }
    
    protected String insertVariables(String line) {
        Matcher varMatcher = line =~ /\$\{([a-z-A-Z0-9]+)\}/
        while (varMatcher.find()) {
            String variableName = varMatcher[0][1]
            if (cssVariables.containsKey(variableName)) {
                String search = String.format('${%s}', variableName)
                line = line.replace(search, cssVariables[variableName])
            } else {
                log.warn "Found reference to an undeclared variable $variableName"
            }
        }
        
        return line
    }
    
}