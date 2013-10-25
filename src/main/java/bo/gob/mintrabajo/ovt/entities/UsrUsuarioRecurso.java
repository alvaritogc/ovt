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
@Table(name = "USR_USUARIO_RECURSO")
@NamedQueries({
    @NamedQuery(name = "UsrUsuarioRecurso.findAll", query = "SELECT u FROM UsrUsuarioRecurso u")})
public class UsrUsuarioRecurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsrUsuarioRecursoPK usrUsuarioRecursoPK;
    @Basic(optional = false)
    @Column(name = "ES_DENEGADO")
    private short esDenegado;
    @Basic(optional = false)
    @Column(name = "WX")
    private String wx;
    @Basic(optional = false)
    @Column(name = "FECHA_LIMITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLimite;
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
    @JoinColumn(name = "ID_RECURSO", referencedColumnName = "ID_RECURSO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private UsrRecurso usrRecurso;

    public UsrUsuarioRecurso() {
    }

    public UsrUsuarioRecurso(UsrUsuarioRecursoPK usrUsuarioRecursoPK) {
        this.usrUsuarioRecursoPK = usrUsuarioRecursoPK;
    }

    public UsrUsuarioRecurso(UsrUsuarioRecursoPK usrUsuarioRecursoPK, short esDenegado, String wx, Date fechaLimite, Date fechaBitacora, String registroBitacora) {
        this.usrUsuarioRecursoPK = usrUsuarioRecursoPK;
        this.esDenegado = esDenegado;
        this.wx = wx;
        this.fechaLimite = fechaLimite;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public UsrUsuarioRecurso(long idUsuario, long idRecurso) {
        this.usrUsuarioRecursoPK = new UsrUsuarioRecursoPK(idUsuario, idRecurso);
    }

    public UsrUsuarioRecursoPK getUsrUsuarioRecursoPK() {
        return usrUsuarioRecursoPK;
    }

    public void setUsrUsuarioRecursoPK(UsrUsuarioRecursoPK usrUsuarioRecursoPK) {
        this.usrUsuarioRecursoPK = usrUsuarioRecursoPK;
    }

    public short getEsDenegado() {
        return esDenegado;
    }

    public void setEsDenegado(short esDenegado) {
        this.esDenegado = esDenegado;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
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

    public UsrRecurso getUsrRecurso() {
        return usrRecurso;
    }

    public void setUsrRecurso(UsrRecurso usrRecurso) {
        this.usrRecurso = usrRecurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrUsuarioRecursoPK != null ? usrUsuarioRecursoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsrUsuarioRecurso)) {
            return false;
        }
        UsrUsuarioRecurso other = (UsrUsuarioRecurso) object;
        if ((this.usrUsuarioRecursoPK == null && other.usrUsuarioRecursoPK != null) || (this.usrUsuarioRecursoPK != null && !this.usrUsuarioRecursoPK.equals(other.usrUsuarioRecursoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.UsrUsuarioRecurso[ usrUsuarioRecursoPK=" + usrUsuarioRecursoPK + " ]";
    }
    
}
