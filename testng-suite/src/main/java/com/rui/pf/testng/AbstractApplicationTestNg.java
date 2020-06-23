package com.rui.pf.testng;


import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.format.FastDateFormat;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.fastjson.JSONObject;
import com.rui.pf.testng.result.TestCaseDesc;
import com.rui.pf.testng.result.TestCaseMethodResult;
import com.rui.pf.testng.result.TestCaseSumResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AbstractApplicationTestNg extends AbstractTestNGSpringContextTests {

    public static final String TESTNG_TEMPLATE_TXT_PATH = "testng/template.txt";
    /***单个类测试的描述*/
    private static TestCaseDesc singleTestCaseDesc;
    /**单个类测试的案例列表*/
    private static ArrayList<TestCaseMethodResult> singleTestResultList;
    private static Logger logger = LoggerFactory.getLogger(AbstractApplicationTestNg.class);
    /***所有类测试的描述*/
    private static TestCaseDesc allTestCaseDesc = new TestCaseDesc();
    /**所有类测试的案例列表*/
    private static List<TestCaseMethodResult> allTestResultList = new LinkedList<>();

    private static TestCaseMethodResult maxTimeCaseResult = null;
    private static TestCaseMethodResult minTimeCaseResult = null;



    @BeforeClass
    public void ngBeforeClass(){
        singleTestCaseDesc = new TestCaseDesc();
        singleTestResultList = new ArrayList<>();
    }

    /**
     * 单个用例运行完成之后的执行
     * @param mm 用例方法
     * @param context 此次用例的上下文根，里面可以获取许多参数
     * @param xmlTest 此次用例中，xml文件的配置数据
     * @param objs 用例方法的参数
     * @param testResult 用例执行的结果，里面也包含了单个用例的所有数据
     */
    @AfterMethod
    public void ngAfterSingleMethod(Method mm, ITestContext context, XmlTest xmlTest, Object[] objs, ITestResult testResult){
        Test testAnnotation = (Test) mm.getDeclaredAnnotations()[0];
        String name = mm.getName();

        Class<?>[] parameterTypes = mm.getParameterTypes();

        int status = testResult.getStatus();
        Object[] parameters = testResult.getParameters();

        StringBuilder sb = new StringBuilder();
        for( int i = 0 ; i <parameters.length ; i ++){
            sb.append(parameterTypes[i].getSimpleName()+":"+parameters[i]+"  ;  ");
        }
        logger.info("我是后置方法  "+name+"   "+(testResult.getEndMillis()-testResult.getStartMillis())+" ms  status" + status);

        //生成单条用例结果
        TestCaseMethodResult testCaseMethodResult = new TestCaseMethodResult();
        testCaseMethodResult.setClz(testResult.getInstanceName());
        testCaseMethodResult.setMethod(testResult.getMethod().getMethodName());
        testCaseMethodResult.setMs(testResult.getEndMillis()-testResult.getStartMillis());
        testCaseMethodResult.setStatus(status==1?true:false);
        testCaseMethodResult.setParamters(sb.toString());
        testCaseMethodResult.setDesc(testAnnotation.description());

        analysis(testCaseMethodResult);
        logger.info("=====================");

    }

    /**
     * 分析每条案例的数据
     * */
    private void analysis(TestCaseMethodResult testCaseMethodResult) {


        logger.info(testCaseMethodResult.toString());
        analysis(testCaseMethodResult, singleTestCaseDesc);

        singleTestResultList.add(testCaseMethodResult);
        allTestResultList.add(testCaseMethodResult);
    }

    /**
     * 分析数据
     * */
    private void analysis(TestCaseMethodResult testCaseMethodResult, TestCaseDesc testCaseDesc){
        testCaseMethodResult.setId(testCaseDesc.getTotalCase()+1);
        testCaseDesc.setTotalCase(testCaseDesc.getTotalCase()+1);
        //比率计算
        if(testCaseMethodResult.isStatus()){
            testCaseDesc.setScCase(testCaseDesc.getScCase()+1);
        }else{
            testCaseDesc.setFailCase(testCaseDesc.getFailCase()+1);
        }
        //最长和最短时间测试
        long ms = testCaseMethodResult.getMs();
        if(ms>testCaseDesc.getMaxTime()){
            testCaseDesc.setMaxTime(ms);
            testCaseDesc.setMaxTimeCase(testCaseMethodResult.getId()+"-"+ testCaseMethodResult.getClz()+":"+ testCaseMethodResult.getMethod());
        }else{
            if(ms<testCaseDesc.getMinTime()){
                testCaseDesc.setMinTime(ms);
                testCaseDesc.setMinTimeCase(testCaseMethodResult.getId()+"-"+ testCaseMethodResult.getClz()+":"+ testCaseMethodResult.getMethod());
            }
        }
        testCaseDesc.setTotalTime(testCaseDesc.getTotalTime() + ms);
    }



    @AfterClass
    public void ngAfterClass(){
        logger.info("单个类的用例运行完成");
        saveReporter(getFileName(),getTitle(), singleTestCaseDesc, singleTestResultList);
        logger.info("----------------生成报告完成------------------");
    }

    @AfterSuite
    public void ndAfterSuite(){
        if(allTestResultList.size() == singleTestResultList.size()){
            logger.info("仅有一个测试类，不需要生成总汇");
            return ;
        }


        allTestResultList.forEach(testCaseMethodResult ->{
            analysis(testCaseMethodResult,allTestCaseDesc);
        });


        logger.info("所有的类的用例运行完成");
        String yyyyMMddhhmm =  FastDateFormat.getInstance("yyyyMMddHHmm").format(System.currentTimeMillis());
        saveReporter(yyyyMMddhhmm+"测试报告总汇","测试报告总汇",allTestCaseDesc , allTestResultList);
        logger.info("----------------生成汇总报告完成------------------");
    }

    @AfterTest
    public void ngAfterTest(){
        logger.info("AfterTest");
    }

    /**
     * 保存报告
     * */
    public void saveReporter(String fileName , String title , TestCaseDesc testCaseDesc , List<TestCaseMethodResult> arrayList){
        long ave = (testCaseDesc.getTotalTime()/testCaseDesc.getTotalCase());
        testCaseDesc.setAveTime(ave);

        double rate = (Double.valueOf(testCaseDesc.getScCase())/testCaseDesc.getTotalCase());
        DecimalFormat df = new DecimalFormat("#.00");
        String str = df.format(rate*100);
        testCaseDesc.setScRate(Double.valueOf(str));

        TestCaseSumResult sumResult = new TestCaseSumResult();
        sumResult.setDesc(testCaseDesc);
        sumResult.setTitle(title);
        sumResult.setResults(arrayList);
        String s = JSONObject.toJSONString(sumResult);
        logger.info("===========获取结果==========");
        logger.info(s);
        logger.info("读取模板");
        String contentAsString = FileReader.create(new ClassPathResource(TESTNG_TEMPLATE_TXT_PATH).getFile()).readString();
        String yyyyMMddhhmm = DatePattern.PURE_DATE_FORMAT.format(System.currentTimeMillis());
        String resultText = contentAsString.replaceAll("\\$\\{dataResult\\}",s);
        String path = System.getProperty("user.dir") + "" + File.separator + "template" + File.separator + yyyyMMddhhmm+File.separator;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
//        AutoSave.writeHtml(resultText,path+fileName+".html");
        FileWriter.create(new File(path+fileName+".html"),Charset.forName("utf-8")).write(resultText);
    }

    /**
     * 获取文件名，可被复写
     * @return
     */
    public  String getFileName(){
        String yyyyMMddhhmm =  FastDateFormat.getInstance("yyyyMMddHHmm").format(System.currentTimeMillis());
        String fileName = this.getClass().getSimpleName()+yyyyMMddhhmm;
        return fileName;
    }

    /**
     * 获取文件里面的title，可被复写
     * @return
     */
    public String getTitle(){
        return getFileName();
    }







}