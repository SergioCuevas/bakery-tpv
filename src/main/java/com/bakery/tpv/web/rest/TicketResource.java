package com.bakery.tpv.web.rest;

import com.bakery.tpv.domain.Oferta;
import com.bakery.tpv.domain.Producto;
import com.bakery.tpv.repository.OfertaRepository;
import com.bakery.tpv.repository.ProductoRepository;
import com.codahale.metrics.annotation.Timed;
import com.bakery.tpv.domain.Ticket;

import com.bakery.tpv.repository.TicketRepository;

import com.bakery.tpv.service.dto.TicketsDiaDTO;
import com.bakery.tpv.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ticket.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TicketResource {

    private final Logger log = LoggerFactory.getLogger(TicketResource.class);

    private static final String ENTITY_NAME = "ticket";

    private final TicketRepository ticketRepository;
    private final ProductoRepository productoRepository;
    private final OfertaRepository ofertaRepository;

    public TicketResource(TicketRepository ticketRepository, ProductoRepository productoRepository, OfertaRepository ofertaRepository) {
        this.ticketRepository = ticketRepository;
        this.productoRepository = productoRepository;
        this.ofertaRepository = ofertaRepository;
    }

    /**
     * POST  /tickets : Create a new ticket.
     *
     * @param ticket the ticket to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ticket, or with status 400 (Bad Request) if the ticket has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tickets")
    @Timed
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) throws URISyntaxException {
        log.debug("REST request to save Ticket : {}", ticket);
        if (ticket.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ticket cannot already have an ID")).body(null);
        }
        ticket.setFecha(ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.systemDefault()));
        Ticket result = ticketRepository.save(ticket);

        return ResponseEntity.created(new URI("/api/tickets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /tickets : Updates an existing ticket.
     *
     * @param ticket the ticket to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ticket,
     * or with status 400 (Bad Request) if the ticket is not valid,
     * or with status 500 (Internal Server Error) if the ticket couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */


    @PutMapping("/tickets")
    @Timed
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket) throws URISyntaxException {
        log.debug("REST request to update Ticket : {}", ticket);
        if (ticket.getId() == null) {
            return createTicket(ticket);
        }
        Ticket result = ticketRepository.save(ticket);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ticket.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/tickets/{id}/producto/{idProducto}/cantidad/{cantidad}")
    @Timed
    public ResponseEntity<Ticket> updateTicketDeleteProduct(@PathVariable long idProducto, @PathVariable long id, @PathVariable int cantidad) throws URISyntaxException {

        log.debug("REST request to add Producto : {}", idProducto);


        Ticket t = ticketRepository.findOne(id);
        Producto p = productoRepository.findOne(idProducto);

        for(int i = t.getProductos().size()-1; i>=0;i--){
            if(t.getProductos().get(i).getId()==p.getId()&&cantidad>0){
                t.getProductos().remove(i);
                t.setCantidad(t.getCantidad().subtract(p.getPrecio()));
                cantidad--;
            }
        }
        Ticket result = ticketRepository.save(t);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, t.getId().toString()))
            .body(result);
    }

    @DeleteMapping("/tickets/{id}/oferta/{idOferta}")
    @Timed
    public ResponseEntity<Ticket> updateTicketDeleteOferta(@PathVariable long idOferta, @PathVariable long id) throws URISyntaxException {

        log.debug("REST request to add Producto : {}", idOferta);


        Ticket t = ticketRepository.findOne(id);
        Oferta o = ofertaRepository.findOne(idOferta);

        t.getOfertas().remove(o);
        t.setCantidad(t.getCantidad().subtract(o.getPrecio()));
        Ticket result = ticketRepository.save(t);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, t.getId().toString()))
            .body(result);
    }
    //Función para añadir un producto a un ticket, primero busca el ticket con el id del path,
    // y a ese ticket se le añade el producto. Finalmente tambien se le suma a la cantidad total
    // del ticket el precio del producto
    ///tickets/{id}/productos/
    @PutMapping("/tickets/{id}/producto/{idProducto}")
    @Timed
    public ResponseEntity<Ticket> updateTicketAddProduct(@PathVariable long idProducto, @PathVariable long id) throws URISyntaxException {

        log.debug("REST request to add Producto : {}", idProducto);


        Ticket t = ticketRepository.findOne(id);
        Producto p = productoRepository.findOne(idProducto);
        t.addProducto(p);

        BigDecimal cantidadSumada = t.getCantidad().add(p.getPrecio());

        t.setCantidad(cantidadSumada);
        Ticket result = ticketRepository.save(t);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, t.getId().toString()))
            .body(result);
    }

    @PutMapping("/tickets/{id}/mesa/{mesa}")
    @Timed
    public ResponseEntity<Ticket> updateTicketAddProduct(@PathVariable int mesa, @PathVariable long id) throws URISyntaxException {
        Ticket t = ticketRepository.findOne(id);
        t.setMesa(mesa);
        Ticket result = ticketRepository.save(t);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, t.getId().toString()))
            .body(result);
    }

    //Función para añadir una oferta a un ticket, primero busca el ticket con el id del path,
    // y a ese ticket se le ae la oferta
    @PutMapping("/tickets/oferta/add/{id}")
    @Timed
    public ResponseEntity<Ticket> updateTicketAddOffer(@RequestBody Oferta oferta, @PathVariable long id) throws URISyntaxException {
        Ticket t = ticketRepository.findOne(id);
        t.addOferta(oferta);
        t.setCantidad(t.getCantidad().add(oferta.getPrecio()));
        Ticket result = ticketRepository.save(t);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, t.getId().toString()))
            .body(result);
    }


    @PutMapping("/tickets/{id}/calculadora/{valor:.+}")
    @Timed
    public ResponseEntity<Ticket> updateTicketCalculator(@PathVariable long id, @PathVariable String valor) throws URISyntaxException {
        BigDecimal v = new BigDecimal(Double.parseDouble(valor));
        Ticket t = ticketRepository.findOne(id);
        t.setCantidad(t.getCantidad().add(v));
        Ticket result = ticketRepository.save(t);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, t.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tickets : get all the tickets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tickets in body
     */
    @GetMapping("/tickets")
    @Timed
    public List<Ticket> getAllTickets() {
        log.debug("REST request to get all Tickets");
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets;
    }

    @GetMapping("/tickets/abiertos")
    @Timed
    public List<Ticket> getAllTicketsOpen() {
        log.debug("REST request to get all Tickets");
        List<Ticket> tickets = ticketRepository.findAllWithEagerRelationships();
        return tickets;
    }

    /* Devuelve una lista con los productos de un ticket a traves de una id*/
    @GetMapping("/tickets/productos/{id}")
    @Timed
    public List<Producto> getAllProductosByTicket(@PathVariable Long id) {
        List<Producto> productos = ticketRepository.findProductsByTicketId(id);
        return productos;
    }

    /**Devuelve una lista con las ofertas de un ticket*/
    @GetMapping("/tickets/ofertas/{id}")
    @Timed
    public List<Oferta> getAllOfertasByTicket(@PathVariable Long id) {
        List<Oferta> ofertas = ticketRepository.findOffersByTicketId(id);
        return ofertas;
    }

    /*Devuelve los tickets y la cantidad total de estos de el dia actual*/
    @GetMapping("/tickets/today")
    public TicketsDiaDTO getTodayTickets(){
        log.debug("Rest requests to get all Tickets from Today");

        ZonedDateTime start = ZonedDateTime.of(LocalDate.now(), LocalTime.MIN, ZoneId.systemDefault());
        ZonedDateTime end = ZonedDateTime.of(LocalDate.now(),LocalTime.MAX, ZoneId.systemDefault());

        List<Ticket> tickets = ticketRepository.findByFechaBetween(start,end);

        BigDecimal total = new BigDecimal(0);

        for (Ticket ticket:tickets
             ) {
            total = total.add(ticket.getCantidad());

        }

        TicketsDiaDTO ticketsDiaDTO = new TicketsDiaDTO(tickets,total);

        return  ticketsDiaDTO;
    }

    /**
     * GET  /tickets/:id : get the "id" ticket.
     *
     * @param id the id of the ticket to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ticket, or with status 404 (Not Found)
     */
    @GetMapping("/tickets/{id}")
    @Timed
    public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
        log.debug("REST request to get Ticket : {}", id);
        Ticket ticket = ticketRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ticket));
    }

    /**
     * DELETE  /tickets/:id : delete the "id" ticket.
     *
     * @param id the id of the ticket to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tickets/{id}")
    @Timed
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        log.debug("REST request to delete Ticket : {}", id);
        ticketRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
