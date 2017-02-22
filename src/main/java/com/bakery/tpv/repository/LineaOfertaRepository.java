package com.bakery.tpv.repository;

import com.bakery.tpv.domain.LineaOferta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LineaOferta entity.
 */
@SuppressWarnings("unused")
public interface LineaOfertaRepository extends JpaRepository<LineaOferta,Long> {

}
