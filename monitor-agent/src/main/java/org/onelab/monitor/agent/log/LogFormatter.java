package org.onelab.monitor.agent.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

class LogFormatter extends Formatter {

	private String lineSeparator;

	public LogFormatter() {
		this.lineSeparator = System.getProperty("line.separator");
	}

	public synchronized String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();
		String text = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(record.getMillis()));
		sb.append("[").append(text).append("]");
		sb.append("[").append(Thread.currentThread().getName()).append("]");
		sb.append("[").append(record.getLevel()).append("]");
		sb.append(": ");
		String message = formatMessage(record);
		sb.append(message);
		sb.append(this.lineSeparator);
		if (record.getThrown() != null) {
			try {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				record.getThrown().printStackTrace(pw);
				pw.close();
				sb.append(sw.toString());
			} catch (Exception ex) {
			}
		}
		return sb.toString();
	}
}
