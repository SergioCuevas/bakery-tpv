package com.bakery.tpv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @Column(name = "imagen_content_type")
    private String imagenContentType;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio")
    private Double precio;

    @ManyToOne
    private Tipo tipo;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private Set<LineaOferta> lineaofertas = new HashSet<>();

    @ManyToMany(mappedBy = "productos")
    @JsonIgnore
    private Set<Ticket> tickets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Producto imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Producto imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public Producto precio(Double precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Producto tipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Set<LineaOferta> getLineaofertas() {
        return lineaofertas;
    }

    public Producto lineaofertas(Set<LineaOferta> lineaOfertas) {
        this.lineaofertas = lineaOfertas;
        return this;
    }

    public Producto addLineaoferta(LineaOferta lineaOferta) {
        this.lineaofertas.add(lineaOferta);
        lineaOferta.setProducto(this);
        return this;
    }

    public Producto removeLineaoferta(LineaOferta lineaOferta) {
        this.lineaofertas.remove(lineaOferta);
        lineaOferta.setProducto(null);
        return this;
    }

    public void setLineaofertas(Set<LineaOferta> lineaOfertas) {
        this.lineaofertas = lineaOfertas;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public Producto tickets(Set<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    public Producto addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        ticket.getProductos().add(this);
        return this;
    }

    public Producto removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
        ticket.getProductos().remove(this);
        return this;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producto producto = (Producto) o;
        if (producto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", imagen='" + imagen + "'" +
            ", imagenContentType='" + imagenContentType + "'" +
            ", descripcion='" + descripcion + "'" +
            ", precio='" + precio + "'" +
            '}';
    }
}
