package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: gveramendi
 * Date: 9/28/13
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "DOC_ESTADO", schema = "ROE", catalog = "")
@Entity
public class DocEstadoEntity {
    private String idEstadoDocumento;

    @javax.persistence.Column(name = "ID_ESTADO_DOCUMENTO", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
    @Id
    public String getIdEstadoDocumento() {
        return idEstadoDocumento;
    }

    public void setIdEstadoDocumento(String idEstadoDocumento) {
        this.idEstadoDocumento = idEstadoDocumento;
    }

    private String descripcion;

    @javax.persistence.Column(name = "DESCRIPCION", nullable = false, insertable = true, updatable = true, length = 120, precision = 0)
    @Basic
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private String estado;

    @javax.persistence.Column(name = "ESTADO", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
    @Basic
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

        DocEstadoEntity that = (DocEstadoEntity) o;

        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (idEstadoDocumento != null ? !idEstadoDocumento.equals(that.idEstadoDocumento) : that.idEstadoDocumento != null)
            return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEstadoDocumento != null ? idEstadoDocumento.hashCode() : 0;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }

    private Collection<DocDocumentoEntity> docDocumentosByIdEstadoDocumento;

    @OneToMany(mappedBy = "docEstadoByIdEstadoDocumento")
    public Collection<DocDocumentoEntity> getDocDocumentosByIdEstadoDocumento() {
        return docDocumentosByIdEstadoDocumento;
    }

    public void setDocDocumentosByIdEstadoDocumento(Collection<DocDocumentoEntity> docDocumentosByIdEstadoDocumento) {
        this.docDocumentosByIdEstadoDocumento = docDocumentosByIdEstadoDocumento;
    }

    private Collection<DocTransicionEntity> docTransicionsByIdEstadoDocumento;

    @OneToMany(mappedBy = "docEstadoByIdEstadoFinal")
    public Collection<DocTransicionEntity> getDocTransicionsByIdEstadoDocumento() {
        return docTransicionsByIdEstadoDocumento;
    }

    public void setDocTransicionsByIdEstadoDocumento(Collection<DocTransicionEntity> docTransicionsByIdEstadoDocumento) {
        this.docTransicionsByIdEstadoDocumento = docTransicionsByIdEstadoDocumento;
    }

    private Collection<DocTransicionEntity> docTransicionsByIdEstadoDocumento_0;

    @OneToMany(mappedBy = "docEstadoByIdEstadoInicial")
    public Collection<DocTransicionEntity> getDocTransicionsByIdEstadoDocumento_0() {
        return docTransicionsByIdEstadoDocumento_0;
    }

    public void setDocTransicionsByIdEstadoDocumento_0(Collection<DocTransicionEntity> docTransicionsByIdEstadoDocumento_0) {
        this.docTransicionsByIdEstadoDocumento_0 = docTransicionsByIdEstadoDocumento_0;
    }
}
