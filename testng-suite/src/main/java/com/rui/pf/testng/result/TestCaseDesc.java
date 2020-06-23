package com.rui.pf.testng.result;

/**
 * 单个类的测试用例的总结
 * */
public class TestCaseDesc {

    private int totalCase = 0;
    private long totalTime = 0;
    private long maxTime = 0;
    private long minTime = 10000;
    private String minTimeCase;
    private String maxTimeCase;
    private long aveTime = 0;
    private int scCase = 0;
    private int failCase = 0;
    private double scRate;

    @Override
    public String toString() {
        return "TestCaseDesc{" +
                "totalCase=" + totalCase +
                ", totalTime=" + totalTime +
                ", maxTime=" + maxTime +
                ", minTime=" + minTime +
                ", minTimeCase='" + minTimeCase + '\'' +
                ", maxTimeCase='" + maxTimeCase + '\'' +
                ", aveTime=" + aveTime +
                ", scCase=" + scCase +
                ", failCase=" + failCase +
                ", scRate=" + scRate +
                '}';
    }

    public int getTotalCase() {
        return totalCase;
    }

    public void setTotalCase(int totalCase) {
        this.totalCase = totalCase;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    public long getMinTime() {
        return minTime;
    }

    public void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    public String getMinTimeCase() {
        return minTimeCase;
    }

    public void setMinTimeCase(String minTimeCase) {
        this.minTimeCase = minTimeCase;
    }

    public String getMaxTimeCase() {
        return maxTimeCase;
    }

    public void setMaxTimeCase(String maxTimeCase) {
        this.maxTimeCase = maxTimeCase;
    }

    public long getAveTime() {
        return aveTime;
    }

    public void setAveTime(long aveTime) {
        this.aveTime = aveTime;
    }

    public int getScCase() {
        return scCase;
    }

    public void setScCase(int scCase) {
        this.scCase = scCase;
    }

    public int getFailCase() {
        return failCase;
    }

    public void setFailCase(int failCase) {
        this.failCase = failCase;
    }

    public double getScRate() {
        return scRate;
    }

    public void setScRate(double scRate) {
        this.scRate = scRate;
    }
}
