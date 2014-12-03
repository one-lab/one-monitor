package org.onelab.monitor.agent.transform.asm.inserter;

import java.util.*;

/**
 * Created by chunliangh on 14-12-3.
 */
public class CodeInserterPool {

    private static Map<String,List<CodeInserter>> listenerMap;

    public static void addMethodCodeInserter(String targetType, String targetMethod, String targetMethodDesc, CodeInserter codeInserter){
        if (listenerMap == null){
            listenerMap = new HashMap<String, List<CodeInserter>>();
        }
        String key = targetType+"/"+targetMethod+"#"+targetMethodDesc;
        List<CodeInserter> codeInserters = listenerMap.get(key);
        if (codeInserters==null){
            codeInserters = new LinkedList<CodeInserter>();
            listenerMap.put(key,codeInserters);
        }
        codeInserters.add(codeInserter);
    }

    public static List<CodeInserter> get(String targetType, String targetMethod, String targetMethodDesc){
        if (listenerMap!=null){
            return listenerMap.get(targetType+"/"+targetMethod+"#"+targetMethodDesc);
        }
        return null;
    }
}
