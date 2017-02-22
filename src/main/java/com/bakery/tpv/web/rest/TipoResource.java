package com.bakery.tpv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bakery.tpv.domain.Tipo;

import com.bakery.tpv.repository.TipoRepository;
import com.bakery.tpv.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tipo.
 */
@RestController
@RequestMapping("/api")
public class TipoResource {

    private final Logger log = LoggerFactory.getLogger(TipoResource.class);

    private static final String ENTITY_NAME = "tipo";
        
    private final TipoRepository tipoRepository;

    public TipoResource(TipoRepository tipoRepository) {
        this.tipoRepository = tipoRepository;
    }

    /**
     * POST  /tipos : Create a new tipo.
     *
     * @param tipo the tipo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipo, or with status 400 (Bad Request) if the tipo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipos")
    @Timed
    public ResponseEntity<Tipo> createTipo(@RequestBody Tipo tipo) throws URISyntaxException {
        log.debug("REST request to save Tipo : {}", tipo);
        if (tipo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tipo cannot already have an ID")).body(null);
        }
        Tipo result = tipoRepository.save(tipo);
        return ResponseEntity.created(new URI("/api/tipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipos : Updates an existing tipo.
     *
     * @param tipo the tipo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipo,
     * or with status 400 (Bad Request) if the tipo is not valid,
     * or with status 500 (Internal Server Error) if the tipo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipos")
    @Timed
    public ResponseEntity<Tipo> updateTipo(@RequestBody Tipo tipo) throws URISyntaxException {
        log.debug("REST request to update Tipo : {}", tipo);
        if (tipo.getId() == null) {
            return createTipo(tipo);
        }
        Tipo result = tipoRepository.save(tipo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipos : get all the tipos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipos in body
     */
    @GetMapping("/tipos")
    @Timed
    public List<Tipo> getAllTipos() {
        log.debug("REST request to get all Tipos");
        List<Tipo> tipos = tipoRepository.findAll();
        return tipos;
    }

    /**
     * GET  /tipos/:id : get the "id" tipo.
     *
     * @param id the id of the tipo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipo, or with status 404 (Not Found)
     */
    @GetMapping("/tipos/{id}")
    @Timed
    public ResponseEntity<Tipo> getTipo(@PathVariable Long id) {
        log.debug("REST request to get Tipo : {}", id);
        Tipo tipo = tipoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipo));
    }

    /**
     * DELETE  /tipos/:id : delete the "id" tipo.
     *
     * @param id the id of the tipo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipo(@PathVariable Long id) {
        log.debug("REST request to delete Tipo : {}", id);
        tipoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
