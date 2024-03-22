package com.pankova.hospitalserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pankova.hospitalserver.entity.Diagnosis;
import com.pankova.hospitalserver.entity.People;
import com.pankova.hospitalserver.exception.DiagnoseBusyException;
import com.pankova.hospitalserver.exception.DiagnoseNotFoundException;
import com.pankova.hospitalserver.exception.DuplicateNameException;
import com.pankova.hospitalserver.exception.WardNotFoundException;
import com.pankova.hospitalserver.repository.DiagnosisRepository;
import com.pankova.hospitalserver.repository.PeopleRepository;
import com.pankova.hospitalserver.repository.WardsRepository;
import com.pankova.hospitalserver.service.DiagnosisService;

import java.util.List;
import java.util.Optional;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {
    private DiagnosisRepository diagnosisRepository;
    private PeopleRepository peopleRepository;
    private WardsRepository wardsRepository;

    @Override
    public List<Diagnosis> listDiagnosis() {
        return (List<Diagnosis>) diagnosisRepository.findAll();
    }

    @Override
    public Diagnosis findDiagnoseById(long id) {
        Optional<Diagnosis> optionalDiagnose = diagnosisRepository.findById(id);
        if (optionalDiagnose.isPresent()) {
            return optionalDiagnose.get();
        } else {
            throw new DiagnoseNotFoundException("Diagnose not found by ID");
        }
    }

    @Override
    public Diagnosis addDiagnose(Diagnosis diagnose) throws DuplicateNameException, IllegalArgumentException {
        if (diagnose.getName() == null) {
            throw new IllegalArgumentException("Diagnose name cannot be empty");
        }
        if (diagnosisRepository.existsByName(diagnose.getName())) {
            throw new DuplicateNameException("Diagnose name already in use");
        }
        return diagnosisRepository.save(diagnose);
    }

    @Override
    public void deleteDiagnoseById(Long id) throws DiagnoseNotFoundException, DiagnoseBusyException {
        if (!diagnosisRepository.existsById(id)) {
            throw new DiagnoseNotFoundException("Diagnose not found by ID");
        }
        if (peopleRepository.existsByDiagnoseId(id)) {
            throw new DiagnoseBusyException("Some people have this diagnose");
        }
        diagnosisRepository.deleteById(id);
    }

    @Override
    public List<People> getPeopleByDiagnoseName(String name) throws DiagnoseNotFoundException {
        if (!diagnosisRepository.existsByName(name)) {
            throw new DiagnoseNotFoundException("Diagnose not found by name");
        }
        return diagnosisRepository.findPeopleByDiagnoseName(name);
    }

    @Override
    public List<Diagnosis> getDiagnosisByWardName(String name) throws WardNotFoundException {
        if (!wardsRepository.existsByName(name)) {
            throw new WardNotFoundException("Ward not find by name");
        }
        return diagnosisRepository.findDiagnosisByWardName(name);
    }

    @Override
    public void updateDiagnoseName(Long id, String newName) throws IllegalArgumentException, DuplicateNameException, DiagnoseNotFoundException {
        if (newName == null) {
            throw new IllegalArgumentException("Diagnose name cannot be empty");
        }
        if (diagnosisRepository.existsByName(newName)) {
            throw new DuplicateNameException("Duplicate name of diagnose");
        }
        if (!diagnosisRepository.existsById(id)) {
            throw new DiagnoseNotFoundException("Diagnose not found by ID");
        }
        diagnosisRepository.updateDiagnoseName(id, newName);
    }

    @Autowired
    public void setDiagnosisRepository(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Autowired
    public void setPeopleRepository(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Autowired
    public void setWardsRepository(WardsRepository wardsRepository) {
        this.wardsRepository = wardsRepository;
    }
}
