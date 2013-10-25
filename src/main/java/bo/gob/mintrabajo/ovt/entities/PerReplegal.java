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
@Table(name = "PER_REPLEGAL")
@NamedQueries({
    @NamedQuery(name = "PerReplegal.findAll", query = "SELECT p FROM PerReplegal p")})
public class PerReplegal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PERSONA_OTRA")
    private Long idPersonaOtra;
    @Basic(optional = false)
    @Column(name = "ID_UNIDAD")
    private long idUnidad;
    @Basic(optional = false)
    @Column(name = "ID_PERSONA")
    private String idPersona;
    @Column(name = "TIPO_DOC_IDENTIDAD")
    private String tipoDocIdentidad;
    @Column(name = "NRO_DOC_IDENTIDAD")
    private String nroDocIdentidad;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "APELLIDO_P")
    private String apellidoP;
    @Column(name = "APELLIDO_M")
    private String apellidoM;
    @Column(name = "FECHA_DOCUMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumento;
    @Column(name = "NRO_DOCUMENTO")
    private String nroDocumento;
    @Column(name = "TIPO_PRECEDENCIA")
    private String tipoPrecedencia;
    @Column(name = "FECHA_REGISTRO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "ESTADO_REP_LEGAL")
    private String estadoRepLegal;

    public PerReplegal() {
    }

    public PerReplegal(Long idPersonaOtra) {
        this.idPersonaOtra = idPersonaOtra;
    }

    public PerReplegal(Long idPersonaOtra, long idUnidad, String idPersona) {
        this.idPersonaOtra = idPersonaOtra;
        this.idUnidad = idUnidad;
        this.idPersona = idPersona;
    }

    public Long getIdPersonaOtra() {
        return idPersonaOtra;
    }

    public void setIdPersonaOtra(Long idPersonaOtra) {
        this.idPersonaOtra = idPersonaOtra;
    }

    public long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(long idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getTipoDocIdentidad() {
        return tipoDocIdentidad;
    }

    public void setTipoDocIdentidad(String tipoDocIdentidad) {
        this.tipoDocIdentidad = tipoDocIdentidad;
    }

    public String getNroDocIdentidad() {
        return nroDocIdentidad;
    }

    public void setNroDocIdentidad(String nroDocIdentidad) {
        this.nroDocIdentidad = nroDocIdentidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getTipoPrecedencia() {
        return tipoPrecedencia;
    }

    public void setTipoPrecedencia(String tipoPrecedencia) {
        this.tipoPrecedencia = tipoPrecedencia;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstadoRepLegal() {
        return estadoRepLegal;
    }

    public void setEstadoRepLegal(String estadoRepLegal) {
        this.estadoRepLegal = estadoRepLegal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersonaOtra != null ? idPersonaOtra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerReplegal)) {
            return false;
        }
        PerReplegal other = (PerReplegal) object;
        if ((this.idPersonaOtra == null && other.idPersonaOtra != null) || (this.idPersonaOtra != null && !this.idPersonaOtra.equals(other.idPersonaOtra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.PerReplegal[ idPersonaOtra=" + idPersonaOtra + " ]";
    }
    
}
