package com.cnpiec.ireader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cnpiec.ireader.service.ErrorDataServiceImpl;
import com.cnpiec.ireader.service.TestServiceImpl;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestServiceImpl tstServiceImpl;

    @RequestMapping("/transactional")
    public void transTestController() {
        tstServiceImpl.addPojo();
    }

    @Autowired
    private ErrorDataServiceImpl errorDataService;

    @RequestMapping("error1")
    public void errorData1() {
        errorDataService.queryErrorData1();
    }
    
    @RequestMapping("error2")
    public void errorData2() {
        errorDataService.queryErrorData2();
    }
}
