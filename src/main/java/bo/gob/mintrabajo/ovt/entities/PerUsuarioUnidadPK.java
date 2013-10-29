/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo.gob.mintrabajo.ovt.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author rvelasquez
 */
@Embeddable
public class PerUsuarioUnidadPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private Long idUsuario;
    @Basic(optional = false)
    @Column(name = "ID_PERSONA")
    private String idPersona;
    @Basic(optional = false)
    @Column(name = "ID_UNIDAD")
    private Long idUnidad;

    public PerUsuarioUnidadPK() {
    }

    public PerUsuarioUnidadPK(Long idUsuario, String idPersona, Long idUnidad) {
        this.idUsuario = idUsuario;
        this.idPersona = idPersona;
        this.idUnidad = idUnidad;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public Long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(Long idUnidad) {
        this.idUnidad = idUnidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
       // hash += (int) idUsuario;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        //hash += (int) idUnidad;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerUsuarioUnidadPK)) {
            return false;
        }
        PerUsuarioUnidadPK other = (PerUsuarioUnidadPK) object;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        if (this.idUnidad != other.idUnidad) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.PerUsuarioUnidadPK[ idUsuario=" + idUsuario + ", idPersona=" + idPersona + ", idUnidad=" + idUnidad + " ]";
    }
    
}
