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
@Table(name = "USR_USUARIO")
@NamedQueries({
    @NamedQuery(name = "UsrUsuario.findAll", query = "SELECT u FROM UsrUsuario u")})
public class UsrUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;
    @Basic(optional = false)
    @Column(name = "USUARIO")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "CLAVE")
    private String clave;
    @Basic(optional = false)
    @Column(name = "TIPO_AUTENTICACION")
    private String tipoAutenticacion;
    @Basic(optional = false)
    @Column(name = "ES_INTERNO")
    private short esInterno;
    @Basic(optional = false)
    @Column(name = "ES_DELEGADO")
    private short esDelegado;
    @Column(name = "FECHA_INHABILITACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInhabilitacion;
    @Column(name = "FECHA_REHABILITACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRehabilitacion;
    @Basic(optional = false)
    @Column(name = "ESTADO_USUARIO")
    private String estadoUsuario;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PerPersona idPersona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrUsuario", fetch = FetchType.LAZY)
    private List<PerUsuarioUnidad> perUsuarioUnidadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrUsuario", fetch = FetchType.LAZY)
    private List<UsrUsuarioRecurso> usrUsuarioRecursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrUsuario", fetch = FetchType.LAZY)
    private List<UsrUsuarioRol> usrUsuarioRolList;

    public UsrUsuario() {
    }

    public UsrUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public UsrUsuario(Long idUsuario, String usuario, String clave, String tipoAutenticacion, short esInterno, short esDelegado, String estadoUsuario, Date fechaBitacora, String registroBitacora) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.clave = clave;
        this.tipoAutenticacion = tipoAutenticacion;
        this.esInterno = esInterno;
        this.esDelegado = esDelegado;
        this.estadoUsuario = estadoUsuario;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTipoAutenticacion() {
        return tipoAutenticacion;
    }

    public void setTipoAutenticacion(String tipoAutenticacion) {
        this.tipoAutenticacion = tipoAutenticacion;
    }

    public short getEsInterno() {
        return esInterno;
    }

    public void setEsInterno(short esInterno) {
        this.esInterno = esInterno;
    }

    public short getEsDelegado() {
        return esDelegado;
    }

    public void setEsDelegado(short esDelegado) {
        this.esDelegado = esDelegado;
    }

    public Date getFechaInhabilitacion() {
        return fechaInhabilitacion;
    }

    public void setFechaInhabilitacion(Date fechaInhabilitacion) {
        this.fechaInhabilitacion = fechaInhabilitacion;
    }

    public Date getFechaRehabilitacion() {
        return fechaRehabilitacion;
    }

    public void setFechaRehabilitacion(Date fechaRehabilitacion) {
        this.fechaRehabilitacion = fechaRehabilitacion;
    }

    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
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

    public PerPersona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(PerPersona idPersona) {
        this.idPersona = idPersona;
    }

    public List<PerUsuarioUnidad> getPerUsuarioUnidadList() {
        return perUsuarioUnidadList;
    }

    public void setPerUsuarioUnidadList(List<PerUsuarioUnidad> perUsuarioUnidadList) {
        this.perUsuarioUnidadList = perUsuarioUnidadList;
    }

    public List<UsrUsuarioRecurso> getUsrUsuarioRecursoList() {
        return usrUsuarioRecursoList;
    }

    public void setUsrUsuarioRecursoList(List<UsrUsuarioRecurso> usrUsuarioRecursoList) {
        this.usrUsuarioRecursoList = usrUsuarioRecursoList;
    }

    public List<UsrUsuarioRol> getUsrUsuarioRolList() {
        return usrUsuarioRolList;
    }

    public void setUsrUsuarioRolList(List<UsrUsuarioRol> usrUsuarioRolList) {
        this.usrUsuarioRolList = usrUsuarioRolList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsrUsuario)) {
            return false;
        }
        UsrUsuario other = (UsrUsuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.UsrUsuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
