package org.hncdev.usom.repository;

import org.hncdev.usom.model.Intelligence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface IntelligenceRepository extends JpaRepository<Intelligence, Long> {

    Optional<Intelligence> findByIntelligenceID(Long intelligenceID);

    @Query("SELECT i.id FROM Intelligence i WHERE i.id IN :ids")
    Set<Long> findExistingIds(@Param("ids") Set<Long> ids);
}
