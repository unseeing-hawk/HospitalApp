package com.pankova.hospitalserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.pankova.hospitalserver.entity.Diagnosis;
import com.pankova.hospitalserver.entity.People;
import com.pankova.hospitalserver.service.DiagnosisService;

import java.util.List;

import javax.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/diagnosis")
public class DiagnosisController {
    private DiagnosisService diagnosisService;

    @GetMapping("/all")
    public ResponseEntity<List<Diagnosis>> getDiagnosis() {
        List<Diagnosis> list = diagnosisService.listDiagnosis();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{name}/people")
    public ResponseEntity<List<People>> getPeopleByDiagnoseName(@PathVariable("name") String name) {
        try {
            List<People> list = diagnosisService.getPeopleByDiagnoseName(name);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/diagnose/{id}")
    public ResponseEntity<Diagnosis> getDiagnose(@PathVariable("id") long id) {
        try {
            Diagnosis diagnose = diagnosisService.findDiagnoseById(id);
            return new ResponseEntity<>(diagnose, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/ward/{name}")
    public ResponseEntity<List<Diagnosis>> getDiagnosisByWardName(@PathVariable("name") String name) {
        try {
            List<Diagnosis> list = diagnosisService.getDiagnosisByWardName(name);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Diagnosis addDiagnose(@RequestBody Diagnosis diagnose) {
        try {
            return diagnosisService.addDiagnose(diagnose);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDiagnose(@PathVariable("id") Long id) {
        try {
            diagnosisService.deleteDiagnoseById(id);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @PutMapping("/update/name/{id}")
    public void updateDiagnoseName(@PathVariable("id") Long id, @RequestParam String newName) {
        try {
            diagnosisService.updateDiagnoseName(id, newName);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Autowired
    public void setDiagnosisService(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }
}
