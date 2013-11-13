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
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gmercado
 */
@Entity
@Table(name = "PAR_CALENDARIO")
@NamedQueries({
    @NamedQuery(name = "ParCalendario.findAll", query = "SELECT p FROM ParCalendario p")})
public class ParCalendario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParCalendarioPK parCalendarioPK;
    @Basic(optional = false)
    @Column(name = "TIPO_CALENDARIO")
    private String tipoCalendario;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parCalendario", fetch = FetchType.LAZY)
    private List<ParObligacionCalendario> parObligacionCalendarioList;

    public ParCalendario() {
    }

    public ParCalendario(ParCalendarioPK parCalendarioPK) {
        this.parCalendarioPK = parCalendarioPK;
    }

    public ParCalendario(ParCalendarioPK parCalendarioPK, String tipoCalendario, Date fechaBitacora, String registroBitacora) {
        this.parCalendarioPK = parCalendarioPK;
        this.tipoCalendario = tipoCalendario;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public ParCalendario(String gestion, String tipoPeriodo) {
        this.parCalendarioPK = new ParCalendarioPK(gestion, tipoPeriodo);
    }

    public ParCalendarioPK getParCalendarioPK() {
        return parCalendarioPK;
    }

    public void setParCalendarioPK(ParCalendarioPK parCalendarioPK) {
        this.parCalendarioPK = parCalendarioPK;
    }

    public String getTipoCalendario() {
        return tipoCalendario;
    }

    public void setTipoCalendario(String tipoCalendario) {
        this.tipoCalendario = tipoCalendario;
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
        hash += (parCalendarioPK != null ? parCalendarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParCalendario)) {
            return false;
        }
        ParCalendario other = (ParCalendario) object;
        if ((this.parCalendarioPK == null && other.parCalendarioPK != null) || (this.parCalendarioPK != null && !this.parCalendarioPK.equals(other.parCalendarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParCalendario[ parCalendarioPK=" + parCalendarioPK + " ]";
    }
    
}
