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

import bo.gob.mintrabajo.ovt.Util.ArtificioEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rvelasquez
 */
@Entity
@Table(name = "USR_RECURSO")
@NamedQueries({
    @NamedQuery(name = "UsrRecurso.findAll", query = "SELECT u FROM UsrRecurso u")})
public class UsrRecurso extends ArtificioEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_RECURSO")
    private Long idRecurso;
    @Basic(optional = false)
    @Column(name = "TIPO_RECURSO")
    private String tipoRecurso;
    @Basic(optional = false)
    @Column(name = "TIPO_PLATAFORMA")
    private String tipoPlataforma;
    @Basic(optional = false)
    @Column(name = "ORDEN")
    private short orden;
    @Basic(optional = false)
    @Column(name = "ETIQUETA")
    private String etiqueta;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "EJECUTABLE")
    private String ejecutable;
    @Basic(optional = false)
    @Column(name = "ES_VERIFICABLE")
    private short esVerificable;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrRecurso", fetch = FetchType.LAZY)
    private List<UsrRolRecurso> usrRolRecursoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usrRecurso", fetch = FetchType.LAZY)
    private List<UsrUsuarioRecurso> usrUsuarioRecursoList;
    @OneToMany(mappedBy = "idRecursoPadre", fetch = FetchType.LAZY)
    private List<UsrRecurso> usrRecursoList;
    @JoinColumn(name = "ID_RECURSO_PADRE", referencedColumnName = "ID_RECURSO")
    @ManyToOne(fetch = FetchType.LAZY)
    private UsrRecurso idRecursoPadre;
    @JoinColumn(name = "ID_MODULO", referencedColumnName = "ID_MODULO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UsrModulo idModulo;
    @OneToMany(mappedBy = "idRecurso", fetch = FetchType.LAZY)
    private List<ParMensajeApp> parMensajeAppList;

    public UsrRecurso() {
    }

    public UsrRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    public UsrRecurso(Long idRecurso, String tipoRecurso, String tipoPlataforma, short orden, String etiqueta, String descripcion, String ejecutable, short esVerificable, String estado, Date fechaBitacora, String registroBitacora) {
        this.idRecurso = idRecurso;
        this.tipoRecurso = tipoRecurso;
        this.tipoPlataforma = tipoPlataforma;
        this.orden = orden;
        this.etiqueta = etiqueta;
        this.descripcion = descripcion;
        this.ejecutable = ejecutable;
        this.esVerificable = esVerificable;
        this.estado = estado;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Long idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public String getTipoPlataforma() {
        return tipoPlataforma;
    }

    public void setTipoPlataforma(String tipoPlataforma) {
        this.tipoPlataforma = tipoPlataforma;
    }

    public short getOrden() {
        return orden;
    }

    public void setOrden(short orden) {
        this.orden = orden;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEjecutable() {
        return ejecutable;
    }

    public void setEjecutable(String ejecutable) {
        this.ejecutable = ejecutable;
    }

    public short getEsVerificable() {
        return esVerificable;
    }

    public void setEsVerificable(short esVerificable) {
        this.esVerificable = esVerificable;
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

    public List<UsrRolRecurso> getUsrRolRecursoList() {
        return usrRolRecursoList;
    }

    public void setUsrRolRecursoList(List<UsrRolRecurso> usrRolRecursoList) {
        this.usrRolRecursoList = usrRolRecursoList;
    }

    public List<UsrUsuarioRecurso> getUsrUsuarioRecursoList() {
        return usrUsuarioRecursoList;
    }

    public void setUsrUsuarioRecursoList(List<UsrUsuarioRecurso> usrUsuarioRecursoList) {
        this.usrUsuarioRecursoList = usrUsuarioRecursoList;
    }

    public List<UsrRecurso> getUsrRecursoList() {
        return usrRecursoList;
    }

    public void setUsrRecursoList(List<UsrRecurso> usrRecursoList) {
        this.usrRecursoList = usrRecursoList;
    }

    public UsrRecurso getIdRecursoPadre() {
        return idRecursoPadre;
    }

    public void setIdRecursoPadre(UsrRecurso idRecursoPadre) {
        this.idRecursoPadre = idRecursoPadre;
    }

    public UsrModulo getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(UsrModulo idModulo) {
        this.idModulo = idModulo;
    }

    public List<ParMensajeApp> getParMensajeAppList() {
        return parMensajeAppList;
    }

    public void setParMensajeAppList(List<ParMensajeApp> parMensajeAppList) {
        this.parMensajeAppList = parMensajeAppList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRecurso != null ? idRecurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsrRecurso)) {
            return false;
        }
        UsrRecurso other = (UsrRecurso) object;
        if ((this.idRecurso == null && other.idRecurso != null) || (this.idRecurso != null && !this.idRecurso.equals(other.idRecurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.UsrRecurso[ idRecurso=" + idRecurso + " ]";
    }
    
}
