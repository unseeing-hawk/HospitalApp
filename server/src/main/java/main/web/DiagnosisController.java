package main.web;

import main.entity.Diagnosis;
import main.entity.People;
import main.entity.Wards;
import main.exception.DiagnoseBusyException;
import main.exception.DiagnoseNotFoundException;
import main.exception.DuplicateNameException;
import main.exception.WardNotFoundException;
import main.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        } catch (DiagnoseNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/diagnose/{id}")
    public ResponseEntity<Diagnosis> getDiagnose(@PathVariable("id") long id) {
        try {
            Diagnosis diagnose = diagnosisService.findDiagnoseById(id);
            return new ResponseEntity<>(diagnose, HttpStatus.OK);
        } catch (DiagnoseNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/ward/{name}")
    public ResponseEntity<List<Diagnosis>> getDiagnosisByWardName(@PathVariable("name") String name) {
        try {
            List<Diagnosis> list = diagnosisService.getDiagnosisByWardName(name);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (WardNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public Diagnosis addDiagnose(@RequestBody Diagnosis diagnose) {
        try {
            return diagnosisService.addDiagnose(diagnose);
        } catch (DuplicateNameException | IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteDiagnose(@PathVariable("id") Long id) {
        try {
            diagnosisService.deleteDiagnoseById(id);
        } catch (DiagnoseNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (DiagnoseBusyException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @PutMapping("/update/name/{id}")
    public void updateDiagnoseName(@PathVariable("id") Long id, @RequestParam String newName) {
        try {
            diagnosisService.updateDiagnoseName(id, newName);
        } catch (DiagnoseNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (IllegalArgumentException | DuplicateNameException exception) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Autowired
    public void setDiagnosisService(DiagnosisService diagnosisService) {
        this.diagnosisService = diagnosisService;
    }
}
