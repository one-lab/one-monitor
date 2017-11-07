package org.onelab.monitor.agent.domain.track;

import org.onelab.monitor.agent.Agent;

/**
 * @author Chunliang.Han on 2017/11/7.
 */
public class TrackService {

  public void execute(Track track, boolean isFail) {
    Agent.logger.info(track.toString() + " FAIL:" + isFail);
  }
}
