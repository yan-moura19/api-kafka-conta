package com.yang.apikafkaconta.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double saldo;
    private String numero;

    @ManyToOne
    private Cliente cliente;

    @ElementCollection
    private List<String> extrato = new ArrayList<>();


}