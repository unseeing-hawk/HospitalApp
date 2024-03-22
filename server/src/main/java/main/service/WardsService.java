package main.service;

import main.entity.People;
import main.entity.Wards;

import java.util.List;

public interface WardsService {
    List<Wards> listWards();
    Wards findWardById(long id);
    Wards addWard(Wards ward);
    void deleteWardByID(Long id);
    void updateWardName(Long id, String newName);
    void updateWardMaxCount(Long id, Integer newMaxCount);
    List<People> getPeopleByWardName(String name);
}
