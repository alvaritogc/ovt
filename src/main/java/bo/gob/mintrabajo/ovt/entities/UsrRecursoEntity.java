package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * User: gveramendi
 * Date: 9/25/13
 * Time: 5:33 PM
 */

@javax.persistence.Table(name = "USR_RECURSO", schema = "ROE", catalog = "")
@Entity
public class UsrRecursoEntity implements Serializable {
    private int idRecurso;

    @javax.persistence.Column(name = "ID_RECURSO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    private String tipoRecurso;

    @javax.persistence.Column(name = "TIPO_RECURSO", nullable = false, insertable = true, updatable = true, length = 15, precision = 0)
    @Basic
    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    private String tipoPlataforma;

    @javax.persistence.Column(name = "TIPO_PLATAFORMA", nullable = false, insertable = true, updatable = true, length = 15, precision = 0)
    @Basic
    public String getTipoPlataforma() {
        return tipoPlataforma;
    }

    public void setTipoPlataforma(String tipoPlataforma) {
        this.tipoPlataforma = tipoPlataforma;
    }

    private int orden;

    @javax.persistence.Column(name = "ORDEN", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
    @Basic
    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    private String etiqueta;

    @javax.persistence.Column(name = "ETIQUETA", nullable = false, insertable = true, updatable = true, length = 80, precision = 0)
    @Basic
    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    private String descripcion;

    @javax.persistence.Column(name = "DESCRIPCION", nullable = false, insertable = true, updatable = true, length = 120, precision = 0)
    @Basic
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private String ejecutable;

    @javax.persistence.Column(name = "EJECUTABLE", nullable = false, insertable = true, updatable = true, length = 500, precision = 0)
    @Basic
    public String getEjecutable() {
        return ejecutable;
    }

    public void setEjecutable(String ejecutable) {
        this.ejecutable = ejecutable;
    }

    private BigInteger esVerificable;

    @javax.persistence.Column(name = "ES_VERIFICABLE", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public BigInteger getEsVerificable() {
        return esVerificable;
    }

    public void setEsVerificable(BigInteger esVerificable) {
        this.esVerificable = esVerificable;
    }

    private String estado;

    @javax.persistence.Column(name = "ESTADO", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
    @Basic
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    private Timestamp fechaBitacora;

    @javax.persistence.Column(name = "FECHA_BITACORA", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Timestamp fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    private String registroBitacora;

    @javax.persistence.Column(name = "REGISTRO_BITACORA", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    public String getRegistroBitacora() {
        return registroBitacora;
    }

    public void setRegistroBitacora(String registroBitacora) {
        this.registroBitacora = registroBitacora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsrRecursoEntity that = (UsrRecursoEntity) o;

        if (idRecurso != that.idRecurso) return false;
        if (orden != that.orden) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (ejecutable != null ? !ejecutable.equals(that.ejecutable) : that.ejecutable != null) return false;
        if (esVerificable != null ? !esVerificable.equals(that.esVerificable) : that.esVerificable != null)
            return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;
        if (etiqueta != null ? !etiqueta.equals(that.etiqueta) : that.etiqueta != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoPlataforma != null ? !tipoPlataforma.equals(that.tipoPlataforma) : that.tipoPlataforma != null)
            return false;
        if (tipoRecurso != null ? !tipoRecurso.equals(that.tipoRecurso) : that.tipoRecurso != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRecurso;
        result = 31 * result + (tipoRecurso != null ? tipoRecurso.hashCode() : 0);
        result = 31 * result + (tipoPlataforma != null ? tipoPlataforma.hashCode() : 0);
        result = 31 * result + orden;
        result = 31 * result + (etiqueta != null ? etiqueta.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (ejecutable != null ? ejecutable.hashCode() : 0);
        result = 31 * result + (esVerificable != null ? esVerificable.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }

    private UsrModuloEntity usrModuloByIdModulo;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "ID_MODULO", referencedColumnName = "ID_MODULO", nullable = false)
    public UsrModuloEntity getUsrModuloByIdModulo() {
        return usrModuloByIdModulo;
    }

    public void setUsrModuloByIdModulo(UsrModuloEntity usrModuloByIdModulo) {
        this.usrModuloByIdModulo = usrModuloByIdModulo;
    }

    private UsrRecursoEntity usrRecursoByIdRecursoPadre;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "ID RECURSO_PADRE", referencedColumnName = "ID_RECURSO")
    public UsrRecursoEntity getUsrRecursoByIdRecursoPadre() {
        return usrRecursoByIdRecursoPadre;
    }

    public void setUsrRecursoByIdRecursoPadre(UsrRecursoEntity usrRecursoByIdRecursoPadre) {
        this.usrRecursoByIdRecursoPadre = usrRecursoByIdRecursoPadre;
    }

    private Collection<UsrRecursoEntity> usrRecursosByIdRecurso;

    @OneToMany(mappedBy = "usrRecursoByIdRecursoPadre")
    public Collection<UsrRecursoEntity> getUsrRecursosByIdRecurso() {
        return usrRecursosByIdRecurso;
    }

    public void setUsrRecursosByIdRecurso(Collection<UsrRecursoEntity> usrRecursosByIdRecurso) {
        this.usrRecursosByIdRecurso = usrRecursosByIdRecurso;
    }

    private UsrRecursoEntity usrRecursoByIdRecurso;

    @OneToOne
    @JoinColumn(name = "ID_RECURSO", referencedColumnName = "ID_RECURSO", nullable = false)
    public UsrRecursoEntity getUsrRecursoByIdRecurso() {
        return usrRecursoByIdRecurso;
    }

    public void setUsrRecursoByIdRecurso(UsrRecursoEntity usrRecursoByIdRecurso) {
        this.usrRecursoByIdRecurso = usrRecursoByIdRecurso;
    }

    private UsrRecursoEntity usrRecursoByIdRecurso_0;

    @OneToOne(mappedBy = "usrRecursoByIdRecurso")
    public UsrRecursoEntity getUsrRecursoByIdRecurso_0() {
        return usrRecursoByIdRecurso_0;
    }

    public void setUsrRecursoByIdRecurso_0(UsrRecursoEntity usrRecursoByIdRecurso_0) {
        this.usrRecursoByIdRecurso_0 = usrRecursoByIdRecurso_0;
    }

    private Collection<UsrRolRecursoEntity> usrRolRecursosByIdRecurso;

    @OneToMany(mappedBy = "usrRecursoByIdRecurso")
    public Collection<UsrRolRecursoEntity> getUsrRolRecursosByIdRecurso() {
        return usrRolRecursosByIdRecurso;
    }

    public void setUsrRolRecursosByIdRecurso(Collection<UsrRolRecursoEntity> usrRolRecursosByIdRecurso) {
        this.usrRolRecursosByIdRecurso = usrRolRecursosByIdRecurso;
    }

    private Collection<UsrUsuarioRecursoEntity> usrUsuarioRecursosByIdRecurso;

    @OneToMany(mappedBy = "usrRecursoByIdRecurso")
    public Collection<UsrUsuarioRecursoEntity> getUsrUsuarioRecursosByIdRecurso() {
        return usrUsuarioRecursosByIdRecurso;
    }

    public void setUsrUsuarioRecursosByIdRecurso(Collection<UsrUsuarioRecursoEntity> usrUsuarioRecursosByIdRecurso) {
        this.usrUsuarioRecursosByIdRecurso = usrUsuarioRecursosByIdRecurso;
    }
}
