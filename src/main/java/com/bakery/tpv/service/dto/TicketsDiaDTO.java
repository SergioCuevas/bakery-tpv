package com.bakery.tpv.service.dto;

import com.bakery.tpv.domain.Ticket;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by DAM on 7/3/17.
 */
public class TicketsDiaDTO {

    private List<Ticket> tickets ;
    private BigDecimal cantidadTotal ;

    public TicketsDiaDTO(List<Ticket> tickets, BigDecimal cantidadTotal) {
        this.tickets = tickets;
        this.cantidadTotal = cantidadTotal;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public BigDecimal getCantidadTotal() {
        return cantidadTotal;
    }
}
