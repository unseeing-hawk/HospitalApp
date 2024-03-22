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

import main.entity.Diagnosis;
import main.entity.People;

import static main.utils.Utils.*;


@Controller
public class DiagnosisController {
    @Value("${server.url}")
    private String serverUrl;
    private User user;

    @GetMapping("/diagnosis")
    public String getDiagnosisPage(Model model) {
        model.addAttribute("title", "Diagnosis");

        String url = serverUrl + "/diagnosis/all";
        List<Diagnosis> diagnosis = processGetRequest(url, List.class);
        model.addAttribute("diagnosis", diagnosis);

        return "diagnosis/diagnosis";
    }

    @GetMapping("/diagnose/{name}")
    public String getDiagnosePage(@PathVariable("name") String name, Model model) {
        model.addAttribute("title", name);

        String url = serverUrl + "/diagnosis/" + name + "/people";

        List<People> people = processGetRequest(url, List.class);
        model.addAttribute("people", people);

        return "diagnosis/diagnose";
    }

    @GetMapping("/diagnose/add")
    public String addDiagnosePage(Model model) {
        model.addAttribute("title", "Add diagnose");
        return "diagnosis/addDiagnose";
    }

    @GetMapping("/diagnose/edit/{id}")
    public String getEditPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("title", "Edit diagnose");

        String url = serverUrl + "/diagnosis/diagnose/" + id;
        Diagnosis diagnose = processGetRequest(url, Diagnosis.class);
        model.addAttribute("diagnose", diagnose);

        return "diagnosis/editDiagnose";
    }

    @PostMapping("/diagnose/edit/{id}")
    public String editDiagnose(@PathVariable("id") long id, @RequestParam String name,
                               @RequestParam String oldName) {
        String url = serverUrl + "/diagnosis/update/name/" + id + "?newName=" + name;

        if (!name.equals(oldName)) {
            processPostRequest(url, user.getToken(), null, HttpMethod.PUT, null);
        }

        return "redirect:/diagnosis";
    }

    @PostMapping("/diagnose/add")
    public String addDiagnose(@RequestParam String name) {
        String url = serverUrl + "/diagnosis/add";
        String json = "{\n" +
                "\"name\":" + "\"" + name + "\"\n" +
                "}";

        processPostRequest(url, user.getToken(), json, HttpMethod.POST, MediaType.APPLICATION_JSON);
        return "redirect:/diagnosis";
    }

    @PostMapping("/diagnose/delete/{id}")
    public String deleteDiagnose(@PathVariable("id") long id) {
        String url = serverUrl + "/diagnosis/delete/" + id;

        processPostRequest(url, user.getToken(), null, HttpMethod.DELETE, null);
        return "redirect:/diagnosis";
    }

    @Autowired
    public void setUser(User user) {
        this.user = user;
    }
}
