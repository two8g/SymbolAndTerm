package com.search.filter;

import org.apache.lucene.analysis.charfilter.MappingCharFilterFactory;
import org.apache.lucene.analysis.util.FilesystemResourceLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class MappingFilter {
    // ����ΪMappingCharFilterFactory���캯���е�Map<String, String>��������Ҫ�滻���ַ���map�����Ǵ���ġ�
    // new MappingCharFilterFactory(Map<String, String>) �е�Map��<"mapping", "filename">
    // file�еĸ�ʽΪPattern.compile("\"(.*)\"\\s*=>\\s*\"(.*)\"\\s*$"); ����:
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
