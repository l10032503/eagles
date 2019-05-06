package com.example.eagles.web;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class WebController {
    @GetMapping("/")
    public String main(Model model) {
        return "main";
    }

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/temp")
    public String temp(){
        return "temp";
    }

}