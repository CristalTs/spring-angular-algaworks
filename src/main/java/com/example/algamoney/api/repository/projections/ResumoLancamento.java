package com.example.algamoney.api.repository.projections;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.algamoney.api.model.TipoLancamento;

public class ResumoLancamento {

    private Long codigo;
    private String descricao;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private TipoLancamento tipo;
    private String categoria;
    private String pessoa;

    public ResumoLancamento(Long codigo, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
            BigDecimal valor, TipoLancamento tipo, String categoria, String pessoa) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
        this.pessoa = pessoa;
    }

    public Long getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataVencimento() {
        return this.dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return this.dataPagamento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoLancamento getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(String pessoa) {
        this.pessoa = pessoa;
    }

}
