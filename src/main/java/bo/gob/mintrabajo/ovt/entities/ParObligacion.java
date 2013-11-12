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
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rvelasquez
 */
@Entity
@Table(name = "PAR_OBLIGACION")
@NamedQueries({
    @NamedQuery(name = "ParObligacion.findAll", query = "SELECT p FROM ParObligacion p")})
public class ParObligacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_OBLIGACION")
    private String codObligacion;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codObligacion", fetch = FetchType.LAZY)
    private List<ParObligacionCalendario> parObligacionCalendarioList;

    public ParObligacion() {
    }

    public ParObligacion(String codObligacion) {
        this.codObligacion = codObligacion;
    }

    public ParObligacion(String codObligacion, String descripcion, String estado, Date fechaBitacora, String registroBitacora) {
        this.codObligacion = codObligacion;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public String getCodObligacion() {
        return codObligacion;
    }

    public void setCodObligacion(String codObligacion) {
        this.codObligacion = codObligacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public List<ParObligacionCalendario> getParObligacionCalendarioList() {
        return parObligacionCalendarioList;
    }
    public void setParObligacionCalendarioList(List<ParObligacionCalendario> parObligacionCalendarioList) {
        this.parObligacionCalendarioList = parObligacionCalendarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codObligacion != null ? codObligacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParObligacion)) {
            return false;
        }
        ParObligacion other = (ParObligacion) object;
        if ((this.codObligacion == null && other.codObligacion != null) || (this.codObligacion != null && !this.codObligacion.equals(other.codObligacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParObligacion[ codObligacion=" + codObligacion + " ]";
    }
    
}
