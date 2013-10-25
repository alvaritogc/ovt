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
import javax.persistence.JoinColumns;
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
@Table(name = "DOC_TRANSICION")
@NamedQueries({
    @NamedQuery(name = "DocTransicion.findAll", query = "SELECT d FROM DocTransicion d")})
public class DocTransicion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DocTransicionPK docTransicionPK;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "COD_ESTADO_INICIAL", referencedColumnName = "COD_ESTADO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ParDocumentoEstado parDocumentoEstado;
    @JoinColumn(name = "COD_ESTADO_FINAL", referencedColumnName = "COD_ESTADO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ParDocumentoEstado parDocumentoEstado1;
    @JoinColumns({
        @JoinColumn(name = "COD_DOCUMENTO", referencedColumnName = "COD_DOCUMENTO", insertable = false, updatable = false),
        @JoinColumn(name = "VERSION", referencedColumnName = "VERSION", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private DocDefinicion docDefinicion;

    public DocTransicion() {
    }

    public DocTransicion(DocTransicionPK docTransicionPK) {
        this.docTransicionPK = docTransicionPK;
    }

    public DocTransicion(DocTransicionPK docTransicionPK, String estado, Date fechaBitacora, String registroBitacora) {
        this.docTransicionPK = docTransicionPK;
        this.estado = estado;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public DocTransicion(String codDocumento, short version, String codEstadoInicial, String codEstadoFinal) {
        this.docTransicionPK = new DocTransicionPK(codDocumento, version, codEstadoInicial, codEstadoFinal);
    }

    public DocTransicionPK getDocTransicionPK() {
        return docTransicionPK;
    }

    public void setDocTransicionPK(DocTransicionPK docTransicionPK) {
        this.docTransicionPK = docTransicionPK;
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

    public ParDocumentoEstado getParDocumentoEstado() {
        return parDocumentoEstado;
    }

    public void setParDocumentoEstado(ParDocumentoEstado parDocumentoEstado) {
        this.parDocumentoEstado = parDocumentoEstado;
    }

    public ParDocumentoEstado getParDocumentoEstado1() {
        return parDocumentoEstado1;
    }

    public void setParDocumentoEstado1(ParDocumentoEstado parDocumentoEstado1) {
        this.parDocumentoEstado1 = parDocumentoEstado1;
    }

    public DocDefinicion getDocDefinicion() {
        return docDefinicion;
    }

    public void setDocDefinicion(DocDefinicion docDefinicion) {
        this.docDefinicion = docDefinicion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docTransicionPK != null ? docTransicionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocTransicion)) {
            return false;
        }
        DocTransicion other = (DocTransicion) object;
        if ((this.docTransicionPK == null && other.docTransicionPK != null) || (this.docTransicionPK != null && !this.docTransicionPK.equals(other.docTransicionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocTransicion[ docTransicionPK=" + docTransicionPK + " ]";
    }
    
}
