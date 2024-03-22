package com.pankova.hospitalserver.service;

import java.util.List;

import com.pankova.hospitalserver.entity.Diagnosis;
import com.pankova.hospitalserver.entity.People;

public interface DiagnosisService {
    List<Diagnosis> listDiagnosis();
    Diagnosis findDiagnoseById(long id);
    Diagnosis addDiagnose(Diagnosis diagnose);
    void deleteDiagnoseById(Long id);
    void updateDiagnoseName(Long id, String newName);
    List<Diagnosis> getDiagnosisByWardName(String name);
    List<People> getPeopleByDiagnoseName(String name);
}
