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

@javax.persistence.Table(name = "USR_ROL", schema = "ROE", catalog = "")
@Entity
public class UsrRolEntity implements Serializable {
    private int idRol;

    @javax.persistence.Column(name = "ID_ROL", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    private String nombre;

    @javax.persistence.Column(name = "NOMBRE", nullable = false, insertable = true, updatable = true, length = 80, precision = 0)
    @Basic
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private BigInteger esInterno;

    @javax.persistence.Column(name = "ES_INTERNO", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public BigInteger getEsInterno() {
        return esInterno;
    }

    public void setEsInterno(BigInteger esInterno) {
        this.esInterno = esInterno;
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

        UsrRolEntity that = (UsrRolEntity) o;

        if (idRol != that.idRol) return false;
        if (esInterno != null ? !esInterno.equals(that.esInterno) : that.esInterno != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRol;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (esInterno != null ? esInterno.hashCode() : 0);
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

    private Collection<UsrRolRecursoEntity> usrRolRecursosByIdRol;

    @OneToMany(mappedBy = "usrRolByIdRol")
    public Collection<UsrRolRecursoEntity> getUsrRolRecursosByIdRol() {
        return usrRolRecursosByIdRol;
    }

    public void setUsrRolRecursosByIdRol(Collection<UsrRolRecursoEntity> usrRolRecursosByIdRol) {
        this.usrRolRecursosByIdRol = usrRolRecursosByIdRol;
    }

    private Collection<UsrUsuarioRolEntity> usrUsuarioRolsByIdRol;

    @OneToMany(mappedBy = "usrRolByIdRol")
    public Collection<UsrUsuarioRolEntity> getUsrUsuarioRolsByIdRol() {
        return usrUsuarioRolsByIdRol;
    }

    public void setUsrUsuarioRolsByIdRol(Collection<UsrUsuarioRolEntity> usrUsuarioRolsByIdRol) {
        this.usrUsuarioRolsByIdRol = usrUsuarioRolsByIdRol;
    }
}
