package com.bakery.tpv.repository;

import com.bakery.tpv.domain.Producto;

import com.bakery.tpv.domain.Ticket;
import com.bakery.tpv.domain.Tipo;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Producto entity.
 */
@SuppressWarnings("unused")
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    @Query("select producto from Producto producto where producto.tipo.nombre=:tipo")
    List<Producto> findProductoByTipo(@Param("tipo") String tipo);
    List<Producto> findProductoByNombreContaining(String name);
}
