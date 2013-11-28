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
import javax.persistence.Lob;
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
@Table(name = "PAR_MENSAJE_CONTENIDO")
@NamedQueries({
    @NamedQuery(name = "ParMensajeContenido.findAll", query = "SELECT p FROM ParMensajeContenido p")})
public class ParMensajeContenido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MENSAJE_CONTENIDO")
    private Long idMensajeContenido;
    @Lob
    @Column(name = "BINARIO")
    private byte[] binario;
    @Lob
    @Column(name = "CONTENIDO")
    private String contenido;
    @Basic(optional = false)
    @Column(name = "ES_DESCARGABLE")
    private short esDescargable;
    @Column(name = "ARCHIVO")
    private String archivo;
    @Column(name = "METADATA")
    private String metadata;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMensajeContenido", fetch = FetchType.LAZY)
//    private List<ParMensajeBinario> parMensajeBinarioList;
    @JoinColumn(name = "ID_MENSAJE_APP", referencedColumnName = "ID_MENSAJE_APP")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParMensajeApp idMensajeApp;

    public ParMensajeContenido() {
    }

    public ParMensajeContenido(Long idMensajeContenido) {
        this.idMensajeContenido = idMensajeContenido;
    }

    public ParMensajeContenido(Long idMensajeContenido, short esDescargable, Date fechaBitacora, String registroBitacora) {
        this.idMensajeContenido = idMensajeContenido;
        this.esDescargable = esDescargable;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdMensajeContenido() {
        return idMensajeContenido;
    }

    public void setIdMensajeContenido(Long idMensajeContenido) {
        this.idMensajeContenido = idMensajeContenido;
    }

    public byte[] getBinario() {
        return binario;
    }

    public void setBinario(byte[] binario) {
        this.binario = binario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public short getEsDescargable() {
        return esDescargable;
    }

    public void setEsDescargable(short esDescargable) {
        this.esDescargable = esDescargable;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
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

//    public List<ParMensajeBinario> getParMensajeBinarioList() {
//        return parMensajeBinarioList;
//    }
//
//    public void setParMensajeBinarioList(List<ParMensajeBinario> parMensajeBinarioList) {
//        this.parMensajeBinarioList = parMensajeBinarioList;
//    }

    public ParMensajeApp getIdMensajeApp() {
        return idMensajeApp;
    }

    public void setIdMensajeApp(ParMensajeApp idMensajeApp) {
        this.idMensajeApp = idMensajeApp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMensajeContenido != null ? idMensajeContenido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParMensajeContenido)) {
            return false;
        }
        ParMensajeContenido other = (ParMensajeContenido) object;
        if ((this.idMensajeContenido == null && other.idMensajeContenido != null) || (this.idMensajeContenido != null && !this.idMensajeContenido.equals(other.idMensajeContenido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParMensajeContenido[ idMensajeContenido=" + idMensajeContenido + " ]";
    }
    
}
