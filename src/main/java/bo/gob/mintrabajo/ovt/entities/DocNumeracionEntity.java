package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User: Renato Velasquez.
 * Date: 05-10-13
 */
@javax.persistence.Table(name = "DOC_NUMERACION", schema = "ROE", catalog = "")
@Entity
public class DocNumeracionEntity {
    private String codDocumento;

    @javax.persistence.Column(name = "COD_DOCUMENTO")
    @Id
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

    private String gestion;

    @javax.persistence.Column(name = "GESTION")
    @Basic
    public String getGestion() {
        return gestion;
    }

    public void setGestion(String gestion) {
        this.gestion = gestion;
    }

    private String mes;

    @javax.persistence.Column(name = "MES")
    @Basic
    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    private Integer ultimoNumero;

    @javax.persistence.Column(name = "ULTIMO_NUMERO")
    @Basic
    public Integer getUltimoNumero() {
        return ultimoNumero;
    }

    public void setUltimoNumero(Integer ultimoNumero) {
        this.ultimoNumero = ultimoNumero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocNumeracionEntity that = (DocNumeracionEntity) o;

        if (codDocumento != null ? !codDocumento.equals(that.codDocumento) : that.codDocumento != null) return false;
        if (gestion != null ? !gestion.equals(that.gestion) : that.gestion != null) return false;
        if (mes != null ? !mes.equals(that.mes) : that.mes != null) return false;
        if (ultimoNumero != null ? !ultimoNumero.equals(that.ultimoNumero) : that.ultimoNumero != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codDocumento != null ? codDocumento.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (gestion != null ? gestion.hashCode() : 0);
        result = 31 * result + (mes != null ? mes.hashCode() : 0);
        result = 31 * result + (ultimoNumero != null ? ultimoNumero.hashCode() : 0);
        return result;
    }
}
