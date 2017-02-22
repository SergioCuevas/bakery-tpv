package com.bakery.tpv.repository;

import com.bakery.tpv.domain.Oferta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Oferta entity.
 */
@SuppressWarnings("unused")
public interface OfertaRepository extends JpaRepository<Oferta,Long> {

}
