package grails.plugin.grcss

import groovy.lang.Closure;

/**
 * CSS rule processor
 * @author Kim A. Betti
 */
class CssRuleProcessor {

    final Closure processor
    
    public CssRuleProcessor(Closure processor) {
        assert processor != null
        this.processor = processor;
    }

    String process(String ruleName, String arguments, StringBuilder output) {
        ProcessorDelegate processorDelegate = new ProcessorDelegate(out: output)
        Closure processorClone = processor.clone()
        processorClone.resolveStrategy = Closure.DELEGATE_FIRST
        processorClone.delegate = processorDelegate
        invokeProcessor(processorClone, ruleName, arguments)
        return output.toString()
    }
    
    protected void invokeProcessor(Closure processor, String ruleName, String arguments) {
        if (processor.maximumNumberOfParameters > 1) {
            processor.call(ruleName, arguments)
        } else {
            processor.call(arguments)
        }
    }
    
}

private class ProcessorDelegate {
    
    StringBuilder out
    
}