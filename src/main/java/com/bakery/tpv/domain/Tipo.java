package com.bakery.tpv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Tipo.
 */
@Entity
@Table(name = "tipo")
public class Tipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "tipo")
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();

    @OneToMany(mappedBy = "tipo")
    @JsonIgnore
    private Set<LineaOferta> lineaofertas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Tipo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImage() {
        return image;
    }

    public Tipo image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Tipo imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Tipo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Tipo productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Tipo addProducto(Producto producto) {
        this.productos.add(producto);
        producto.setTipo(this);
        return this;
    }

    public Tipo removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.setTipo(null);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

    public Set<LineaOferta> getLineaofertas() {
        return lineaofertas;
    }

    public Tipo lineaofertas(Set<LineaOferta> lineaOfertas) {
        this.lineaofertas = lineaOfertas;
        return this;
    }

    public Tipo addLineaoferta(LineaOferta lineaOferta) {
        this.lineaofertas.add(lineaOferta);
        lineaOferta.setTipo(this);
        return this;
    }

    public Tipo removeLineaoferta(LineaOferta lineaOferta) {
        this.lineaofertas.remove(lineaOferta);
        lineaOferta.setTipo(null);
        return this;
    }

    public void setLineaofertas(Set<LineaOferta> lineaOfertas) {
        this.lineaofertas = lineaOfertas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tipo tipo = (Tipo) o;
        if (tipo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, tipo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tipo{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", image='" + image + "'" +
            ", imageContentType='" + imageContentType + "'" +
            ", descripcion='" + descripcion + "'" +
            '}';
    }
}
