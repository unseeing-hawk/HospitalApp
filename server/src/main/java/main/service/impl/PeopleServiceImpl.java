package main.service.impl;

import main.entity.People;
import main.exception.DiagnoseNotFoundException;
import main.exception.PeopleNotFoundException;
import main.exception.WardNotFoundException;
import main.exception.WardOverflowException;
import main.repository.DiagnosisRepository;
import main.repository.PeopleRepository;
import main.repository.WardsRepository;
import main.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeopleServiceImpl implements PeopleService {
    private PeopleRepository peopleRepository;
    private WardsRepository wardsRepository;
    private DiagnosisRepository diagnosisRepository;

    @Override
    public List<People> listPeople() {
        return (List<People>) peopleRepository.findAll();
    }

    @Override
    public People findPeopleByID(Long id) throws PeopleNotFoundException {
        Optional<People> optionalPeople = peopleRepository.findById(id);
        if (optionalPeople.isPresent()) {
            return optionalPeople.get();
        } else {
            throw new PeopleNotFoundException("People not found by ID");
        }
    }

    @Override
    public void deletePeopleByID(Long id) throws PeopleNotFoundException {
        if (!peopleRepository.existsById(id)) {
            throw new PeopleNotFoundException("People not found by ID");
        }
        peopleRepository.deleteById(id);
    }

    @Override
    public People addPeople(People people) throws DiagnoseNotFoundException, WardNotFoundException, WardOverflowException {
        if (!diagnosisRepository.existsById(people.getDiagnose().getId())) {
            throw new DiagnoseNotFoundException("The diagnose must exist");
        }
        if (!wardsRepository.existsById(people.getWard().getId())) {
            throw new WardNotFoundException("The ward must exist");
        }
        if (peopleRepository.countByWardId(people.getWard().getId()).intValue() == wardsRepository.searchById(people.getWard().getId()).getMaxCount()) {
            throw new WardOverflowException("Too many people in ward");
        }

        return peopleRepository.save(people);
    }

    @Override
    public List<People> findPeopleByFullName(String firstName, String lastName, String patherName) throws IllegalArgumentException {
        if (firstName == null || lastName == null || patherName == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        return (List<People>) peopleRepository.findAllByFirstNameAndLastNameAndPatherName(firstName, lastName, patherName);
    }

    @Override
    public void updatePeopleWard(Long id, Long wardId) throws WardNotFoundException, PeopleNotFoundException, WardOverflowException {
        if (!wardsRepository.existsById(wardId)) {
            throw new WardNotFoundException("Ward not found by ID");
        }
        if (!peopleRepository.existsById(id)) {
            throw new PeopleNotFoundException("People not found by ID");
        }
        if (peopleRepository.countByWardId(wardId) > wardsRepository.searchById(wardId).getMaxCount()) {
            throw new WardOverflowException("Too many people in ward");
        }
        peopleRepository.updatePeopleWard(id, wardId);
    }

    @Override
    public void updatePeopleDiagnose(Long id, Long diagnoseId) throws DiagnoseNotFoundException, PeopleNotFoundException {
        if (!diagnosisRepository.existsById(diagnoseId)) {
            throw new DiagnoseNotFoundException("Diagnose not found by ID");
        }
        if (!peopleRepository.existsById(id)) {
            throw new PeopleNotFoundException("People not found by ID");
        }
        peopleRepository.updatePeopleDiagnose(id, diagnoseId);
    }

    @Override
    public void updatePeopleFullName(Long id, String firstName, String lastName, String patherName) throws IllegalArgumentException, PeopleNotFoundException {
        if (firstName == null || lastName == null || patherName == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        if (!peopleRepository.existsById(id)) {
            throw new PeopleNotFoundException("People not found by ID");
        }
        peopleRepository.updatePeopleFullName(id, firstName, lastName, patherName);
    }

    @Override
    public List<People> findPeopleByWardNameAndDiagnoseName(String wardName, String diagnoseName) throws WardNotFoundException, DiagnoseNotFoundException {
        if (!wardsRepository.existsByName(wardName)) {
            throw new WardNotFoundException("Ward not found by name");
        }
        if (!diagnosisRepository.existsByName(diagnoseName)) {
            throw new DiagnoseNotFoundException("Diagnose not found by name");
        }
        return peopleRepository.findPeopleByWardNameAndDiagnoseName(wardName, diagnoseName);
    }

    @Autowired
    public void setPeopleRepository(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Autowired
    public void setWardsRepository(WardsRepository wardsRepository) {
        this.wardsRepository = wardsRepository;
    }

    @Autowired
    public void setDiagnosisRepository(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }
}
