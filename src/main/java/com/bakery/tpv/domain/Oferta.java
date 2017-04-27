package com.bakery.tpv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * A Oferta.
 */
@Entity
@Table(name = "oferta")
public class Oferta implements Serializable {

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
    private BigDecimal precio;

    @Column(name = "fechainicio")
    private ZonedDateTime fechainicio;

    @Column(name = "fechafinal")
    private ZonedDateTime fechafinal;

    @OneToMany(mappedBy = "oferta")
    @JsonIgnore
    private Set<LineaOferta> lineaofertas = new HashSet<>();

    @ManyToMany(mappedBy = "ofertas")
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Oferta nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public Oferta imagen(byte[] imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public String getImagenContentType() {
        return imagenContentType;
    }

    public Oferta imagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
        return this;
    }

    public void setImagenContentType(String imagenContentType) {
        this.imagenContentType = imagenContentType;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Oferta descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Oferta precio(BigDecimal precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public ZonedDateTime getFechainicio() {
        return fechainicio;
    }

    public Oferta fechainicio(ZonedDateTime fechainicio) {
        this.fechainicio = fechainicio;
        return this;
    }

    public void setFechainicio(ZonedDateTime fechainicio) {
        this.fechainicio = fechainicio;
    }

    public ZonedDateTime getFechafinal() {
        return fechafinal;
    }

    public Oferta fechafinal(ZonedDateTime fechafinal) {
        this.fechafinal = fechafinal;
        return this;
    }

    public void setFechafinal(ZonedDateTime fechafinal) {
        this.fechafinal = fechafinal;
    }

    public Set<LineaOferta> getLineaofertas() {
        return lineaofertas;
    }

    public Oferta lineaofertas(Set<LineaOferta> lineaOfertas) {
        this.lineaofertas = lineaOfertas;
        return this;
    }

    public Oferta addLineaoferta(LineaOferta lineaOferta) {
        this.lineaofertas.add(lineaOferta);
        lineaOferta.setOferta(this);
        return this;
    }

    public Oferta removeLineaoferta(LineaOferta lineaOferta) {
        this.lineaofertas.remove(lineaOferta);
        lineaOferta.setOferta(null);
        return this;
    }

    public void setLineaofertas(Set<LineaOferta> lineaOfertas) {
        this.lineaofertas = lineaOfertas;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Oferta tickets(List<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    public Oferta addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        ticket.getOfertas().add(this);
        return this;
    }

    public Oferta removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
        ticket.getOfertas().remove(this);
        return this;
    }

    public void setTickets(List<Ticket> tickets) {
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
        Oferta oferta = (Oferta) o;
        if (oferta.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, oferta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Oferta{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", imagen='" + imagen + "'" +
            ", imagenContentType='" + imagenContentType + "'" +
            ", descripcion='" + descripcion + "'" +
            ", precio='" + precio + "'" +
            ", fechainicio='" + fechainicio + "'" +
            ", fechafinal='" + fechafinal + "'" +
            '}';
    }
}
