package com.avaliacao.app.controller;

import com.avaliacao.app.exception.TaxaInvalidaException;
import com.avaliacao.app.model.Transferencia;
import com.avaliacao.app.service.TransferenciaService;
import com.avaliacao.app.dto.DadosCalculoTaxa;
import com.avaliacao.app.dto.TaxaDTO;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {
    private static final Logger logger = LoggerFactory.getLogger(TransferenciaController.class);

    private final TransferenciaService transferenciaService;

    public TransferenciaController(TransferenciaService transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    @PostMapping("/calcular-taxa")
    public ResponseEntity<TaxaDTO> calcularTaxa(@RequestBody DadosCalculoTaxa dados) {
        try {
            TaxaDTO taxa = transferenciaService.calcularTaxa(dados.getValor(), dados.getDataTransferencia());
            return ResponseEntity.ok(taxa);
        } catch (TaxaInvalidaException e) {
            return ResponseEntity.badRequest().body(new TaxaDTO(BigDecimal.ZERO)); // Use um objeto de resposta apropriado aqui
        }
    }


    @GetMapping
    public ResponseEntity<List<Transferencia>> listarTransferencias() {
        return ResponseEntity.ok(transferenciaService.listarTodas());
    }

    @PostMapping("/agendar")
    public ResponseEntity<Transferencia> agendarTransferencia(@RequestBody Transferencia transferencia) {
        logger.info("Agendando transferência: {}", transferencia);
        try {
            Transferencia result = transferenciaService.agendarTransferencia(transferencia.getContaOrigem(),transferencia.getContaDestino(),transferencia.getValor().toString(),transferencia.getDataTransferencia().toString());
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            logger.error("Erro ao agendar transferência", ex);
            throw ex;
        }
    }
}