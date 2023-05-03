package com.hescha.medicalappointment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/about")
    public String about(){
        return "about";
    }
    @GetMapping("/contact")
    public String contact(){
        return "contact";
    }
    @GetMapping("/departments")
    public String departments(){
        return "departments";
    }
    @GetMapping(path = {"/index", "/"})
    public String index(){
        return "index";
    }
}
