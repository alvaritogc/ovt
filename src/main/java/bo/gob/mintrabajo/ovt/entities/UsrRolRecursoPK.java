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
public class UsrRolRecursoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_ROL")
    private long idRol;
    @Basic(optional = false)
    @Column(name = "ID_RECURSO")
    private long idRecurso;

    public UsrRolRecursoPK() {
    }

    public UsrRolRecursoPK(long idRol, long idRecurso) {
        this.idRol = idRol;
        this.idRecurso = idRecurso;
    }

    public long getIdRol() {
        return idRol;
    }

    public void setIdRol(long idRol) {
        this.idRol = idRol;
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
        hash += (int) idRol;
        hash += (int) idRecurso;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsrRolRecursoPK)) {
            return false;
        }
        UsrRolRecursoPK other = (UsrRolRecursoPK) object;
        if (this.idRol != other.idRol) {
            return false;
        }
        if (this.idRecurso != other.idRecurso) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.UsrRolRecursoPK[ idRol=" + idRol + ", idRecurso=" + idRecurso + " ]";
    }
    
}
