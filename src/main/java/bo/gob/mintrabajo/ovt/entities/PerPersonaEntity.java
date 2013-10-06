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
@javax.persistence.Table(name = "PER_PERSONA", schema = "ROE", catalog = "")
@Entity
public class PerPersonaEntity {
    private String idPersona;

    @javax.persistence.Column(name = "ID_PERSONA")
    @Id
    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    private String tipoIdentificacion;

    @javax.persistence.Column(name = "TIPO_IDENTIFICACION")
    @Basic
    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    private String nroIdentificacion;

    @javax.persistence.Column(name = "NRO_IDENTIFICACION")
    @Basic
    public String getNroIdentificacion() {
        return nroIdentificacion;
    }

    public void setNroIdentificacion(String nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    private String nombreRazonSocial;

    @javax.persistence.Column(name = "NOMBRE_RAZON_SOCIAL")
    @Basic
    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    private String apellidoPaterno;

    @javax.persistence.Column(name = "APELLIDO_PATERNO")
    @Basic
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    private String apellidoMaterno;

    @javax.persistence.Column(name = "APELLIDO_MATERNO")
    @Basic
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    private BigInteger esNatural;

    @javax.persistence.Column(name = "ES_NATURAL")
    @Basic
    public BigInteger getEsNatural() {
        return esNatural;
    }

    public void setEsNatural(BigInteger esNatural) {
        this.esNatural = esNatural;
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

        PerPersonaEntity that = (PerPersonaEntity) o;

        if (apellidoMaterno != null ? !apellidoMaterno.equals(that.apellidoMaterno) : that.apellidoMaterno != null)
            return false;
        if (apellidoPaterno != null ? !apellidoPaterno.equals(that.apellidoPaterno) : that.apellidoPaterno != null)
            return false;
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
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
