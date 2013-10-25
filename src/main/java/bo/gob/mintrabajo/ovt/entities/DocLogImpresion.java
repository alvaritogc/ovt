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
@Table(name = "DOC_LOG_IMPRESION")
@NamedQueries({
    @NamedQuery(name = "DocLogImpresion.findAll", query = "SELECT d FROM DocLogImpresion d")})
public class DocLogImpresion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DOCLOGIMPRESION")
    private Long idDoclogimpresion;
    @Basic(optional = false)
    @Column(name = "TIPO_IMPRESION")
    private String tipoImpresion;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    @ManyToOne(optional = false)
    private DocDocumento idDocumento;

    public DocLogImpresion() {
    }

    public DocLogImpresion(Long idDoclogimpresion) {
        this.idDoclogimpresion = idDoclogimpresion;
    }

    public DocLogImpresion(Long idDoclogimpresion, String tipoImpresion, Date fechaBitacora, String registroBitacora) {
        this.idDoclogimpresion = idDoclogimpresion;
        this.tipoImpresion = tipoImpresion;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdDoclogimpresion() {
        return idDoclogimpresion;
    }

    public void setIdDoclogimpresion(Long idDoclogimpresion) {
        this.idDoclogimpresion = idDoclogimpresion;
    }

    public String getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
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

    public DocDocumento getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(DocDocumento idDocumento) {
        this.idDocumento = idDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDoclogimpresion != null ? idDoclogimpresion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocLogImpresion)) {
            return false;
        }
        DocLogImpresion other = (DocLogImpresion) object;
        if ((this.idDoclogimpresion == null && other.idDoclogimpresion != null) || (this.idDoclogimpresion != null && !this.idDoclogimpresion.equals(other.idDoclogimpresion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocLogImpresion[ idDoclogimpresion=" + idDoclogimpresion + " ]";
    }
    
}
