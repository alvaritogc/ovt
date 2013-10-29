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
@Table(name = "PER_PERSONA")
@NamedQueries({
    @NamedQuery(name = "PerPersona.findAll", query = "SELECT p FROM PerPersona p")})
public class PerPersona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PERSONA")
    private String idPersona;
    @Basic(optional = false)
    @Column(name = "TIPO_IDENTIFICACION")
    private String tipoIdentificacion;
    @Basic(optional = false)
    @Column(name = "NRO_IDENTIFICACION")
    private String nroIdentificacion;
    @Basic(optional = false)
    @Column(name = "NOMBRE_RAZON_SOCIAL")
    private String nombreRazonSocial;
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Basic(optional = false)
    @Column(name = "ES_NATURAL")
    private short esNatural;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPersona", fetch = FetchType.LAZY)
    private List<UsrUsuario> usrUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perPersona", fetch = FetchType.LAZY)
    private List<PerUnidad> perUnidadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPersona", fetch = FetchType.LAZY)
    private List<DocDocumento> docDocumentoList;
    @JoinColumn(name = "COD_LOCALIDAD", referencedColumnName = "COD_LOCALIDAD")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParLocalidad codLocalidad;

    public PerPersona() {
    }

    public PerPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public PerPersona(String idPersona, String tipoIdentificacion, String nroIdentificacion, String nombreRazonSocial, short esNatural, Date fechaBitacora, String registroBitacora) {
        this.idPersona = idPersona;
        this.tipoIdentificacion = tipoIdentificacion;
        this.nroIdentificacion = nroIdentificacion;
        this.nombreRazonSocial = nombreRazonSocial;
        this.esNatural = esNatural;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNroIdentificacion() {
        return nroIdentificacion;
    }

    public void setNroIdentificacion(String nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public short getEsNatural() {
        return esNatural;
    }

    public void setEsNatural(short esNatural) {
        this.esNatural = esNatural;
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

    public List<UsrUsuario> getUsrUsuarioList() {
        return usrUsuarioList;
    }

    public void setUsrUsuarioList(List<UsrUsuario> usrUsuarioList) {
        this.usrUsuarioList = usrUsuarioList;
    }

    public List<PerUnidad> getPerUnidadList() {
        return perUnidadList;
    }

    public void setPerUnidadList(List<PerUnidad> perUnidadList) {
        this.perUnidadList = perUnidadList;
    }

    public List<DocDocumento> getDocDocumentoList() {
        return docDocumentoList;
    }

    public void setDocDocumentoList(List<DocDocumento> docDocumentoList) {
        this.docDocumentoList = docDocumentoList;
    }

    public ParLocalidad getCodLocalidad() {
        return codLocalidad;
    }

    public void setCodLocalidad(ParLocalidad codLocalidad) {
        this.codLocalidad = codLocalidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerPersona)) {
            return false;
        }
        PerPersona other = (PerPersona) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.PerPersona[ idPersona=" + idPersona + " ]";
    }
    
}
