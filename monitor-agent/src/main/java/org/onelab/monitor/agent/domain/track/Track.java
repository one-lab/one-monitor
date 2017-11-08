package org.onelab.monitor.agent.domain.track;

/**
 * @author Chunliang.Han on 2017/3/9.
 */
public class Track {

  private String trackId;
  private int index;
  private String className;
  private String methodName;
  private String methodDesc;
  private Object thisObj;
  private Object[] args;
  private Object returnValue;
  private Throwable throwable;
  private long STime;
  private long ETime;
  boolean recursive;
  private Track parent;

  public void setTrackId(String trackId) {
    this.trackId = trackId;
  }

  public String getTrackId() {
    return trackId;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getClassName() {
    return className;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodDesc(String methodDesc) {
    this.methodDesc = methodDesc;
  }

  public String getMethodDesc() {
    return methodDesc;
  }

  public void setThisObj(Object thisObj) {
    this.thisObj = thisObj;
  }

  public Object getThisObj() {
    return thisObj;
  }

  public void setArgs(Object[] args) {
    this.args = args;
  }

  public Object[] getArgs() {
    return args;
  }

  public void setReturnValue(Object returnValue) {
    this.returnValue = returnValue;
  }

  public Object getReturnValue() {
    return returnValue;
  }

  public void setThrowable(Throwable throwable) {
    this.throwable = throwable;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  public void setSTime(long STime) {
    this.STime = STime;
  }

  public long getSTime() {
    return STime;
  }

  public void setETime(long ETime) {
    this.ETime = ETime;
  }

  public long getETime() {
    return ETime;
  }

  public boolean isRecursive() {
    return recursive;
  }

  public void setRecursive(boolean recursive) {
    this.recursive = recursive;
  }

  public Track getParent() {
    return parent;
  }

  public void setParent(Track parent) {
    this.parent = parent;
  }

  public boolean equals(String className, String methodName, String methodDesc, Object thisObj) {
    return this.className == className
           && this.methodName == methodName
           && this.methodDesc == methodDesc && this.thisObj == thisObj;
  }

  public String toString(){
    return new StringBuilder()
        .append("[").append(trackId).append("] ").append(index).append("\t")
        .append(ETime-STime).append("\t")
        .append(className).append("#").append(methodName).append(methodDesc)
        .append(thisObj == null ? " STATIC" : thisObj.hashCode())
        .append(isRecursive() ? " RECURSIVE" : "")
        .toString();
  }
}
