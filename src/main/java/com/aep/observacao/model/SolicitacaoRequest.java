package com.aep.observacao.model;

import com.aep.observacao.enums.Categoria;
import com.aep.observacao.enums.Prioridade;

public class SolicitacaoRequest {

    private String descricao;
    private String localizacao;
    private Categoria categoria;
    private Prioridade prioridade;
    private boolean anonimo;
    private String nomeRequerente;
    private String contatoRequerente;

    public SolicitacaoRequest() {}

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

    public boolean isAnonimo() { return anonimo; }
    public void setAnonimo(boolean anonimo) { this.anonimo = anonimo; }

    public String getNomeRequerente() { return nomeRequerente; }
    public void setNomeRequerente(String nomeRequerente) { this.nomeRequerente = nomeRequerente; }

    public String getContatoRequerente() { return contatoRequerente; }
    public void setContatoRequerente(String contatoRequerente) { this.contatoRequerente = contatoRequerente; }
}
