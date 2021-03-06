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

import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author rvelasquez
 */
@Entity
@Table(name = "DOC_BINARIO")
@NamedQueries({
    @NamedQuery(name = "DocBinario.findAll", query = "SELECT d FROM DocBinario d")})
public class DocBinario implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DocBinarioPK docBinarioPK;
    @Basic(optional = false)
    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;
    @Lob
    @Column(name = "BINARIO")
    private byte[] binario;
    @Basic(optional = false)
    @Column(name = "METADATA")
    private String metadata;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DocDocumento docDocumento;
    @Basic(optional = false)
    @Column(name = "VALIDADO")
    private boolean validado;
    @Transient
    private InputStream inputStream;

    public DocBinario() {
    }

    public DocBinario(DocBinarioPK docBinarioPK) {
        this.docBinarioPK = docBinarioPK;
    }

    public DocBinario(DocBinarioPK docBinarioPK, String tipoDocumento, String metadata, Date fechaBitacora, String registroBitacora, boolean validado) {
        this.docBinarioPK = docBinarioPK;
        this.tipoDocumento = tipoDocumento;
        this.metadata = metadata;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
        this.validado = validado;
    }

    public DocBinario(long idBinario, long idDocumento) {
        this.docBinarioPK = new DocBinarioPK(idBinario, idDocumento);
    }

    public DocBinarioPK getDocBinarioPK() {
        return docBinarioPK;
    }

    public void setDocBinarioPK(DocBinarioPK docBinarioPK) {
        this.docBinarioPK = docBinarioPK;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public byte[] getBinario() {
        return binario;
    }

    public void setBinario(byte[] binario) {
        this.binario = binario;
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

    public DocDocumento getDocDocumento() {
        return docDocumento;
    }

    public void setDocDocumento(DocDocumento docDocumento) {
        this.docDocumento = docDocumento;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docBinarioPK != null ? docBinarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocBinario)) {
            return false;
        }
        DocBinario other = (DocBinario) object;
        if ((this.docBinarioPK == null && other.docBinarioPK != null) || (this.docBinarioPK != null && !this.docBinarioPK.equals(other.docBinarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocBinario[ docBinarioPK=" + docBinarioPK + " ]";
    }
}