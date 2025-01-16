package com.foroHub.demo.controller;


import com.foroHub.demo.domain.topico.DatosActualizarTopico;
import com.foroHub.demo.domain.topico.DatosListadoTopico;
import com.foroHub.demo.domain.topico.DatosRegistroTopico;
import com.foroHub.demo.domain.topico.DatosRespuestaTopico;
import com.foroHub.demo.domain.topico.Topico;
import com.foroHub.demo.domain.topico.TopicoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicosController {

    @Autowired
    TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrarTopicos(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriBuilder) {
        boolean existeDuplicado = topicoRepository.existsByTituloAndMensaje(datosRegistroTopico.titulo(), datosRegistroTopico.mensaje());
        if (existeDuplicado) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El tópico y mensaje ya existe");
        }
        Topico topico = topicoRepository.save(new Topico(datosRegistroTopico));

        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestaTopico);
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<DatosListadoTopico>> listadoTopico() {
        List<Topico> topicos = topicoRepository.findAll();
        List<DatosListadoTopico> listado = topicos.stream()
                .map(DatosListadoTopico::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listado);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosListadoTopico> listadoTopicoPorId(@PathVariable Long id) {
        Optional<Topico> Optopicos = topicoRepository.findById(id);
        if (Optopicos.isPresent()) {
            return ResponseEntity.ok(new DatosListadoTopico(Optopicos.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            topico.actualizarDatos(datosActualizarTopico);
            return ResponseEntity.ok(new DatosRespuestaTopico(topico));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
