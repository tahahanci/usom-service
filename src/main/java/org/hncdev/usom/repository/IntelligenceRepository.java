package org.hncdev.usom.repository;

import org.hncdev.usom.model.Intelligence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IntelligenceRepository extends JpaRepository<Intelligence, Long> {

    Optional<Intelligence> findByIntelligenceID(Long intelligenceID);
}
