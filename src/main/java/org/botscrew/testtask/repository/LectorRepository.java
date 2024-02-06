package org.botscrew.testtask.repository;

import org.botscrew.testtask.entity.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
    @Query(value = "select * from lectors where first_name ilike :searchStr or last_name ilike :searchStr", nativeQuery = true)
    List<Lector> findByFirstNameOrLastNameContainingIgnoreCase(@Param("searchStr") String searchStr);
}
