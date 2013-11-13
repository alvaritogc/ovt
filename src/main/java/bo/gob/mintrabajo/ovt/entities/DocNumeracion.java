/*
 * Copyright 2013 gmercado.
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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author gmercado
 */
@Entity
@Table(name = "DOC_NUMERACION")
@NamedQueries({
    @NamedQuery(name = "DocNumeracion.findAll", query = "SELECT d FROM DocNumeracion d")})
public class DocNumeracion implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DocNumeracionPK docNumeracionPK;
    @Column(name = "GESTION")
    private String gestion;
    @Column(name = "MES")
    private String mes;
    @Basic(optional = false)
    @Column(name = "ULTIMO_NUMERO")
    private long ultimoNumero;
    @JoinColumns({
        @JoinColumn(name = "COD_DOCUMENTO", referencedColumnName = "COD_DOCUMENTO", insertable = false, updatable = false),
        @JoinColumn(name = "VERSION", referencedColumnName = "VERSION", insertable = false, updatable = false)})
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private DocDefinicion docDefinicion;

    public DocNumeracion() {
    }

    public DocNumeracion(DocNumeracionPK docNumeracionPK) {
        this.docNumeracionPK = docNumeracionPK;
    }

    public DocNumeracion(DocNumeracionPK docNumeracionPK, long ultimoNumero) {
        this.docNumeracionPK = docNumeracionPK;
        this.ultimoNumero = ultimoNumero;
    }

    public DocNumeracion(String codDocumento, short version) {
        this.docNumeracionPK = new DocNumeracionPK(codDocumento, version);
    }

    public DocNumeracionPK getDocNumeracionPK() {
        return docNumeracionPK;
    }

    public void setDocNumeracionPK(DocNumeracionPK docNumeracionPK) {
        this.docNumeracionPK = docNumeracionPK;
    }

    public String getGestion() {
        return gestion;
    }

    public void setGestion(String gestion) {
        this.gestion = gestion;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public long getUltimoNumero() {
        return ultimoNumero;
    }

    public void setUltimoNumero(long ultimoNumero) {
        this.ultimoNumero = ultimoNumero;
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
        hash += (docNumeracionPK != null ? docNumeracionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocNumeracion)) {
            return false;
        }
        DocNumeracion other = (DocNumeracion) object;
        if ((this.docNumeracionPK == null && other.docNumeracionPK != null) || (this.docNumeracionPK != null && !this.docNumeracionPK.equals(other.docNumeracionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocNumeracion[ docNumeracionPK=" + docNumeracionPK + " ]";
    }
    
}
