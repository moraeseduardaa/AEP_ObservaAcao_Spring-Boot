package com.aep.observacao.model;

import com.aep.observacao.enums.Status;

public class AtualizarStatusRequest {

    private Status novoStatus;
    private String comentario;
    private String responsavel;

    public AtualizarStatusRequest() {}

    public Status getNovoStatus() { return novoStatus; }
    public void setNovoStatus(Status novoStatus) { this.novoStatus = novoStatus; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }
}
