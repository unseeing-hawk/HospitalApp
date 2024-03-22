package main.repository;

import main.entity.Diagnosis;
import main.entity.People;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DiagnosisRepository extends CrudRepository<Diagnosis, Long> {
    boolean existsByName(String name);

    @Query("select p from People p where p.diagnose.name= :name")
    List<People> findPeopleByDiagnoseName(@Param("name") String name);

    @Query("select distinct p.diagnose from People p where p.ward.name= :name")
    List<Diagnosis> findDiagnosisByWardName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("update Diagnosis set name= :newName where id= :id")
    void updateDiagnoseName(@Param("id") Long id, @Param("newName") String newName);
}
