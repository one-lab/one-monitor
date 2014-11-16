package org.onelab.monitor.agent.domain.track;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chunliangh on 14-11-16.
 */
public class TrackStore {

    public static final String linkId = "linkId";

    public static final String trackId = "trackId";

    public static final String methodIndex = "methodIndex";

    public static final String methodIndexPrefix = "#";

    private static ThreadLocal<Map<String,Object>> localTrack = new ThreadLocal<Map<String,Object>>();

    public static void setLinkId(String linkId){
        put(TrackStore.linkId,linkId);
    }

    public static void setTrackId(String trackId){
        put(TrackStore.trackId,trackId);
    }

    public static int getMethodIndex(){
        Integer index = get(methodIndex);
        if (index == null){
            index = 0;
            put(methodIndex,index);
        }
        return index;
    }

    public static void addMethodIndex(){
        Integer index = getMethodIndex();
        put(methodIndex,++index);
    }

    public static void minusMethodIndex(){
        Integer index = getMethodIndex();
        put(methodIndex,--index);
    }

    public static void mappingMethodIndexAndId(int index,String methodId){
        put(methodIndexPrefix+index,methodId);
    }


    public static boolean exist(String key){
        Map<String,Object> info = localTrack.get();
        if (info!=null){
            return info.containsKey(key);
        }
        return false;
    }
    public static void put(String key,Object value){
        Map<String,Object> info = localTrack.get();
        if (info==null){
            info = new HashMap<String, Object>();
            localTrack.set(info);
        }
        info.put(key,value);
    }

    public static<T> T get(String key){
        Map<String,Object> info = localTrack.get();
        if (info!=null){
            return (T)info.get(key);
        }
        return null;
    }

    public static void clear(){
        localTrack.remove();
    }

    public static void main(String[] args){
    }
}
