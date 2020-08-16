package com.example.algamoney.api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.example.algamoney.api.model.Categoria_;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Lancamento_;
import com.example.algamoney.api.model.Pessoa_;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import com.example.algamoney.api.repository.projections.ResumoLancamento;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        final CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        final Root<Lancamento> root = criteria.from(Lancamento.class);

        final Predicate[] predicates = this.criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        final TypedQuery<Lancamento> query = this.manager.createQuery(criteria);
        this.adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, this.total(lancamentoFilter));
    }

    @Override
    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {

        final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        final CriteriaQuery<ResumoLancamento> criteria = builder.createQuery(ResumoLancamento.class);
        final Root<Lancamento> root = criteria.from(Lancamento.class);

        criteria.select(builder.construct(ResumoLancamento.class, root.get(Lancamento_.codigo), root.get(Lancamento_.descricao),
                root.get(Lancamento_.dataVencimento), root.get(Lancamento_.dataPagamento), root.get(Lancamento_.valor),
                root.get(Lancamento_.tipo), root.get(Lancamento_.categoria).get(Categoria_.nome),
                root.get(Lancamento_.pessoa).get(Pessoa_.nome)));

        final Predicate[] predicates = this.criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        final TypedQuery<ResumoLancamento> query = this.manager.createQuery(criteria);
        this.adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, this.total(lancamentoFilter));
    }

    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
            Root<Lancamento> root) {
        final List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
            predicates.add(builder.like(
                    builder.lower(root.get(Lancamento_.descricao)), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
        }

        if (lancamentoFilter.getDataVencimentoDe() != null) {
            predicates.add(
                    builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoDe()));
        }

        if (lancamentoFilter.getDataVencimentoAte() != null) {
            predicates.add(
                    builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), lancamentoFilter.getDataVencimentoAte()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        final int paginaAtual = pageable.getPageNumber();
        final int totalRegistrosPorPagina = pageable.getPageSize();
        final int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(LancamentoFilter lancamentoFilter) {
        final CriteriaBuilder builder = this.manager.getCriteriaBuilder();
        final CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        final Root<Lancamento> root = criteria.from(Lancamento.class);

        final Predicate[] predicates = this.criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);

        criteria.select(builder.count(root));
        return this.manager.createQuery(criteria).getSingleResult();
    }

}
