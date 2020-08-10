package com.example.algamoney.api.repository.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class LancamentoFilter {

    private String descricao;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoDe;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoAte;

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataVencimentoDe() {
        return this.dataVencimentoDe;
    }

    public void setDataVencimentoDe(LocalDate datavencimentoDe) {
        this.dataVencimentoDe = datavencimentoDe;
    }

    public LocalDate getDataVencimentoAte() {
        return this.dataVencimentoAte;
    }

    public void setDataVencimentoAte(LocalDate datavencimentoAte) {
        this.dataVencimentoAte = datavencimentoAte;
    }

}
