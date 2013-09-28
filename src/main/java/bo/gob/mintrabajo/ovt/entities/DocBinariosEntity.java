package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: gveramendi
 * Date: 9/28/13
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "DOC_BINARIOS", schema = "ROE", catalog = "")
@Entity
public class DocBinariosEntity {
    private int idBinario;
    private String tipoDocumento;
    private Blob binario;
    private String metadata;
    private Timestamp fechaBitacora;
    private String registroBitacora;
    private DocDocumentoEntity docDocumentoByIdDocumento;

    @javax.persistence.Column(name = "ID_BINARIO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdBinario() {
        return idBinario;
    }

    public void setIdBinario(int idBinario) {
        this.idBinario = idBinario;
    }

    @javax.persistence.Column(name = "TIPO_DOCUMENTO", nullable = false, insertable = true, updatable = true, length = 15, precision = 0)
    @Basic
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @javax.persistence.Column(name = "BINARIO", nullable = false, insertable = true, updatable = true, length = 4000, precision = 0)
    @Lob
    @Basic
    public Blob getBinario() {
        return binario;
    }

    public void setBinario(Blob binario) {
        this.binario = binario;
    }

    @javax.persistence.Column(name = "METADATA", nullable = false, insertable = true, updatable = true, length = 500, precision = 0)
    @Basic
    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @javax.persistence.Column(name = "FECHA_BITACORA", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Timestamp fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    @javax.persistence.Column(name = "REGISTRO_BITACORA", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
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

        DocBinariosEntity that = (DocBinariosEntity) o;

        if (idBinario != that.idBinario) return false;
        if (binario != null ? !binario.equals(that.binario) : that.binario != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (metadata != null ? !metadata.equals(that.metadata) : that.metadata != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoDocumento != null ? !tipoDocumento.equals(that.tipoDocumento) : that.tipoDocumento != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idBinario;
        result = 31 * result + (tipoDocumento != null ? tipoDocumento.hashCode() : 0);
        result = 31 * result + (binario != null ? binario.hashCode() : 0);
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO", nullable = false)
    public DocDocumentoEntity getDocDocumentoByIdDocumento() {
        return docDocumentoByIdDocumento;
    }

    public void setDocDocumentoByIdDocumento(DocDocumentoEntity docDocumentoByIdDocumento) {
        this.docDocumentoByIdDocumento = docDocumentoByIdDocumento;
    }
}
