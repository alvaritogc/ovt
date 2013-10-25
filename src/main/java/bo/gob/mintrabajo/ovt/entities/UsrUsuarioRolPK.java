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
public class UsrUsuarioRolPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private long idUsuario;
    @Basic(optional = false)
    @Column(name = "ID_ROL")
    private long idRol;

    public UsrUsuarioRolPK() {
    }

    public UsrUsuarioRolPK(long idUsuario, long idRol) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdRol() {
        return idRol;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUsuario;
        hash += (int) idRol;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsrUsuarioRolPK)) {
            return false;
        }
        UsrUsuarioRolPK other = (UsrUsuarioRolPK) object;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (this.idRol != other.idRol) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.UsrUsuarioRolPK[ idUsuario=" + idUsuario + ", idRol=" + idRol + " ]";
    }
    
}
