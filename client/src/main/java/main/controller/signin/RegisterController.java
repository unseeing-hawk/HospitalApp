package main.controller.signin;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static main.utils.Utils.*;

@Controller
public class RegisterController {
    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("title", "Register");
        return "signin/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String login, @RequestParam String password) {
        String url = "http://localhost:8080/auth/register";
        String json = "{\n" +
                "\"login\":" + "\"" + login + "\",\n" +
                "\"password\":" + "\"" + password + "\",\n" +
                "\"roles\":" + "[\"ROLE_USER\"]\n" +
                "}";

        processPostRequest(url, null, json, HttpMethod.POST, MediaType.APPLICATION_JSON);
        return "redirect:/login";
    }
}
