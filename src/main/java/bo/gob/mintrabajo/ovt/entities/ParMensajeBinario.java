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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gmercado
 */
@Entity
@Table(name = "PAR_MENSAJE_BINARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ParMensajeBinario.findAll", query = "SELECT p FROM ParMensajeBinario p"),
    @NamedQuery(name = "ParMensajeBinario.findByIdMensajeBinario", query = "SELECT p FROM ParMensajeBinario p WHERE p.idMensajeBinario = :idMensajeBinario"),
    @NamedQuery(name = "ParMensajeBinario.findByFechaBitacora", query = "SELECT p FROM ParMensajeBinario p WHERE p.fechaBitacora = :fechaBitacora"),
    @NamedQuery(name = "ParMensajeBinario.findByRegistroBitacora", query = "SELECT p FROM ParMensajeBinario p WHERE p.registroBitacora = :registroBitacora")})
public class ParMensajeBinario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MENSAJE_BINARIO")
    private Long idMensajeBinario;
    @Lob
    @Column(name = "BINARIO")
    private byte[] binario;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "ID_MENSAJE_CONTENIDO", referencedColumnName = "ID_MENSAJE_CONTENIDO")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ParMensajeContenido idMensajeContenido;

    public ParMensajeBinario() {
    }

    public ParMensajeBinario(Long idMensajeBinario) {
        this.idMensajeBinario = idMensajeBinario;
    }

    public ParMensajeBinario(Long idMensajeBinario, Date fechaBitacora, String registroBitacora) {
        this.idMensajeBinario = idMensajeBinario;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdMensajeBinario() {
        return idMensajeBinario;
    }

    public void setIdMensajeBinario(Long idMensajeBinario) {
        this.idMensajeBinario = idMensajeBinario;
    }

    public byte[] getBinario() {
        return binario;
    }

    public void setBinario(byte[] binario) {
        this.binario = binario;
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

    public ParMensajeContenido getIdMensajeContenido() {
        return idMensajeContenido;
    }

    public void setIdMensajeContenido(ParMensajeContenido idMensajeContenido) {
        this.idMensajeContenido = idMensajeContenido;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMensajeBinario != null ? idMensajeBinario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ParMensajeBinario)) {
            return false;
        }
        ParMensajeBinario other = (ParMensajeBinario) object;
        if ((this.idMensajeBinario == null && other.idMensajeBinario != null) || (this.idMensajeBinario != null && !this.idMensajeBinario.equals(other.idMensajeBinario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParMensajeBinario[ idMensajeBinario=" + idMensajeBinario + " ]";
    }
    
}
