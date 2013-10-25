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
@Table(name = "USR_USUARIO_ROL")
@NamedQueries({
    @NamedQuery(name = "UsrUsuarioRol.findAll", query = "SELECT u FROM UsrUsuarioRol u")})
public class UsrUsuarioRol implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsrUsuarioRolPK usrUsuarioRolPK;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UsrUsuario usrUsuario;
    @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UsrRol usrRol;
    @JoinColumn(name = "ID_MODULO", referencedColumnName = "ID_MODULO")
    @ManyToOne(optional = false)
    private UsrModulo idModulo;

    public UsrUsuarioRol() {
    }

    public UsrUsuarioRol(UsrUsuarioRolPK usrUsuarioRolPK) {
        this.usrUsuarioRolPK = usrUsuarioRolPK;
    }

    public UsrUsuarioRol(UsrUsuarioRolPK usrUsuarioRolPK, Date fechaBitacora, String registroBitacora) {
        this.usrUsuarioRolPK = usrUsuarioRolPK;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public UsrUsuarioRol(long idUsuario, long idRol) {
        this.usrUsuarioRolPK = new UsrUsuarioRolPK(idUsuario, idRol);
    }

    public UsrUsuarioRolPK getUsrUsuarioRolPK() {
        return usrUsuarioRolPK;
    }

    public void setUsrUsuarioRolPK(UsrUsuarioRolPK usrUsuarioRolPK) {
        this.usrUsuarioRolPK = usrUsuarioRolPK;
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

    public UsrRol getUsrRol() {
        return usrRol;
    }

    public void setUsrRol(UsrRol usrRol) {
        this.usrRol = usrRol;
    }

    public UsrModulo getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(UsrModulo idModulo) {
        this.idModulo = idModulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrUsuarioRolPK != null ? usrUsuarioRolPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsrUsuarioRol)) {
            return false;
        }
        UsrUsuarioRol other = (UsrUsuarioRol) object;
        if ((this.usrUsuarioRolPK == null && other.usrUsuarioRolPK != null) || (this.usrUsuarioRolPK != null && !this.usrUsuarioRolPK.equals(other.usrUsuarioRolPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.UsrUsuarioRol[ usrUsuarioRolPK=" + usrUsuarioRolPK + " ]";
    }
    
}
