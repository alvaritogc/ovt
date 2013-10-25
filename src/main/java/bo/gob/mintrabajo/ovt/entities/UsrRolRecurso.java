/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo.gob.mintrabajo.ovt.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "USR_ROL_RECURSO")
@NamedQueries({
    @NamedQuery(name = "UsrRolRecurso.findAll", query = "SELECT u FROM UsrRolRecurso u")})
public class UsrRolRecurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsrRolRecursoPK usrRolRecursoPK;
    @Basic(optional = false)
    @Column(name = "WX")
    private String wx;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UsrRol usrRol;
    @JoinColumn(name = "ID_RECURSO", referencedColumnName = "ID_RECURSO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UsrRecurso usrRecurso;

    public UsrRolRecurso() {
    }

    public UsrRolRecurso(UsrRolRecursoPK usrRolRecursoPK) {
        this.usrRolRecursoPK = usrRolRecursoPK;
    }

    public UsrRolRecurso(UsrRolRecursoPK usrRolRecursoPK, String wx, Date fechaBitacora, String registroBitacora) {
        this.usrRolRecursoPK = usrRolRecursoPK;
        this.wx = wx;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public UsrRolRecurso(long idRol, long idRecurso) {
        this.usrRolRecursoPK = new UsrRolRecursoPK(idRol, idRecurso);
    }

    public UsrRolRecursoPK getUsrRolRecursoPK() {
        return usrRolRecursoPK;
    }

    public void setUsrRolRecursoPK(UsrRolRecursoPK usrRolRecursoPK) {
        this.usrRolRecursoPK = usrRolRecursoPK;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
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

    public UsrRol getUsrRol() {
        return usrRol;
    }

    public void setUsrRol(UsrRol usrRol) {
        this.usrRol = usrRol;
    }

    public UsrRecurso getUsrRecurso() {
        return usrRecurso;
    }

    public void setUsrRecurso(UsrRecurso usrRecurso) {
        this.usrRecurso = usrRecurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrRolRecursoPK != null ? usrRolRecursoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsrRolRecurso)) {
            return false;
        }
        UsrRolRecurso other = (UsrRolRecurso) object;
        if ((this.usrRolRecursoPK == null && other.usrRolRecursoPK != null) || (this.usrRolRecursoPK != null && !this.usrRolRecursoPK.equals(other.usrRolRecursoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.UsrRolRecurso[ usrRolRecursoPK=" + usrRolRecursoPK + " ]";
    }
    
}
