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
@Table(name = "DOC_LOG_ESTADO")
@NamedQueries({
    @NamedQuery(name = "DocLogEstado.findAll", query = "SELECT d FROM DocLogEstado d")})
public class DocLogEstado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_LOGESTADO")
    private Long idLogestado;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "COD_ESTADO_FINAL", referencedColumnName = "COD_ESTADO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParDocumentoEstado codEstadoFinal;
    @JoinColumn(name = "COD_ESTADO_INICIAL", referencedColumnName = "COD_ESTADO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParDocumentoEstado codEstadoInicial;
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DocDocumento idDocumento;
    //
    @Column(name = "OBSERVACION")
    private String observacion;

    public DocLogEstado() {
    }

    public DocLogEstado(Long idLogestado) {
        this.idLogestado = idLogestado;
    }

    public DocLogEstado(Long idLogestado, Date fechaBitacora, String registroBitacora) {
        this.idLogestado = idLogestado;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdLogestado() {
        return idLogestado;
    }

    public void setIdLogestado(Long idLogestado) {
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

    public ParDocumentoEstado getCodEstadoFinal() {
        return codEstadoFinal;
    }

    public void setCodEstadoFinal(ParDocumentoEstado codEstadoFinal) {
        this.codEstadoFinal = codEstadoFinal;
    }

    public ParDocumentoEstado getCodEstadoInicial() {
        return codEstadoInicial;
    }

    public void setCodEstadoInicial(ParDocumentoEstado codEstadoInicial) {
        this.codEstadoInicial = codEstadoInicial;
    }

    public DocDocumento getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(DocDocumento idDocumento) {
        this.idDocumento = idDocumento;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
    
}
