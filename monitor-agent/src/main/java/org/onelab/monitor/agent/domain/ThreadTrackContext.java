package org.onelab.monitor.agent.domain;

import org.onelab.monitor.agent.config.Config;
import org.onelab.monitor.agent.domain.track.Track;
import org.onelab.monitor.agent.utils.UUIDGenerator;

/**
 * @author Chunliang.Han on 2017/11/7.
 */
public class ThreadTrackContext {

  private Track head ;

  private String tid = UUIDGenerator.randomUUID();

  //当前方法执行栈深度
  private int currDeeps = 0;
  //当前trackStack大小
  private int trackSize = 0;

  public Track peek() {
    return head;
  }

  public Track pop() {
    currDeeps--;
    if (currDeeps+1 == trackSize){
      trackSize--;
      if (trackSize >= 0){
        Track result = head;
        head = head.getParent();
        return result;
      }
    }
    return null;
  }

  public boolean push(Track track) {
    currDeeps++;
    if (Config.trackDuration >= 0 && currDeeps<=200){
      if (head==null || !head.isRecursive()){
        trackSize++;
        track.setTid(tid);
        track.setIndex(trackSize-1);
        track.setParent(head);
        head = track;
        return true;
      }
    }
    return false;
  }
}
