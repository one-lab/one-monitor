package org.onelab.monitor.agent.domain;

import org.onelab.monitor.agent.domain.track.Track;
import org.onelab.monitor.agent.utils.UUIDGenerator;

/**
 * @author Chunliang.Han on 2017/11/7.
 */
public class ThreadTrackContext {

  //当前方法执行栈深度
  private int currDeeps = 0;
  //当前trackStack大小
  private int trackSize = 0;

  private String traceId = UUIDGenerator.randomUUID();

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

  /**
   * 尝试pop, 并减少深度currDeeps
   * @return
   */
  public Track pop() {
    if (prePop()){
      Track result = head;
      trackSize--;
      head = head.getParent();
      return result;
    }
    return null;
  }

  public void push(Track track) {
    track.setParent(head);
    head = track;
    head.setIndex(trackSize++);
    head.setTrackId(traceId);
  }

  public Track peek() {
    return head;
  }

  /**
   * 增加深度currDeeps , 测试是否可以push
   * @return
   */
  public boolean prePush() {
    currDeeps++;
    if (currDeeps > 200) return false;
    if (head!=null && head.isRecursive()) return false;
    return true;
  }

  private boolean prePop(){
    if (trackSize > --currDeeps){
      return true;
    }
    return false;
  }
}
