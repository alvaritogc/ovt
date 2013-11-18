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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rvelasquez
 */
@Entity
@Table(name = "PAR_PARAMETRIZACION")
@NamedQueries({
    @NamedQuery(name = "ParParametrizacion.findAll", query = "SELECT p FROM ParParametrizacion p")})
public class ParParametrizacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParParametrizacionPK parParametrizacionPK;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "DATO_DESCRIPCION")
    private String datoDescripcion;
    @Column(name = "OBSERVACION")
    private String observacion;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;

    public ParParametrizacion() {
    }

    public ParParametrizacion(ParParametrizacionPK parParametrizacionPK) {
        this.parParametrizacionPK = parParametrizacionPK;
    }

    public ParParametrizacion(ParParametrizacionPK parParametrizacionPK, String descripcion, String datoDescripcion, Date fechaBitacora, String registroBitacora) {
        this.parParametrizacionPK = parParametrizacionPK;
        this.descripcion = descripcion;
        this.datoDescripcion = datoDescripcion;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public ParParametrizacion(String idParametro, String valor) {
        this.parParametrizacionPK = new ParParametrizacionPK(idParametro, valor);
    }

    public ParParametrizacionPK getParParametrizacionPK() {
        return parParametrizacionPK;
    }

    public void setParParametrizacionPK(ParParametrizacionPK parParametrizacionPK) {
        this.parParametrizacionPK = parParametrizacionPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDatoDescripcion() {
        return datoDescripcion;
    }

    public void setDatoDescripcion(String datoDescripcion) {
        this.datoDescripcion = datoDescripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Date fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    public String getRegistroBitacora() {
        return registroBitacora;
    }

    public void setRegistroBitacora(String registroBitacora) {
        this.registroBitacora = registroBitacora;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parParametrizacionPK != null ? parParametrizacionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParParametrizacion)) {
            return false;
        }
        ParParametrizacion other = (ParParametrizacion) object;
        if ((this.parParametrizacionPK == null && other.parParametrizacionPK != null) || (this.parParametrizacionPK != null && !this.parParametrizacionPK.equals(other.parParametrizacionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParParametrizacion[ parParametrizacionPK=" + parParametrizacionPK + " ]";
    }
    
}
