package com.avaliacao.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Transferencia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Conta contaOrigem;
    
    @ManyToOne(cascade = CascadeType.ALL)
    private Conta contaDestino;
    

    private BigDecimal valor;
    private BigDecimal taxa;
    private LocalDate dataTransferencia;
    private LocalDate dataAgendamento = LocalDate.now();

    public Long getId() { return id; }
    public Conta getContaOrigem() { return contaOrigem; }
    public Conta getContaDestino() { return contaDestino; }
    public BigDecimal getValor() { return valor; }
    public BigDecimal getTaxa() { return taxa; }
    public LocalDate getDataTransferencia() { return dataTransferencia; }
    public LocalDate getDataAgendamento() { return dataAgendamento; }

    public void setId(Long id) { this.id = id; }
    public void setContaOrigem(Conta contaOrigem) { this.contaOrigem = contaOrigem; }
    public void setContaDestino(Conta contaDestino) { this.contaDestino = contaDestino; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public void setTaxa(BigDecimal taxa) { this.taxa = taxa; }
    public void setDataTransferencia(LocalDate dataTransferencia) { this.dataTransferencia = dataTransferencia; }
    public void setDataAgendamento(LocalDate dataAgendamento) { this.dataAgendamento = dataAgendamento; }
}