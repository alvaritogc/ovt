package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Renato Velasquez.
 * Date: 03-10-13
 */
public class UsrUsuarioRecursoEntityPK implements Serializable {
    private Integer idUsuario;
    private Integer idRecurso;

@Id
@Column(name = "ID_USUARIO")
public Integer getIdUsuario() {
    return idUsuario;
}

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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

        UsrUsuarioRecursoEntityPK that = (UsrUsuarioRecursoEntityPK) o;

        if (idRecurso != null ? !idRecurso.equals(that.idRecurso) : that.idRecurso != null) return false;
        if (idUsuario != null ? !idUsuario.equals(that.idUsuario) : that.idUsuario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario != null ? idUsuario.hashCode() : 0;
        result = 31 * result + (idRecurso != null ? idRecurso.hashCode() : 0);
        return result;
}}
