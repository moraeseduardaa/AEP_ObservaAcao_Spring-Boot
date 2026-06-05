package com.aep.observacao.service;

import com.aep.observacao.enums.Categoria;
import com.aep.observacao.enums.Prioridade;
import com.aep.observacao.enums.Status;
import com.aep.observacao.model.AtualizarStatusRequest;
import com.aep.observacao.model.Solicitacao;
import com.aep.observacao.model.SolicitacaoRequest;
import com.aep.observacao.repository.SolicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SolicitacaoService {

    @Autowired
    private SolicitacaoRepository repository;

    public Solicitacao criar(SolicitacaoRequest request) {
        validarDescricao(request.getDescricao());
        validarLocalizacao(request.getLocalizacao());

        if (!request.isAnonimo()) {
            validarIdentificacao(request.getNomeRequerente(), request.getContatoRequerente());
        }

        String protocolo = gerarProtocolo();

        Solicitacao solicitacao = new Solicitacao(
                protocolo,
                request.getCategoria(),
                request.getDescricao(),
                request.getLocalizacao(),
                request.getPrioridade(),
                request.isAnonimo(),
                request.getNomeRequerente(),
                request.getContatoRequerente()
        );

        System.out.println("[LOG] Nova solicitacao | Protocolo: " + protocolo
                + " | Anonimo: " + request.isAnonimo()
                + " | Categoria: " + request.getCategoria());

        return repository.save(solicitacao);
    }

    public List<Solicitacao> listar() {
        return repository.findAll();
    }

    public Optional<Solicitacao> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Optional<Solicitacao> buscarPorProtocolo(String protocolo) {
        return repository.findByProtocolo(protocolo);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public List<Solicitacao> listarPorPrioridade(Prioridade prioridade) {
        return repository.findByPrioridade(prioridade);
    }

    public List<Solicitacao> listarPorCategoria(Categoria categoria) {
        return repository.findByCategoria(categoria);
    }

    public List<Solicitacao> listarPorLocalizacao(String local) {
        return repository.findByLocalizacaoIgnoreCase(local);
    }

    public List<Solicitacao> listarPorStatus(Status status) {
        return repository.findByStatus(status);
    }

    public List<Solicitacao> filaOrdenada() {
        return repository.findAllByOrderByPrioridadeDesc();
    }

    public Solicitacao atualizarStatus(String protocolo, AtualizarStatusRequest request) {
        Solicitacao solicitacao = repository.findByProtocolo(protocolo)
                .orElseThrow(() -> new IllegalArgumentException("Solicitacao nao encontrada: " + protocolo));

        if (!fluxoValido(solicitacao.getStatus(), request.getNovoStatus())) {
            throw new IllegalStateException(
                    "Mudanca invalida: " + solicitacao.getStatus() + " -> " + request.getNovoStatus()
            );
        }

        validarComentario(request.getComentario());
        validarResponsavel(request.getResponsavel());

        solicitacao.atualizarStatus(request.getNovoStatus(), request.getComentario(), request.getResponsavel());

        return repository.save(solicitacao);
    }

    private void validarDescricao(String desc) {
        if (desc == null || desc.isBlank())
            throw new IllegalArgumentException("Descricao e obrigatoria");
    }

    private void validarLocalizacao(String loc) {
        if (loc == null || loc.isBlank())
            throw new IllegalArgumentException("Localizacao e obrigatoria");
    }

    private void validarIdentificacao(String nome, String contato) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome e obrigatorio para solicitacoes identificadas.");
        if (contato == null || contato.isBlank())
            throw new IllegalArgumentException("Contato e obrigatorio para solicitacoes identificadas.");
    }

    private void validarComentario(String comentario) {
        if (comentario == null || comentario.isBlank())
            throw new IllegalArgumentException("Comentario e obrigatorio ao atualizar status.");
    }

    private void validarResponsavel(String responsavel) {
        if (responsavel == null || responsavel.isBlank())
            throw new IllegalArgumentException("Nome do responsavel e obrigatorio.");
    }

    private boolean fluxoValido(Status atual, Status novo) {
        return switch (atual) {
            case ABERTO      -> novo == Status.TRIAGEM;
            case TRIAGEM     -> novo == Status.EM_EXECUCAO;
            case EM_EXECUCAO -> novo == Status.RESOLVIDO;
            case RESOLVIDO   -> false;
        };
    }

    private String gerarProtocolo() {
        Long ultimoId = repository.buscarUltimoId();

        long proximoNumero = (ultimoId == null) ? 1 : ultimoId + 1;

        return String.format("%06d", proximoNumero);
    }
}
