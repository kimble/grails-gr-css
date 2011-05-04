package grails.plugin.grcss

import java.util.regex.Matcher;
import java.util.regex.Pattern

import org.grails.plugin.resource.ResourceMeta
import org.grails.plugin.resource.ResourceService;
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean;

/**
 * Provides basic support for something like CSS variables 
 * and rule post processors.   
 * @author Kim A. Betti
 */
class GrCssResourceMapper implements InitializingBean {

    static final Logger log = LoggerFactory.getLogger(GrCssResourceMapper)
    
    int priority = 1001
    
    CssProcessorLoader cssProcessorLoader
    Map cssRuleProcessors
    Map cssVariables = [:]   
    
    @Override
    public void afterPropertiesSet() throws Exception {
        cssRuleProcessors = cssProcessorLoader.getProcessors()
        log.debug "Loaded css rule processors: " + cssRuleProcessors
    }

    def map(ResourceMeta resource, ConfigObject config) {
        File file = resource.processedFile
        if (resource.processedFileExtension?.equalsIgnoreCase("css")) {
            log.debug "Processing {}", file.absolutePath
            file.text = processCssFile(file)
        }
    }
    
    protected String processCssFile(File cssFile) {
        StringBuilder output = new StringBuilder(2000)
        cssFile.eachLine { String line ->
             line = pickUpAnyVariableDefinition(line)
             line = insertVariables(line)
             line = subjectToCssProcessors(line)
             output.append(line).append('\n')
        }
        
        return output.toString()
    }
    
    protected String pickUpAnyVariableDefinition(String line) { 
        Matcher matcher = (line =~ /^\#define\s+([a-zA-Z0-9]+)\s+(.*)$/)
        if (matcher.matches()) {
            String variableName = matcher[0][1]
            String value = matcher[0][2]
            
            log.debug "Setting variable $variableName = $value"
            cssVariables[variableName] = value
            return ""
        } else {
            return line
        }
    }
    
    protected String insertVariables(String line) {
        Matcher varMatcher = (line =~ /\$\{([a-z-A-Z0-9]+)\}/)
        varMatcher.each { matched, variableName ->
            if (cssVariables.containsKey(variableName)) {
                line = line.replace(matched, cssVariables[variableName])
            } else {
                log.warn "Found reference to an undeclared variable $variableName"
            }
        }
        
        return line
    }
    
    protected String subjectToCssProcessors(String line) {
        Matcher cssRuleMatcher = (line =~ /([\w\-]+)\:\s?([^\;]+)\;/)
        
        cssRuleMatcher.each { matched, ruleName, arguments ->
            if (cssRuleProcessors.containsKey(ruleName)) {
                log.debug "Processing $ruleName"

                StringBuilder output = new StringBuilder(300)
                CssRuleProcessor ruleProcessor = cssRuleProcessors[ruleName]
                ruleProcessor.process(ruleName, arguments, output)
                line = line.replace(matched, output.toString())
            }
        }
        
        return line
    }
    
}