package com.example.algamoney.api.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        final String mensagemUsuario = this.messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        final String mensagemDesenvolvedor = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        final List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

        return this.handleExceptionInternal(ex, erros, headers, status, request);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    protected ResponseEntity<Object> handleHttpMessageNotReadable(DataIntegrityViolationException ex, WebRequest request) {

        final String mensagemUsuario = this.messageSource.getMessage("recurso.operacao-nao-permitida", null,
                LocaleContextHolder.getLocale());
        final String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
        final List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));

        return this.handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        final List<Erro> erros = this.criarListaDeErros(ex.getBindingResult());

        return this.handleExceptionInternal(ex, erros, headers, status, request);
    }

    private List<Erro> criarListaDeErros(BindingResult bindResult) {
        final List<Erro> erros = new ArrayList<>();

        for (final FieldError fieldError : bindResult.getFieldErrors()) {
            final String mensagemUsuario = this.messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            final String mensagemDesenvolvedor = fieldError.toString();

            erros.add(new Erro(mensagemUsuario, mensagemDesenvolvedor));
        }

        return erros;
    }

    public static class Erro {

        private final String mensagemUsuario;
        private final String mensagemDesenvolvedor;

        public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
            this.mensagemUsuario = mensagemUsuario;
            this.mensagemDesenvolvedor = mensagemDesenvolvedor;
        }

        public String getMensagemUsuario() {
            return this.mensagemUsuario;
        }

        public String getMensagemDesenvolvedor() {
            return this.mensagemDesenvolvedor;
        }

    }

}
