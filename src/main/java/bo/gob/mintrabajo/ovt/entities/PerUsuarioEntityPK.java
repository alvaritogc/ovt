package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Renato Velasquez.
 * Date: 06-10-13
 */
public class PerUsuarioEntityPK implements Serializable {
    private String idPersona;
    private Integer idUnidad;@Id@Column(name = "ID_PERSONA")
                             public String getIdPersona() {
        return idPersona;
    }

    private Integer idUsuario;

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
        }

        @Id
    @Column(name = "ID_UNIDAD")
    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }
@Id@Column(name = "ID_USUARIO")
public Integer getIdUsuario() {
    return idUsuario;
}

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PerUsuarioEntityPK that = (PerUsuarioEntityPK) o;

        if (idPersona != null ? !idPersona.equals(that.idPersona) : that.idPersona != null) return false;
        if (idUnidad != null ? !idUnidad.equals(that.idUnidad) : that.idUnidad != null) return false;
        if (idUsuario != null ? !idUsuario.equals(that.idUsuario) : that.idUsuario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPersona != null ? idPersona.hashCode() : 0;
        result = 31 * result + (idUnidad != null ? idUnidad.hashCode() : 0);
        result = 31 * result + (idUsuario != null ? idUsuario.hashCode() : 0);
        return result;
}}
