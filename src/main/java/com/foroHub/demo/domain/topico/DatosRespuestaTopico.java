package com.foroHub.demo.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(Long id,
                                   String titulo,
                                   String mensaje,
                                   LocalDateTime fecha_creacion,
                                   Long status,
                                   String autor,
                                   String curso) {
    public DatosRespuestaTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFecha_creacion(), topico.getStatus(), topico.getAutor(), topico.getCurso());
    }
}
