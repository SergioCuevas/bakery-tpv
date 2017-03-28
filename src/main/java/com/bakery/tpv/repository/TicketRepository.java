package com.bakery.tpv.repository;

import com.bakery.tpv.domain.Oferta;
import com.bakery.tpv.domain.Producto;
import com.bakery.tpv.domain.Ticket;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Ticket entity.
 */
@SuppressWarnings("unused")
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @Query("select ticket from Ticket ticket where ticket.user.login = ?#{principal.username}")
    List<Ticket> findByUserIsCurrentUser();

    @Query("select distinct ticket from Ticket ticket left join fetch ticket.ofertas left join fetch ticket.productos")
    List<Ticket> findAllWithEagerRelationships();

    @Query("select ticket from Ticket ticket left join fetch ticket.ofertas left join fetch ticket.productos where ticket.id =:id")
    Ticket findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select ticket.productos from Ticket ticket where ticket.id=:id")
    List<Producto> findProductsByTicketId(@Param("id") Long id);

    @Query("select ticket.ofertas from Ticket ticket where ticket.id=:id")
    List<Oferta> findOffersByTicketId(@Param("id") Long id);

}
