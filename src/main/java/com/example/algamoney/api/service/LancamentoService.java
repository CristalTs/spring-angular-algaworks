package com.example.algamoney.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativiException;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Lancamento salvar(Lancamento lancamento) {

        final Pessoa pessoa = this.pessoaRepository.getOne(lancamento.getPessoa().getCodigo());
        if (pessoa == null || !pessoa.getAtivo()) {
            throw new PessoaInexistenteOuInativiException();
        }
        return this.lancamentoRepository.save(lancamento);
    }

}
