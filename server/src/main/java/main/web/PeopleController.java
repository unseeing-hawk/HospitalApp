package main.web;

import main.entity.People;
import main.exception.DiagnoseNotFoundException;
import main.exception.PeopleNotFoundException;
import main.exception.WardNotFoundException;
import main.exception.WardOverflowException;
import main.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/people")
public class PeopleController {
    private PeopleService peopleService;

    @GetMapping("/all")
    public ResponseEntity<List<People>> getAllPeople() {
        List<People> list = peopleService.listPeople();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<People> getPeopleByID(@PathVariable("id") long id) {
        try {
            People people = peopleService.findPeopleByID(id);
            return new ResponseEntity<>(people, HttpStatus.OK);
        } catch (PeopleNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/{wardName}/{diagnoseName}")
    public ResponseEntity<List<People>> getPeopleByWardNameAndDiagnoseName(@PathVariable("wardName") String wardName,
                                                                           @PathVariable("diagnoseName") String diagnoseName) {
        try {
            List<People> list = peopleService.findPeopleByWardNameAndDiagnoseName(wardName, diagnoseName);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (WardNotFoundException | DiagnoseNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/fullName")
    public ResponseEntity<List<People>> getPeopleByFullName(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String patherName) {
        try {
            List<People> list = peopleService.findPeopleByFullName(firstName, lastName, patherName);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public People addPeople(@RequestBody People people) {
        try {
            return peopleService.addPeople(people);
        } catch (DiagnoseNotFoundException | WardNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (WardOverflowException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deletePeople(@PathVariable("id") Long id) {
        try {
            peopleService.deletePeopleByID(id);
        } catch (PeopleNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PutMapping("/update/ward/{id}")
    public void updatePeopleWard(@PathVariable("id") Long id, @RequestParam Long wardId) {
        try {
            peopleService.updatePeopleWard(id, wardId);
        } catch (WardNotFoundException | PeopleNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (WardOverflowException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @PutMapping("/update/diagnose/{id}")
    public void updatePeopleDiagnose(@PathVariable("id") Long id, @RequestParam Long diagnoseId) {
        try {
            peopleService.updatePeopleDiagnose(id, diagnoseId);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PutMapping("/update/fullName/{id}")
    public void updatePeopleFullName(@PathVariable("id") Long id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String patherName) {
        try {
            peopleService.updatePeopleFullName(id, firstName, lastName, patherName);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        } catch (PeopleNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @Autowired
    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }
}
