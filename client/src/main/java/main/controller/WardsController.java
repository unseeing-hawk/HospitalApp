package main.controller;

import java.util.List;

import main.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.entity.People;
import main.entity.Wards;

import static main.utils.Utils.*;


@Controller
public class WardsController {
    @Value("${server.url}")
    private String serverUrl;
    private User user;

    @GetMapping("/wards")
    public String getWards(Model model) {
        model.addAttribute("title", "Wards");

        String url = serverUrl + "/wards/all";
        List<Wards> wards = processGetRequest(url, List.class);
        model.addAttribute("wards", wards);

        return "wards/wards";
    }

    @GetMapping("/ward/{name}")
    public String getWard(@PathVariable("name") String name, Model model) {
        model.addAttribute("title", name);

        String url = serverUrl + "/wards/" + name;
        List<People> people = processGetRequest(url, List.class);
        model.addAttribute("people", people);

        return "wards/ward";
    }

    @GetMapping("/ward/add")
    public String getAddWard(Model model) {
        model.addAttribute("title", "Add ward");
        return "wards/addWard";
    }

    @GetMapping("/ward/edit/{id}")
    public String getEditPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("title", "Edit ward");

        String url = serverUrl + "/wards/ward/" + id;
        Wards ward = processGetRequest(url, Wards.class);
        model.addAttribute("ward", ward);

        return "wards/editWard";
    }

    @PostMapping("/ward/edit/{id}")
    public String editWard(@PathVariable("id") long id, @RequestParam String name, @RequestParam long maxCount,
                           @RequestParam String oldName) {
        String nameURL = serverUrl + "/wards/update/name/" + id + "?newName=" + name;
        String maxCountURL = serverUrl + "/wards/update/maxCount/" + id + "?newMaxCount=" + maxCount;

        if (!name.equals(oldName)) {
            processPostRequest(nameURL, user.getToken(), null, HttpMethod.PUT, null);
        }
        processPostRequest(maxCountURL, user.getToken(), null, HttpMethod.PUT, null);
        return "redirect:/wards";
    }

    @PostMapping("/ward/add")
    public String addWard(@RequestParam String name, @RequestParam int maxCount) {
        String url = serverUrl + "/wards/add";
        String json = "{\n" +
                "\"name\":" + "\"" + name + "\",\n" +
                "\"maxCount\":" + maxCount + "\n" +
                "}";

        processPostRequest(url, user.getToken(), json, HttpMethod.POST, MediaType.APPLICATION_JSON);
        return "redirect:/wards";
    }

    @PostMapping("/wards/delete/{id}")
    public String deleteDiagnose(@PathVariable("id") long id) {
        String url = serverUrl + "/wards/delete/" + id;

        processPostRequest(url, user.getToken(), null, HttpMethod.DELETE, null);
        return "redirect:/wards";
    }

    @Autowired
    public void setUser(User user) {
        this.user = user;
    }
}
