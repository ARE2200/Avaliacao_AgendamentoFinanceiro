package com.avaliacao.app.service;

import com.avaliacao.app.exception.TaxaInvalidaException;
import com.avaliacao.app.repository.TransferenciaRepository;
import com.avaliacao.app.dto.TaxaDTO;
import com.avaliacao.app.model.*;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepository;

    public TransferenciaService(TransferenciaRepository transferenciaRepository) {
        this.transferenciaRepository = transferenciaRepository;
    }

    
    public Transferencia agendarTransferencia(Conta contaOrigem, Conta contaDestino, String valor, String dataTransferencia) throws TaxaInvalidaException {

        BigDecimal taxa = calcularTaxa(new BigDecimal(valor), LocalDate.parse(dataTransferencia)).getTaxa();
        Transferencia  transferencia = new Transferencia();
        transferencia.setValor(new BigDecimal(valor));
        transferencia.setContaDestino(contaDestino);
        transferencia.setContaOrigem(contaOrigem);
        transferencia.setTaxa(taxa);
        transferencia.setDataAgendamento(LocalDate.now());
        transferencia.setDataTransferencia(LocalDate.parse(dataTransferencia));
        return transferenciaRepository.save(transferencia);
    }

    public TaxaDTO calcularTaxa(BigDecimal valor, LocalDate dataTransferencia) throws TaxaInvalidaException {
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), dataTransferencia);
        BigDecimal taxaFixa = BigDecimal.ZERO;
        BigDecimal taxaPercentual = BigDecimal.ZERO;

        if (dias == 0) {
            taxaFixa = new BigDecimal("3");
            taxaPercentual = valor.multiply(new BigDecimal("0.025"));
        } else if (dias >= 1 && dias <= 10) {
            taxaFixa = new BigDecimal("12");
        } else if (dias >= 11 && dias <= 20) {
            taxaPercentual = valor.multiply(new BigDecimal("0.082"));
        } else if (dias >= 21 && dias <= 30) {
            taxaPercentual = valor.multiply(new BigDecimal("0.069"));
        } else if (dias >= 31 && dias <= 40) {
            taxaPercentual = valor.multiply(new BigDecimal("0.047"));
        } else if (dias >= 41 && dias <= 50) {
            taxaPercentual = valor.multiply(new BigDecimal("0.017"));
        }

        BigDecimal taxaTotal = taxaFixa.add(taxaPercentual);

        if (taxaPercentual.compareTo(BigDecimal.ZERO) == 0) {
            throw new TaxaInvalidaException("Taxa inválida para o período de transferência.");
        }

        return new TaxaDTO(taxaTotal);
    }

    public List<Transferencia> listarTodas() {
        return transferenciaRepository.findAll();
    }
}
