package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 05-10-13
 */
@javax.persistence.Table(name = "DOC_DOCUMENTO", schema = "ROE", catalog = "")
@Entity
public class DocDocumentoEntity {
    private Integer idDocumento;

    @javax.persistence.Column(name = "ID_DOCUMENTO")
    @Id
    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    private String idPersona;

    @javax.persistence.Column(name = "ID_PERSONA")
    @Basic
    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    private Integer idUnidad;

    @javax.persistence.Column(name = "ID_UNIDAD")
    @Basic
    public Integer getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    private String codDocumento;

    @javax.persistence.Column(name = "COD_DOCUMENTO")
    @Basic
    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    private Integer numeroDocumento;

    @javax.persistence.Column(name = "NUMERO_DOCUMENTO")
    @Basic
    public Integer getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Integer numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    private Timestamp fechaDocumento;

    @javax.persistence.Column(name = "FECHA_DOCUMENTO")
    @Basic
    public Timestamp getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Timestamp fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    private Integer idDocumentoRef;

    @javax.persistence.Column(name = "ID_DOCUMENTO_REF")
    @Basic
    public Integer getIdDocumentoRef() {
        return idDocumentoRef;
    }

    public void setIdDocumentoRef(Integer idDocumentoRef) {
        this.idDocumentoRef = idDocumentoRef;
    }

    private String codEstado;

    @javax.persistence.Column(name = "COD_ESTADO")
    @Basic
    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    private Timestamp fechaReferenca;

    @javax.persistence.Column(name = "FECHA_REFERENCA")
    @Basic
    public Timestamp getFechaReferenca() {
        return fechaReferenca;
    }

    public void setFechaReferenca(Timestamp fechaReferenca) {
        this.fechaReferenca = fechaReferenca;
    }

    private String tipoMedioRegistro;

    @javax.persistence.Column(name = "TIPO_MEDIO_REGISTRO")
    @Basic
    public String getTipoMedioRegistro() {
        return tipoMedioRegistro;
    }

    public void setTipoMedioRegistro(String tipoMedioRegistro) {
        this.tipoMedioRegistro = tipoMedioRegistro;
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

        DocDocumentoEntity that = (DocDocumentoEntity) o;

        if (codDocumento != null ? !codDocumento.equals(that.codDocumento) : that.codDocumento != null) return false;
        if (codEstado != null ? !codEstado.equals(that.codEstado) : that.codEstado != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (fechaDocumento != null ? !fechaDocumento.equals(that.fechaDocumento) : that.fechaDocumento != null)
            return false;
        if (fechaReferenca != null ? !fechaReferenca.equals(that.fechaReferenca) : that.fechaReferenca != null)
            return false;
        if (idDocumento != null ? !idDocumento.equals(that.idDocumento) : that.idDocumento != null) return false;
        if (idDocumentoRef != null ? !idDocumentoRef.equals(that.idDocumentoRef) : that.idDocumentoRef != null)
            return false;
        if (idPersona != null ? !idPersona.equals(that.idPersona) : that.idPersona != null) return false;
        if (idUnidad != null ? !idUnidad.equals(that.idUnidad) : that.idUnidad != null) return false;
        if (numeroDocumento != null ? !numeroDocumento.equals(that.numeroDocumento) : that.numeroDocumento != null)
            return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoMedioRegistro != null ? !tipoMedioRegistro.equals(that.tipoMedioRegistro) : that.tipoMedioRegistro != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDocumento != null ? idDocumento.hashCode() : 0;
        result = 31 * result + (idPersona != null ? idPersona.hashCode() : 0);
        result = 31 * result + (idUnidad != null ? idUnidad.hashCode() : 0);
        result = 31 * result + (codDocumento != null ? codDocumento.hashCode() : 0);
        result = 31 * result + (numeroDocumento != null ? numeroDocumento.hashCode() : 0);
        result = 31 * result + (fechaDocumento != null ? fechaDocumento.hashCode() : 0);
        result = 31 * result + (idDocumentoRef != null ? idDocumentoRef.hashCode() : 0);
        result = 31 * result + (codEstado != null ? codEstado.hashCode() : 0);
        result = 31 * result + (fechaReferenca != null ? fechaReferenca.hashCode() : 0);
        result = 31 * result + (tipoMedioRegistro != null ? tipoMedioRegistro.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
