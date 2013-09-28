package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * User: gveramendi
 * Date: 9/25/13
 * Time: 5:33 PM
 */

@Table(name = "USR_USUARIO", schema = "ROE", catalog = "")
@Entity
public class UsrUsuarioEntity implements Serializable {
    private int idUsuario;
    private String usuario;
    private String clave;
    private String tipoAutenticacion;
    private BigInteger esInterno;
    private BigInteger esDelegado;
    private Timestamp fechaInhabilitacion;
    private Timestamp fechaRehabilitacion;
    private String estadoUsuario;
    private Timestamp fechaBitacora;
    private String registroBitacora;
    private Collection<PerUsuarioEntity> perUsuariosByIdUsuario;
    private PerPersonaEntity perPersonaByIdPersona;
    private Collection<UsrUsuarioRecursoEntity> usrUsuarioRecursosByIdUsuario;
    private Collection<UsrUsuarioRolEntity> usrUsuarioRolsByIdUsuario;

    @Column(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Column(name = "USUARIO", nullable = false, insertable = true, updatable = true, length = 80, precision = 0)
    @Basic
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Column(name = "CLAVE", nullable = false, insertable = true, updatable = true, length = 80, precision = 0)
    @Basic
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Column(name = "TIPO_AUTENTICACION", nullable = false, insertable = true, updatable = true, length = 15, precision = 0)
    @Basic
    public String getTipoAutenticacion() {
        return tipoAutenticacion;
    }

    public void setTipoAutenticacion(String tipoAutenticacion) {
        this.tipoAutenticacion = tipoAutenticacion;
    }

    @Column(name = "ES_INTERNO", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public BigInteger getEsInterno() {
        return esInterno;
    }

    public void setEsInterno(BigInteger esInterno) {
        this.esInterno = esInterno;
    }

    @Column(name = "ES_DELEGADO", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public BigInteger getEsDelegado() {
        return esDelegado;
    }

    public void setEsDelegado(BigInteger esDelegado) {
        this.esDelegado = esDelegado;
    }

    @Column(name = "FECHA_INHABILITACION", nullable = true, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaInhabilitacion() {
        return fechaInhabilitacion;
    }

    public void setFechaInhabilitacion(Timestamp fechaInhabilitacion) {
        this.fechaInhabilitacion = fechaInhabilitacion;
    }

    @Column(name = "FECHA_REHABILITACION", nullable = true, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaRehabilitacion() {
        return fechaRehabilitacion;
    }

    public void setFechaRehabilitacion(Timestamp fechaRehabilitacion) {
        this.fechaRehabilitacion = fechaRehabilitacion;
    }

    @Column(name = "ESTADO_USUARIO", nullable = false, insertable = true, updatable = true, length = 15, precision = 0)
    @Basic
    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    @Column(name = "FECHA_BITACORA", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Timestamp fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    @Column(name = "REGISTRO_BITACORA", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
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

        if (idUsuario != that.idUsuario) return false;
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
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoAutenticacion != null ? !tipoAutenticacion.equals(that.tipoAutenticacion) : that.tipoAutenticacion != null)
            return false;
        if (usuario != null ? !usuario.equals(that.usuario) : that.usuario != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUsuario;
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

    @OneToMany(mappedBy = "usrUsuarioByIdUsuario")
    public Collection<PerUsuarioEntity> getPerUsuariosByIdUsuario() {
        return perUsuariosByIdUsuario;
    }

    public void setPerUsuariosByIdUsuario(Collection<PerUsuarioEntity> perUsuariosByIdUsuario) {
        this.perUsuariosByIdUsuario = perUsuariosByIdUsuario;
    }

    @ManyToOne
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", nullable = false)
    public PerPersonaEntity getPerPersonaByIdPersona() {
        return perPersonaByIdPersona;
    }

    public void setPerPersonaByIdPersona(PerPersonaEntity perPersonaByIdPersona) {
        this.perPersonaByIdPersona = perPersonaByIdPersona;
    }

    @OneToMany(mappedBy = "usrUsuarioByIdUsuario")
    public Collection<UsrUsuarioRecursoEntity> getUsrUsuarioRecursosByIdUsuario() {
        return usrUsuarioRecursosByIdUsuario;
    }

    public void setUsrUsuarioRecursosByIdUsuario(Collection<UsrUsuarioRecursoEntity> usrUsuarioRecursosByIdUsuario) {
        this.usrUsuarioRecursosByIdUsuario = usrUsuarioRecursosByIdUsuario;
    }

    @OneToMany(mappedBy = "usrUsuarioByIdUsuario")
    public Collection<UsrUsuarioRolEntity> getUsrUsuarioRolsByIdUsuario() {
        return usrUsuarioRolsByIdUsuario;
    }

    public void setUsrUsuarioRolsByIdUsuario(Collection<UsrUsuarioRolEntity> usrUsuarioRolsByIdUsuario) {
        this.usrUsuarioRolsByIdUsuario = usrUsuarioRolsByIdUsuario;
    }
}
