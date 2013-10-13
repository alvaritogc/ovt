package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: rvelasquez
 * Date: 10/12/13
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class DocBinarioEntityPK implements Serializable {
    private Integer idBinario;

    @Id
    @Column(name = "ID_BINARIO")
    Integer getIdBinario() {
        return idBinario;
    }

    void setIdBinario(Integer idBinario) {
        this.idBinario = idBinario;
    }

    private Integer idDocumento;

    @Id
    @Column(name = "ID_DOCUMENTO")
    Integer getIdDocumento() {
        return idDocumento;
    }

    void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocBinarioEntityPK that = (DocBinarioEntityPK) o;

        if (idBinario != null ? !idBinario.equals(that.idBinario) : that.idBinario != null) return false;
        if (idDocumento != null ? !idDocumento.equals(that.idDocumento) : that.idDocumento != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idBinario != null ? idBinario.hashCode() : 0;
        result = 31 * result + (idDocumento != null ? idDocumento.hashCode() : 0);
        return result;
    }
}
