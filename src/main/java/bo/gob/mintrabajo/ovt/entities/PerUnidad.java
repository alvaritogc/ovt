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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
@Table(name = "PER_UNIDAD")
@NamedQueries({
    @NamedQuery(name = "PerUnidad.findAll", query = "SELECT p FROM PerUnidad p")})
public class PerUnidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PerUnidadPK perUnidadPK;
    @Basic(optional = false)
    @Column(name = "NOMBRE_COMERCIAL")
    private String nombreComercial;
    @Basic(optional = false)
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(name = "NRO_CAJA_SALUD")
    private String nroCajaSalud;
    @Column(name = "NRO_AFP")
    private String nroAfp;
    @Column(name = "NRO_FUNDAEMPRESA")
    private String nroFundaempresa;
    @Column(name = "NRO_OTRO")
    private String nroOtro;
    @Basic(optional = false)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Basic(optional = false)
    @Column(name = "TIPO_SOCIEDAD")
    private String tipoSociedad;
    @Basic(optional = false)
    @Column(name = "TIPO_EMPRESA")
    private String tipoEmpresa;
    @Basic(optional = false)
    @Column(name = "ACTIVIDAD_DECLARADA")
    private String actividadDeclarada;
    @Basic(optional = false)
    @Column(name = "ESTADO_UNIDAD")
    private String estadoUnidad;
    @Basic(optional = false)
    @Column(name = "NRO_REFERENCIAL")
    private String nroReferencial;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perUnidad")
    private List<PerUsuarioUnidad> perUsuarioUnidadList;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", insertable = false, updatable = false)
    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    private PerPersona perPersona;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perUnidad")
    private List<DocDocumento> docDocumentoList;
    @OneToMany(mappedBy = "perUnidad")
    private List<ParEntidad> parEntidadList;

    public PerUnidad() {
    }

    public PerUnidad(PerUnidadPK perUnidadPK) {
        this.perUnidadPK = perUnidadPK;
    }

    public PerUnidad(PerUnidadPK perUnidadPK, String nombreComercial, Date fechaNacimiento, String observaciones, String tipoSociedad, String tipoEmpresa, String actividadDeclarada, String estadoUnidad, String nroReferencial, Date fechaBitacora, String registroBitacora) {
        this.perUnidadPK = perUnidadPK;
        this.nombreComercial = nombreComercial;
        this.fechaNacimiento = fechaNacimiento;
        this.observaciones = observaciones;
        this.tipoSociedad = tipoSociedad;
        this.tipoEmpresa = tipoEmpresa;
        this.actividadDeclarada = actividadDeclarada;
        this.estadoUnidad = estadoUnidad;
        this.nroReferencial = nroReferencial;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public PerUnidad(String idPersona, long idUnidad) {
        this.perUnidadPK = new PerUnidadPK(idPersona, idUnidad);
    }

    public PerUnidadPK getPerUnidadPK() {
        return perUnidadPK;
    }

    public void setPerUnidadPK(PerUnidadPK perUnidadPK) {
        this.perUnidadPK = perUnidadPK;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNroCajaSalud() {
        return nroCajaSalud;
    }

    public void setNroCajaSalud(String nroCajaSalud) {
        this.nroCajaSalud = nroCajaSalud;
    }

    public String getNroAfp() {
        return nroAfp;
    }

    public void setNroAfp(String nroAfp) {
        this.nroAfp = nroAfp;
    }

    public String getNroFundaempresa() {
        return nroFundaempresa;
    }

    public void setNroFundaempresa(String nroFundaempresa) {
        this.nroFundaempresa = nroFundaempresa;
    }

    public String getNroOtro() {
        return nroOtro;
    }

    public void setNroOtro(String nroOtro) {
        this.nroOtro = nroOtro;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipoSociedad() {
        return tipoSociedad;
    }

    public void setTipoSociedad(String tipoSociedad) {
        this.tipoSociedad = tipoSociedad;
    }

    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public String getActividadDeclarada() {
        return actividadDeclarada;
    }

    public void setActividadDeclarada(String actividadDeclarada) {
        this.actividadDeclarada = actividadDeclarada;
    }

    public String getEstadoUnidad() {
        return estadoUnidad;
    }

    public void setEstadoUnidad(String estadoUnidad) {
        this.estadoUnidad = estadoUnidad;
    }

    public String getNroReferencial() {
        return nroReferencial;
    }

    public void setNroReferencial(String nroReferencial) {
        this.nroReferencial = nroReferencial;
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

    public List<PerUsuarioUnidad> getPerUsuarioUnidadList() {
        return perUsuarioUnidadList;
    }

    public void setPerUsuarioUnidadList(List<PerUsuarioUnidad> perUsuarioUnidadList) {
        this.perUsuarioUnidadList = perUsuarioUnidadList;
    }

    public PerPersona getPerPersona() {
        return perPersona;
    }

    public void setPerPersona(PerPersona perPersona) {
        this.perPersona = perPersona;
    }

    public List<DocDocumento> getDocDocumentoList() {
        return docDocumentoList;
    }

    public void setDocDocumentoList(List<DocDocumento> docDocumentoList) {
        this.docDocumentoList = docDocumentoList;
    }

    public List<ParEntidad> getParEntidadList() {
        return parEntidadList;
    }

    public void setParEntidadList(List<ParEntidad> parEntidadList) {
        this.parEntidadList = parEntidadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perUnidadPK != null ? perUnidadPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PerUnidad)) {
            return false;
        }
        PerUnidad other = (PerUnidad) object;
        if ((this.perUnidadPK == null && other.perUnidadPK != null) || (this.perUnidadPK != null && !this.perUnidadPK.equals(other.perUnidadPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.PerUnidad[ perUnidadPK=" + perUnidadPK + " ]";
    }
    
}
