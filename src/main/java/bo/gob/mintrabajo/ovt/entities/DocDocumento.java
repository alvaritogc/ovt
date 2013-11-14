/*
 * Copyright 2013 rvelasquez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bo.gob.mintrabajo.ovt.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rvelasquez
 */
@Entity
@Table(name = "DOC_DOCUMENTO")
@NamedQueries({
    @NamedQuery(name = "DocDocumento.findAll", query = "SELECT d FROM DocDocumento d")})
public class DocDocumento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DOCUMENTO")
    private Long idDocumento;
    @Basic(optional = false)
    @Column(name = "NUMERO_DOCUMENTO")
    private long numeroDocumento;
    @Basic(optional = false)
    @Column(name = "FECHA_DOCUMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumento;
    @Column(name = "FECHA_REFERENCA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReferenca;
    @Basic(optional = false)
    @Column(name = "TIPO_MEDIO_REGISTRO")
    private String tipoMedioRegistro;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDocumento", fetch = FetchType.LAZY)
    private List<DocLogImpresion> docLogImpresionList;
    @JoinColumns({
        @JoinColumn(name = "ID_UNIDAD", referencedColumnName = "ID_UNIDAD"),
        @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private PerUnidad perUnidad;
    @JoinColumn(name = "COD_ESTADO", referencedColumnName = "COD_ESTADO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParDocumentoEstado codEstado;
    @OneToMany(mappedBy = "idDocumentoRef", fetch = FetchType.LAZY)
    private List<DocDocumento> docDocumentoList;
    @JoinColumn(name = "ID_DOCUMENTO_REF", referencedColumnName = "ID_DOCUMENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private DocDocumento idDocumentoRef;
    @JoinColumns({
        @JoinColumn(name = "COD_DOCUMENTO", referencedColumnName = "COD_DOCUMENTO"),
        @JoinColumn(name = "VERSION", referencedColumnName = "VERSION")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DocDefinicion docDefinicion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDocumento", fetch = FetchType.LAZY)
    private List<DocLogEstado> docLogEstadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDocumento", fetch = FetchType.LAZY)
    private List<DocGenerico> docGenericoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docDocumento", fetch = FetchType.LAZY)
    private List<DocBinario> docBinarioList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idDocumento", fetch = FetchType.LAZY)
    private DocPlanilla docPlanilla;

    public DocDocumento() {
    }

    public DocDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public DocDocumento(Long idDocumento, long numeroDocumento, Date fechaDocumento, String tipoMedioRegistro, Date fechaBitacora, String registroBitacora) {
        this.idDocumento = idDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaDocumento = fechaDocumento;
        this.tipoMedioRegistro = tipoMedioRegistro;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public Date getFechaReferenca() {
        return fechaReferenca;
    }

    public void setFechaReferenca(Date fechaReferenca) {
        this.fechaReferenca = fechaReferenca;
    }

    public String getTipoMedioRegistro() {
        return tipoMedioRegistro;
    }

    public void setTipoMedioRegistro(String tipoMedioRegistro) {
        this.tipoMedioRegistro = tipoMedioRegistro;
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

    public List<DocLogImpresion> getDocLogImpresionList() {
        return docLogImpresionList;
    }

    public void setDocLogImpresionList(List<DocLogImpresion> docLogImpresionList) {
        this.docLogImpresionList = docLogImpresionList;
    }

    public PerUnidad getPerUnidad() {
        return perUnidad;
    }

    public void setPerUnidad(PerUnidad perUnidad) {
        this.perUnidad = perUnidad;
    }

    public ParDocumentoEstado getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(ParDocumentoEstado codEstado) {
        this.codEstado = codEstado;
    }

    public List<DocDocumento> getDocDocumentoList() {
        return docDocumentoList;
    }

    public void setDocDocumentoList(List<DocDocumento> docDocumentoList) {
        this.docDocumentoList = docDocumentoList;
    }

    public DocDocumento getIdDocumentoRef() {
        return idDocumentoRef;
    }

    public void setIdDocumentoRef(DocDocumento idDocumentoRef) {
        this.idDocumentoRef = idDocumentoRef;
    }

    public DocDefinicion getDocDefinicion() {
        return docDefinicion;
    }

    public void setDocDefinicion(DocDefinicion docDefinicion) {
        this.docDefinicion = docDefinicion;
    }

    public List<DocLogEstado> getDocLogEstadoList() {
        return docLogEstadoList;
    }

    public void setDocLogEstadoList(List<DocLogEstado> docLogEstadoList) {
        this.docLogEstadoList = docLogEstadoList;
    }

    public List<DocGenerico> getDocGenericoList() {
        return docGenericoList;
    }

    public void setDocGenericoList(List<DocGenerico> docGenericoList) {
        this.docGenericoList = docGenericoList;
    }

    public List<DocBinario> getDocBinarioList() {
        return docBinarioList;
    }

    public void setDocBinarioList(List<DocBinario> docBinarioList) {
        this.docBinarioList = docBinarioList;
    }

    public DocPlanilla getDocPlanilla() {
        return docPlanilla;
    }

    public void setDocPlanilla(DocPlanilla docPlanilla) {
        this.docPlanilla = docPlanilla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocumento != null ? idDocumento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocDocumento)) {
            return false;
        }
        DocDocumento other = (DocDocumento) object;
        if ((this.idDocumento == null && other.idDocumento != null) || (this.idDocumento != null && !this.idDocumento.equals(other.idDocumento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocDocumento[ idDocumento=" + idDocumento + " ]";
    }
    
}
