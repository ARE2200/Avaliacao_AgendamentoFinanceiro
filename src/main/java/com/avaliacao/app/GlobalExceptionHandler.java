package com.avaliacao.app;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.avaliacao.app.exception.TaxaInvalidaException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TaxaInvalidaException.class)
    public ResponseEntity<ErrorResponse> handleTaxaInvalidaException(TaxaInvalidaException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Taxa Inválida", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    public static class ErrorResponse {
        private String erro;
        private String mensagem;

        public ErrorResponse(String erro, String mensagem) {
            this.erro = erro;
            this.mensagem = mensagem;
        }

        public String getErro() {
            return erro;
        }

        public String getMensagem() {
            return mensagem;
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        logger.error("Erro inesperado", ex);
        return new ResponseEntity<>("Erro interno do servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }




}