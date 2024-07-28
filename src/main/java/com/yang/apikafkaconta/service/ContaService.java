package com.yang.apikafkaconta.service;


import com.yang.apikafkaconta.models.Cliente;
import com.yang.apikafkaconta.models.Conta;
import com.yang.apikafkaconta.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yang.apikafkaconta.repository.ContaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {
    @Autowired
    private ContaRepository contaRepository;


    @Autowired
    private ClienteRepository clienteRepository;

    public Conta criarConta(Long clienteId, Conta conta) {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        conta.setCliente(cliente);
        return contaRepository.save(conta);
    }

    @Transactional
    public Conta depositar(String numero, double valor) {
        Optional<Conta> contaOptional = Optional.ofNullable(contaRepository.findByNumero(numero));
        if (contaOptional.isPresent()) {
            Conta conta = contaOptional.get();
            conta.setSaldo(conta.getSaldo() + valor);
            conta.getExtrato().add("Depósito de " + valor + " realizado.");
            return contaRepository.save(conta);
        } else {
            throw new RuntimeException("Conta não encontrada");
        }
    }

    @Transactional
    public Conta sacar(String numero, double valor) {
        Optional<Conta> contaOptional = Optional.ofNullable(contaRepository.findByNumero(numero));
        if (contaOptional.isPresent()) {
            Conta conta = contaOptional.get();
            if (conta.getSaldo() >= valor) {
                conta.setSaldo(conta.getSaldo() - valor);
                conta.getExtrato().add("Saque de R$ " + valor);
                return contaRepository.save(conta);
            } else {
                throw new RuntimeException("Saldo insuficiente");
            }
        } else {
            throw new RuntimeException("Conta não encontrada");
        }
    }

    @Transactional
    public Conta transferencia(String numeroOrigem, String numeroDestino, double valor) {
        Conta contaOrigem = contaRepository.findByNumero(numeroOrigem);
        Conta contaDestino = contaRepository.findByNumero(numeroDestino);
        if (contaOrigem.getSaldo() >= valor) {
            contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
            contaDestino.setSaldo(contaDestino.getSaldo() + valor);
            contaOrigem.getExtrato().add("Transferência de R$ " + valor + " para conta " + numeroDestino);
            contaDestino.getExtrato().add("Recebimento de R$ " + valor + " da conta " + numeroOrigem);
            contaRepository.save(contaOrigem);
            return contaRepository.save(contaDestino);
        }
        throw new RuntimeException("Saldo insuficiente");
    }

    public List<String> extrato(String numero) {
        Conta conta = contaRepository.findByNumero(numero);
        return conta.getExtrato();
    }
}
