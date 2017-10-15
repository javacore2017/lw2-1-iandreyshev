package ru.iandreyshev.util;

public class StringTool {
    public static void argClear(String str) {
        int startPos = 0;
        for (; startPos < str.length(); startPos++) {
            if (str.charAt(startPos) != SPACE) {
                break;
            }
        }

        if (startPos >= str.length()) {
            str = "";
        }

        int endPos = str.length() - 1;
        for (; endPos >= 0; endPos--) {
            if (str.charAt(endPos) != SPACE) {
                break;
            }
        }

        String result = "";
        for (; startPos <= endPos; startPos++) {
            result += str.charAt(startPos);
        }
        str = result;
    }

    private static char SPACE = ' ';
}
