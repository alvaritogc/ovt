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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PAR_OBLIGACION_CALENDARIO")
@NamedQueries({
    @NamedQuery(name = "ParObligacionCalendario.findAll", query = "SELECT p FROM ParObligacionCalendario p")})
public class ParObligacionCalendario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_OBLIGACION_CALENDARIO")
    private Long idObligacionCalendario;
    @Basic(optional = false)
    @Column(name = "TIPO_CALENDARIO")
    private String tipoCalendario;
    @Basic(optional = false)
    @Column(name = "GESTION")
    private String gestion;
    @Basic(optional = false)
    @Column(name = "FECHA_DESDE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDesde;
    @Basic(optional = false)
    @Column(name = "FECHA_HASTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHasta;
    @Basic(optional = false)
    @Column(name = "FECHA_PLAZO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPlazo;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "COD_OBLIGACION", referencedColumnName = "COD_OBLIGACION")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParObligacion codObligacion;

    public ParObligacionCalendario() {
    }

    public ParObligacionCalendario(Long idObligacionCalendario) {
        this.idObligacionCalendario = idObligacionCalendario;
    }

    public ParObligacionCalendario(Long idObligacionCalendario, String tipoCalendario, String gestion, Date fechaDesde, Date fechaHasta, Date fechaPlazo, Date fechaBitacora, String registroBitacora) {
        this.idObligacionCalendario = idObligacionCalendario;
        this.tipoCalendario = tipoCalendario;
        this.gestion = gestion;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.fechaPlazo = fechaPlazo;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdObligacionCalendario() {
        return idObligacionCalendario;
    }

    public void setIdObligacionCalendario(Long idObligacionCalendario) {
        this.idObligacionCalendario = idObligacionCalendario;
    }

    public String getTipoCalendario() {
        return tipoCalendario;
    }

    public void setTipoCalendario(String tipoCalendario) {
        this.tipoCalendario = tipoCalendario;
    }

    public String getGestion() {
        return gestion;
    }

    public void setGestion(String gestion) {
        this.gestion = gestion;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Date getFechaPlazo() {
        return fechaPlazo;
    }

    public void setFechaPlazo(Date fechaPlazo) {
        this.fechaPlazo = fechaPlazo;
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

    public ParObligacion getCodObligacion() {
        return codObligacion;
    }

    public void setCodObligacion(ParObligacion codObligacion) {
        this.codObligacion = codObligacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObligacionCalendario != null ? idObligacionCalendario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParObligacionCalendario)) {
            return false;
        }
        ParObligacionCalendario other = (ParObligacionCalendario) object;
        if ((this.idObligacionCalendario == null && other.idObligacionCalendario != null) || (this.idObligacionCalendario != null && !this.idObligacionCalendario.equals(other.idObligacionCalendario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParObligacionCalendario[ idObligacionCalendario=" + idObligacionCalendario + " ]";
    }
    
}
