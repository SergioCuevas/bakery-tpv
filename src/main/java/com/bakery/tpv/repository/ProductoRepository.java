package com.bakery.tpv.repository;

import com.bakery.tpv.domain.Producto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Producto entity.
 */
@SuppressWarnings("unused")
public interface ProductoRepository extends JpaRepository<Producto,Long> {

}
