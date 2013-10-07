package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 06-10-13
 */
@javax.persistence.IdClass(bo.gob.mintrabajo.ovt.entities.UsrRolRecursoEntityPK.class)
@javax.persistence.Table(name = "USR_ROL_RECURSO", schema = "OVT", catalog = "")
@Entity
public class UsrRolRecursoEntity {
    private Integer idRol;

    @javax.persistence.Column(name = "ID_ROL")
    @Id
    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    private Integer idRecurso;

    @javax.persistence.Column(name = "ID_RECURSO")
    @Id
    public Integer getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Integer idRecurso) {
        this.idRecurso = idRecurso;
    }

    private String wx;

    @javax.persistence.Column(name = "WX")
    @Basic
    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
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

        UsrRolRecursoEntity that = (UsrRolRecursoEntity) o;

        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (idRecurso != null ? !idRecurso.equals(that.idRecurso) : that.idRecurso != null) return false;
        if (idRol != null ? !idRol.equals(that.idRol) : that.idRol != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (wx != null ? !wx.equals(that.wx) : that.wx != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idRol != null ? idRol.hashCode() : 0;
        result = 31 * result + (idRecurso != null ? idRecurso.hashCode() : 0);
        result = 31 * result + (wx != null ? wx.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
