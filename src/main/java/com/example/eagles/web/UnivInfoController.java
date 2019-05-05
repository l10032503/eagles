package com.example.eagles.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class UnivInfoController {

    @GetMapping("/univ-info")
    public String univ_info() {
        return "univ_info";
    }
}
