package com.aep.observacao.model;

import com.aep.observacao.enums.Categoria;
import com.aep.observacao.enums.Prioridade;
import com.aep.observacao.enums.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_solicitacao")
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String protocolo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Categoria categoria;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String localizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    private boolean anonimo;

    private String nomeRequerente;
    private String contatoRequerente;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime dataCriacao;

    private int prazoAlvo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "solicitacao_id")
    private List<HistoricoStatus> historico = new ArrayList<>();

    public Solicitacao() {}

    public Solicitacao(String protocolo, Categoria categoria, String descricao,
                       String localizacao, Prioridade prioridade,
                       boolean anonimo, String nomeRequerente, String contatoRequerente) {
        this.protocolo = protocolo;
        this.categoria = categoria;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.prioridade = prioridade;
        this.anonimo = anonimo;
        this.nomeRequerente = anonimo ? null : nomeRequerente;
        this.contatoRequerente = anonimo ? null : contatoRequerente;
        this.status = Status.ABERTO;
        this.dataCriacao = LocalDateTime.now();
        this.prazoAlvo = calcularPrazo(prioridade);
        this.historico.add(new HistoricoStatus(Status.ABERTO, "Solicitacao criada", "Sistema"));
    }

    private int calcularPrazo(Prioridade prioridade) {
        return switch (prioridade) {
            case ALTA  -> 24;
            case MEDIA -> 48;
            case BAIXA -> 72;
        };
    }

    public void atualizarStatus(Status novoStatus, String comentario, String responsavel) {
        this.status = novoStatus;
        this.historico.add(new HistoricoStatus(novoStatus, comentario, responsavel));
    }

    public boolean estaAtrasado() {
        if (status == Status.RESOLVIDO) return false;
        long horasPassadas = java.time.Duration.between(dataCriacao, LocalDateTime.now()).toHours();
        return horasPassadas > prazoAlvo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProtocolo() { return protocolo; }
    public void setProtocolo(String protocolo) { this.protocolo = protocolo; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

    public boolean isAnonimo() { return anonimo; }
    public void setAnonimo(boolean anonimo) { this.anonimo = anonimo; }

    public String getNomeRequerente() { return nomeRequerente; }
    public void setNomeRequerente(String nomeRequerente) { this.nomeRequerente = nomeRequerente; }

    public String getContatoRequerente() { return contatoRequerente; }
    public void setContatoRequerente(String contatoRequerente) { this.contatoRequerente = contatoRequerente; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public int getPrazoAlvo() { return prazoAlvo; }
    public void setPrazoAlvo(int prazoAlvo) { this.prazoAlvo = prazoAlvo; }

    public List<HistoricoStatus> getHistorico() { return historico; }
    public void setHistorico(List<HistoricoStatus> historico) { this.historico = historico; }
}
