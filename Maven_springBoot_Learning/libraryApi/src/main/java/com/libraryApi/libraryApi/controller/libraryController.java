package com.libraryApi.libraryApi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class libraryController {

    @RequestMapping("/")
    public String index(){
//        return "index page";
        return "index.html";
    }

    @RequestMapping("/books/data")
    public String createBook(){
        return "{'json':data}";
    }

}
