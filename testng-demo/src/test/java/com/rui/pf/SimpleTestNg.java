package com.rui.pf;

import com.rui.pf.testng.AbstractApplicationTestNg;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Date;

public class SimpleTestNg extends AbstractApplicationTestNg {

    public static Date beforeDate ;


    @Test
    public void testAdd() throws Exception {
        Thread.sleep(500);
        Assert.assertEquals(add(4,4),8);
    }

    @Test
    public void testDec() throws Exception {
        Thread.sleep(100);
        Assert.assertEquals(3,dec(8,5));
    }

    //执行5次
    @Test(timeOut = 1,invocationCount = 5)
    public void testTime(){
        int i =0;
        while(i<1000000000){
            i++;
        }
    }
    //如果捕获到预想的异常，视为运行正常的用例
    @Test(expectedExceptions = NullPointerException.class)
    public void testSub(){
        throw new NullPointerException();
    }
    //依赖型用例，除非必要，不能使用这个注解，相关注解还有：dependsOnGroups
    @Test(dependsOnMethods = {"testTime","testSub"})
    public void testMethond(){
        System.out.println(0);
    }

    //定义数据，有5个对象，如果引用了这个provider相当于会执行5次
    @DataProvider(name="user")
    public Object[][] getStr(){
        return new Object[][]{
                { "", "", "账号不能为空" },
                { "admin"," " ,"密码不能为空" },
                { " ", "a123456","账号不能为空" },
                { "ad ", "123456","账号“ad”不存在" },
                { "admin","12345" ,"密码错误" },
        };
    }
    //引用user的注解
    @Test(dataProvider = "user")
    private void sout(String uname,String pword,String msg){
        System.out.println(uname+"->"+pword+"->"+msg);

    }

    public int add(int a ,int b){
        System.out.println(a+"+"+b+ "="+(a+b));
        return a+b;
    }
    public int dec(int a ,int b){
        System.out.println(a+"-"+b+ "="+(a-b));
        return a-b;
    }
    @Test(dataProvider = "dataProvider")
    private void sout1(Method m){
        System.out.println(m);

    }

    @DataProvider(name = "dataProvider")
    public Object[][] provideData(Method method, ITestContext context) {

        Object[][] result = new Object[][]{{method}};

        return result;

    }//原文出自【易百教程】，商业转载请联系作者获得授权，非商业请保留原文链接：https://www.yiibai.com/testng/parameterized-test.html




}
