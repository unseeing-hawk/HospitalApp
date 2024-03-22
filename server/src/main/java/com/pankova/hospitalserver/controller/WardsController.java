package com.pankova.hospitalserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.pankova.hospitalserver.entity.People;
import com.pankova.hospitalserver.entity.Wards;
import com.pankova.hospitalserver.service.WardsService;

import java.util.List;

import javax.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/wards")
public class WardsController {
    private WardsService wardsService;

    @GetMapping("/all")
    public ResponseEntity<List<Wards>> getWards() {
        List<Wards> list = wardsService.listWards();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/ward/{id}")
    public ResponseEntity<Wards> getWard(@PathVariable("id") long id) {
        try {
            Wards ward = wardsService.findWardById(id);
            return new ResponseEntity<>(ward, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<People>> getPeopleByWardName(@PathVariable("name") String name) {
        try {
            List<People> list = wardsService.getPeopleByWardName(name);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Wards addWard(@RequestBody Wards ward) {
        try {
            return wardsService.addWard(ward);
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteWardById(@PathVariable("id") long id) {
        try {
            wardsService.deleteWardByID(id);
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @PutMapping("/update/name/{id}")
    public void updateWardName(@PathVariable("id") Long id, @RequestParam String newName) {
        try {
            wardsService.updateWardName(id, newName);
        } catch (IllegalArgumentException | EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @PutMapping("/update/maxCount/{id}")
    public void updateWardMaxCount(@PathVariable("id") Long id, @RequestParam Integer newMaxCount) {
        try {
            wardsService.updateWardMaxCount(id, newMaxCount);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        } catch (EntityNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (RuntimeException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Autowired
    public void setWardsService(WardsService wardsService) {
        this.wardsService = wardsService;
    }
}
