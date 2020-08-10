package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa atualizar(long codigo, Pessoa pessoa) {
        Pessoa pessoaSalva = this.buscarPessoaPeloCodigo(codigo);
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
        return this.pessoaRepository.save(pessoaSalva);
    }

    public void atualizarAtivo(Long codigo, Boolean ativo) {
        Pessoa pessoaSalva = this.buscarPessoaPeloCodigo(codigo);
        pessoaSalva.setAtivo(ativo);
        this.pessoaRepository.save(pessoaSalva);

    }

    private Pessoa buscarPessoaPeloCodigo(long codigo) {
        Pessoa pessoaSalva = this.pessoaRepository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
        if (pessoaSalva == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return pessoaSalva;
    }

}
