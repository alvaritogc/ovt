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
@Table(name = "USR_MODULO")
@NamedQueries({
    @NamedQuery(name = "UsrModulo.findAll", query = "SELECT u FROM UsrModulo u")})
public class UsrModulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MODULO")
    private String idModulo;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "TIPO_MODULO")
    private String tipoModulo;
    @Basic(optional = false)
    @Column(name = "TIPO_AREA")
    private String tipoArea;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idModulo", fetch = FetchType.LAZY)
    private List<ParDominio> parDominioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idModulo", fetch = FetchType.LAZY)
    private List<UsrUsuarioRol> usrUsuarioRolList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idModulo", fetch = FetchType.LAZY)
    private List<UsrRol> usrRolList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idModulo", fetch = FetchType.LAZY)
    private List<UsrRecurso> usrRecursoList;

    public UsrModulo() {
    }

    public UsrModulo(String idModulo) {
        this.idModulo = idModulo;
    }

    public UsrModulo(String idModulo, String nombre, String tipoModulo, String tipoArea, Date fechaBitacora, String registroBitacora) {
        this.idModulo = idModulo;
        this.nombre = nombre;
        this.tipoModulo = tipoModulo;
        this.tipoArea = tipoArea;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public String getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(String idModulo) {
        this.idModulo = idModulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoModulo() {
        return tipoModulo;
    }

    public void setTipoModulo(String tipoModulo) {
        this.tipoModulo = tipoModulo;
    }

    public String getTipoArea() {
        return tipoArea;
    }

    public void setTipoArea(String tipoArea) {
        this.tipoArea = tipoArea;
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

    public List<ParDominio> getParDominioList() {
        return parDominioList;
    }

    public void setParDominioList(List<ParDominio> parDominioList) {
        this.parDominioList = parDominioList;
    }

    public List<UsrUsuarioRol> getUsrUsuarioRolList() {
        return usrUsuarioRolList;
    }

    public void setUsrUsuarioRolList(List<UsrUsuarioRol> usrUsuarioRolList) {
        this.usrUsuarioRolList = usrUsuarioRolList;
    }

    public List<UsrRol> getUsrRolList() {
        return usrRolList;
    }

    public void setUsrRolList(List<UsrRol> usrRolList) {
        this.usrRolList = usrRolList;
    }

    public List<UsrRecurso> getUsrRecursoList() {
        return usrRecursoList;
    }

    public void setUsrRecursoList(List<UsrRecurso> usrRecursoList) {
        this.usrRecursoList = usrRecursoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idModulo != null ? idModulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsrModulo)) {
            return false;
        }
        UsrModulo other = (UsrModulo) object;
        if ((this.idModulo == null && other.idModulo != null) || (this.idModulo != null && !this.idModulo.equals(other.idModulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.UsrModulo[ idModulo=" + idModulo + " ]";
    }
    
}
