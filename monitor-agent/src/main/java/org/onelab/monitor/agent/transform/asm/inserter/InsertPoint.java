package org.onelab.monitor.agent.transform.asm.inserter;

/**
 * 代码切入点
 * Created by chunliangh on 14-12-4.
 */
public class InsertPoint {
    //调用方法命令对应的类名
    private String pointType;
    //调用方法命令对应的方法名
    private String pointMethod;
    //调用方法命令对应的方法描述
    private String pointDesc;
    //调用方法命令被调用的次数
    private int pointIndex;

    public InsertPoint(String pointType, String pointMethod, String pointDesc, int pointIndex) {
        if(pointIndex<1 || pointType==null || pointMethod==null || pointDesc==null){
            throw new IllegalArgumentException();
        }
        this.pointType = pointType;
        this.pointMethod = pointMethod;
        this.pointDesc = pointDesc;
        this.pointIndex = pointIndex;
    }

    public String getPointType() {
        return pointType;
    }

    public String getPointMethod() {
        return pointMethod;
    }

    public String getPointDesc() {
        return pointDesc;
    }

    public int getPointIndex() {
        return pointIndex;
    }
}
