package com.search.filter;

import org.apache.lucene.analysis.pattern.PatternReplaceCharFilterFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;


public class PatternReplaceFilter {

    private static Map<String, String> map = null;

    public static String strfilter(String str, String pattern, String replacement) {
        StringBuilder out = new StringBuilder();
        StringReader strReader = new StringReader(str);
        try {
            map = new HashMap<String, String>();
            map.put("pattern", pattern);
            map.put("replacement", replacement);
            Reader baseReader = new PatternReplaceCharFilterFactory(map).create(strReader);
            char[] cbuf = new char[10240];
            while (true) {
                int count = baseReader.read(cbuf);
                if (count == -1)
                    break;
                if (count > 0)
                    out.append(cbuf, 0, count);
            }
            baseReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toString();
    }

}
