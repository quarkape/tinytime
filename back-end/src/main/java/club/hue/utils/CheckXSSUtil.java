package club.hue.utils;

public class CheckXSSUtil {

    public String checkXSS(String str) {
        str.replace("<", "&lt;");
        str.replace(">", "&gt;");
        return str;
    }
}
