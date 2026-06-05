package com.aep.observacao.controller;

import com.aep.observacao.enums.Categoria;
import com.aep.observacao.enums.Prioridade;
import com.aep.observacao.enums.Status;
import com.aep.observacao.model.AtualizarStatusRequest;
import com.aep.observacao.model.Solicitacao;
import com.aep.observacao.model.SolicitacaoRequest;
import com.aep.observacao.service.SolicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService service;

    @PostMapping
    public ResponseEntity<Solicitacao> criar(@RequestBody SolicitacaoRequest request) {
        try {
            Solicitacao criada = service.criar(request);
            URI uri = URI.create("/solicitacoes/" + criada.getId());
            return ResponseEntity.created(uri).body(criada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Solicitacao>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitacao> buscarPorId(@PathVariable Long id) {
        Optional<Solicitacao> resultado = service.buscarPorId(id);
        return resultado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/protocolo/{protocolo}")
    public ResponseEntity<Solicitacao> buscarPorProtocolo(@PathVariable String protocolo) {
        Optional<Solicitacao> resultado = service.buscarPorProtocolo(protocolo);
        return resultado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{protocolo}/status")
    public ResponseEntity<Solicitacao> atualizarStatus(
            @PathVariable String protocolo,
            @RequestBody AtualizarStatusRequest request) {
        try {
            Solicitacao atualizada = service.atualizarStatus(protocolo, request);
            return ResponseEntity.ok(atualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/filtro/prioridade")
    public ResponseEntity<List<Solicitacao>> filtrarPorPrioridade(@RequestParam Prioridade valor) {
        return ResponseEntity.ok(service.listarPorPrioridade(valor));
    }

    @GetMapping("/filtro/categoria")
    public ResponseEntity<List<Solicitacao>> filtrarPorCategoria(@RequestParam Categoria valor) {
        return ResponseEntity.ok(service.listarPorCategoria(valor));
    }

    @GetMapping("/filtro/localizacao")
    public ResponseEntity<List<Solicitacao>> filtrarPorLocalizacao(@RequestParam String valor) {
        return ResponseEntity.ok(service.listarPorLocalizacao(valor));
    }

    @GetMapping("/filtro/status")
    public ResponseEntity<List<Solicitacao>> filtrarPorStatus(@RequestParam Status valor) {
        return ResponseEntity.ok(service.listarPorStatus(valor));
    }

    @GetMapping("/fila")
    public ResponseEntity<List<Solicitacao>> fila() {
        return ResponseEntity.ok(service.filaOrdenada());
    }
}
