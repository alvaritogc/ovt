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
@Table(name = "USR_ROL")
@NamedQueries({
    @NamedQuery(name = "UsrRol.findAll", query = "SELECT u FROM UsrRol u")})
public class UsrRol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ROL")
    private Long idRol;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "ES_INTERNO")
    private short esInterno;
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
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrRol", fetch = FetchType.LAZY)
//    private List<UsrRolRecurso> usrRolRecursoList;
//    @OneToMany(mappedBy = "idRol", fetch = FetchType.LAZY)
//    private List<DocTransicion> docTransicionList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrRol", fetch = FetchType.LAZY)
//    private List<UsrUsuarioRol> usrUsuarioRolList;
    @JoinColumn(name = "ID_MODULO", referencedColumnName = "ID_MODULO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UsrModulo idModulo;

    public UsrRol() {
    }

    public UsrRol(Long idRol) {
        this.idRol = idRol;
    }

    public UsrRol(Long idRol, String nombre, short esInterno, String estado, Date fechaBitacora, String registroBitacora) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.esInterno = esInterno;
        this.estado = estado;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public short getEsInterno() {
        return esInterno;
    }

    public void setEsInterno(short esInterno) {
        this.esInterno = esInterno;
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

//    public List<UsrRolRecurso> getUsrRolRecursoList() {
//        return usrRolRecursoList;
//    }
//
//    public void setUsrRolRecursoList(List<UsrRolRecurso> usrRolRecursoList) {
//        this.usrRolRecursoList = usrRolRecursoList;
//    }
//
//    public List<DocTransicion> getDocTransicionList() {
//        return docTransicionList;
//    }
//
//    public void setDocTransicionList(List<DocTransicion> docTransicionList) {
//        this.docTransicionList = docTransicionList;
//    }
//
//    public List<UsrUsuarioRol> getUsrUsuarioRolList() {
//        return usrUsuarioRolList;
//    }
//
//    public void setUsrUsuarioRolList(List<UsrUsuarioRol> usrUsuarioRolList) {
//        this.usrUsuarioRolList = usrUsuarioRolList;
//    }

    public UsrModulo getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(UsrModulo idModulo) {
        this.idModulo = idModulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsrRol)) {
            return false;
        }
        UsrRol other = (UsrRol) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.UsrRol[ idRol=" + idRol + " ]";
    }
    
}
