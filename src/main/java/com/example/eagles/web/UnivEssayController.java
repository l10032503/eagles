package com.example.eagles.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class UnivEssayController {

    @GetMapping("/sookmyung")
    public String sookmyung(){
        return "sookmyung";
    }
}
