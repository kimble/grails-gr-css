import grails.plugin.grcss.CssProcessorLoader;

class GrCssGrailsPlugin {

    def version = "0.3"
    def grailsVersion = "1.3.7 > *"
    
    def dependsOn = [:]
    def loadAfter = [ "resources" ]
    
    def pluginExcludes = [
        "grails-app/views/error.gsp",
        "grails-app/conf/testResources.groovy",
        "web-app/css/**"
    ]

    def author = "Kim A. Betti"
    def authorEmail = "kim@developer-b.com"
    def title = "Gr-CSS"
    def description = """Grails plugin aiming to deliver small, incremental, non-intrusive improvements to CSS by 
                leveraging resource mappers provided by the Resource plugin."""

    def documentation = "https://github.com/kimble/grails-gr-css"

    
    def doWithSpring = {
        cssProcessorLoader(CssProcessorLoader)
    }

    def doWithDynamicMethods = { ctx -> }
    def doWithApplicationContext = { applicationContext -> }
    def onChange = { event -> }
    def onConfigChange = { event -> }
    def doWithWebDescriptor = { xml -> }
    
}