modules = {
    
    example {
        resource "css/colorTheme.css"
        resource "css/variableTest.css"
        resource "css/processorTest.css"
    }
    
}

cssProcessors = {
        
    "border-radius" { args ->
       out << "-webkit-border-radius: $args;"
       out << "-moz-border-radius: $args;"
       out << "-ie-border-radius: $args;"
       out << "-o-border-radius: $args;"
       out << "border-radius: $args;"
    }
    
    "-x-linear-background" { args ->
        def (startColor, stopColor) = args.split(",")
        out << "background: -webkit-gradient(linear, left top, left bottom, from($startColor), to($stopColor));\n"
        out << "background: -moz-linear-gradient(top, $startColor, $stopColor);\n"
    }
   
}