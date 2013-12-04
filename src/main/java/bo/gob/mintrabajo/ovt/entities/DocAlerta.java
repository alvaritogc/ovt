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

/**
 *
 * @author rvelasquez
 */
@Entity
@Table(name = "DOC_ALERTA")
@NamedQueries({
    @NamedQuery(name = "DocAlerta.findAll", query = "SELECT d FROM DocAlerta d")})
public class DocAlerta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ALERTA")
    private Long idAlerta;
    @Column(name = "ESTADO_ALERTA")
    private String estadoAlerta;
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DocDocumento idDocumento;
    @JoinColumn(name = "COD_ALERTA", referencedColumnName = "COD_ALERTA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DocAlertaDefinicion codAlerta;
    @Column(name = "OBSERVACION")
    private String observacion;

    public DocAlerta() {
    }

    public DocAlerta(Long idAlerta) {
        this.idAlerta = idAlerta;
    }

    public Long getIdAlerta() {
        return idAlerta;
    }

    public void setIdAlerta(Long idAlerta) {
        this.idAlerta = idAlerta;
    }

    public String getEstadoAlerta() {
        return estadoAlerta;
    }

    public void setEstadoAlerta(String estadoAlerta) {
        this.estadoAlerta = estadoAlerta;
    }

    public DocDocumento getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(DocDocumento idDocumento) {
        this.idDocumento = idDocumento;
    }

    public DocAlertaDefinicion getCodAlerta() {
        return codAlerta;
    }

    public void setCodAlerta(DocAlertaDefinicion codAlerta) {
        this.codAlerta = codAlerta;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlerta != null ? idAlerta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocAlerta)) {
            return false;
        }
        DocAlerta other = (DocAlerta) object;
        if ((this.idAlerta == null && other.idAlerta != null) || (this.idAlerta != null && !this.idAlerta.equals(other.idAlerta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocAlerta[ idAlerta=" + idAlerta + " ]";
    }
    
}
