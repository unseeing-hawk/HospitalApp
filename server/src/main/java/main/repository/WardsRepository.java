package main.repository;

import main.entity.People;
import main.entity.Wards;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WardsRepository extends CrudRepository<Wards, Long> {
    boolean existsByName(String name);
    Wards searchById(Long id);

    @Query("select p from People p where p.ward.name= :wardName")
    List<People> findPeopleByWardName(@Param("wardName") String wardName);

    @Modifying
    @Transactional
    @Query("update Wards set name= :newName where id= :id")
    void updateWardName(@Param("id") Long id, @Param("newName") String newName);

    @Modifying
    @Transactional
    @Query("update Wards set maxCount= :newMaxCount where id= :id")
    void updateWardMaxCount(@Param("id") Long id, @Param("newMaxCount") Integer newMaxCount);
}
