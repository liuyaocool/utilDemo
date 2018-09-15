package com.liuyao.demo.controller;

import com.liuyao.demo.util.FileIOUtil;
import com.liuyao.demo.util.MyFileIOUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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

        return FileIOUtil.importExcel(file, columns, "yyyy-MM-dd HH:mm:ss", 3);
    }
 @PostMapping("/upload")
    public String fileUpload(@PathVariable("file") MultipartFile file, HttpServletRequest request){
        return MyFileIOUtil.upload(request.getSession(), file, "c:/java/upload");
    }

    @PostMapping("/upLoad")
    public String upLoad (HttpServletRequest request, @PathVariable("file") MultipartFile file) throws IOException {

//        return FileIOUtil.uploadFile("/Users/apple/Desktop/qjyl/upload", file);
        return null;
    }

    @PostMapping("/upWord")
    public String upWord (HttpServletRequest request, @PathVariable("word") MultipartFile word) throws IOException {

        return FileIOUtil.getWordBook(word);
    }

}
