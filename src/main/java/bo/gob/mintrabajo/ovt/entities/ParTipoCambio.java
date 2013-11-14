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
@Table(name = "PAR_TIPO_CAMBIO")
@NamedQueries({
    @NamedQuery(name = "ParTipoCambio.findAll", query = "SELECT p FROM ParTipoCambio p")})
public class ParTipoCambio implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParTipoCambioPK parTipoCambioPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "VALOR_OFICIAL")
    private BigDecimal valorOficial;
    @Basic(optional = false)
    @Column(name = "VALOR_VENTA")
    private BigDecimal valorVenta;
    @Basic(optional = false)
    @Column(name = "VALOR_COMPRA")
    private BigDecimal valorCompra;
    @Column(name = "VALOR_OTRO")
    private BigDecimal valorOtro;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;

    public ParTipoCambio() {
    }

    public ParTipoCambio(ParTipoCambioPK parTipoCambioPK) {
        this.parTipoCambioPK = parTipoCambioPK;
    }

    public ParTipoCambio(ParTipoCambioPK parTipoCambioPK, BigDecimal valorOficial, BigDecimal valorVenta, BigDecimal valorCompra, Date fechaBitacora, String registroBitacora) {
        this.parTipoCambioPK = parTipoCambioPK;
        this.valorOficial = valorOficial;
        this.valorVenta = valorVenta;
        this.valorCompra = valorCompra;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public ParTipoCambio(Date idFecha, String tipoMonedaBase, String tipoMonedaCambio) {
        this.parTipoCambioPK = new ParTipoCambioPK(idFecha, tipoMonedaBase, tipoMonedaCambio);
    }

    public ParTipoCambioPK getParTipoCambioPK() {
        return parTipoCambioPK;
    }

    public void setParTipoCambioPK(ParTipoCambioPK parTipoCambioPK) {
        this.parTipoCambioPK = parTipoCambioPK;
    }

    public BigDecimal getValorOficial() {
        return valorOficial;
    }

    public void setValorOficial(BigDecimal valorOficial) {
        this.valorOficial = valorOficial;
    }

    public BigDecimal getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(BigDecimal valorVenta) {
        this.valorVenta = valorVenta;
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public BigDecimal getValorOtro() {
        return valorOtro;
    }

    public void setValorOtro(BigDecimal valorOtro) {
        this.valorOtro = valorOtro;
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
        hash += (parTipoCambioPK != null ? parTipoCambioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParTipoCambio)) {
            return false;
        }
        ParTipoCambio other = (ParTipoCambio) object;
        if ((this.parTipoCambioPK == null && other.parTipoCambioPK != null) || (this.parTipoCambioPK != null && !this.parTipoCambioPK.equals(other.parTipoCambioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParTipoCambio[ parTipoCambioPK=" + parTipoCambioPK + " ]";
    }
    
}
