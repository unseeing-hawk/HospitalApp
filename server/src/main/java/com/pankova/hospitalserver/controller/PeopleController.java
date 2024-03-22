package com.pankova.hospitalserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.pankova.hospitalserver.entity.People;
import com.pankova.hospitalserver.service.PeopleService;

import java.util.List;
import javax.persistence.EntityNotFoundException;


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
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/{wardName}/{diagnoseName}")
    public ResponseEntity<List<People>> getPeopleByWardNameAndDiagnoseName(@PathVariable("wardName") String wardName,
                                                                           @PathVariable("diagnoseName") String diagnoseName) {
        try {
            List<People> list = peopleService.findPeopleByWardNameAndDiagnoseName(wardName, diagnoseName);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/fullName")
    public ResponseEntity<List<People>> getPeopleByFullName(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String patherName) {
        try {
            List<People> list = peopleService.findPeopleByFullName(firstName, lastName, patherName);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public People addPeople(@RequestBody People people) {
        try {
            return peopleService.addPeople(people);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deletePeople(@PathVariable("id") Long id) {
        try {
            peopleService.deletePeopleByID(id);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PutMapping("/update/ward/{id}")
    public void updatePeopleWard(@PathVariable("id") Long id, @RequestParam Long wardId) {
        try {
            peopleService.updatePeopleWard(id, wardId);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (RuntimeException exception) {
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @Autowired
    public void setPeopleService(PeopleService peopleService) {
        this.peopleService = peopleService;
    }
}
