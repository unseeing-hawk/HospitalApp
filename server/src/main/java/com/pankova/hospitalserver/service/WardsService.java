package com.pankova.hospitalserver.service;

import java.util.List;

import com.pankova.hospitalserver.entity.People;
import com.pankova.hospitalserver.entity.Wards;

public interface WardsService {
    List<Wards> listWards();
    Wards findWardById(long id);
    Wards addWard(Wards ward);
    void deleteWardByID(Long id);
    void updateWardName(Long id, String newName);
    void updateWardMaxCount(Long id, Integer newMaxCount);
    List<People> getPeopleByWardName(String name);
}
