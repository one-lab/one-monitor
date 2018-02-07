package org.onelab.monitor.agent.domain.track;

import org.onelab.monitor.agent.config.Config;
import org.onelab.monitor.agent.log.AgentLogger;

/**
 * @author Chunliang.Han on 2017/11/7.
 */
public class TrackListener {

  private final String FLAG_FAIL = " F";
  private final String FLAG_SUCC = " S";

  public void onCome(Track track){

  }

  public void onQuit(Track track, boolean isFail) {
    if (track.duration() >= Config.trackDuration){
      if (isFail){
        AgentLogger.cus.info(track.toString() + FLAG_FAIL);
      } else {
        AgentLogger.cus.info(track.toString() + FLAG_SUCC);
      }
    }
  }
}
