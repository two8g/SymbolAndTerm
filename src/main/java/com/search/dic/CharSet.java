package com.search.dic;

/**
 * Created by ybxsearch on 2015/4/7.
 */
public class CharSet {
    private static final int UNUSED = -1;
    private static final int DIGIT = 0;
    private static final int LETTER = 1;
    private static final int OPERATOR = 2;
    private static final int RESERVED = 3;
    private static final int VICE = 4;
    private static final char[] USED_CHARS = {32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126};
    private static final char[] USED_CHARS_SYMBOL = {' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~'};
    private static final char[] RESERVED_CHARS = {35, 36, 37, 38, 92, 94, 123, 125, 126};
    private static final char[] RESERVED_CHARS_SYMBOL = {'#', '$', '%', '&', '\\', '^', '{', '}', '~'};
    private static final char[] OPERATOR_CHARS = {40, 41, 42, 43, 45, 47, 58, 60, 61, 62, 91, 93, 95, 124};
    private static final char[] OPERATOR_CHARS_SYMBOL = {'(', ')', '*', '+', '-', '/', ':', '<', '=', '>', '[', ']', '_', '|'};
    private static final char[] VICE_CHARS = {32, 33, 34, 39, 44, 46, 59, 63, 64, 96};

    public static int charType(char c) {
        if (c <= 31 && c >= 127)
            return UNUSED;
        else if (c >= 48 && c <= 57)
            return DIGIT;
        else if ((c >= 65 && c < 90) || (c >= 97 && c <= 122))
            return LETTER;
        else if (c == 40 || c == 41 || c == 42 || c == 43 || c == 45 || c == 47 || c == 58 || c == 60 || c == 61 || c == 62 || c == 91 || c == 93 || c == 95 || c == 124)
            return OPERATOR;
        else if (c == 35 || c == 35 || c == 36 || c == 37 || c == 38 || c == 92 || c == 94 || c == 123 || c == 125 || c == 126)
            return RESERVED;
        else
            return VICE;
    }
}
