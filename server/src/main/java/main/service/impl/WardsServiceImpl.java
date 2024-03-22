package main.service.impl;

import main.entity.People;
import main.entity.Wards;
import main.exception.*;
import main.repository.PeopleRepository;
import main.repository.WardsRepository;
import main.service.WardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WardsServiceImpl implements WardsService {
    private WardsRepository wardsRepository;
    private PeopleRepository peopleRepository;

    @Override
    public List<Wards> listWards() {
        return (List<Wards>) wardsRepository.findAll();
    }

    @Override
    public Wards findWardById(long id) throws WardNotFoundException {
        Optional<Wards> optionalWard = wardsRepository.findById(id);
        if (optionalWard.isPresent()) {
            return optionalWard.get();
        } else {
            throw new WardNotFoundException("Ward not found by ID");
        }
    }

    @Override
    public Wards addWard(Wards ward) throws DuplicateNameException {
        if (wardsRepository.existsByName(ward.getName())) {
            throw new DuplicateNameException("Ward name already in use");
        }

        return wardsRepository.save(ward);
    }

    @Override
    public void deleteWardByID(Long id) throws WardBusyException, WardNotFoundException {
        if (!wardsRepository.existsById(id)) {
            throw new WardNotFoundException("No ward with this id");
        }
        if (peopleRepository.existsByWardId(id)) {
            throw new WardBusyException("Some people have this ward");
        }
        wardsRepository.deleteById(id);
    }

    @Override
    public List<People> getPeopleByWardName(String name) throws WardNotFoundException {
        if (!wardsRepository.existsByName(name)) {
            throw new WardNotFoundException("Ward not found by name");
        }
        return wardsRepository.findPeopleByWardName(name);
    }

    @Override
    public void updateWardName(Long id, String newName) throws DuplicateNameException, WardNotFoundException, IllegalArgumentException {
        if (newName == null) {
            throw new IllegalArgumentException("Ward name cannot be empty");
        }
        if (wardsRepository.existsByName(newName)) {
            throw new DuplicateNameException("Duplicate name of ward");
        }
        if (!wardsRepository.existsById(id)) {
            throw new WardNotFoundException("Ward not found by ID");
        }
        wardsRepository.updateWardName(id, newName);
    }

    @Override
    public void updateWardMaxCount(Long id, Integer newMaxCount) throws WardNotFoundException, IllegalArgumentException {
        if (newMaxCount == null) {
            throw new IllegalArgumentException("Max count cannot be empty");
        }
        if (!wardsRepository.existsById(id)) {
            throw new WardNotFoundException("Ward not found by ID");
        }
        if (peopleRepository.countByWardId(id).intValue() > newMaxCount) {
            throw new WardOverflowException("New maxCount less than the number of people");
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
