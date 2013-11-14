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
import java.math.BigDecimal;
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
@Table(name = "PAR_MULTA_RANGO")
@NamedQueries({
    @NamedQuery(name = "ParMultaRango.findAll", query = "SELECT p FROM ParMultaRango p")})
public class ParMultaRango implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MULTA_RANGO")
    private Long idMultaRango;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "RANGO_INICIAL")
    private BigDecimal rangoInicial;
    @Basic(optional = false)
    @Column(name = "RANGO_FINAL")
    private BigDecimal rangoFinal;
    @Basic(optional = false)
    @Column(name = "FACTOR")
    private BigDecimal factor;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "ID_MULTA", referencedColumnName = "ID_MULTA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParMulta idMulta;

    public ParMultaRango() {
    }

    public ParMultaRango(Long idMultaRango) {
        this.idMultaRango = idMultaRango;
    }

    public ParMultaRango(Long idMultaRango, BigDecimal rangoInicial, BigDecimal rangoFinal, BigDecimal factor, Date fechaBitacora, String registroBitacora) {
        this.idMultaRango = idMultaRango;
        this.rangoInicial = rangoInicial;
        this.rangoFinal = rangoFinal;
        this.factor = factor;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdMultaRango() {
        return idMultaRango;
    }

    public void setIdMultaRango(Long idMultaRango) {
        this.idMultaRango = idMultaRango;
    }

    public BigDecimal getRangoInicial() {
        return rangoInicial;
    }

    public void setRangoInicial(BigDecimal rangoInicial) {
        this.rangoInicial = rangoInicial;
    }

    public BigDecimal getRangoFinal() {
        return rangoFinal;
    }

    public void setRangoFinal(BigDecimal rangoFinal) {
        this.rangoFinal = rangoFinal;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
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

    public ParMulta getIdMulta() {
        return idMulta;
    }

    public void setIdMulta(ParMulta idMulta) {
        this.idMulta = idMulta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMultaRango != null ? idMultaRango.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParMultaRango)) {
            return false;
        }
        ParMultaRango other = (ParMultaRango) object;
        if ((this.idMultaRango == null && other.idMultaRango != null) || (this.idMultaRango != null && !this.idMultaRango.equals(other.idMultaRango))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParMultaRango[ idMultaRango=" + idMultaRango + " ]";
    }
    
}
