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
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "PAR_DOMINIO")
@NamedQueries({
    @NamedQuery(name = "ParDominio.findAll", query = "SELECT p FROM ParDominio p")})
public class ParDominio implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParDominioPK parDominioPK;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "ID_MODULO", referencedColumnName = "ID_MODULO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UsrModulo idModulo;
    @OneToMany(mappedBy = "parDominio", fetch = FetchType.LAZY)
    private List<ParDominio> parDominioList;
    @JoinColumns({
        @JoinColumn(name = "ID_DOMINIO_PADRE", referencedColumnName = "ID_DOMINIO"),
        @JoinColumn(name = "VALOR_PADRE", referencedColumnName = "VALOR")})
    @ManyToOne(fetch = FetchType.LAZY)
    private ParDominio parDominio;

    public ParDominio() {
    }

    public ParDominio(ParDominioPK parDominioPK) {
        this.parDominioPK = parDominioPK;
    }

    public ParDominio(ParDominioPK parDominioPK, String descripcion, String observacion, Date fechaBitacora, String registroBitacora) {
        this.parDominioPK = parDominioPK;
        this.descripcion = descripcion;
        this.observacion = observacion;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public ParDominio(String idDominio, String valor) {
        this.parDominioPK = new ParDominioPK(idDominio, valor);
    }

    public ParDominioPK getParDominioPK() {
        return parDominioPK;
    }

    public void setParDominioPK(ParDominioPK parDominioPK) {
        this.parDominioPK = parDominioPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public UsrModulo getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(UsrModulo idModulo) {
        this.idModulo = idModulo;
    }

    public List<ParDominio> getParDominioList() {
        return parDominioList;
    }

    public void setParDominioList(List<ParDominio> parDominioList) {
        this.parDominioList = parDominioList;
    }

    public ParDominio getParDominio() {
        return parDominio;
    }

    public void setParDominio(ParDominio parDominio) {
        this.parDominio = parDominio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (parDominioPK != null ? parDominioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParDominio)) {
            return false;
        }
        ParDominio other = (ParDominio) object;
        if ((this.parDominioPK == null && other.parDominioPK != null) || (this.parDominioPK != null && !this.parDominioPK.equals(other.parDominioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParDominio[ parDominioPK=" + parDominioPK + " ]";
    }
    
}
