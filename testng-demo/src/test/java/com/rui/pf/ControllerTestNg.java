package com.rui.pf;

import com.alibaba.fastjson.JSONObject;
import com.rui.pf.testng.AbstractApplicationTestNg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@SpringBootTest(classes = DemoServiceApplication.class)
public class ControllerTestNg  extends AbstractApplicationTestNg {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeClass
    public void setUp() {
        //单个类,项目拦截器无效
//      mvc = MockMvcBuilders.standaloneSetup(new ProductController()).build();
        //项目拦截器有效
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void executeGet() throws  Exception {
        //调用接口，传入添加的用户参数
        RequestBuilder request = MockMvcRequestBuilders.get("/demo/clear");

        MvcResult mvcResult = mockMvc.perform(request).andReturn() ;
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("返回结果："+status);
        System.out.println(content);
        Assert.assertEquals("我就是do", content,"结果不对哦");

    }
    @Test(description = "这是个post的测试")
    public void executePost() throws  Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user","lalalal");
        //调用接口，传入添加的用户参数
        //如果传参，在controller的方法需要加个@RequestBody注解，因为通过mockMvc方式调用的spring方法，
        // 是直接模拟controller的处理，而正常的请求，是通过tomcat进来的，tomcat进来的请求，spring会将post体传入到request中。
        RequestBuilder request = MockMvcRequestBuilders.post("/demo/add")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonObject.toString());

        MvcResult mvcResult = mockMvc.perform(request).andReturn() ;
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        System.out.println("返回结果："+status);
        System.out.println(content);
//        Assert.assertEquals("正确", content,"结果不对哦");
        Assert.assertTrue(content.endsWith("add"),"结果不对哦，要以add结尾");
    }

}
