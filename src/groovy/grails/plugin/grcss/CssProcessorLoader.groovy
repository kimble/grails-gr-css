package grails.plugin.grcss

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Load processor definitions from *Resources 
 * @author Kim A. Betti
 */
class CssProcessorLoader implements GrailsApplicationAware {

    GrailsApplication grailsApplication

    Map<String, CssRuleProcessor> getProcessors() {
        ProcessorBuilder processorBuilder = new ProcessorBuilder()
        ConfigSlurper slurper = new ConfigSlurper()
        grailsApplication.resourcesClasses.each {
            ConfigObject result = slurper.parse(it.clazz)
            if (result.containsKey("cssProcessors")) {
                Closure definition = result.get("cssProcessors")
                definition.delegate = processorBuilder
                definition.resolveStrategy = Closure.DELEGATE_FIRST
                definition.call()
            }
        }
        
        return processorBuilder.processors
    }

}

class ProcessorBuilder {
    
    static final Logger log = LoggerFactory.getLogger(ProcessorBuilder)
    
    final Map<String, CssRuleProcessor> processors = [:]
    
    void methodMissing(String name, args) {
        assert args.length == 1 && args[0] instanceof Closure
        if (processors.containsKey(name)) {
            log.warn "Detected multiple processors for $name"
        }
        
        processors[name] = new CssRuleProcessor(args[0])
    }
    
}