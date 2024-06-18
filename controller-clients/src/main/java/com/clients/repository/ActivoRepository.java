package com.clients.repository;

import com.clients.entities.Activo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActivoRepository extends JpaRepository<Activo, Long> {


}
