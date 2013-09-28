package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: gveramendi
 * Date: 9/28/13
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "DOC_TRANSICION", schema = "ROE", catalog = "")
@Entity
public class DocTransicionEntity {
    private int idTransicion;

    @javax.persistence.Column(name = "ID_TRANSICION", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdTransicion() {
        return idTransicion;
    }

    public void setIdTransicion(int idTransicion) {
        this.idTransicion = idTransicion;
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

        DocTransicionEntity that = (DocTransicionEntity) o;

        if (idTransicion != that.idTransicion) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idTransicion;
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }

    private DocDocumentoEntity docDocumentoByIdDocumento;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO", nullable = false)
    public DocDocumentoEntity getDocDocumentoByIdDocumento() {
        return docDocumentoByIdDocumento;
    }

    public void setDocDocumentoByIdDocumento(DocDocumentoEntity docDocumentoByIdDocumento) {
        this.docDocumentoByIdDocumento = docDocumentoByIdDocumento;
    }

    private DocEstadoEntity docEstadoByIdEstadoFinal;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "ID_ESTADO_FINAL", referencedColumnName = "ID_ESTADO_DOCUMENTO", nullable = false)
    public DocEstadoEntity getDocEstadoByIdEstadoFinal() {
        return docEstadoByIdEstadoFinal;
    }

    public void setDocEstadoByIdEstadoFinal(DocEstadoEntity docEstadoByIdEstadoFinal) {
        this.docEstadoByIdEstadoFinal = docEstadoByIdEstadoFinal;
    }

    private DocEstadoEntity docEstadoByIdEstadoInicial;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "ID_ESTADO_INICIAL", referencedColumnName = "ID_ESTADO_DOCUMENTO", nullable = false)
    public DocEstadoEntity getDocEstadoByIdEstadoInicial() {
        return docEstadoByIdEstadoInicial;
    }

    public void setDocEstadoByIdEstadoInicial(DocEstadoEntity docEstadoByIdEstadoInicial) {
        this.docEstadoByIdEstadoInicial = docEstadoByIdEstadoInicial;
    }
}
