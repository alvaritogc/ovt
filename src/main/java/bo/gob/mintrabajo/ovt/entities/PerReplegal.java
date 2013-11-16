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
import javax.persistence.*;

/**
 *
 * @author gmercado
 */
@Entity
@Table(name = "PER_REPLEGAL")
@NamedQueries({
    @NamedQuery(name = "PerReplegal.findAll", query = "SELECT p FROM PerReplegal p")})
public class PerReplegal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_REPLEGAL")
    private Long idReplegal;
    @Basic(optional = false)
    @Column(name = "TIPO_IDENTIFICACION")
    private String tipoIdentificacion;
    @Basic(optional = false)
    @Column(name = "NRO_INDENTIFICACION")
    private String nroIndentificacion;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Column(name = "FECHA_DOCUMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumento;
    @Column(name = "NRO_DOCUMENTO")
    private String nroDocumento;
    @Basic(optional = false)
    @Column(name = "TIPO_PROCEDENCIA")
    private String tipoProcedencia;
    @Column(name = "ESTADO_REP_LEGAL")
    private String estadoRepLegal;
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


    @Transient
    private String departamento;

    public PerReplegal() {
    }

    public PerReplegal(Long idReplegal) {
        this.idReplegal = idReplegal;
    }

    public PerReplegal(Long idReplegal, String tipoIdentificacion, String nroIndentificacion, String nombre, String apellidoPaterno, String tipoProcedencia, Date fechaBitacora, String registroBitacora) {
        this.idReplegal = idReplegal;
        this.tipoIdentificacion = tipoIdentificacion;
        this.nroIndentificacion = nroIndentificacion;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.tipoProcedencia = tipoProcedencia;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdReplegal() {
        return idReplegal;
    }

    public void setIdReplegal(Long idReplegal) {
        this.idReplegal = idReplegal;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNroIndentificacion() {
        return nroIndentificacion;
    }

    public void setNroIndentificacion(String nroIndentificacion) {
        this.nroIndentificacion = nroIndentificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getTipoProcedencia() {
        return tipoProcedencia;
    }

    public void setTipoProcedencia(String tipoProcedencia) {
        this.tipoProcedencia = tipoProcedencia;
    }

    public String getEstadoRepLegal() {
        return estadoRepLegal;
    }

    public void setEstadoRepLegal(String estadoRepLegal) {
        this.estadoRepLegal = estadoRepLegal;
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReplegal != null ? idReplegal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerReplegal)) {
            return false;
        }
        PerReplegal other = (PerReplegal) object;
        if ((this.idReplegal == null && other.idReplegal != null) || (this.idReplegal != null && !this.idReplegal.equals(other.idReplegal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.PerReplegal[ idReplegal=" + idReplegal + " ]";
    }
    
}
