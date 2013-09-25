package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: gveramendi
 * Date: 9/25/13
 * Time: 5:33 PM
 */

public class PerUsuarioEntityPK implements Serializable {
    private int idUsuario;
    private String idPersona;@Id@Column(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
                             public int getIdUsuario() {
        return idUsuario;
    }

    private int idUnidad;

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
        }

    @Id@Column(name = "ID_PERSONA", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
public String getIdPersona() {
    return idPersona;
}

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }
@Id@Column(name = "ID_UNIDAD", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
public int getIdUnidad() {
    return idUnidad;
}

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PerUsuarioEntityPK that = (PerUsuarioEntityPK) o;

        if (idUnidad != that.idUnidad) return false;
        if (idUsuario != that.idUsuario) return false;
        if (idPersona != null ? !idPersona.equals(that.idPersona) : that.idPersona != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario;
        result = 31 * result + (idPersona != null ? idPersona.hashCode() : 0);
        result = 31 * result + idUnidad;
        return result;
}}
