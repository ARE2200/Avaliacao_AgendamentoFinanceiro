package com.avaliacao.app.dto;

import java.math.BigDecimal;

public class TaxaDTO {
    private BigDecimal taxa;

    public TaxaDTO() {
    }

    public TaxaDTO(BigDecimal taxa) {
        this.taxa = taxa;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }
}
