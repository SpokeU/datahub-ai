package dev.omyshko.datahubai.connections.repository;

import dev.omyshko.datahubai.connections.entity.DatabaseConnectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatabaseConnectionRepository extends JpaRepository<DatabaseConnectionEntity, Long> {
    List<DatabaseConnectionEntity> findByNameContainingIgnoreCase(String name);
    
    boolean existsByName(String name);
} 