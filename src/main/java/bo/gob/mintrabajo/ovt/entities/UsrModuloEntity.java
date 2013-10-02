package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * User: gveramendi
 * Date: 9/25/13
 * Time: 5:33 PM
 */

@Table(name = "USR_MODULO", schema = "ROE", catalog = "")
@Entity
public class UsrModuloEntity implements Serializable {
    private String idModulo;
    private String nombre;
    private String tipoModulo;
    private String tipoArea;
    private Timestamp fechaBitacora;
    private String registroBitacora;
    private Collection<ParDominioEntity> parDominiosByIdModulo;
    private Collection<UsrRecursoEntity> usrRecursosByIdModulo;
    private Collection<UsrRolEntity> usrRolsByIdModulo;
    private Collection<UsrUsuarioRolEntity> usrUsuarioRolsByIdModulo;

    @Column(name = "ID_MODULO", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
    @Id
    public String getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }

    @Column(name = "NOMBRE", nullable = false, insertable = true, updatable = true, length = 80, precision = 0)
    @Basic
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "TIPO_MODULO", nullable = false, insertable = true, updatable = true, length = 15, precision = 0)
    @Basic
    public String getTipoModulo() {
        return tipoModulo;
    }

    public void setTipoModulo(String tipoModulo) {
        this.tipoModulo = tipoModulo;
    }

    @Column(name = "TIPO_AREA", nullable = false, insertable = true, updatable = true, length = 15, precision = 0)
    @Basic
    public String getTipoArea() {
        return tipoArea;
    }

    public void setTipoArea(String tipoArea) {
        this.tipoArea = tipoArea;
    }

    @Column(name = "FECHA_BITACORA", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Timestamp fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    @Column(name = "REGISTRO_BITACORA", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
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

        UsrModuloEntity that = (UsrModuloEntity) o;

        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (idModulo != null ? !idModulo.equals(that.idModulo) : that.idModulo != null) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoArea != null ? !tipoArea.equals(that.tipoArea) : that.tipoArea != null) return false;
        if (tipoModulo != null ? !tipoModulo.equals(that.tipoModulo) : that.tipoModulo != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idModulo != null ? idModulo.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (tipoModulo != null ? tipoModulo.hashCode() : 0);
        result = 31 * result + (tipoArea != null ? tipoArea.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "usrModuloByIdModulo")
    public Collection<ParDominioEntity> getParDominiosByIdModulo() {
        return parDominiosByIdModulo;
    }

    public void setParDominiosByIdModulo(Collection<ParDominioEntity> parDominiosByIdModulo) {
        this.parDominiosByIdModulo = parDominiosByIdModulo;
    }

    @OneToMany(mappedBy = "usrModuloByIdModulo")
    public Collection<UsrRecursoEntity> getUsrRecursosByIdModulo() {
        return usrRecursosByIdModulo;
    }

    public void setUsrRecursosByIdModulo(Collection<UsrRecursoEntity> usrRecursosByIdModulo) {
        this.usrRecursosByIdModulo = usrRecursosByIdModulo;
    }

    @OneToMany(mappedBy = "usrModuloByIdModulo")
    public Collection<UsrRolEntity> getUsrRolsByIdModulo() {
        return usrRolsByIdModulo;
    }

    public void setUsrRolsByIdModulo(Collection<UsrRolEntity> usrRolsByIdModulo) {
        this.usrRolsByIdModulo = usrRolsByIdModulo;
    }

    @OneToMany(mappedBy = "usrModuloByIdModulo")
    public Collection<UsrUsuarioRolEntity> getUsrUsuarioRolsByIdModulo() {
        return usrUsuarioRolsByIdModulo;
    }

    public void setUsrUsuarioRolsByIdModulo(Collection<UsrUsuarioRolEntity> usrUsuarioRolsByIdModulo) {
        this.usrUsuarioRolsByIdModulo = usrUsuarioRolsByIdModulo;
    }
}