/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo.gob.mintrabajo.ovt.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rvelasquez
 */
@Entity
@Table(name = "PAR_OBLIGACION")
@NamedQueries({
    @NamedQuery(name = "ParObligacion.findAll", query = "SELECT p FROM ParObligacion p")})
public class ParObligacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_OBLIGACION")
    private String codObligacion;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
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
    @Column(name = "ID_OBLIGACION")
    private String idObligacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codObligacion")
    private List<ParObligacionCalendario> parObligacionCalendarioList;

    public ParObligacion() {
    }

    public ParObligacion(String codObligacion) {
        this.codObligacion = codObligacion;
    }

    public ParObligacion(String codObligacion, String descripcion, String estado, Date fechaBitacora, String registroBitacora) {
        this.codObligacion = codObligacion;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public String getCodObligacion() {
        return codObligacion;
    }

    public void setCodObligacion(String codObligacion) {
        this.codObligacion = codObligacion;
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

    public String getIdObligacion() {
        return idObligacion;
    }

    public void setIdObligacion(String idObligacion) {
        this.idObligacion = idObligacion;
    }

    public List<ParObligacionCalendario> getParObligacionCalendarioList() {
        return parObligacionCalendarioList;
    }

    public void setParObligacionCalendarioList(List<ParObligacionCalendario> parObligacionCalendarioList) {
        this.parObligacionCalendarioList = parObligacionCalendarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codObligacion != null ? codObligacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParObligacion)) {
            return false;
        }
        ParObligacion other = (ParObligacion) object;
        if ((this.codObligacion == null && other.codObligacion != null) || (this.codObligacion != null && !this.codObligacion.equals(other.codObligacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParObligacion[ codObligacion=" + codObligacion + " ]";
    }
    
}
