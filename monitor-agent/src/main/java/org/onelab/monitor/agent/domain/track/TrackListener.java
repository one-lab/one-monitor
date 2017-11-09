package org.onelab.monitor.agent.domain.track;

import org.onelab.monitor.agent.config.Config;
import org.onelab.monitor.agent.log.AgentLogger;

/**
 * @author Chunliang.Han on 2017/11/7.
 */
public class TrackListener {

  public void onCome(Track track){

  }

  public void onQuit(Track track, boolean isFail) {
    if (track.duration() > Config.pointCost){
      AgentLogger.cus.info(track.toString() + " FAIL:" + isFail);
    }
  }
}
