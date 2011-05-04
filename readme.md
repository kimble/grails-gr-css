Grails GrCSS
=============

Grails plugin aiming to deliver small, incremental, non-intrusive improvements to CSS by leveraging resource mappers provided by the Resource plugin.

I started working on this plugin to address my two biggest pain points when writing css:

 * No variables, I keep repeating the same hex codes multiple places.
 * Having to write more or less the same CSS3 lines over and over again with different vendor prefixes.  

Pain point number one: Something like CSS variables
-------------------------------------------------
This is not the implementation that has been suggested for CSS, but just a very simple approach implemented with a line by line regex search and replace. 

You define variables like this

    #define textcolor #333

And use them in a way that should be familiar to Groovy and Grails developers. Not that it's implemented using search and replace so you can't embed other logic in there. 

    body {
        color: ${textcolor};
    }

Pain point number two: All those vendor prefixed lines
-------------------------------------------------------------------------------
The vendor prefixed lines still have to go in there, but at least you don't have to type all of them over and over again! The plugin will look for CSS rule processors in your existing *Resources.groovy files. 

    modules = {
        // ... define your modules and resources as usual
    }
    cssProcessors = {
            
        // Automatic generation of vendor prefixed lines
        // Important: Note that you're responsible for printing the original line as well! 
        "border-radius" { args ->
           out << "-webkit-border-radius: $args;"
           out << "-moz-border-radius: $args;"
           out << "-ie-border-radius: $args;"
           out << "-o-border-radius: $args;"
           out << "border-radius: $args;"
        }
        
        // You can also implement your own CSS rules
        "-x-linear-background" { args ->
            def (startColor, stopColor) = args.split(",")
            out << "background: -webkit-gradient(linear, left top, left bottom, from($startColor), to($stopColor));\n"
            out << "background: -moz-linear-gradient(top, $startColor, $stopColor);\n"
        }
       
    }

Examples
------------------
Have a look in the [CSS folder](https://github.com/kimble/grails-gr-css/tree/master/web-app/css) and at the [resource definition file](https://github.com/kimble/grails-gr-css/blob/master/grails-app/conf/testResources.groovy) for more examples. 