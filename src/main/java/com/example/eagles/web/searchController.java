package com.example.eagles.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class searchController {

    @GetMapping("/search")
    public String search(){
        return "search";
    }
}
