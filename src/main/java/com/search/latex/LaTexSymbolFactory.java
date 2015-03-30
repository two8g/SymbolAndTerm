package com.search.latex;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by ybxsearch on 2015/3/30.
 */
public class LaTexSymbolFactory {
    /**
     *
     */
    public void extractAllSymbol() {
        String filePath = "LaTeX.html";
        String outFilePath = "LaTexSymbol";
        StringBuilder sb = new StringBuilder();
        try {
            Document doc = Jsoup.parse(LaTexSymbolFactory.class.getClassLoader().getResourceAsStream(filePath), "utf-8", "");
            Elements commands = doc.select("td[class=command]");
            int i = 0;
            for (Element command : commands) {
                Elements names = command.select("a[name]");
                for (Element name : names) {
                    i++;
                    System.out.println(i + ":\t" + name.html());
                    sb.append(names.get(0).html() + "\n");
                }
            }
            FileUtils.write(new File(outFilePath), sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 输出LaTeX 符号为MapFilter格式.".."=>""
     */
    public void extractAllSymbol2Map() {
        String filePath = "LaTeX.html";
        String outFilePath = "LaTexSymbolMap1.0";
        StringBuilder sb = new StringBuilder();
        try {
            Document doc = Jsoup.parse(LaTexSymbolFactory.class.getClassLoader().getResourceAsStream(filePath), "utf-8", "");
            Elements commands = doc.select("td[class=command]");
            int i = 0;
            for (Element command : commands) {
                Elements names = command.select("a[name]");
                for (Element name : names) {
                    i++;
                    String s = " ";
                    String namestr = name.html()+" ";
                    String filterstr = MappingFilter.strfilter(namestr,"LaTeXFilterMap");
                    if(!namestr.equals(filterstr)){
                        s = filterstr;
                    }
                    String outstr = "\"" + name.html().replace("\\", "\\\\") + " \"=>\""+s+"\"";
                    System.out.println(i + ":\t" + outstr);
                    sb.append(outstr + "\n");
                }
            }
            FileUtils.write(new File(outFilePath), sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
//        new LaTexSymbolFactory().extractAllSymbol2Map();
        String s = FileUtils.readFileToString(new File("C:\\Users\\ybxsearch\\Desktop\\error.txt"));
        System.out.println(MappingFilter.strfilter(s, "LaTexSymbolMap1.0"));
    }
}
