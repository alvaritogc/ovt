package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Renato Velasquez.
 * Date: 03-10-13
 */
public class UsrUsuarioRolEntityPK implements Serializable {
    private Integer idUsuario;
    private Integer idRol;

@Id@Column(name = "ID_USUARIO")
public Integer getIdUsuario() {
    return idUsuario;
}

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id@Column(name = "ID_ROL")
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsrUsuarioRolEntityPK that = (UsrUsuarioRolEntityPK) o;

        if (idRol != null ? !idRol.equals(that.idRol) : that.idRol != null) return false;
        if (idUsuario != null ? !idUsuario.equals(that.idUsuario) : that.idUsuario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario != null ? idUsuario.hashCode() : 0;
        result = 31 * result + (idRol != null ? idRol.hashCode() : 0);
        return result;
}}
