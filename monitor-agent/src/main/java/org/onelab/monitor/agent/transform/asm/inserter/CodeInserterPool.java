package org.onelab.monitor.agent.transform.asm.inserter;

import org.onelab.monitor.agent.Agent;
import org.onelab.monitor.agent.log.AgentLogger;
import org.onelab.monitor.agent.transform.asm.inserter.builder.CodeInserterBuilder;

import java.util.*;

/**
 * Created by chunliangh on 14-12-3.
 */
public class CodeInserterPool {

    private static Map<String,List<CodeInserter>> listenerMap;

    static {
        init();
    }

    public static void addCodeInserter(CodeInserter codeInserter){
        if (listenerMap == null){
            listenerMap = new HashMap<String, List<CodeInserter>>();
        }
        String key = codeInserter.getTargetType()+"/"+codeInserter.getTargetMethod()+"#"+codeInserter.getTargetMethodDesc();
        List<CodeInserter> codeInserters = listenerMap.get(key);
        if (codeInserters==null){
            codeInserters = new LinkedList<CodeInserter>();
            listenerMap.put(key,codeInserters);
        }
        codeInserters.add(codeInserter);
    }

    public static CodeInserter get(String targetType, String targetMethod, String targetMethodDesc,String owner,String name,String desc){
        if (listenerMap == null) return null;
        int insertCount = 0;
        List<CodeInserter> codeInserters = listenerMap.get(targetType+"/"+targetMethod+"#"+targetMethodDesc);
        CodeInserter inserter = null;
        if (codeInserters != null){
            for (CodeInserter codeInserter:codeInserters){
                if (codeInserter.match(owner, name, desc)){
                    inserter = codeInserter;
                    insertCount++;
                }
            }
        }
        if (insertCount>1){
            String info = "there are "+insertCount+" CodeInserters in same codeInsertPoint which required one!";
            AgentLogger.sys.severe(info);
            return null;
        }
        return inserter;
    }

    public static void init(){
        Set<String> codeInserterBuilders = Agent.config.getCodeInserterBuilders();
        if (codeInserterBuilders!=null){
            for (String builder:codeInserterBuilders){
                try {
                    Class<CodeInserterBuilder> clazz = (Class<CodeInserterBuilder>) Class.forName(builder);
                    CodeInserterBuilder codeInserterBuilder = clazz.newInstance();
                    CodeInserterPool.addCodeInserter(codeInserterBuilder.build());
                    AgentLogger.sys.info("CodeInserterBuilder [" + builder + "] init success!");
                } catch (Exception e) {
                    AgentLogger.sys.severe("CodeInserterBuilder init error!");
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
