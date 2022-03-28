package daeunn.daeunspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // localhost:8080으로 들어오면 호출되는 페이지
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
