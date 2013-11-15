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

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author rvelasquez
 */
@Embeddable
public class ParCalendarioPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "GESTION")
    private String gestion;
    @Basic(optional = false)
    @Column(name = "TIPO_PERIODO")
    private String tipoPeriodo;

    public ParCalendarioPK() {
    }

    public ParCalendarioPK(String gestion, String tipoPeriodo) {
        this.gestion = gestion;
        this.tipoPeriodo = tipoPeriodo;
    }

    public String getGestion() {
        return gestion;
    }

    public void setGestion(String gestion) {
        this.gestion = gestion;
    }

    public String getTipoPeriodo() {
        return tipoPeriodo;
    }

    public void setTipoPeriodo(String tipoPeriodo) {
        this.tipoPeriodo = tipoPeriodo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gestion != null ? gestion.hashCode() : 0);
        hash += (tipoPeriodo != null ? tipoPeriodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParCalendarioPK)) {
            return false;
        }
        ParCalendarioPK other = (ParCalendarioPK) object;
        if ((this.gestion == null && other.gestion != null) || (this.gestion != null && !this.gestion.equals(other.gestion))) {
            return false;
        }
        if ((this.tipoPeriodo == null && other.tipoPeriodo != null) || (this.tipoPeriodo != null && !this.tipoPeriodo.equals(other.tipoPeriodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParCalendarioPK[ gestion=" + gestion + ", tipoPeriodo=" + tipoPeriodo + " ]";
    }
    
}
