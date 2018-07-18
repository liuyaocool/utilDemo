package com.liuyao.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("system")
public class TestController {

    @GetMapping("/testId/{id}")
    public String testId(@PathVariable("id") String id){

        return "hello" + id;
    }
    @GetMapping("/test")
    public String test(){

        return "hello";
    }

}
