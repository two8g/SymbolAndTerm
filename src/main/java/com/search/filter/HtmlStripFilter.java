package com.search.filter;

import org.apache.lucene.analysis.charfilter.HTMLStripCharFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class HtmlStripFilter {

    public static String strfilter(String str) {
        StringBuilder out = new StringBuilder();
        StringReader strReader = new StringReader(str);
        try {
            HTMLStripCharFilter html = new HTMLStripCharFilter(
                    strReader.markSupported() ? strReader : new BufferedReader(
                            strReader));
            char[] cbuf = new char[10240];
            while (true) {
                int count = html.read(cbuf);
                if (count == -1)
                    break;
                if (count > 0)
                    out.append(cbuf, 0, count);
            }
            html.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toString();
    }

}
