// DadosCalculoTaxa.java
package com.avaliacao.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DadosCalculoTaxa {
    private BigDecimal valor;
    private LocalDate dataTransferencia;

    public DadosCalculoTaxa() {
    }

    public DadosCalculoTaxa(BigDecimal valor, LocalDate dataTransferencia) {
        this.valor = valor;
        this.dataTransferencia = dataTransferencia;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getDataTransferencia() {
        return dataTransferencia;
    }

    public void setDataTransferencia(LocalDate dataTransferencia) {
        this.dataTransferencia = dataTransferencia;
    }
}
