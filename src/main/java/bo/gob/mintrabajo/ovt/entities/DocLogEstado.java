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
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "DOC_LOG_ESTADO")
@NamedQueries({
    @NamedQuery(name = "DocLogEstado.findAll", query = "SELECT d FROM DocLogEstado d")})
public class DocLogEstado implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LOGESTADO")
    private BigDecimal idLogestado;
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @Column(name = "ID_DOCUMENTO")
    private BigInteger idDocumento;
    @Column(name = "COD_ESTADO_FINAL")
    private String codEstadoFinal;
    @Column(name = "COD_ESTADO_INICIAL")
    private String codEstadoInicial;

    public DocLogEstado() {
    }

    public DocLogEstado(BigDecimal idLogestado) {
        this.idLogestado = idLogestado;
    }

    public BigDecimal getIdLogestado() {
        return idLogestado;
    }

    public void setIdLogestado(BigDecimal idLogestado) {
        this.idLogestado = idLogestado;
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

    public BigInteger getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(BigInteger idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getCodEstadoFinal() {
        return codEstadoFinal;
    }

    public void setCodEstadoFinal(String codEstadoFinal) {
        this.codEstadoFinal = codEstadoFinal;
    }

    public String getCodEstadoInicial() {
        return codEstadoInicial;
    }

    public void setCodEstadoInicial(String codEstadoInicial) {
        this.codEstadoInicial = codEstadoInicial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLogestado != null ? idLogestado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocLogEstado)) {
            return false;
        }
        DocLogEstado other = (DocLogEstado) object;
        if ((this.idLogestado == null && other.idLogestado != null) || (this.idLogestado != null && !this.idLogestado.equals(other.idLogestado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocLogEstado[ idLogestado=" + idLogestado + " ]";
    }
    
}
