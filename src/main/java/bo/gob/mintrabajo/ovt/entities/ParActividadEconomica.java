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
 * @author rvelasquez
 */
@Entity
@Table(name = "PAR_ACTIVIDAD_ECONOMICA")
@NamedQueries({
    @NamedQuery(name = "ParActividadEconomica.findAll", query = "SELECT p FROM ParActividadEconomica p")})
public class ParActividadEconomica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ACTIVIDAD_ECONOMICA")
    private Long idActividadEconomica;
    @Basic(optional = false)
    @Column(name = "COD_ACTIVIDAD_ECONOMICA")
    private String codActividadEconomica;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "COD_IMPUESTOS")
    private String codImpuestos;
    @Column(name = "DESCRICPION_IMPUESTOS")
    private String descricpionImpuestos;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
//    @OneToMany(mappedBy = "idActividadEconomica2", fetch = FetchType.LAZY)
//    private List<ParActividadEconomica> parActividadEconomicaList;
    @JoinColumn(name = "ID_ACTIVIDAD_ECONOMICA2", referencedColumnName = "ID_ACTIVIDAD_ECONOMICA")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParActividadEconomica idActividadEconomica2;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idActividadEconomica", fetch = FetchType.LAZY)
//    private List<PerActividad> perActividadList;

    public ParActividadEconomica() {
    }

    public ParActividadEconomica(Long idActividadEconomica) {
        this.idActividadEconomica = idActividadEconomica;
    }

    public ParActividadEconomica(Long idActividadEconomica, String codActividadEconomica, String descripcion, String estado, Date fechaBitacora, String registroBitacora) {
        this.idActividadEconomica = idActividadEconomica;
        this.codActividadEconomica = codActividadEconomica;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdActividadEconomica() {
        return idActividadEconomica;
    }

    public void setIdActividadEconomica(Long idActividadEconomica) {
        this.idActividadEconomica = idActividadEconomica;
    }

    public String getCodActividadEconomica() {
        return codActividadEconomica;
    }

    public void setCodActividadEconomica(String codActividadEconomica) {
        this.codActividadEconomica = codActividadEconomica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodImpuestos() {
        return codImpuestos;
    }

    public void setCodImpuestos(String codImpuestos) {
        this.codImpuestos = codImpuestos;
    }

    public String getDescricpionImpuestos() {
        return descricpionImpuestos;
    }

    public void setDescricpionImpuestos(String descricpionImpuestos) {
        this.descricpionImpuestos = descricpionImpuestos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

//    public List<ParActividadEconomica> getParActividadEconomicaList() {
//        return parActividadEconomicaList;
//    }
//
//    public void setParActividadEconomicaList(List<ParActividadEconomica> parActividadEconomicaList) {
//        this.parActividadEconomicaList = parActividadEconomicaList;
//    }

    public ParActividadEconomica getIdActividadEconomica2() {
        return idActividadEconomica2;
    }

    public void setIdActividadEconomica2(ParActividadEconomica idActividadEconomica2) {
        this.idActividadEconomica2 = idActividadEconomica2;
    }

//    public List<PerActividad> getPerActividadList() {
//        return perActividadList;
//    }
//
//    public void setPerActividadList(List<PerActividad> perActividadList) {
//        this.perActividadList = perActividadList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idActividadEconomica != null ? idActividadEconomica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParActividadEconomica)) {
            return false;
        }
        ParActividadEconomica other = (ParActividadEconomica) object;
        if ((this.idActividadEconomica == null && other.idActividadEconomica != null) || (this.idActividadEconomica != null && !this.idActividadEconomica.equals(other.idActividadEconomica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParActividadEconomica[ idActividadEconomica=" + idActividadEconomica + " ]";
    }
    
}
