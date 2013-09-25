package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * User: gveramendi
 * Date: 9/25/13
 * Time: 5:33 PM
 */

@javax.persistence.IdClass(bo.gob.mintrabajo.ovt.entities.UsrUsuarioRecursoEntityPK.class)
@javax.persistence.Table(name = "USR_USUARIO_RECURSO", schema = "ROE", catalog = "")
@Entity
public class UsrUsuarioRecursoEntity implements Serializable {
    private int idUsuario;

    @javax.persistence.Column(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    private int idRecurso;

    @javax.persistence.Column(name = "ID_RECURSO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    private BigInteger esDenegado;

    @javax.persistence.Column(name = "ES_DENEGADO", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public BigInteger getEsDenegado() {
        return esDenegado;
    }

    public void setEsDenegado(BigInteger esDenegado) {
        this.esDenegado = esDenegado;
    }

    private String wx;

    @javax.persistence.Column(name = "WX", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
    @Basic
    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    private Timestamp fechaLimite;

    @javax.persistence.Column(name = "FECHA_LIMITE", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Timestamp fechaLimite) {
        this.fechaLimite = fechaLimite;
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

        UsrUsuarioRecursoEntity that = (UsrUsuarioRecursoEntity) o;

        if (idRecurso != that.idRecurso) return false;
        if (idUsuario != that.idUsuario) return false;
        if (esDenegado != null ? !esDenegado.equals(that.esDenegado) : that.esDenegado != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (fechaLimite != null ? !fechaLimite.equals(that.fechaLimite) : that.fechaLimite != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (wx != null ? !wx.equals(that.wx) : that.wx != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario;
        result = 31 * result + idRecurso;
        result = 31 * result + (esDenegado != null ? esDenegado.hashCode() : 0);
        result = 31 * result + (wx != null ? wx.hashCode() : 0);
        result = 31 * result + (fechaLimite != null ? fechaLimite.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }

    private UsrRecursoEntity usrRecursoByIdRecurso;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "ID_RECURSO", referencedColumnName = "ID_RECURSO", nullable = false)
    public UsrRecursoEntity getUsrRecursoByIdRecurso() {
        return usrRecursoByIdRecurso;
    }

    public void setUsrRecursoByIdRecurso(UsrRecursoEntity usrRecursoByIdRecurso) {
        this.usrRecursoByIdRecurso = usrRecursoByIdRecurso;
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
