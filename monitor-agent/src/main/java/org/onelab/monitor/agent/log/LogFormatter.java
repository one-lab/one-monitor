package org.onelab.monitor.agent.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter {

  private String lineSeparator;

  public LogFormatter() {
    this.lineSeparator = System.getProperty("line.separator");
  }

  public String format(LogRecord record) {
    StringBuilder sb = new StringBuilder();
    sb.append(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date(record.getMillis())));
    sb.append(" ").append(record.getMessage());
    sb.append(this.lineSeparator);
    return sb.toString();
  }
}
