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

    @GetMapping("/sookmyung-2019-01-01")
    public String sookmyung_2019_01_01(){
        return "sookmyung-2019-01-01";
    }
}
