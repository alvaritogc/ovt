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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rvelasquez
 */
@Entity
@Table(name = "PER_USUARIO_UNIDAD")
@NamedQueries({
    @NamedQuery(name = "PerUsuarioUnidad.findAll", query = "SELECT p FROM PerUsuarioUnidad p")})
public class PerUsuarioUnidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PerUsuarioUnidadPK perUsuarioUnidadPK;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UsrUsuario usrUsuario;
    @JoinColumns({
        @JoinColumn(name = "ID_UNIDAD", referencedColumnName = "ID_UNIDAD", insertable = false, updatable = false),
        @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PerUnidad perUnidad;

    public PerUsuarioUnidad() {
    }

    public PerUsuarioUnidad(PerUsuarioUnidadPK perUsuarioUnidadPK) {
        this.perUsuarioUnidadPK = perUsuarioUnidadPK;
    }

    public PerUsuarioUnidad(PerUsuarioUnidadPK perUsuarioUnidadPK, Date fechaBitacora, String registroBitacora) {
        this.perUsuarioUnidadPK = perUsuarioUnidadPK;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public PerUsuarioUnidad(long idUsuario, String idPersona, long idUnidad) {
        this.perUsuarioUnidadPK = new PerUsuarioUnidadPK(idUsuario, idPersona, idUnidad);
    }

    public PerUsuarioUnidadPK getPerUsuarioUnidadPK() {
        return perUsuarioUnidadPK;
    }

    public void setPerUsuarioUnidadPK(PerUsuarioUnidadPK perUsuarioUnidadPK) {
        this.perUsuarioUnidadPK = perUsuarioUnidadPK;
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

    public UsrUsuario getUsrUsuario() {
        return usrUsuario;
    }

    public void setUsrUsuario(UsrUsuario usrUsuario) {
        this.usrUsuario = usrUsuario;
    }

    public PerUnidad getPerUnidad() {
        return perUnidad;
    }

    public void setPerUnidad(PerUnidad perUnidad) {
        this.perUnidad = perUnidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perUsuarioUnidadPK != null ? perUsuarioUnidadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerUsuarioUnidad)) {
            return false;
        }
        PerUsuarioUnidad other = (PerUsuarioUnidad) object;
        if ((this.perUsuarioUnidadPK == null && other.perUsuarioUnidadPK != null) || (this.perUsuarioUnidadPK != null && !this.perUsuarioUnidadPK.equals(other.perUsuarioUnidadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.PerUsuarioUnidad[ perUsuarioUnidadPK=" + perUsuarioUnidadPK + " ]";
    }
    
}
