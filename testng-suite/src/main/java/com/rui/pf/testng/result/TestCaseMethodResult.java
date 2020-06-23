package com.rui.pf.testng.result;

/**
 * 单个测试方法的结果展示
 * */
public class TestCaseMethodResult {

    private String clz ;
    private String method;
    private String paramters;
    private boolean status;
    private String desc;
    private long ms;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParamters() {
        return paramters;
    }

    public void setParamters(String paramters) {
        this.paramters = paramters;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public long getMs() {
        return ms;
    }

    public void setMs(long ms) {
        this.ms = ms;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "TestCaseMethodResult{" +
                "clz='" + clz + '\'' +
                ", method='" + method + '\'' +
                ", paramters='" + paramters + '\'' +
                ", status=" + status +
                ", desc='" + desc + '\'' +
                ", ms=" + ms +
                ", id=" + id +
                '}';
    }
}
