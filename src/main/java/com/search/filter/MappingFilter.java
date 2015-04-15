package com.search.filter;

import org.apache.lucene.analysis.charfilter.MappingCharFilterFactory;
import org.apache.lucene.analysis.util.FilesystemResourceLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class MappingFilter {
    // 我以为MappingCharFilterFactory构造函数中的Map<String, String>就是我们要替换的字符集map，这是错误的。
    // new MappingCharFilterFactory(Map<String, String>) 中的Map是<"mapping", "filename">
    // file中的格式为Pattern.compile("\"(.*)\"\\s*=>\\s*\"(.*)\"\\s*$"); 例如:
    // "xxx" = > "yy"

    private static Map<String, String> map = new HashMap<String, String>();

    public static String strfilter(String str, String mapfilename) {
        map.put("mapping", mapfilename);
        StringBuilder out = new StringBuilder();
        StringReader strReader = new StringReader(str);
        try {
            MappingCharFilterFactory mappingCharFilterFactory = new MappingCharFilterFactory(
                    map);
            mappingCharFilterFactory.inform(new FilesystemResourceLoader());
            Reader baseReader = (Reader) mappingCharFilterFactory
                    .create(strReader);
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
