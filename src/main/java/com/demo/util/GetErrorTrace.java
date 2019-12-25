package com.demo.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class GetErrorTrace {
    
    /**
     *@Author:xjy
     *@Description:获取错误信息，方便打印到日志中
     *@Date:2018/1/2_11:32
     */
    public static String getTrace(Throwable t){

        StringWriter stringWriter= new StringWriter();
        PrintWriter writer= new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer= stringWriter.getBuffer();
        return buffer.toString();
    }

}
