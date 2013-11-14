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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "PAR_LOCALIDAD")
@NamedQueries({
    @NamedQuery(name = "ParLocalidad.findAll", query = "SELECT p FROM ParLocalidad p")})
public class ParLocalidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_LOCALIDAD")
    private String codLocalidad;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "TIPO_LOCALIDAD")
    private String tipoLocalidad;
    @Column(name = "CODIGO_OTR")
    private String codigoOtr;
    @Column(name = "CODIGO_REF")
    private String codigoRef;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codLocalidad", fetch = FetchType.LAZY)
    private List<PerDireccion> perDireccionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codLocalidad", fetch = FetchType.LAZY)
    private List<PerPersona> perPersonaList;
    @OneToMany(mappedBy = "codLocalidadPadre", fetch = FetchType.LAZY)
    private List<ParLocalidad> parLocalidadList;
    @JoinColumn(name = "COD_LOCALIDAD_PADRE", referencedColumnName = "COD_LOCALIDAD")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParLocalidad codLocalidadPadre;

    public ParLocalidad() {
    }

    public ParLocalidad(String codLocalidad) {
        this.codLocalidad = codLocalidad;
    }

    public ParLocalidad(String codLocalidad, String descripcion, String tipoLocalidad, Date fechaBitacora, String registroBitacora) {
        this.codLocalidad = codLocalidad;
        this.descripcion = descripcion;
        this.tipoLocalidad = tipoLocalidad;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public String getCodLocalidad() {
        return codLocalidad;
    }

    public void setCodLocalidad(String codLocalidad) {
        this.codLocalidad = codLocalidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoLocalidad() {
        return tipoLocalidad;
    }

    public void setTipoLocalidad(String tipoLocalidad) {
        this.tipoLocalidad = tipoLocalidad;
    }

    public String getCodigoOtr() {
        return codigoOtr;
    }

    public void setCodigoOtr(String codigoOtr) {
        this.codigoOtr = codigoOtr;
    }

    public String getCodigoRef() {
        return codigoRef;
    }

    public void setCodigoRef(String codigoRef) {
        this.codigoRef = codigoRef;
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

    public List<PerDireccion> getPerDireccionList() {
        return perDireccionList;
    }

    public void setPerDireccionList(List<PerDireccion> perDireccionList) {
        this.perDireccionList = perDireccionList;
    }

    public List<PerPersona> getPerPersonaList() {
        return perPersonaList;
    }

    public void setPerPersonaList(List<PerPersona> perPersonaList) {
        this.perPersonaList = perPersonaList;
    }

    public List<ParLocalidad> getParLocalidadList() {
        return parLocalidadList;
    }

    public void setParLocalidadList(List<ParLocalidad> parLocalidadList) {
        this.parLocalidadList = parLocalidadList;
    }

    public ParLocalidad getCodLocalidadPadre() {
        return codLocalidadPadre;
    }

    public void setCodLocalidadPadre(ParLocalidad codLocalidadPadre) {
        this.codLocalidadPadre = codLocalidadPadre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codLocalidad != null ? codLocalidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParLocalidad)) {
            return false;
        }
        ParLocalidad other = (ParLocalidad) object;
        if ((this.codLocalidad == null && other.codLocalidad != null) || (this.codLocalidad != null && !this.codLocalidad.equals(other.codLocalidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParLocalidad[ codLocalidad=" + codLocalidad + " ]";
    }
    
}
