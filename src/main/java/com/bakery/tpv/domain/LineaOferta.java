package com.bakery.tpv.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LineaOferta.
 */
@Entity
@Table(name = "linea_oferta")
public class LineaOferta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "cantidad")
    private Integer cantidad;

    @ManyToOne
    private Oferta oferta;

    @ManyToOne
    private Producto producto;

    @ManyToOne
    private Tipo tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public LineaOferta nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public LineaOferta cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public LineaOferta oferta(Oferta oferta) {
        this.oferta = oferta;
        return this;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public Producto getProducto() {
        return producto;
    }

    public LineaOferta producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public LineaOferta tipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LineaOferta lineaOferta = (LineaOferta) o;
        if (lineaOferta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lineaOferta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LineaOferta{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", cantidad='" + cantidad + "'" +
            '}';
    }
}
