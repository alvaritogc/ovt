package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 06-10-13
 */
@javax.persistence.IdClass(bo.gob.mintrabajo.ovt.entities.UsrUsuarioRecursoEntityPK.class)
@javax.persistence.Table(name = "USR_USUARIO_RECURSO", schema = "ROE", catalog = "")
@Entity
public class UsrUsuarioRecursoEntity {
    private Integer idUsuario;

    @javax.persistence.Column(name = "ID_USUARIO")
    @Id
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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

    private BigInteger esDenegado;

    @javax.persistence.Column(name = "ES_DENEGADO")
    @Basic
    public BigInteger getEsDenegado() {
        return esDenegado;
    }

    public void setEsDenegado(BigInteger esDenegado) {
        this.esDenegado = esDenegado;
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

    private Timestamp fechaLimite;

    @javax.persistence.Column(name = "FECHA_LIMITE")
    @Basic
    public Timestamp getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Timestamp fechaLimite) {
        this.fechaLimite = fechaLimite;
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

        UsrUsuarioRecursoEntity that = (UsrUsuarioRecursoEntity) o;

        if (esDenegado != null ? !esDenegado.equals(that.esDenegado) : that.esDenegado != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (fechaLimite != null ? !fechaLimite.equals(that.fechaLimite) : that.fechaLimite != null) return false;
        if (idRecurso != null ? !idRecurso.equals(that.idRecurso) : that.idRecurso != null) return false;
        if (idUsuario != null ? !idUsuario.equals(that.idUsuario) : that.idUsuario != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (wx != null ? !wx.equals(that.wx) : that.wx != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario != null ? idUsuario.hashCode() : 0;
        result = 31 * result + (idRecurso != null ? idRecurso.hashCode() : 0);
        result = 31 * result + (esDenegado != null ? esDenegado.hashCode() : 0);
        result = 31 * result + (wx != null ? wx.hashCode() : 0);
        result = 31 * result + (fechaLimite != null ? fechaLimite.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
