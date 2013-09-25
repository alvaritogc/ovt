package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: gveramendi
 * Date: 9/25/13
 * Time: 5:33 PM
 */

public class UsrUsuarioRecursoEntityPK implements Serializable {
    private int idUsuario;
    private int idRecurso;

@Id@Column(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
public int getIdUsuario() {
    return idUsuario;
}

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Id@Column(name = "ID_RECURSO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsrUsuarioRecursoEntityPK that = (UsrUsuarioRecursoEntityPK) o;

        if (idRecurso != that.idRecurso) return false;
        if (idUsuario != that.idUsuario) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario;
        result = 31 * result + idRecurso;
        return result;
}}
