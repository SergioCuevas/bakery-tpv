package com.bakery.tpv.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.bakery.tpv.domain.enumeration.MetodoPago;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private ZonedDateTime fecha;

    @Column(name = "mesa")
    private Integer mesa;

    @Column(name = "cantidad")
    private Double cantidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago")
    private MetodoPago metodoPago;

    @Column(name = "cerrado")
    private Boolean cerrado;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(name = "ticket_oferta",
               joinColumns = @JoinColumn(name="tickets_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="ofertas_id", referencedColumnName="id"))
    private Set<Oferta> ofertas = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "ticket_producto",
               joinColumns = @JoinColumn(name="tickets_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="productos_id", referencedColumnName="id"))
    private Set<Producto> productos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public Ticket fecha(ZonedDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getMesa() {
        return mesa;
    }

    public Ticket mesa(Integer mesa) {
        this.mesa = mesa;
        return this;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public Ticket cantidad(Double cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public Ticket metodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
        return this;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Boolean isCerrado() {
        return cerrado;
    }

    public Ticket cerrado(Boolean cerrado) {
        this.cerrado = cerrado;
        return this;
    }

    public void setCerrado(Boolean cerrado) {
        this.cerrado = cerrado;
    }

    public User getUser() {
        return user;
    }

    public Ticket user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Oferta> getOfertas() {
        return ofertas;
    }

    public Ticket ofertas(Set<Oferta> ofertas) {
        this.ofertas = ofertas;
        return this;
    }

    public Ticket addOferta(Oferta oferta) {
        this.ofertas.add(oferta);
        oferta.getTickets().add(this);
        return this;
    }

    public Ticket removeOferta(Oferta oferta) {
        this.ofertas.remove(oferta);
        oferta.getTickets().remove(this);
        return this;
    }

    public void setOfertas(Set<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Ticket productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Ticket addProducto(Producto producto) {
        this.productos.add(producto);
        producto.getTickets().add(this);
        return this;
    }

    public Ticket removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.getTickets().remove(this);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        if (ticket.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + id +
            ", fecha='" + fecha + "'" +
            ", mesa='" + mesa + "'" +
            ", cantidad='" + cantidad + "'" +
            ", metodoPago='" + metodoPago + "'" +
            ", cerrado='" + cerrado + "'" +
            '}';
    }
}
