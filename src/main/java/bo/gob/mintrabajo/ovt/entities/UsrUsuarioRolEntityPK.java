package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: gveramendi
 * Date: 9/25/13
 * Time: 5:33 PM
 */

public class UsrUsuarioRolEntityPK implements Serializable {
    private int idUsuario;
    private int idRol;

@Id@Column(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
public int getIdUsuario() {
    return idUsuario;
}

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id@Column(name = "ID_ROL", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsrUsuarioRolEntityPK that = (UsrUsuarioRolEntityPK) o;

        if (idRol != that.idRol) return false;
        if (idUsuario != that.idUsuario) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario;
        result = 31 * result + idRol;
        return result;
}}
