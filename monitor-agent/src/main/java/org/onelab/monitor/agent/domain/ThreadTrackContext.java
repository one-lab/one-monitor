package org.onelab.monitor.agent.domain;

import org.onelab.monitor.agent.domain.track.Track;

import java.util.UUID;

/**
 * @author Chunliang.Han on 2017/11/7.
 */
public class ThreadTrackContext {

  //当前方法执行栈深度
  private int currDeeps = 0;
  //当前trackStack大小
  private int trackSize = 0;

  private Track head ;

  public int getCurrDeeps() {
    return currDeeps;
  }

  public void setCurrDeeps(int currDeeps) {
    this.currDeeps = currDeeps;
  }

  public int getTrackSize() {
    return trackSize;
  }

  public void setTrackSize(int trackSize) {
    this.trackSize = trackSize;
  }

  public void push(Track track) {
    String tid ;
    if (trackSize == 0){
      tid = UUID.randomUUID().toString();
    } else {
      Track t = head;
      tid = t.getTrackId();
      track.setParent(head);
    }
    head = track;
    head.setIndex(trackSize++);
    head.setTrackId(tid);
  }

  /**
   * 增加深度currDeeps , 测试是否可以push
   * @return
   */
  public boolean incrementCurrDeepsAndTestPush() {
    currDeeps++;
    if (currDeeps>200){
      return false;
    }
    if (trackSize > 0){
      if (head.isRecursive()){
        return false;
      }
    }
    return true;
  }

  /**
   * 尝试pop, 并减少深度currDeeps
   * @return
   */
  public Track testPop() {
    currDeeps--;
    if (trackSize > currDeeps){
      trackSize--;
      Track result = head;
      head = head.getParent();
      return result;
    }
    return null;
  }
}
