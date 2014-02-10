/*
 * Copyright 2013 rvelasquez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

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
