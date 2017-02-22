package com.bakery.tpv.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bakery.tpv.domain.LineaOferta;

import com.bakery.tpv.repository.LineaOfertaRepository;
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
 * REST controller for managing LineaOferta.
 */
@RestController
@RequestMapping("/api")
public class LineaOfertaResource {

    private final Logger log = LoggerFactory.getLogger(LineaOfertaResource.class);

    private static final String ENTITY_NAME = "lineaOferta";
        
    private final LineaOfertaRepository lineaOfertaRepository;

    public LineaOfertaResource(LineaOfertaRepository lineaOfertaRepository) {
        this.lineaOfertaRepository = lineaOfertaRepository;
    }

    /**
     * POST  /linea-ofertas : Create a new lineaOferta.
     *
     * @param lineaOferta the lineaOferta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lineaOferta, or with status 400 (Bad Request) if the lineaOferta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/linea-ofertas")
    @Timed
    public ResponseEntity<LineaOferta> createLineaOferta(@RequestBody LineaOferta lineaOferta) throws URISyntaxException {
        log.debug("REST request to save LineaOferta : {}", lineaOferta);
        if (lineaOferta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new lineaOferta cannot already have an ID")).body(null);
        }
        LineaOferta result = lineaOfertaRepository.save(lineaOferta);
        return ResponseEntity.created(new URI("/api/linea-ofertas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /linea-ofertas : Updates an existing lineaOferta.
     *
     * @param lineaOferta the lineaOferta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lineaOferta,
     * or with status 400 (Bad Request) if the lineaOferta is not valid,
     * or with status 500 (Internal Server Error) if the lineaOferta couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/linea-ofertas")
    @Timed
    public ResponseEntity<LineaOferta> updateLineaOferta(@RequestBody LineaOferta lineaOferta) throws URISyntaxException {
        log.debug("REST request to update LineaOferta : {}", lineaOferta);
        if (lineaOferta.getId() == null) {
            return createLineaOferta(lineaOferta);
        }
        LineaOferta result = lineaOfertaRepository.save(lineaOferta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lineaOferta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /linea-ofertas : get all the lineaOfertas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lineaOfertas in body
     */
    @GetMapping("/linea-ofertas")
    @Timed
    public List<LineaOferta> getAllLineaOfertas() {
        log.debug("REST request to get all LineaOfertas");
        List<LineaOferta> lineaOfertas = lineaOfertaRepository.findAll();
        return lineaOfertas;
    }

    /**
     * GET  /linea-ofertas/:id : get the "id" lineaOferta.
     *
     * @param id the id of the lineaOferta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lineaOferta, or with status 404 (Not Found)
     */
    @GetMapping("/linea-ofertas/{id}")
    @Timed
    public ResponseEntity<LineaOferta> getLineaOferta(@PathVariable Long id) {
        log.debug("REST request to get LineaOferta : {}", id);
        LineaOferta lineaOferta = lineaOfertaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lineaOferta));
    }

    /**
     * DELETE  /linea-ofertas/:id : delete the "id" lineaOferta.
     *
     * @param id the id of the lineaOferta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/linea-ofertas/{id}")
    @Timed
    public ResponseEntity<Void> deleteLineaOferta(@PathVariable Long id) {
        log.debug("REST request to delete LineaOferta : {}", id);
        lineaOfertaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
