package Selenium.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringModifier {
    public static String UniqueString(String str){
        return str + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
}
