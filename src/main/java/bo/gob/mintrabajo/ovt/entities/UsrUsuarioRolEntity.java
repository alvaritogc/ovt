package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 06-10-13
 */
@javax.persistence.IdClass(bo.gob.mintrabajo.ovt.entities.UsrUsuarioRolEntityPK.class)
@javax.persistence.Table(name = "USR_USUARIO_ROL", schema = "OVT", catalog = "")
@Entity
public class UsrUsuarioRolEntity {
    private Integer idUsuario;

    @javax.persistence.Column(name = "ID_USUARIO")
    @Id
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    private Integer idRol;

    @javax.persistence.Column(name = "ID_ROL")
    @Id
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    private Timestamp fechaBitacora;

    @javax.persistence.Column(name = "FECHA_BITACORA")
    @Basic
    public Timestamp getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Timestamp fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    private String registroBitacora;

    @javax.persistence.Column(name = "REGISTRO_BITACORA")
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

        UsrUsuarioRolEntity that = (UsrUsuarioRolEntity) o;

        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (idRol != null ? !idRol.equals(that.idRol) : that.idRol != null) return false;
        if (idUsuario != null ? !idUsuario.equals(that.idUsuario) : that.idUsuario != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario != null ? idUsuario.hashCode() : 0;
        result = 31 * result + (idRol != null ? idRol.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
