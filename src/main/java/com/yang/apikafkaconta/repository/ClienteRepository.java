package com.yang.apikafkaconta.repository;

import com.yang.apikafkaconta.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
