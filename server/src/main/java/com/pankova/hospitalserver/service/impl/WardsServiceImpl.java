package com.pankova.hospitalserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pankova.hospitalserver.entity.People;
import com.pankova.hospitalserver.entity.Wards;
import com.pankova.hospitalserver.repository.PeopleRepository;
import com.pankova.hospitalserver.repository.WardsRepository;
import com.pankova.hospitalserver.service.WardsService;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;


@Service
public class WardsServiceImpl implements WardsService {
    private WardsRepository wardsRepository;
    private PeopleRepository peopleRepository;

    @Override
    public List<Wards> listWards() {
        return (List<Wards>) wardsRepository.findAll();
    }

    @Override
    public Wards findWardById(long id) throws EntityNotFoundException {
        Optional<Wards> optionalWard = wardsRepository.findById(id);
        if (optionalWard.isPresent()) {
            return optionalWard.get();
        } else {
            throw new EntityNotFoundException("Ward not found by ID");
        }
    }

    @Override
    public Wards addWard(Wards ward) throws RuntimeException {
        if (wardsRepository.existsByName(ward.getName())) {
            throw new RuntimeException("Ward name already in use");
        }

        return wardsRepository.save(ward);
    }

    @Override
    public void deleteWardByID(Long id) throws EntityNotFoundException, RuntimeException {
        if (!wardsRepository.existsById(id)) {
            throw new EntityNotFoundException("No ward with this id");
        }
        if (peopleRepository.existsByWardId(id)) {
            throw new RuntimeException("Some people have this ward");
        }
        wardsRepository.deleteById(id);
    }

    @Override
    public List<People> getPeopleByWardName(String name) throws EntityNotFoundException {
        if (!wardsRepository.existsByName(name)) {
            throw new EntityNotFoundException("Ward not found by name");
        }
        return wardsRepository.findPeopleByWardName(name);
    }

    @Override
    public void updateWardName(Long id, String newName) throws IllegalArgumentException, RuntimeException, EntityNotFoundException {
        if (newName == null) {
            throw new IllegalArgumentException("Ward name cannot be empty");
        }
        if (wardsRepository.existsByName(newName)) {
            throw new RuntimeException("Duplicate name of ward");
        }
        if (!wardsRepository.existsById(id)) {
            throw new EntityNotFoundException("Ward not found by ID");
        }
        wardsRepository.updateWardName(id, newName);
    }

    @Override
    public void updateWardMaxCount(Long id, Integer newMaxCount) throws IllegalArgumentException, EntityNotFoundException, RuntimeException {
        if (newMaxCount == null) {
            throw new IllegalArgumentException("Max count cannot be empty");
        }
        if (!wardsRepository.existsById(id)) {
            throw new EntityNotFoundException("Ward not found by ID");
        }
        if (peopleRepository.countByWardId(id).intValue() > newMaxCount) {
            throw new RuntimeException("New maxCount less than the number of people");
        }
        wardsRepository.updateWardMaxCount(id, newMaxCount);
    }

    @Autowired
    public void setWardsRepository(WardsRepository wardsRepository) {
        this.wardsRepository = wardsRepository;
    }

    @Autowired
    public void setPeopleRepository(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
}
