package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: gveramendi
 * Date: 9/28/13
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "DOC_DOCUMENTO", schema = "ROE", catalog = "")
@Entity
public class DocDocumentoEntity {
    private int idDocumento;

    @javax.persistence.Column(name = "ID_DOCUMENTO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    private int numeroDocumento;

    @javax.persistence.Column(name = "NUMERO_DOCUMENTO", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(int numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    private Timestamp fechaDocumento;

    @javax.persistence.Column(name = "FECHA_DOCUMENTO", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Timestamp fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    private Timestamp fechaReferenca;

    @javax.persistence.Column(name = "FECHA_REFERENCA", nullable = true, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaReferenca() {
        return fechaReferenca;
    }

    public void setFechaReferenca(Timestamp fechaReferenca) {
        this.fechaReferenca = fechaReferenca;
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

        DocDocumentoEntity that = (DocDocumentoEntity) o;

        if (idDocumento != that.idDocumento) return false;
        if (numeroDocumento != that.numeroDocumento) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (fechaDocumento != null ? !fechaDocumento.equals(that.fechaDocumento) : that.fechaDocumento != null)
            return false;
        if (fechaReferenca != null ? !fechaReferenca.equals(that.fechaReferenca) : that.fechaReferenca != null)
            return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDocumento;
        result = 31 * result + numeroDocumento;
        result = 31 * result + (fechaDocumento != null ? fechaDocumento.hashCode() : 0);
        result = 31 * result + (fechaReferenca != null ? fechaReferenca.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }

    private Collection<DocBinariosEntity> docBinariosesByIdDocumento;

    @OneToMany(mappedBy = "docDocumentoByIdDocumento")
    public Collection<DocBinariosEntity> getDocBinariosesByIdDocumento() {
        return docBinariosesByIdDocumento;
    }

    public void setDocBinariosesByIdDocumento(Collection<DocBinariosEntity> docBinariosesByIdDocumento) {
        this.docBinariosesByIdDocumento = docBinariosesByIdDocumento;
    }

    private DocDefinicionEntity docDefinicionByCodDocumento;

    @ManyToOne
    @JoinColumn(name = "COD_DOCUMENTO", referencedColumnName = "COD_DOCUMENTO", nullable = false)
    public DocDefinicionEntity getDocDefinicionByCodDocumento() {
        return docDefinicionByCodDocumento;
    }

    public void setDocDefinicionByCodDocumento(DocDefinicionEntity docDefinicionByCodDocumento) {
        this.docDefinicionByCodDocumento = docDefinicionByCodDocumento;
    }

    private DocDocumentoEntity docDocumentoByIdDocumentoRef;

    @ManyToOne
    @JoinColumn(name = "ID_DOCUMENTO_REF", referencedColumnName = "ID_DOCUMENTO")
    public DocDocumentoEntity getDocDocumentoByIdDocumentoRef() {
        return docDocumentoByIdDocumentoRef;
    }

    public void setDocDocumentoByIdDocumentoRef(DocDocumentoEntity docDocumentoByIdDocumentoRef) {
        this.docDocumentoByIdDocumentoRef = docDocumentoByIdDocumentoRef;
    }

    private Collection<DocDocumentoEntity> docDocumentosByIdDocumento;

    @OneToMany(mappedBy = "docDocumentoByIdDocumentoRef")
    public Collection<DocDocumentoEntity> getDocDocumentosByIdDocumento() {
        return docDocumentosByIdDocumento;
    }

    public void setDocDocumentosByIdDocumento(Collection<DocDocumentoEntity> docDocumentosByIdDocumento) {
        this.docDocumentosByIdDocumento = docDocumentosByIdDocumento;
    }

    private DocEstadoEntity docEstadoByIdEstadoDocumento;

    @ManyToOne
    @JoinColumn(name = "ID_ESTADO_DOCUMENTO", referencedColumnName = "ID_ESTADO_DOCUMENTO", nullable = false)
    public DocEstadoEntity getDocEstadoByIdEstadoDocumento() {
        return docEstadoByIdEstadoDocumento;
    }

    public void setDocEstadoByIdEstadoDocumento(DocEstadoEntity docEstadoByIdEstadoDocumento) {
        this.docEstadoByIdEstadoDocumento = docEstadoByIdEstadoDocumento;
    }

    private PerUnidadEntity perUnidad;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "ID_UNIDAD", referencedColumnName = "ID_UNIDAD", nullable = false), @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", nullable = false)})
    public PerUnidadEntity getPerUnidad() {
        return perUnidad;
    }

    public void setPerUnidad(PerUnidadEntity perUnidad) {
        this.perUnidad = perUnidad;
    }

    private Collection<DocTransicionEntity> docTransicionsByIdDocumento;

    @OneToMany(mappedBy = "docDocumentoByIdDocumento")
    public Collection<DocTransicionEntity> getDocTransicionsByIdDocumento() {
        return docTransicionsByIdDocumento;
    }

    public void setDocTransicionsByIdDocumento(Collection<DocTransicionEntity> docTransicionsByIdDocumento) {
        this.docTransicionsByIdDocumento = docTransicionsByIdDocumento;
    }
}
