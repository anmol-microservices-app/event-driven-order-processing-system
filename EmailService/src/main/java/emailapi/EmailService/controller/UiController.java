package emailapi.EmailService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {
	@GetMapping("/mail")
    public String show() {
        return "mailForm";
    }

}
