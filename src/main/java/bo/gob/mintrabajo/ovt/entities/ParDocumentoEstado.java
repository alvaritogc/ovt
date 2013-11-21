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
@Table(name = "PAR_DOCUMENTO_ESTADO")
@NamedQueries({
    @NamedQuery(name = "ParDocumentoEstado.findAll", query = "SELECT p FROM ParDocumentoEstado p")})
public class ParDocumentoEstado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_ESTADO")
    private String codEstado;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "METADATA")
    private String metadata;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEstado", fetch = FetchType.LAZY)
//    private List<DocDocumento> docDocumentoList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEstadoFinal", fetch = FetchType.LAZY)
//    private List<DocLogEstado> docLogEstadoList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codEstadoInicial", fetch = FetchType.LAZY)
//    private List<DocLogEstado> docLogEstadoList1;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parDocumentoEstado", fetch = FetchType.LAZY)
//    private List<DocTransicion> docTransicionList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parDocumentoEstado1", fetch = FetchType.LAZY)
//    private List<DocTransicion> docTransicionList1;

    public ParDocumentoEstado() {
    }

    public ParDocumentoEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    public ParDocumentoEstado(String codEstado, String descripcion, String estado, Date fechaBitacora, String registroBitacora) {
        this.codEstado = codEstado;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
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

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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

//    public List<DocDocumento> getDocDocumentoList() {
//        return docDocumentoList;
//    }
//
//    public void setDocDocumentoList(List<DocDocumento> docDocumentoList) {
//        this.docDocumentoList = docDocumentoList;
//    }
//
//    public List<DocLogEstado> getDocLogEstadoList() {
//        return docLogEstadoList;
//    }
//
//    public void setDocLogEstadoList(List<DocLogEstado> docLogEstadoList) {
//        this.docLogEstadoList = docLogEstadoList;
//    }
//
//    public List<DocLogEstado> getDocLogEstadoList1() {
//        return docLogEstadoList1;
//    }
//
//    public void setDocLogEstadoList1(List<DocLogEstado> docLogEstadoList1) {
//        this.docLogEstadoList1 = docLogEstadoList1;
//    }
//
//    public List<DocTransicion> getDocTransicionList() {
//        return docTransicionList;
//    }
//
//    public void setDocTransicionList(List<DocTransicion> docTransicionList) {
//        this.docTransicionList = docTransicionList;
//    }
//
//    public List<DocTransicion> getDocTransicionList1() {
//        return docTransicionList1;
//    }
//
//    public void setDocTransicionList1(List<DocTransicion> docTransicionList1) {
//        this.docTransicionList1 = docTransicionList1;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codEstado != null ? codEstado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParDocumentoEstado)) {
            return false;
        }
        ParDocumentoEstado other = (ParDocumentoEstado) object;
        if ((this.codEstado == null && other.codEstado != null) || (this.codEstado != null && !this.codEstado.equals(other.codEstado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado[ codEstado=" + codEstado + " ]";
    }
    
}
