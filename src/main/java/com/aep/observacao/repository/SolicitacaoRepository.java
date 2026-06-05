package com.aep.observacao.repository;

import com.aep.observacao.enums.Categoria;
import com.aep.observacao.enums.Prioridade;
import com.aep.observacao.enums.Status;
import com.aep.observacao.model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    Optional<Solicitacao> findByProtocolo(String protocolo);
    @Query("SELECT MAX(s.id) FROM Solicitacao s")
    Long buscarUltimoId();

    List<Solicitacao> findByPrioridade(Prioridade prioridade);

    List<Solicitacao> findByCategoria(Categoria categoria);

    List<Solicitacao> findByLocalizacaoIgnoreCase(String localizacao);

    List<Solicitacao> findByStatus(Status status);

    List<Solicitacao> findAllByOrderByPrioridadeDesc();
}
