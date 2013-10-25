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
public class UsrUsuarioRecursoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private long idUsuario;
    @Basic(optional = false)
    @Column(name = "ID_RECURSO")
    private long idRecurso;

    public UsrUsuarioRecursoPK() {
    }

    public UsrUsuarioRecursoPK(long idUsuario, long idRecurso) {
        this.idUsuario = idUsuario;
        this.idRecurso = idRecurso;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(long idRecurso) {
        this.idRecurso = idRecurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUsuario;
        hash += (int) idRecurso;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsrUsuarioRecursoPK)) {
            return false;
        }
        UsrUsuarioRecursoPK other = (UsrUsuarioRecursoPK) object;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (this.idRecurso != other.idRecurso) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.UsrUsuarioRecursoPK[ idUsuario=" + idUsuario + ", idRecurso=" + idRecurso + " ]";
    }
    
}
