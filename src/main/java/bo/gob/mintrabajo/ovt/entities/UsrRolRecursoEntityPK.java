package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Renato Velasquez.
 * Date: 06-10-13
 */
public class UsrRolRecursoEntityPK implements Serializable {
    private Integer idRol;
    private Integer idRecurso;

    @Id
    @Column(name = "ID_ROL")
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    @Id@Column(name = "ID_RECURSO")
    public Integer getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Integer idRecurso) {
        this.idRecurso = idRecurso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsrRolRecursoEntityPK that = (UsrRolRecursoEntityPK) o;

        if (idRecurso != null ? !idRecurso.equals(that.idRecurso) : that.idRecurso != null) return false;
        if (idRol != null ? !idRol.equals(that.idRol) : that.idRol != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRol != null ? idRol.hashCode() : 0;
        result = 31 * result + (idRecurso != null ? idRecurso.hashCode() : 0);
        return result;
}}
