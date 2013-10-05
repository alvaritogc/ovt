package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.sql.Blob;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 05-10-13
 */
@javax.persistence.Table(name = "DOC_BINARIO", schema = "ROE", catalog = "")
@Entity
public class DocBinarioEntity {
    private Integer idBinario;

    @javax.persistence.Column(name = "ID_BINARIO")
    @Id
    public Integer getIdBinario() {
        return idBinario;
    }

    public void setIdBinario(Integer idBinario) {
        this.idBinario = idBinario;
    }

    private String tipoDocumento;

    @javax.persistence.Column(name = "TIPO_DOCUMENTO")
    @Basic
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    private Blob binario;

    @javax.persistence.Column(name = "BINARIO")
    @Lob
    @Basic
    public Blob getBinario() {
        return binario;
    }

    public void setBinario(Blob binario) {
        this.binario = binario;
    }

    private String metadata;

    @javax.persistence.Column(name = "METADATA")
    @Basic
    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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

        DocBinarioEntity that = (DocBinarioEntity) o;

        if (binario != null ? !binario.equals(that.binario) : that.binario != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (idBinario != null ? !idBinario.equals(that.idBinario) : that.idBinario != null) return false;
        if (metadata != null ? !metadata.equals(that.metadata) : that.metadata != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoDocumento != null ? !tipoDocumento.equals(that.tipoDocumento) : that.tipoDocumento != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idBinario != null ? idBinario.hashCode() : 0;
        result = 31 * result + (tipoDocumento != null ? tipoDocumento.hashCode() : 0);
        result = 31 * result + (binario != null ? binario.hashCode() : 0);
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
