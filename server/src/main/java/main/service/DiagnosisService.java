package main.service;

import main.entity.Diagnosis;
import main.entity.People;

import java.util.List;

public interface DiagnosisService {
    List<Diagnosis> listDiagnosis();
    Diagnosis findDiagnoseById(long id);
    Diagnosis addDiagnose(Diagnosis diagnose);
    void deleteDiagnoseById(Long id);
    void updateDiagnoseName(Long id, String newName);
    List<Diagnosis> getDiagnosisByWardName(String name);
    List<People> getPeopleByDiagnoseName(String name);
}
