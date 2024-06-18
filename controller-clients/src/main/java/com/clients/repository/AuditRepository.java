package com.clients.repository;

import com.clients.entities.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditRepository extends JpaRepository<Audit, Long> {

    List<Audit> findByUser(String user);
}
