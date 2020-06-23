package com.rui.pf.testng.result;

import java.util.List;

/**
 * 所有的测试汇总
 * */
public class TestCaseSumResult {

    private List<TestCaseMethodResult> results;
    private TestCaseDesc desc;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TestCaseSumResult{" +
                "results=" + results +
                ", desc=" + desc +
                '}';
    }

    public List<TestCaseMethodResult> getResults() {
        return results;
    }

    public void setResults(List<TestCaseMethodResult> results) {
        this.results = results;
    }

    public TestCaseDesc getDesc() {
        return desc;
    }

    public void setDesc(TestCaseDesc desc) {
        this.desc = desc;
    }
}
