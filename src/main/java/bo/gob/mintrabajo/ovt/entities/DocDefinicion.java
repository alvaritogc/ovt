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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
@Table(name = "DOC_DEFINICION")
@NamedQueries({
    @NamedQuery(name = "DocDefinicion.findAll", query = "SELECT d FROM DocDefinicion d")})
public class DocDefinicion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DocDefinicionPK docDefinicionPK;
    @Basic(optional = false)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "TIPO_GRUPO_DOCUMENTO")
    private String tipoGrupoDocumento;
    @Basic(optional = false)
    @Column(name = "ALIAS")
    private String alias;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "COD_ESTADO", referencedColumnName = "COD_ESTADO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParDocumentoEstado codEstado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docDefinicion", fetch = FetchType.LAZY)
    private List<DocDocumento> docDocumentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "docDefinicion", fetch = FetchType.LAZY)
    private List<DocTransicion> docTransicionList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "docDefinicion", fetch = FetchType.LAZY)
    private DocNumeracion docNumeracion;

    public DocDefinicion() {
    }

    public DocDefinicion(DocDefinicionPK docDefinicionPK) {
        this.docDefinicionPK = docDefinicionPK;
    }

    public DocDefinicion(DocDefinicionPK docDefinicionPK, String nombre, String tipoGrupoDocumento, String alias, Date fechaBitacora, String registroBitacora) {
        this.docDefinicionPK = docDefinicionPK;
        this.nombre = nombre;
        this.tipoGrupoDocumento = tipoGrupoDocumento;
        this.alias = alias;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public DocDefinicion(String codDocumento, short version) {
        this.docDefinicionPK = new DocDefinicionPK(codDocumento, version);
    }

    public DocDefinicionPK getDocDefinicionPK() {
        return docDefinicionPK;
    }

    public void setDocDefinicionPK(DocDefinicionPK docDefinicionPK) {
        this.docDefinicionPK = docDefinicionPK;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoGrupoDocumento() {
        return tipoGrupoDocumento;
    }

    public void setTipoGrupoDocumento(String tipoGrupoDocumento) {
        this.tipoGrupoDocumento = tipoGrupoDocumento;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public List<DocTransicion> getDocTransicionList() {
        return docTransicionList;
    }

    public void setDocTransicionList(List<DocTransicion> docTransicionList) {
        this.docTransicionList = docTransicionList;
    }

    public DocNumeracion getDocNumeracion() {
        return docNumeracion;
    }

    public void setDocNumeracion(DocNumeracion docNumeracion) {
        this.docNumeracion = docNumeracion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docDefinicionPK != null ? docDefinicionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocDefinicion)) {
            return false;
        }
        DocDefinicion other = (DocDefinicion) object;
        if ((this.docDefinicionPK == null && other.docDefinicionPK != null) || (this.docDefinicionPK != null && !this.docDefinicionPK.equals(other.docDefinicionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocDefinicion[ docDefinicionPK=" + docDefinicionPK + " ]";
    }
    
}
