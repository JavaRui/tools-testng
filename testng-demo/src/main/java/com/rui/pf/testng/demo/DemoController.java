package com.rui.pf.testng.demo;

import com.alibaba.fastjson.JSONObject;
import com.rui.pf.testng.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author XuZhuohao
 * @date 2019/10/17
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @PostMapping("/add")
    public String add(@RequestBody JSONObject testParam){
        return testParam+" add";
    }

    @GetMapping("/clear")
    public String clear(){
        return demoService.doth();
    }
}
