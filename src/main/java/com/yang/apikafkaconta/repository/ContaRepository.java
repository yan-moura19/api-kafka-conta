package com.yang.apikafkaconta.repository;

import com.yang.apikafkaconta.models.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Conta findByNumero(String numero);
}