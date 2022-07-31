package com.intern.firstproject.util;

import org.springframework.stereotype.Component;

@Component
public class StringUtils {
    private volatile static StringUtils stringUtils;
    private StringUtils(){}
    public static StringUtils getInstance() {
        if (stringUtils == null) {
            synchronized (StringUtils.class) {
                if (stringUtils == null) {
                    stringUtils = new StringUtils();
                }
            }
        }
        return stringUtils;
    }

    public boolean isAnyEmpty(String ... strings){
        for (String string :strings){
            if (string==null){
                return true;
            }
        }
        return false;
    }

}
