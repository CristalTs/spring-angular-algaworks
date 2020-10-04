package com.example.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Lancamento salvar(Lancamento lancamento) {

        final Pessoa pessoa = this.pessoaRepository.getOne(lancamento.getPessoa().getCodigo());
        if (pessoa == null || !pessoa.getAtivo()) {
            throw new PessoaInexistenteOuInativaException();
        }
        return this.lancamentoRepository.save(lancamento);
    }

    public Lancamento atualizar(Long codigo, Lancamento lancamento) {
        final Lancamento lancamentoSalvo = this.buscarLancamentoExistente(codigo);
        if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
            this.validarPessoa(lancamento);
        }

        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

        return this.lancamentoRepository.save(lancamentoSalvo);
    }

    private void validarPessoa(Lancamento lancamento) {
        Optional<Pessoa> pessoa = null;
        if (lancamento.getPessoa().getCodigo() != null) {
            pessoa = this.pessoaRepository.findById(lancamento.getPessoa().getCodigo());
        }

        if (!pessoa.isPresent() || pessoa.get().isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }
    }

    private Lancamento buscarLancamentoExistente(Long codigo) {
        final Optional<Lancamento> lancamentoSalvo = this.lancamentoRepository.findById(codigo);
        if (!lancamentoSalvo.isPresent()) {
            throw new IllegalArgumentException();
        }
        return lancamentoSalvo.get();
    }

}
