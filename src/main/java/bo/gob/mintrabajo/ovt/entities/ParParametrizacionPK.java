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
public class ParParametrizacionPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_PARAMETRO")
    private String idParametro;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;

    public ParParametrizacionPK() {
    }

    public ParParametrizacionPK(String idParametro, String valor) {
        this.idParametro = idParametro;
        this.valor = valor;
    }

    public String getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(String idParametro) {
        this.idParametro = idParametro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametro != null ? idParametro.hashCode() : 0);
        hash += (valor != null ? valor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParParametrizacionPK)) {
            return false;
        }
        ParParametrizacionPK other = (ParParametrizacionPK) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        if ((this.valor == null && other.valor != null) || (this.valor != null && !this.valor.equals(other.valor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParParametrizacionPK[ idParametro=" + idParametro + ", valor=" + valor + " ]";
    }
    
}
