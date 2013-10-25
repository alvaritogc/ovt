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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "PAR_ENTIDAD")
@NamedQueries({
    @NamedQuery(name = "ParEntidad.findAll", query = "SELECT p FROM ParEntidad p")})
public class ParEntidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ENTIDAD")
    private Long idEntidad;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "TIPO_ENTIDAD")
    private String tipoEntidad;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumns({
        @JoinColumn(name = "ID_UNIDAD", referencedColumnName = "ID_UNIDAD"),
        @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")})
    @ManyToOne
    private PerUnidad perUnidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntidadBanco")
    private List<DocPlanilla> docPlanillaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEntidadSalud")
    private List<DocPlanilla> docPlanillaList1;

    public ParEntidad() {
    }

    public ParEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public ParEntidad(Long idEntidad, String descripcion, String codigo, String tipoEntidad, Date fechaBitacora, String registroBitacora) {
        this.idEntidad = idEntidad;
        this.descripcion = descripcion;
        this.codigo = codigo;
        this.tipoEntidad = tipoEntidad;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(Long idEntidad) {
        this.idEntidad = idEntidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoEntidad() {
        return tipoEntidad;
    }

    public void setTipoEntidad(String tipoEntidad) {
        this.tipoEntidad = tipoEntidad;
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

    public PerUnidad getPerUnidad() {
        return perUnidad;
    }

    public void setPerUnidad(PerUnidad perUnidad) {
        this.perUnidad = perUnidad;
    }

    public List<DocPlanilla> getDocPlanillaList() {
        return docPlanillaList;
    }

    public void setDocPlanillaList(List<DocPlanilla> docPlanillaList) {
        this.docPlanillaList = docPlanillaList;
    }

    public List<DocPlanilla> getDocPlanillaList1() {
        return docPlanillaList1;
    }

    public void setDocPlanillaList1(List<DocPlanilla> docPlanillaList1) {
        this.docPlanillaList1 = docPlanillaList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntidad != null ? idEntidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParEntidad)) {
            return false;
        }
        ParEntidad other = (ParEntidad) object;
        if ((this.idEntidad == null && other.idEntidad != null) || (this.idEntidad != null && !this.idEntidad.equals(other.idEntidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParEntidad[ idEntidad=" + idEntidad + " ]";
    }
    
}
