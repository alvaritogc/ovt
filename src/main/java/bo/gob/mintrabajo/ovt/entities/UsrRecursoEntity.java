package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 06-10-13
 */
@javax.persistence.Table(name = "USR_RECURSO", schema = "ROE", catalog = "")
@Entity
public class UsrRecursoEntity {
    private Integer idRecurso;
    private String tipoRecurso;
    private String tipoPlataforma;
    private Integer orden;
    private String etiqueta;
    private String descripcion;
    private String ejecutable;
    private BigInteger esVerificable;
    private String estado;
    private Timestamp fechaBitacora;
    private String registroBitacora;
    private String idModulo;
    private Integer idRecursoPadre;

    @javax.persistence.Column(name = "ID_RECURSO")
    @Id
    public Integer getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Integer idRecurso) {
        this.idRecurso = idRecurso;
    }

    @javax.persistence.Column(name = "TIPO_RECURSO")
    @Basic
    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    @javax.persistence.Column(name = "TIPO_PLATAFORMA")
    @Basic
    public String getTipoPlataforma() {
        return tipoPlataforma;
    }

    public void setTipoPlataforma(String tipoPlataforma) {
        this.tipoPlataforma = tipoPlataforma;
    }

    @javax.persistence.Column(name = "ORDEN")
    @Basic
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    @javax.persistence.Column(name = "ETIQUETA")
    @Basic
    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @javax.persistence.Column(name = "DESCRIPCION")
    @Basic
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @javax.persistence.Column(name = "EJECUTABLE")
    @Basic
    public String getEjecutable() {
        return ejecutable;
    }

    public void setEjecutable(String ejecutable) {
        this.ejecutable = ejecutable;
    }

    @javax.persistence.Column(name = "ES_VERIFICABLE")
    @Basic
    public BigInteger getEsVerificable() {
        return esVerificable;
    }

    public void setEsVerificable(BigInteger esVerificable) {
        this.esVerificable = esVerificable;
    }

    @javax.persistence.Column(name = "ESTADO")
    @Basic
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @javax.persistence.Column(name = "FECHA_BITACORA")
    @Basic
    public Timestamp getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Timestamp fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    @javax.persistence.Column(name = "REGISTRO_BITACORA")
    @Basic
    public String getRegistroBitacora() {
        return registroBitacora;
    }

    public void setRegistroBitacora(String registroBitacora) {
        this.registroBitacora = registroBitacora;
    }

    @Column(name = "ID_MODULO")
    @Basic
    public String getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }

    @Column(name = "ID RECURSO_PADRE")
    @Basic
    public Integer getIdRecursoPadre() {
        return idRecursoPadre;
    }

    public void setIdRecursoPadre(Integer idRecursoPadre) {
        this.idRecursoPadre = idRecursoPadre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsrRecursoEntity that = (UsrRecursoEntity) o;

        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (ejecutable != null ? !ejecutable.equals(that.ejecutable) : that.ejecutable != null) return false;
        if (esVerificable != null ? !esVerificable.equals(that.esVerificable) : that.esVerificable != null)
            return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;
        if (etiqueta != null ? !etiqueta.equals(that.etiqueta) : that.etiqueta != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (idRecurso != null ? !idRecurso.equals(that.idRecurso) : that.idRecurso != null) return false;
        if (orden != null ? !orden.equals(that.orden) : that.orden != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoPlataforma != null ? !tipoPlataforma.equals(that.tipoPlataforma) : that.tipoPlataforma != null)
            return false;
        if (tipoRecurso != null ? !tipoRecurso.equals(that.tipoRecurso) : that.tipoRecurso != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRecurso != null ? idRecurso.hashCode() : 0;
        result = 31 * result + (tipoRecurso != null ? tipoRecurso.hashCode() : 0);
        result = 31 * result + (tipoPlataforma != null ? tipoPlataforma.hashCode() : 0);
        result = 31 * result + (orden != null ? orden.hashCode() : 0);
        result = 31 * result + (etiqueta != null ? etiqueta.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (ejecutable != null ? ejecutable.hashCode() : 0);
        result = 31 * result + (esVerificable != null ? esVerificable.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
