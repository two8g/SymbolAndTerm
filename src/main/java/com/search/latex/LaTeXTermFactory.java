package com.search.latex;

import com.search.c3p0.DBPooldbtest;
import com.search.filter.HtmlStripFilter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by YBX on 2015/4/14.
 * latex字典生成工具
 */
public class LaTeXTermFactory {
    public static List<String> LATEX_TERMS = new ArrayList<String>(100000);
    public static List<String> LATEX_FORMULAR = new ArrayList<String>(100000);

    public static void main(String[] args) {
        List<String> sids = getSids();
        if (sids == null) {
            return;
        }
        System.out.println("sids.size():" + sids.size());
        int n = 0;
        for (String sid : sids) {
            n++;
            String title = getTitle(sid).replaceAll("\\s*","");
            if (title == null)
                continue;
            System.out.println("findLatexFormular:"+n);
            findLatexFormular(title);
        }
        Collections.sort(LATEX_FORMULAR);
        saveFormularFile();
        for(String formular : LATEX_FORMULAR){
            findTermsProcess(formular);
        }
        Collections.sort(LATEX_TERMS);
        saveTermsFile();
    }

    private static void findLatexFormular(String input) {
        String regex = "\\$(.*?)\\$";
        Matcher m = Pattern.compile(regex).matcher(input);
        if (m.find()) {
            String formular = m.group(1);
            if (!LATEX_FORMULAR.contains(formular))
                LATEX_FORMULAR.add(formular);
        }
    }

    private static void saveFormularFile() {
        String filePath = "latex_fromular";
        for (String formular : LATEX_FORMULAR) {
            try {
                FileUtils.write(new File(filePath), formular + "\n", "UTF-8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void findTermsProcess(String input) {
        simpleRegex(input);
    }

    private static void saveTermsFile() {
        String filePath = "latex_terms";
        for (String term : LATEX_TERMS) {
            try {
                FileUtils.write(new File(filePath), term + "\n", "UTF-8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 一.简单正则匹配的词
     * 1.加减乘除次幂底数公式 [0-9\.a-zA-Z\+-\*÷∠\=\(\)\|\^\{\}]+
     * 2.坐标,区间  [\(\[]([a-z]+|^([-\+]{0,1}[0-9]+)(\.[0-9]*)?$),([a-z]+|^([-\+]{0,1}[0-9]+)(\.[0-9]*)?$)[\)\]]
     * 3.百分数 ^([-\+]{0,1}[0-9]+)(\.[0-9]*)?%$
     * 4.简单分式 frac{[^{}]+}{{[^{}]+}}
     * 5.简单根式 sqrt{[^{}]+}|sqrt\[^([1-9])[0-9]*\]{[^{}]+}
     * 6.三角形 △[A-Z]{3,3}|△[A-Z]_{\d}[A-Z]_{\d}[A-Z]_{\d}|△[A-Z][′″‴][A-Z][′″‴][A-Z][′″‴]|△[A-Z]^{[′″‴]}[A-Z]^{[′″‴]}[A-Z]^{[′″‴]}
     * 7.比例 [a-z]+|^([-\+]{0,1}[0-9]+)(\.[0-9]*)?$):[a-z]+|^([-\+]{0,1}[0-9]+)(\.[0-9]*)?$)
     * 8.
     */

    private static String[] regexes = {"[0-9\\.a-zA-Z\\+-\\*\u00F7\u2220=\\(\\)\\|\\^\\{\\}]+",
            "[\\(\\[]([a-z]+|^([-\\+]{0,1}[0-9]+)(\\.[0-9]*)?$),([a-z]+|^([-\\+]{0,1}[0-9]+)(\\.[0-9]*)?$)[\\)\\]]",
            "^([-\\+]{0,1}[0-9]+)(\\.[0-9]*)?%$",
            "frac{[^{}]+}{{[^{}]+}}",
            "sqrt{[^{}]+}|sqrt\\[^([1-9])[0-9]*\\]{[^{}]+}",
            "\u25b3[A-Z]{3,3}|\u25b3[A-Z]_{\\d}[A-Z]_{\\d}[A-Z]_{\\d}|\u25b3[A-Z][′″‴][A-Z][′″‴][A-Z][′″‴]|\u25b3[A-Z]^{[′″‴]}[A-Z]^{[′″‴]}[A-Z]^{[′″‴]}",
            "[a-z]+|^([-\\+]{0,1}[0-9]+)(\\.[0-9]*)?$):[a-z]+|^([-\\+]{0,1}[0-9]+)(\\.[0-9]*)?$)"};

    private static void simpleRegex(String input) {
        for (String regex : regexes)
            simpleRegexMatchTerms(input, regex);
    }

    /**
     * 简单正则匹配
     */
    private static void simpleRegexMatchTerms(String input, String regex) {
        Matcher m = Pattern.compile(regex).matcher(input);
        if (m.find()) {
            String term = m.group().toLowerCase();
            if (!LATEX_TERMS.contains(term)) {
                System.out.println(term);
                LATEX_TERMS.add(term);
            }
        }
    }

    /**
     * 链接数据库
     */
    private static List<String> getSids() {
        List<String> sids = new ArrayList<String>();
        Connection con = null;
        String SQL = "select top 1000 SID from dbo.TB_SUBJECT WHERE STATUS=4 ORDER BY NEWID()";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBPooldbtest.getConnection();
            ps = con.prepareStatement(SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                sids.add(rs.getString("SID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            return sids;
        }
    }

    private static String getTitle(String sid) {
        String title = null;
        Connection con = null;
        String SQL = "select TITLE2 from dbo.TB_SUBJECT WHERE SID=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBPooldbtest.getConnection();
            ps = con.prepareStatement(SQL);
            ps.setString(1, sid);
            rs = ps.executeQuery();
            while (rs.next())
                title = rs.getString("TITLE2");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (ps != null)
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            return MappingFilter.strfilter(HtmlStripFilter.strfilter(title),"unify");
        }
    }
}
