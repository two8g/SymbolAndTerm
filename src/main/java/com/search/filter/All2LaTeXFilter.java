package com.search.filter;

public class All2LaTeXFilter {
    public static String filer(String str) {
        str = str.replace("<sub>", "_{").replace("</sub>", "}")
                .replace("<sup>", "^{").replace("</sup>", "}");
        str = str.replace("$", "").replaceAll("(_{2,})", "\\$$1\\$").replace("<p>", "<p>$").replace("</p>", "$</p>").replace("$$", "");
        str = str.replaceAll("\\\\( \\\\color\\{red\\}\\{)frac\\}", "\\\\frac");
        str = str.replaceAll("\\\\( \\\\color\\{red\\}\\{)sqrt\\}", "\\\\sqrt");
        return str;
    }
}
