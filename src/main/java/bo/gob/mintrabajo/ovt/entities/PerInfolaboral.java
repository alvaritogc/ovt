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
import javax.persistence.*;

/**
 *
 * @author rvelasquez
 */
@Entity
@Table(name = "PER_INFOLABORAL")
@NamedQueries({
    @NamedQuery(name = "PerInfolaboral.findAll", query = "SELECT p FROM PerInfolaboral p")})
public class PerInfolaboral implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_INFOLABORAL")
    private Long idInfolaboral;
    @Basic(optional = false)
    @Column(name = "NRO_TOTAL_TRABAJADORES")
    private int nroTotalTrabajadores;
    @Basic(optional = false)
    @Column(name = "NRO_HOMBRES")
    private int nroHombres;
    @Basic(optional = false)
    @Column(name = "NRO_MUJERES")
    private int nroMujeres;
    @Basic(optional = false)
    @Column(name = "NRO_EXTRANJEROS")
    private int nroExtranjeros;
    @Basic(optional = false)
    @Column(name = "NRO_FIJOS")
    private int nroFijos;
    @Basic(optional = false)
    @Column(name = "NRO_EVENTUALES")
    private int nroEventuales;
    @Basic(optional = false)
    @Column(name = "NRO_MENORES18")
    private int nroMenores18;
    @Basic(optional = false)
    @Column(name = "NRO_MAYORES60")
    private int nroMayores60;
    @Basic(optional = false)
    @Column(name = "NRO_JUBILADOS")
    private int nroJubilados;
    @Basic(optional = false)
    @Column(name = "NRO_CAPDIFERENTE")
    private int nroCapdiferente;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "TOTAL_PLANILLA")
    private BigDecimal totalPlanilla;
    @Basic(optional = false)
    @Column(name = "NRO_ASEG_AFP")
    private int nroAsegAfp;
    @Basic(optional = false)
    @Column(name = "NRO_ASEG_CAJA")
    private int nroAsegCaja;
    @Basic(optional = false)
    @Column(name = "MONTO_ASEG_AFP")
    private BigDecimal montoAsegAfp;
    @Basic(optional = false)
    @Column(name = "MONTO_ASEG_CAJA")
    private BigDecimal montoAsegCaja;
    @Basic(optional = false)
    @Column(name = "TIPO_SINDICATO")
    private String tipoSindicato;
    @Basic(optional = false)
    @Column(name = "ESTADO_INFOLABORAL")
    private String estadoInfolaboral;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumns({
        @JoinColumn(name = "ID_UNIDAD", referencedColumnName = "ID_UNIDAD"),
        @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PerUnidad perUnidad;

    public PerInfolaboral() {
    }

    public PerInfolaboral(Long idInfolaboral) {
        this.idInfolaboral = idInfolaboral;
    }

    public PerInfolaboral(Long idInfolaboral, int nroTotalTrabajadores, int nroHombres, int nroMujeres, int nroExtranjeros, int nroFijos, int nroEventuales, int nroMenores18, int nroMayores60, int nroJubilados, int nroCapdiferente, BigDecimal totalPlanilla, int nroAsegAfp, int nroAsegCaja, BigDecimal montoAsegAfp, BigDecimal montoAsegCaja, String tipoSindicato, String estadoInfolaboral, Date fechaBitacora, String registroBitacora) {
        this.idInfolaboral = idInfolaboral;
        this.nroTotalTrabajadores = nroTotalTrabajadores;
        this.nroHombres = nroHombres;
        this.nroMujeres = nroMujeres;
        this.nroExtranjeros = nroExtranjeros;
        this.nroFijos = nroFijos;
        this.nroEventuales = nroEventuales;
        this.nroMenores18 = nroMenores18;
        this.nroMayores60 = nroMayores60;
        this.nroJubilados = nroJubilados;
        this.nroCapdiferente = nroCapdiferente;
        this.totalPlanilla = totalPlanilla;
        this.nroAsegAfp = nroAsegAfp;
        this.nroAsegCaja = nroAsegCaja;
        this.montoAsegAfp = montoAsegAfp;
        this.montoAsegCaja = montoAsegCaja;
        this.tipoSindicato = tipoSindicato;
        this.estadoInfolaboral = estadoInfolaboral;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdInfolaboral() {
        return idInfolaboral;
    }

    public void setIdInfolaboral(Long idInfolaboral) {
        this.idInfolaboral = idInfolaboral;
    }

    public int getNroTotalTrabajadores() {
        return nroTotalTrabajadores;
    }

    public void setNroTotalTrabajadores(int nroTotalTrabajadores) {
        this.nroTotalTrabajadores = nroTotalTrabajadores;
    }

    public int getNroHombres() {
        return nroHombres;
    }

    public void setNroHombres(int nroHombres) {
        this.nroHombres = nroHombres;
    }

    public int getNroMujeres() {
        return nroMujeres;
    }

    public void setNroMujeres(int nroMujeres) {
        this.nroMujeres = nroMujeres;
    }

    public int getNroExtranjeros() {
        return nroExtranjeros;
    }

    public void setNroExtranjeros(int nroExtranjeros) {
        this.nroExtranjeros = nroExtranjeros;
    }

    public int getNroFijos() {
        return nroFijos;
    }

    public void setNroFijos(int nroFijos) {
        this.nroFijos = nroFijos;
    }

    public int getNroEventuales() {
        return nroEventuales;
    }

    public void setNroEventuales(int nroEventuales) {
        this.nroEventuales = nroEventuales;
    }

    public int getNroMenores18() {
        return nroMenores18;
    }

    public void setNroMenores18(int nroMenores18) {
        this.nroMenores18 = nroMenores18;
    }

    public int getNroMayores60() {
        return nroMayores60;
    }

    public void setNroMayores60(int nroMayores60) {
        this.nroMayores60 = nroMayores60;
    }

    public int getNroJubilados() {
        return nroJubilados;
    }

    public void setNroJubilados(int nroJubilados) {
        this.nroJubilados = nroJubilados;
    }

    public int getNroCapdiferente() {
        return nroCapdiferente;
    }

    public void setNroCapdiferente(int nroCapdiferente) {
        this.nroCapdiferente = nroCapdiferente;
    }

    public BigDecimal getTotalPlanilla() {
        return totalPlanilla;
    }

    public void setTotalPlanilla(BigDecimal totalPlanilla) {
        this.totalPlanilla = totalPlanilla;
    }

    public int getNroAsegAfp() {
        return nroAsegAfp;
    }

    public void setNroAsegAfp(int nroAsegAfp) {
        this.nroAsegAfp = nroAsegAfp;
    }

    public int getNroAsegCaja() {
        return nroAsegCaja;
    }

    public void setNroAsegCaja(int nroAsegCaja) {
        this.nroAsegCaja = nroAsegCaja;
    }

    public BigDecimal getMontoAsegAfp() {
        return montoAsegAfp;
    }

    public void setMontoAsegAfp(BigDecimal montoAsegAfp) {
        this.montoAsegAfp = montoAsegAfp;
    }

    public BigDecimal getMontoAsegCaja() {
        return montoAsegCaja;
    }

    public void setMontoAsegCaja(BigDecimal montoAsegCaja) {
        this.montoAsegCaja = montoAsegCaja;
    }

    public String getTipoSindicato() {
        return tipoSindicato;
    }

    public void setTipoSindicato(String tipoSindicato) {
        this.tipoSindicato = tipoSindicato;
    }

    public String getEstadoInfolaboral() {
        return estadoInfolaboral;
    }

    public void setEstadoInfolaboral(String estadoInfolaboral) {
        this.estadoInfolaboral = estadoInfolaboral;
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

    public PerUnidad getPerUnidad() {
        return perUnidad;
    }

    public void setPerUnidad(PerUnidad perUnidad) {
        this.perUnidad = perUnidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInfolaboral != null ? idInfolaboral.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerInfolaboral)) {
            return false;
        }
        PerInfolaboral other = (PerInfolaboral) object;
        if ((this.idInfolaboral == null && other.idInfolaboral != null) || (this.idInfolaboral != null && !this.idInfolaboral.equals(other.idInfolaboral))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.PerInfolaboral[ idInfolaboral=" + idInfolaboral + " ]";
    }
    
}
