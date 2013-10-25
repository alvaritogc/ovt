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
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "DOC_ESTADO")
@NamedQueries({
    @NamedQuery(name = "DocEstado.findAll", query = "SELECT d FROM DocEstado d")})
public class DocEstado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ESTADO_DOCUMENTO")
    private String idEstadoDocumento;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;

    public DocEstado() {
    }

    public DocEstado(String idEstadoDocumento) {
        this.idEstadoDocumento = idEstadoDocumento;
    }

    public String getIdEstadoDocumento() {
        return idEstadoDocumento;
    }

    public void setIdEstadoDocumento(String idEstadoDocumento) {
        this.idEstadoDocumento = idEstadoDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        hash += (idEstadoDocumento != null ? idEstadoDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocEstado)) {
            return false;
        }
        DocEstado other = (DocEstado) object;
        if ((this.idEstadoDocumento == null && other.idEstadoDocumento != null) || (this.idEstadoDocumento != null && !this.idEstadoDocumento.equals(other.idEstadoDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocEstado[ idEstadoDocumento=" + idEstadoDocumento + " ]";
    }
    
}
