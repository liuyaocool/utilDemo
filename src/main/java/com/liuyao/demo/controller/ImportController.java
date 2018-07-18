package com.liuyao.demo.controller;

import com.liuyao.demo.entity.Hero;
import com.liuyao.demo.util.FileIOUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("systemFile")
public class ImportController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/import")
    public List<Map<String,String>> fileImport(@PathVariable("file") MultipartFile file){

        Map<Integer, String > columns = new HashMap<>();
        columns.put(1, "name");
        columns.put(2, "age");
        columns.put(3, "id");
        columns.put(4, "sex");
        columns.put(5, "hobby");
        columns.put(7, "beizhu");

        return FileIOUtil.importExcel(file, Hero.class, columns, "yyyy-MM-dd HH:mm:ss");
    }


}
