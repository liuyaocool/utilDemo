package com.liuyao.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

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

    //跨域通信 可加在类上
//    @CrossOrigin(origins = {"http://localhost:8980","null"})
    @RequestMapping(value = "/testIp", method = RequestMethod.POST)
    @ResponseBody
    public String ttt(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("testIp");
        return "123123";
    }

    @RequestMapping(value = "/{pa}/{param}", method = RequestMethod.POST)
    @ResponseBody
    public String tt(@PathVariable("pa") String pa,@PathVariable("param") String param,
            HttpServletRequest request, HttpServletResponse response) {
        System.out.println(new Date() +request.getRemoteAddr());
        return "123123";
    }

}
