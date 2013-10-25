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
@Table(name = "PER_USUARIO")
@NamedQueries({
    @NamedQuery(name = "PerUsuario.findAll", query = "SELECT p FROM PerUsuario p")})
public class PerUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PerUsuarioPK perUsuarioPK;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;

    public PerUsuario() {
    }

    public PerUsuario(PerUsuarioPK perUsuarioPK) {
        this.perUsuarioPK = perUsuarioPK;
    }

    public PerUsuario(PerUsuarioPK perUsuarioPK, Date fechaBitacora, String registroBitacora) {
        this.perUsuarioPK = perUsuarioPK;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public PerUsuario(String idPersona, long idUnidad, long idUsuario) {
        this.perUsuarioPK = new PerUsuarioPK(idPersona, idUnidad, idUsuario);
    }

    public PerUsuarioPK getPerUsuarioPK() {
        return perUsuarioPK;
    }

    public void setPerUsuarioPK(PerUsuarioPK perUsuarioPK) {
        this.perUsuarioPK = perUsuarioPK;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perUsuarioPK != null ? perUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerUsuario)) {
            return false;
        }
        PerUsuario other = (PerUsuario) object;
        if ((this.perUsuarioPK == null && other.perUsuarioPK != null) || (this.perUsuarioPK != null && !this.perUsuarioPK.equals(other.perUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.PerUsuario[ perUsuarioPK=" + perUsuarioPK + " ]";
    }
    
}