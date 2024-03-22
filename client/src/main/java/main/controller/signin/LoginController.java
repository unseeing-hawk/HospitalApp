package main.controller.signin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static main.utils.Utils.*;

@Controller
public class LoginController {
    private User user;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("title", "Login");
        return "signin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String password) {
        String url = "http://localhost:8080/auth/login";
        String json = "{\n" +
                "\"login\":" + "\"" + login + "\"" + ",\n" +
                "\"password\":" + "\"" + password + "\"" + "\n" +
                "}";
        ResponseEntity<String> responseEntity = processPostRequest(url, null, json,
                HttpMethod.POST, MediaType.APPLICATION_JSON);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User tempUser = objectMapper.readValue(responseEntity.getBody(), User.class);
            user.setLogin(tempUser.getLogin());
            user.setToken(tempUser.getToken());
        } catch (JsonProcessingException error) {
            System.err.println(error.getMessage());
        }

        return "redirect:/account";
    }

    @Autowired
    public void setUser(User user) {
        this.user = user;
    }
}
