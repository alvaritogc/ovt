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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gmercado
 */
@Entity
@Table(name = "PER_DIRECCION")
@NamedQueries({
    @NamedQuery(name = "PerDireccion.findAll", query = "SELECT p FROM PerDireccion p")})
public class PerDireccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DIRECCION")
    private Long idDireccion;
    @Basic(optional = false)
    @Column(name = "TIPO_DIRECCION")
    private String tipoDireccion;
    @Basic(optional = false)
    @Column(name = "ZONA_URBANIZACION")
    private String zonaUrbanizacion;
    @Basic(optional = false)
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "PISO_DEP_OFI")
    private String pisoDepOfi;
    @Column(name = "REFERENCIA")
    private String referencia;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "TELEFONO2")
    private String telefono2;
    @Column(name = "FAX")
    private String fax;
    @Column(name = "CELULAR")
    private String celular;
    @Column(name = "CELULAR2")
    private String celular2;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "EMAIL2")
    private String email2;
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
    @JoinColumns({
        @JoinColumn(name = "ID_UNIDAD", referencedColumnName = "ID_UNIDAD"),
        @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PerUnidad perUnidad;
    @JoinColumn(name = "COD_LOCALIDAD", referencedColumnName = "COD_LOCALIDAD")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParLocalidad codLocalidad;

    public PerDireccion() {
    }

    public PerDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public PerDireccion(Long idDireccion, String tipoDireccion, String zonaUrbanizacion, String direccion, String estado, Date fechaBitacora, String registroBitacora) {
        this.idDireccion = idDireccion;
        this.tipoDireccion = tipoDireccion;
        this.zonaUrbanizacion = zonaUrbanizacion;
        this.direccion = direccion;
        this.estado = estado;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public String getZonaUrbanizacion() {
        return zonaUrbanizacion;
    }

    public void setZonaUrbanizacion(String zonaUrbanizacion) {
        this.zonaUrbanizacion = zonaUrbanizacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPisoDepOfi() {
        return pisoDepOfi;
    }

    public void setPisoDepOfi(String pisoDepOfi) {
        this.pisoDepOfi = pisoDepOfi;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCelular2() {
        return celular2;
    }

    public void setCelular2(String celular2) {
        this.celular2 = celular2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
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

    public PerUnidad getPerUnidad() {
        return perUnidad;
    }

    public void setPerUnidad(PerUnidad perUnidad) {
        this.perUnidad = perUnidad;
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
        hash += (idDireccion != null ? idDireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerDireccion)) {
            return false;
        }
        PerDireccion other = (PerDireccion) object;
        if ((this.idDireccion == null && other.idDireccion != null) || (this.idDireccion != null && !this.idDireccion.equals(other.idDireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.PerDireccion[ idDireccion=" + idDireccion + " ]";
    }
    
}
