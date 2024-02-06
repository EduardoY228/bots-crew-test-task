package org.botscrew.testtask.repository;

import org.botscrew.testtask.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(value = "SELECT degree, COUNT(l.id) FROM lectors_departments INNER JOIN lectors l ON l.id = lectors_departments.lector_id WHERE ld.department_id = :departmentId GROUP BY degree", nativeQuery = true)
    List<Integer[]> countLectorsByDegreeInDepartment(@Param("departmentId") Long departmentId);
}
