package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User: gveramendi
 * Date: 9/25/13
 * Time: 5:33 PM
 */

@javax.persistence.IdClass(bo.gob.mintrabajo.ovt.entities.PerUsuarioEntityPK.class)
@javax.persistence.Table(name = "PER_USUARIO", schema = "ROE", catalog = "")
@Entity
public class PerUsuarioEntity implements Serializable {
    private int idUsuario;

    @javax.persistence.Column(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    private String idPersona;

    @javax.persistence.Column(name = "ID_PERSONA", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Id
    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    private int idUnidad;

    @javax.persistence.Column(name = "ID_UNIDAD", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    private Timestamp fechaBitacora;

    @javax.persistence.Column(name = "FECHA_BITACORA", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Timestamp fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    private String registroBitacora;

    @javax.persistence.Column(name = "REGISTRO_BITACORA", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
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

        PerUsuarioEntity that = (PerUsuarioEntity) o;

        if (idUnidad != that.idUnidad) return false;
        if (idUsuario != that.idUsuario) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (idPersona != null ? !idPersona.equals(that.idPersona) : that.idPersona != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario;
        result = 31 * result + (idPersona != null ? idPersona.hashCode() : 0);
        result = 31 * result + idUnidad;
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }

    private PerUnidadEntity perUnidad;

    @ManyToOne
    @javax.persistence.JoinColumns({@javax.persistence.JoinColumn(name = "ID_UNIDAD", referencedColumnName = "ID_UNIDAD", nullable = false), @javax.persistence.JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", nullable = false)})
    public PerUnidadEntity getPerUnidad() {
        return perUnidad;
    }

    public void setPerUnidad(PerUnidadEntity perUnidad) {
        this.perUnidad = perUnidad;
    }

    private UsrUsuarioEntity usrUsuarioByIdUsuario;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", nullable = false)
    public UsrUsuarioEntity getUsrUsuarioByIdUsuario() {
        return usrUsuarioByIdUsuario;
    }

    public void setUsrUsuarioByIdUsuario(UsrUsuarioEntity usrUsuarioByIdUsuario) {
        this.usrUsuarioByIdUsuario = usrUsuarioByIdUsuario;
    }
}
