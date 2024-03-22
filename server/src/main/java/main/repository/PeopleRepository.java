package main.repository;

import main.entity.People;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PeopleRepository extends CrudRepository<People, Long> {
    boolean existsByWardId(long id);
    boolean existsByDiagnoseId(long id);
    Long countByWardId(Long id);
    Iterable<People> findAllByFirstNameAndLastNameAndPatherName(String firstName, String LastName, String PatherName);

    @Query("select p from People p where p.ward.name= :wardName and p.diagnose.name = :diagnoseName")
    List<People> findPeopleByWardNameAndDiagnoseName(@Param("wardName") String wardName, @Param("diagnoseName") String diagnoseName);

    @Modifying
    @Transactional
    @Query("update People set diagnose.id= :diagnoseId where id= :id")
    void updatePeopleDiagnose(@Param("id") Long id, @Param("diagnoseId") Long diagnoseId);

    @Modifying
    @Transactional
    @Query("update People set ward.id= :wardId where id= :id")
    void updatePeopleWard(@Param("id") Long id, @Param("wardId") Long wardId);

    @Modifying
    @Transactional
    @Query("update People set firstName= :newFirstName, lastName= :newLastName, patherName= :newPatherName where id= :id")
    void updatePeopleFullName(@Param("id") Long id, @Param("newFirstName") String firstName, @Param("newLastName") String lastName, @Param("newPatherName") String patherName);
}
