package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 05-10-13
 */
@javax.persistence.IdClass(bo.gob.mintrabajo.ovt.entities.DocTransicionEntityPK.class)
@javax.persistence.Table(name = "DOC_TRANSICION", schema = "ROE", catalog = "")
@Entity
public class DocTransicionEntity {
    private String codDocumento;

    @javax.persistence.Column(name = "COD_DOCUMENTO")
    @Id
    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    private String codEstadoInicial;

    @javax.persistence.Column(name = "COD_ESTADO_INICIAL")
    @Id
    public String getCodEstadoInicial() {
        return codEstadoInicial;
    }

    public void setCodEstadoInicial(String codEstadoInicial) {
        this.codEstadoInicial = codEstadoInicial;
    }

    private String codEstadoFinal;

    @javax.persistence.Column(name = "COD_ESTADO_FINAL")
    @Id
    public String getCodEstadoFinal() {
        return codEstadoFinal;
    }

    public void setCodEstadoFinal(String codEstadoFinal) {
        this.codEstadoFinal = codEstadoFinal;
    }

    private String estado;

    @javax.persistence.Column(name = "ESTADO")
    @Basic
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

        DocTransicionEntity that = (DocTransicionEntity) o;

        if (codDocumento != null ? !codDocumento.equals(that.codDocumento) : that.codDocumento != null) return false;
        if (codEstadoFinal != null ? !codEstadoFinal.equals(that.codEstadoFinal) : that.codEstadoFinal != null)
            return false;
        if (codEstadoInicial != null ? !codEstadoInicial.equals(that.codEstadoInicial) : that.codEstadoInicial != null)
            return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codDocumento != null ? codDocumento.hashCode() : 0;
        result = 31 * result + (codEstadoInicial != null ? codEstadoInicial.hashCode() : 0);
        result = 31 * result + (codEstadoFinal != null ? codEstadoFinal.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
