package com.aep.observacao.model;

import com.aep.observacao.enums.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "tb_historico_status")
public class HistoricoStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String comentario;

    private String responsavel;

    public HistoricoStatus() {}

    public HistoricoStatus(Status status, String comentario, String responsavel) {
        this.data = LocalDateTime.now();
        this.status = status;
        this.comentario = comentario;
        this.responsavel = responsavel;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return data.format(fmt)
                + " | " + status
                + " | Responsavel: " + responsavel
                + " | " + comentario;
    }
}
