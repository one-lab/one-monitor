package org.onelab.monitor.agent.transform;

/**
 * Created by chunliangh on 14-11-16.
 */
public class Aop {
    public static final String http = "http";
    public static final String pojo = "pojo";
    public static final String jdbc = "jdbc";
    public static String getPointCutName(String className,String supperName,String[] interfaces,String methodName,String methodDescription){
        if (methodName.equals("doPut")){
            System.out.println("==============className:"+className);
            System.out.println("==============supperName:"+supperName);
            System.out.print("==============interfaces:");
            if (interfaces!=null){
                System.out.print(interfaces.length);
                for (String iif:interfaces){
                    System.out.print(iif);
                }
            }
            System.out.println();
            System.out.println("==============methodName:"+methodName);
            System.out.println("==============methodDescription:"+methodDescription);
        }
        return http;
    }
}
