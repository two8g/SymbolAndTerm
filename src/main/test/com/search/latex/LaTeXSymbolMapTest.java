package com.search.latex;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by ybxsearch on 2015/3/30.
 */
public class LaTeXSymbolMapTest {
    public static void main(String[] args) {
        String s = "";
        try {
            s = FileUtils.readFileToString(new File("src/main/resources/results"));
            System.out.println(MappingFilter.strfilter(s, "LaTexSymbolMap1.0"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
