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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author gmercado
 */
@Embeddable
public class PerUsuarioPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_PERSONA")
    private String idPersona;
    @Basic(optional = false)
    @Column(name = "ID_UNIDAD")
    private long idUnidad;
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private long idUsuario;

    public PerUsuarioPK() {
    }

    public PerUsuarioPK(String idPersona, long idUnidad, long idUsuario) {
        this.idPersona = idPersona;
        this.idUnidad = idUnidad;
        this.idUsuario = idUsuario;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(long idUnidad) {
        this.idUnidad = idUnidad;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        hash += (int) idUnidad;
        hash += (int) idUsuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerUsuarioPK)) {
            return false;
        }
        PerUsuarioPK other = (PerUsuarioPK) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        if (this.idUnidad != other.idUnidad) {
            return false;
        }
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.PerUsuarioPK[ idPersona=" + idPersona + ", idUnidad=" + idUnidad + ", idUsuario=" + idUsuario + " ]";
    }
    
}
