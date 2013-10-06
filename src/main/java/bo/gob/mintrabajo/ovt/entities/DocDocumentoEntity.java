package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 06-10-13
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

    private String codEstado;

    @javax.persistence.Column(name = "COD_ESTADO")
    @Basic
    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    private String idEstadoDocumento;

    @javax.persistence.Column(name = "ID_ESTADO_DOCUMENTO")
    @Basic
    public String getIdEstadoDocumento() {
        return idEstadoDocumento;
    }

    public void setIdEstadoDocumento(String idEstadoDocumento) {
        this.idEstadoDocumento = idEstadoDocumento;
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
    
    private Integer version;
    @javax.persistence.Column(name = "VERSION")
    @Basic
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
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
    
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocDocumentoEntity that = (DocDocumentoEntity) o;

        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (fechaDocumento != null ? !fechaDocumento.equals(that.fechaDocumento) : that.fechaDocumento != null)
            return false;
        if (fechaReferenca != null ? !fechaReferenca.equals(that.fechaReferenca) : that.fechaReferenca != null)
            return false;
        if (idDocumento != null ? !idDocumento.equals(that.idDocumento) : that.idDocumento != null) return false;
        if (idEstadoDocumento != null ? !idEstadoDocumento.equals(that.idEstadoDocumento) : that.idEstadoDocumento != null)
            return false;
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
        result = 31 * result + (numeroDocumento != null ? numeroDocumento.hashCode() : 0);
        result = 31 * result + (fechaDocumento != null ? fechaDocumento.hashCode() : 0);
        result = 31 * result + (fechaReferenca != null ? fechaReferenca.hashCode() : 0);
        result = 31 * result + (tipoMedioRegistro != null ? tipoMedioRegistro.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        result = 31 * result + (idEstadoDocumento != null ? idEstadoDocumento.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DocDocumentoEntity{" + "idDocumento=" + idDocumento + ", numeroDocumento=" + numeroDocumento + ", fechaDocumento=" + fechaDocumento + ", fechaReferenca=" + fechaReferenca + ", tipoMedioRegistro=" + tipoMedioRegistro + ", fechaBitacora=" + fechaBitacora + ", registroBitacora=" + registroBitacora + ", codEstado=" + codEstado + ", idEstadoDocumento=" + idEstadoDocumento + ", idPersona=" + idPersona + ", idUnidad=" + idUnidad + ", codDocumento=" + codDocumento + ", version=" + version + ", idDocumentoRef=" + idDocumentoRef + '}';
    }
    
    
}
