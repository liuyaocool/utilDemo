package com.liuyao.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/system")
public class TestController {

    @GetMapping("/testId/{id}")
    public String testId(@PathVariable("id") String id){

        return "hello" + id;
    }
    @GetMapping("/test")
    public String test(){

        return "login";
    }

    //跨域通信
    @CrossOrigin(origins = {"http://localhost:8980","null"})
    @RequestMapping(value = "/testIp", method = RequestMethod.POST)
    @ResponseBody
    public String ttt(HttpServletRequest request, HttpServletResponse response) {
        System.out.println();
        return "123123";
    }

}
