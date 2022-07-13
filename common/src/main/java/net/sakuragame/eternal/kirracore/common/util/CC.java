package net.sakuragame.eternal.kirracore.common.util;

public class CC {

    public static final char COLOR_CHAR = '\u00A7';

    public static String toColored(String str) {
        char[] b = str.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
}