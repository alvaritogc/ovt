package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Renato Velasquez.
 * Date: 06-10-13
 */
public class DocNumeracionEntityPK implements Serializable {
    private String codDocumento;
    private Integer version;

@Id@Column(name = "COD_DOCUMENTO")
public String getCodDocumento() {
    return codDocumento;
}

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    @Id@Column(name = "VERSION")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocNumeracionEntityPK that = (DocNumeracionEntityPK) o;

        if (codDocumento != null ? !codDocumento.equals(that.codDocumento) : that.codDocumento != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codDocumento != null ? codDocumento.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
}}
