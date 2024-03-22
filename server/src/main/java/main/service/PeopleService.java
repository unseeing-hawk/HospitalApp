package main.service;

import main.entity.People;

import java.util.List;

public interface PeopleService {
    List<People> listPeople();
    People findPeopleByID(Long id);
    People addPeople(People people);
    void deletePeopleByID(Long id);
    List<People> findPeopleByFullName(String firstName, String lastName, String patherName);
    void updatePeopleWard(Long id, Long wardId);
    void updatePeopleDiagnose(Long id, Long diagnoseId);
    void updatePeopleFullName(Long id, String firstName, String lastName, String patherName);
    List<People> findPeopleByWardNameAndDiagnoseName(String wardName, String diagnoseName);
}
