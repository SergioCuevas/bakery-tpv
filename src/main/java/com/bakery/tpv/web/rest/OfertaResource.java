package com.bakery.tpv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bakery.tpv.domain.Oferta;

import com.bakery.tpv.repository.OfertaRepository;
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
 * REST controller for managing Oferta.
 */
@RestController
@RequestMapping("/api")
public class OfertaResource {

    private final Logger log = LoggerFactory.getLogger(OfertaResource.class);

    private static final String ENTITY_NAME = "oferta";
        
    private final OfertaRepository ofertaRepository;

    public OfertaResource(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    /**
     * POST  /ofertas : Create a new oferta.
     *
     * @param oferta the oferta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new oferta, or with status 400 (Bad Request) if the oferta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ofertas")
    @Timed
    public ResponseEntity<Oferta> createOferta(@RequestBody Oferta oferta) throws URISyntaxException {
        log.debug("REST request to save Oferta : {}", oferta);
        if (oferta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new oferta cannot already have an ID")).body(null);
        }
        Oferta result = ofertaRepository.save(oferta);
        return ResponseEntity.created(new URI("/api/ofertas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ofertas : Updates an existing oferta.
     *
     * @param oferta the oferta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated oferta,
     * or with status 400 (Bad Request) if the oferta is not valid,
     * or with status 500 (Internal Server Error) if the oferta couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ofertas")
    @Timed
    public ResponseEntity<Oferta> updateOferta(@RequestBody Oferta oferta) throws URISyntaxException {
        log.debug("REST request to update Oferta : {}", oferta);
        if (oferta.getId() == null) {
            return createOferta(oferta);
        }
        Oferta result = ofertaRepository.save(oferta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, oferta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ofertas : get all the ofertas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ofertas in body
     */
    @GetMapping("/ofertas")
    @Timed
    public List<Oferta> getAllOfertas() {
        log.debug("REST request to get all Ofertas");
        List<Oferta> ofertas = ofertaRepository.findAll();
        return ofertas;
    }

    /**
     * GET  /ofertas/:id : get the "id" oferta.
     *
     * @param id the id of the oferta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the oferta, or with status 404 (Not Found)
     */
    @GetMapping("/ofertas/{id}")
    @Timed
    public ResponseEntity<Oferta> getOferta(@PathVariable Long id) {
        log.debug("REST request to get Oferta : {}", id);
        Oferta oferta = ofertaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(oferta));
    }

    /**
     * DELETE  /ofertas/:id : delete the "id" oferta.
     *
     * @param id the id of the oferta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ofertas/{id}")
    @Timed
    public ResponseEntity<Void> deleteOferta(@PathVariable Long id) {
        log.debug("REST request to delete Oferta : {}", id);
        ofertaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
