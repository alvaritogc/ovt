/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo.gob.mintrabajo.ovt.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author rvelasquez
 */
@Embeddable
public class DocBinarioPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_BINARIO")
    private long idBinario;
    @Basic(optional = false)
    @Column(name = "ID_DOCUMENTO")
    private long idDocumento;

    public DocBinarioPK() {
    }

    public DocBinarioPK(long idBinario, long idDocumento) {
        this.idBinario = idBinario;
        this.idDocumento = idDocumento;
    }

    public long getIdBinario() {
        return idBinario;
    }

    public void setIdBinario(long idBinario) {
        this.idBinario = idBinario;
    }

    public long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(long idDocumento) {
        this.idDocumento = idDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idBinario;
        hash += (int) idDocumento;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocBinarioPK)) {
            return false;
        }
        DocBinarioPK other = (DocBinarioPK) object;
        if (this.idBinario != other.idBinario) {
            return false;
        }
        if (this.idDocumento != other.idDocumento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocBinarioPK[ idBinario=" + idBinario + ", idDocumento=" + idDocumento + " ]";
    }
    
}
