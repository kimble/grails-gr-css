class GrCssGrailsPlugin {

    def version = "0.1"
    def grailsVersion = "1.3.7 > *"
    def dependsOn = [:]
    def pluginExcludes = [
        "grails-app/views/error.gsp",
        "web-app/css/**"
    ]

    def author = "Kim A. Betti"
    def authorEmail = "kim@developer-b.com"
    def title = "Gr-CSS"
    def description = """This plugin aims to deliver small, incremental, non-intrusive 
        improvements to CSS leveraging the Resource plugin framework"""

    def documentation = "http://grails.org/plugin/gr-css"

    

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx -> }
    def doWithApplicationContext = { applicationContext -> }
    def onChange = { event -> }
    def onConfigChange = { event -> }
    def doWithWebDescriptor = { xml -> }
    
}