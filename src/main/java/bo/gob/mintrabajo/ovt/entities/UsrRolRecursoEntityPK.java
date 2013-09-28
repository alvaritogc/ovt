package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: gveramendi
 * Date: 9/25/13
 * Time: 5:33 PM
 */

public class UsrRolRecursoEntityPK implements Serializable {
    private int idRol;
    private int idRecurso;

@Id
@Column(name = "ID_ROL", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
public int getIdRol() {
    return idRol;
}

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    @Id
    @Column(name = "ID_RECURSO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
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

        UsrRolRecursoEntityPK that = (UsrRolRecursoEntityPK) o;

        if (idRecurso != that.idRecurso) return false;
        if (idRol != that.idRol) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRol;
        result = 31 * result + idRecurso;
        return result;
}}
