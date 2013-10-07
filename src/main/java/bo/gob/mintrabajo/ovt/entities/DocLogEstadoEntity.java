package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 06-10-13
 */
@javax.persistence.Table(name = "DOC_LOG_ESTADO", schema = "OVT", catalog = "")
@Entity
public class DocLogEstadoEntity {
    private Integer idLogestado;

    @javax.persistence.Column(name = "ID_LOGESTADO")
    @Id
    public Integer getIdLogestado() {
        return idLogestado;
    }

    public void setIdLogestado(Integer idLogestado) {
        this.idLogestado = idLogestado;
    }

    private Integer idDocumento;

    @javax.persistence.Column(name = "ID_DOCUMENTO")
    @Basic
    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    private String codEstadoInicial;

    @javax.persistence.Column(name = "COD_ESTADO_INICIAL")
    @Basic
    public String getCodEstadoInicial() {
        return codEstadoInicial;
    }

    public void setCodEstadoInicial(String codEstadoInicial) {
        this.codEstadoInicial = codEstadoInicial;
    }

    private String codEstadoFinal;

    @javax.persistence.Column(name = "COD_ESTADO_FINAL")
    @Basic
    public String getCodEstadoFinal() {
        return codEstadoFinal;
    }

    public void setCodEstadoFinal(String codEstadoFinal) {
        this.codEstadoFinal = codEstadoFinal;
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

        DocLogEstadoEntity that = (DocLogEstadoEntity) o;

        if (codEstadoFinal != null ? !codEstadoFinal.equals(that.codEstadoFinal) : that.codEstadoFinal != null)
            return false;
        if (codEstadoInicial != null ? !codEstadoInicial.equals(that.codEstadoInicial) : that.codEstadoInicial != null)
            return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (idDocumento != null ? !idDocumento.equals(that.idDocumento) : that.idDocumento != null) return false;
        if (idLogestado != null ? !idLogestado.equals(that.idLogestado) : that.idLogestado != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLogestado != null ? idLogestado.hashCode() : 0;
        result = 31 * result + (idDocumento != null ? idDocumento.hashCode() : 0);
        result = 31 * result + (codEstadoInicial != null ? codEstadoInicial.hashCode() : 0);
        result = 31 * result + (codEstadoFinal != null ? codEstadoFinal.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
