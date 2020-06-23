package com.rui.pf.testng.demo.service;


import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String doth() {
        return "我就是do";
    }
}
