package com.intern.firstproject.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogUtils<E>{
    private volatile static LogUtils logUtils;
    private LogUtils(){}
    public static LogUtils getInstance() {
        if (logUtils == null) {
            synchronized (LogUtils.class) {
                if (logUtils == null) {
                    logUtils = new LogUtils();
                }
            }
        }
        return logUtils;
    }

    public String printListAsLog(List<E> list){
        StringBuffer stringBuffer=new StringBuffer();
        list.forEach(object->{stringBuffer.append(object.toString());});
        return stringBuffer.toString();
    }

    public String printObjectAsLog(E object){
        return  object.toString();
    }

}
