package com.yang.apikafkaconta.controller;

import com.yang.apikafkaconta.models.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yang.apikafkaconta.service.ContaService;

import java.util.List;

@RestController
@RequestMapping("/conta")
public class ContaController {
    @Autowired
    private ContaService contaService;

    @PostMapping
    public Conta criarConta(@RequestParam Long clienteId, @RequestBody Conta conta) {
        return contaService.criarConta(clienteId, conta);
    }

    @PostMapping("/{id}/deposito")
    public Conta depositar(@PathVariable Long id, @RequestParam double valor) {
        return contaService.depositar(id.toString(), valor);
    }

    @PostMapping("/{id}/saque")
    public Conta saque(@RequestParam String numero, @RequestParam double valor) {
        return contaService.sacar(numero, valor);
    }

    @PostMapping("/transferencia")
    public Conta transferencia(@RequestParam String numeroOrigem, @RequestParam String numeroDestino, @RequestParam double valor) {
        return contaService.transferencia(numeroOrigem, numeroDestino, valor);
    }

    @GetMapping("/{id}/extrato")
    public List<String> extrato(@RequestParam String numero) {
        return contaService.extrato(numero);
    }
}