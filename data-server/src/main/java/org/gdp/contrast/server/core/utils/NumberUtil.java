package org.gdp.contrast.server.core.utils;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {

    private static Pattern NUMBER_PATTERN = Pattern.compile("[0-9]+");

    /**
     * 带逗号的数字转换为long类型
     * @param content
     * @return
     */
    public static Long comma2Long(String content) {
        if (null == content || "" == content){
            return 0L;
        }
        Matcher matcher = NUMBER_PATTERN.matcher(content.replaceAll(",", ""));
        return matcher.find() ? Long.parseLong(matcher.group()) : 0L;
    }
}
