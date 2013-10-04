package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 03-10-13
 */
@javax.persistence.Table(name = "USR_USUARIO", schema = "ROE", catalog = "")
@Entity
public class UsrUsuarioEntity {
    private Integer idUsuario;

    @javax.persistence.Column(name = "ID_USUARIO")
    @Id
    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    private String usuario;

    @javax.persistence.Column(name = "USUARIO")
    @Basic
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    private String clave;

    @javax.persistence.Column(name = "CLAVE")
    @Basic
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    private String tipoAutenticacion;

    @javax.persistence.Column(name = "TIPO_AUTENTICACION")
    @Basic
    public String getTipoAutenticacion() {
        return tipoAutenticacion;
    }

    public void setTipoAutenticacion(String tipoAutenticacion) {
        this.tipoAutenticacion = tipoAutenticacion;
    }

    private BigInteger esInterno;

    @javax.persistence.Column(name = "ES_INTERNO")
    @Basic
    public BigInteger getEsInterno() {
        return esInterno;
    }

    public void setEsInterno(BigInteger esInterno) {
        this.esInterno = esInterno;
    }

    private BigInteger esDelegado;

    @javax.persistence.Column(name = "ES_DELEGADO")
    @Basic
    public BigInteger getEsDelegado() {
        return esDelegado;
    }

    public void setEsDelegado(BigInteger esDelegado) {
        this.esDelegado = esDelegado;
    }

    private Timestamp fechaInhabilitacion;

    @javax.persistence.Column(name = "FECHA_INHABILITACION")
    @Basic
    public Timestamp getFechaInhabilitacion() {
        return fechaInhabilitacion;
    }

    public void setFechaInhabilitacion(Timestamp fechaInhabilitacion) {
        this.fechaInhabilitacion = fechaInhabilitacion;
    }

    private Timestamp fechaRehabilitacion;

    @javax.persistence.Column(name = "FECHA_REHABILITACION")
    @Basic
    public Timestamp getFechaRehabilitacion() {
        return fechaRehabilitacion;
    }

    public void setFechaRehabilitacion(Timestamp fechaRehabilitacion) {
        this.fechaRehabilitacion = fechaRehabilitacion;
    }

    private String estadoUsuario;

    @javax.persistence.Column(name = "ESTADO_USUARIO")
    @Basic
    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
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

        UsrUsuarioEntity that = (UsrUsuarioEntity) o;

        if (clave != null ? !clave.equals(that.clave) : that.clave != null) return false;
        if (esDelegado != null ? !esDelegado.equals(that.esDelegado) : that.esDelegado != null) return false;
        if (esInterno != null ? !esInterno.equals(that.esInterno) : that.esInterno != null) return false;
        if (estadoUsuario != null ? !estadoUsuario.equals(that.estadoUsuario) : that.estadoUsuario != null)
            return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (fechaInhabilitacion != null ? !fechaInhabilitacion.equals(that.fechaInhabilitacion) : that.fechaInhabilitacion != null)
            return false;
        if (fechaRehabilitacion != null ? !fechaRehabilitacion.equals(that.fechaRehabilitacion) : that.fechaRehabilitacion != null)
            return false;
        if (idUsuario != null ? !idUsuario.equals(that.idUsuario) : that.idUsuario != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoAutenticacion != null ? !tipoAutenticacion.equals(that.tipoAutenticacion) : that.tipoAutenticacion != null)
            return false;
        if (usuario != null ? !usuario.equals(that.usuario) : that.usuario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario != null ? idUsuario.hashCode() : 0;
        result = 31 * result + (usuario != null ? usuario.hashCode() : 0);
        result = 31 * result + (clave != null ? clave.hashCode() : 0);
        result = 31 * result + (tipoAutenticacion != null ? tipoAutenticacion.hashCode() : 0);
        result = 31 * result + (esInterno != null ? esInterno.hashCode() : 0);
        result = 31 * result + (esDelegado != null ? esDelegado.hashCode() : 0);
        result = 31 * result + (fechaInhabilitacion != null ? fechaInhabilitacion.hashCode() : 0);
        result = 31 * result + (fechaRehabilitacion != null ? fechaRehabilitacion.hashCode() : 0);
        result = 31 * result + (estadoUsuario != null ? estadoUsuario.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
