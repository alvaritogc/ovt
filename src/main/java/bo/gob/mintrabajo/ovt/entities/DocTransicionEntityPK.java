package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: rvelasquez
 * Date: 10/10/13
 * Time: 2:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class DocTransicionEntityPK implements Serializable {
    private String codDocumento;

    @Id
    @Column(name = "COD_DOCUMENTO")
    String getCodDocumento() {
        return codDocumento;
    }

    void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    private Integer version;

    @Id
    @Column(name = "VERSION")
    Integer getVersion() {
        return version;
    }

    void setVersion(Integer version) {
        this.version = version;
    }

    private String codEstadoInicial;

    @Id
    @Column(name = "COD_ESTADO_INICIAL")
    String getCodEstadoInicial() {
        return codEstadoInicial;
    }

    void setCodEstadoInicial(String codEstadoInicial) {
        this.codEstadoInicial = codEstadoInicial;
    }

    private String codEstadoFinal;

    @Id
    @Column(name = "COD_ESTADO_FINAL")
    String getCodEstadoFinal() {
        return codEstadoFinal;
    }

    void setCodEstadoFinal(String codEstadoFinal) {
        this.codEstadoFinal = codEstadoFinal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocTransicionEntityPK that = (DocTransicionEntityPK) o;

        if (codDocumento != null ? !codDocumento.equals(that.codDocumento) : that.codDocumento != null) return false;
        if (codEstadoFinal != null ? !codEstadoFinal.equals(that.codEstadoFinal) : that.codEstadoFinal != null)
            return false;
        if (codEstadoInicial != null ? !codEstadoInicial.equals(that.codEstadoInicial) : that.codEstadoInicial != null)
            return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codDocumento != null ? codDocumento.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (codEstadoInicial != null ? codEstadoInicial.hashCode() : 0);
        result = 31 * result + (codEstadoFinal != null ? codEstadoFinal.hashCode() : 0);
        return result;
    }
}
