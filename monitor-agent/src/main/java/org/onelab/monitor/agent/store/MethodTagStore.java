package org.onelab.monitor.agent.store;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 存储方法标记
 * @author Chunliang.Han on 15/12/9.
 */
public class MethodTagStore {
  private static final Map<String,Set<MethodTag>> methodTagMap = new HashMap<String, Set<MethodTag>>();
  public static void add(String type,String method,String desc,MethodTag tag){
    String key = type+"#"+method+"#"+desc;
    Set<MethodTag> tagSet = methodTagMap.get(key);
    if (tagSet == null){
      tagSet = new HashSet();
      methodTagMap.put(key,tagSet);
    }
    tagSet.add(tag);
  }
  public static Set<MethodTag> get(String type,String method,String desc){
    String key = type+"#"+method+"#"+desc;
    return methodTagMap.get(key);
  }
  public static boolean isTagExist(String type,String method,String desc,MethodTag tag){
    Set<MethodTag> tagSet = get(type,method,desc);
    if (tagSet!=null && tag!=null){
      return tagSet.contains(tag);
    }
    return false;
  }
}
