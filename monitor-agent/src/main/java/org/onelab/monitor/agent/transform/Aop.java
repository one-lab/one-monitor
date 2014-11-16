package org.onelab.monitor.agent.transform;

/**
 * Created by chunliangh on 14-11-16.
 */
public class Aop {
    public static final String http = "http";
    public static final String pojo = "pojo";
    public static final String jdbc = "jdbc";
    public static String getPointCutName(String className,String supperName,String[] interfaces,String methodName,String methodDescription){
        return http;
    }
}
