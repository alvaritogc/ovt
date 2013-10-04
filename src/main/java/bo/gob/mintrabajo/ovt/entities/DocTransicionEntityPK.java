package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Renato Velasquez.
 * Date: 03-10-13
 */
public class DocTransicionEntityPK implements Serializable {
    private String codDocumento;
    private String codEstadoInicial;@Id
                                    @Column(name = "COD_DOCUMENTO")
                                    public String getCodDocumento() {
        return codDocumento;
    }

    private String codEstadoFinal;

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
        }

    @Id@Column(name = "COD_ESTADO_INICIAL")
public String getCodEstadoInicial() {
    return codEstadoInicial;
}

    public void setCodEstadoInicial(String codEstadoInicial) {
        this.codEstadoInicial = codEstadoInicial;
    }
@Id@Column(name = "COD_ESTADO_FINAL")
public String getCodEstadoFinal() {
    return codEstadoFinal;
}

    public void setCodEstadoFinal(String codEstadoFinal) {
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

        return true;
    }

    @Override
    public int hashCode() {
        int result = codDocumento != null ? codDocumento.hashCode() : 0;
        result = 31 * result + (codEstadoInicial != null ? codEstadoInicial.hashCode() : 0);
        result = 31 * result + (codEstadoFinal != null ? codEstadoFinal.hashCode() : 0);
        return result;
}}
