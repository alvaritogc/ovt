package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * User: gveramendi
 * Date: 9/25/13
 * Time: 5:33 PM
 */

@javax.persistence.Table(name = "PER_PERSONA", schema = "ROE", catalog = "")
@Entity
public class PerPersonaEntity implements Serializable {
    private String idPersona;

    @javax.persistence.Column(name = "ID_PERSONA", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Id
    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    private String tipoIdentificacion;

    @javax.persistence.Column(name = "TIPO_IDENTIFICACION", nullable = false, insertable = true, updatable = true, length = 15, precision = 0)
    @Basic
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    private String nroIdentificacion;

    @javax.persistence.Column(name = "NRO_IDENTIFICACION", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
    @Basic
    public String getNroIdentificacion() {
        return nroIdentificacion;
    }

    public void setNroIdentificacion(String nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    private String nombreRazonSocial;

    @javax.persistence.Column(name = "NOMBRE_RAZON_SOCIAL", nullable = false, insertable = true, updatable = true, length = 120, precision = 0)
    @Basic
    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    private String apellidoPaterno;

    @javax.persistence.Column(name = "APELLIDO_PATERNO", nullable = true, insertable = true, updatable = true, length = 80, precision = 0)
    @Basic
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    private String apellidoMaterno;

    @javax.persistence.Column(name = "APELLIDO_MATERNO", nullable = true, insertable = true, updatable = true, length = 80, precision = 0)
    @Basic
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    private BigInteger esNatural;

    @javax.persistence.Column(name = "ES_NATURAL", nullable = false, insertable = true, updatable = true, length = 1, precision = 0)
    @Basic
    public BigInteger getEsNatural() {
        return esNatural;
    }

    public void setEsNatural(BigInteger esNatural) {
        this.esNatural = esNatural;
    }

    private String codLocalidad;

    @javax.persistence.Column(name = "COD_LOCALIDAD", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    public String getCodLocalidad() {
        return codLocalidad;
    }

    public void setCodLocalidad(String codLocalidad) {
        this.codLocalidad = codLocalidad;
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

        PerPersonaEntity that = (PerPersonaEntity) o;

        if (apellidoMaterno != null ? !apellidoMaterno.equals(that.apellidoMaterno) : that.apellidoMaterno != null)
            return false;
        if (apellidoPaterno != null ? !apellidoPaterno.equals(that.apellidoPaterno) : that.apellidoPaterno != null)
            return false;
        if (codLocalidad != null ? !codLocalidad.equals(that.codLocalidad) : that.codLocalidad != null) return false;
        if (esNatural != null ? !esNatural.equals(that.esNatural) : that.esNatural != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (idPersona != null ? !idPersona.equals(that.idPersona) : that.idPersona != null) return false;
        if (nombreRazonSocial != null ? !nombreRazonSocial.equals(that.nombreRazonSocial) : that.nombreRazonSocial != null)
            return false;
        if (nroIdentificacion != null ? !nroIdentificacion.equals(that.nroIdentificacion) : that.nroIdentificacion != null)
            return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoIdentificacion != null ? !tipoIdentificacion.equals(that.tipoIdentificacion) : that.tipoIdentificacion != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPersona != null ? idPersona.hashCode() : 0;
        result = 31 * result + (tipoIdentificacion != null ? tipoIdentificacion.hashCode() : 0);
        result = 31 * result + (nroIdentificacion != null ? nroIdentificacion.hashCode() : 0);
        result = 31 * result + (nombreRazonSocial != null ? nombreRazonSocial.hashCode() : 0);
        result = 31 * result + (apellidoPaterno != null ? apellidoPaterno.hashCode() : 0);
        result = 31 * result + (apellidoMaterno != null ? apellidoMaterno.hashCode() : 0);
        result = 31 * result + (esNatural != null ? esNatural.hashCode() : 0);
        result = 31 * result + (codLocalidad != null ? codLocalidad.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }

    private Collection<PerUnidadEntity> perUnidadsByIdPersona;

    @OneToMany(mappedBy = "perPersonaByIdPersona")
    public Collection<PerUnidadEntity> getPerUnidadsByIdPersona() {
        return perUnidadsByIdPersona;
    }

    public void setPerUnidadsByIdPersona(Collection<PerUnidadEntity> perUnidadsByIdPersona) {
        this.perUnidadsByIdPersona = perUnidadsByIdPersona;
    }

    private Collection<UsrUsuarioEntity> usrUsuariosByIdPersona;

    @OneToMany(mappedBy = "perPersonaByIdPersona")
    public Collection<UsrUsuarioEntity> getUsrUsuariosByIdPersona() {
        return usrUsuariosByIdPersona;
    }

    public void setUsrUsuariosByIdPersona(Collection<UsrUsuarioEntity> usrUsuariosByIdPersona) {
        this.usrUsuariosByIdPersona = usrUsuariosByIdPersona;
    }
}
