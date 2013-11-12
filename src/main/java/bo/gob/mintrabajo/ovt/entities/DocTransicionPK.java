/*
 * Copyright 2013 gmercado.
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

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author gmercado
 */
@Embeddable
public class DocTransicionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "COD_DOCUMENTO")
    private String codDocumento;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private short version;
    @Basic(optional = false)
    @Column(name = "COD_ESTADO_INICIAL")
    private String codEstadoInicial;
    @Basic(optional = false)
    @Column(name = "COD_ESTADO_FINAL")
    private String codEstadoFinal;

    public DocTransicionPK() {
    }

    public DocTransicionPK(String codDocumento, short version, String codEstadoInicial, String codEstadoFinal) {
        this.codDocumento = codDocumento;
        this.version = version;
        this.codEstadoInicial = codEstadoInicial;
        this.codEstadoFinal = codEstadoFinal;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public String getCodEstadoInicial() {
        return codEstadoInicial;
    }

    public void setCodEstadoInicial(String codEstadoInicial) {
        this.codEstadoInicial = codEstadoInicial;
    }

    public String getCodEstadoFinal() {
        return codEstadoFinal;
    }

    public void setCodEstadoFinal(String codEstadoFinal) {
        this.codEstadoFinal = codEstadoFinal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codDocumento != null ? codDocumento.hashCode() : 0);
        hash += (int) version;
        hash += (codEstadoInicial != null ? codEstadoInicial.hashCode() : 0);
        hash += (codEstadoFinal != null ? codEstadoFinal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocTransicionPK)) {
            return false;
        }
        DocTransicionPK other = (DocTransicionPK) object;
        if ((this.codDocumento == null && other.codDocumento != null) || (this.codDocumento != null && !this.codDocumento.equals(other.codDocumento))) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if ((this.codEstadoInicial == null && other.codEstadoInicial != null) || (this.codEstadoInicial != null && !this.codEstadoInicial.equals(other.codEstadoInicial))) {
            return false;
        }
        if ((this.codEstadoFinal == null && other.codEstadoFinal != null) || (this.codEstadoFinal != null && !this.codEstadoFinal.equals(other.codEstadoFinal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocTransicionPK[ codDocumento=" + codDocumento + ", version=" + version + ", codEstadoInicial=" + codEstadoInicial + ", codEstadoFinal=" + codEstadoFinal + " ]";
    }
    
}
