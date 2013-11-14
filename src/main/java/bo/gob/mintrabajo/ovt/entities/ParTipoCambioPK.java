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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rvelasquez
 */
@Embeddable
public class ParTipoCambioPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_FECHA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date idFecha;
    @Basic(optional = false)
    @Column(name = "TIPO_MONEDA_BASE")
    private String tipoMonedaBase;
    @Basic(optional = false)
    @Column(name = "TIPO_MONEDA_CAMBIO")
    private String tipoMonedaCambio;

    public ParTipoCambioPK() {
    }

    public ParTipoCambioPK(Date idFecha, String tipoMonedaBase, String tipoMonedaCambio) {
        this.idFecha = idFecha;
        this.tipoMonedaBase = tipoMonedaBase;
        this.tipoMonedaCambio = tipoMonedaCambio;
    }

    public Date getIdFecha() {
        return idFecha;
    }

    public void setIdFecha(Date idFecha) {
        this.idFecha = idFecha;
    }

    public String getTipoMonedaBase() {
        return tipoMonedaBase;
    }

    public void setTipoMonedaBase(String tipoMonedaBase) {
        this.tipoMonedaBase = tipoMonedaBase;
    }

    public String getTipoMonedaCambio() {
        return tipoMonedaCambio;
    }

    public void setTipoMonedaCambio(String tipoMonedaCambio) {
        this.tipoMonedaCambio = tipoMonedaCambio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFecha != null ? idFecha.hashCode() : 0);
        hash += (tipoMonedaBase != null ? tipoMonedaBase.hashCode() : 0);
        hash += (tipoMonedaCambio != null ? tipoMonedaCambio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParTipoCambioPK)) {
            return false;
        }
        ParTipoCambioPK other = (ParTipoCambioPK) object;
        if ((this.idFecha == null && other.idFecha != null) || (this.idFecha != null && !this.idFecha.equals(other.idFecha))) {
            return false;
        }
        if ((this.tipoMonedaBase == null && other.tipoMonedaBase != null) || (this.tipoMonedaBase != null && !this.tipoMonedaBase.equals(other.tipoMonedaBase))) {
            return false;
        }
        if ((this.tipoMonedaCambio == null && other.tipoMonedaCambio != null) || (this.tipoMonedaCambio != null && !this.tipoMonedaCambio.equals(other.tipoMonedaCambio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParTipoCambioPK[ idFecha=" + idFecha + ", tipoMonedaBase=" + tipoMonedaBase + ", tipoMonedaCambio=" + tipoMonedaCambio + " ]";
    }
    
}
