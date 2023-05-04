package com.hescha.medicalappointment.controller;

import com.hescha.medicalappointment.model.Appointment;
import com.hescha.medicalappointment.model.TimeSlot;
import com.hescha.medicalappointment.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final CategoryService categoryService;
    @GetMapping("/about")
    public String about(){
        return "about";
    }
    @GetMapping(path = {"/index", "/"})
    public String index(Model model){
        model.addAttribute("entity", new Appointment());
        model.addAttribute("timeslots", TimeSlot.values());
        model.addAttribute("categories", categoryService.readAll());
        return "index";
    }
}
