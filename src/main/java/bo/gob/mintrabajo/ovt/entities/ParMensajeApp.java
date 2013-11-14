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
@Table(name = "PAR_MENSAJE_APP")
@NamedQueries({
    @NamedQuery(name = "ParMensajeApp.findAll", query = "SELECT p FROM ParMensajeApp p")})
public class ParMensajeApp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_MENSAJE_APP")
    private Long idMensajeApp;
    @Column(name = "MENSAJE")
    private String mensaje;
    @Column(name = "REFERENCIA")
    private String referencia;
    @Column(name = "FECHA_DESDE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDesde;
    @Column(name = "FECHA_HASTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHasta;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumn(name = "ID_RECURSO", referencedColumnName = "ID_RECURSO")
    @ManyToOne(fetch = FetchType.LAZY)
    private UsrRecurso idRecurso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMensajeApp", fetch = FetchType.LAZY)
    private List<ParMensajeContenido> parMensajeContenidoList;

    public ParMensajeApp() {
    }

    public ParMensajeApp(Long idMensajeApp) {
        this.idMensajeApp = idMensajeApp;
    }

    public ParMensajeApp(Long idMensajeApp, Date fechaBitacora, String registroBitacora) {
        this.idMensajeApp = idMensajeApp;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public Long getIdMensajeApp() {
        return idMensajeApp;
    }

    public void setIdMensajeApp(Long idMensajeApp) {
        this.idMensajeApp = idMensajeApp;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
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

    public UsrRecurso getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(UsrRecurso idRecurso) {
        this.idRecurso = idRecurso;
    }

    public List<ParMensajeContenido> getParMensajeContenidoList() {
        return parMensajeContenidoList;
    }

    public void setParMensajeContenidoList(List<ParMensajeContenido> parMensajeContenidoList) {
        this.parMensajeContenidoList = parMensajeContenidoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMensajeApp != null ? idMensajeApp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParMensajeApp)) {
            return false;
        }
        ParMensajeApp other = (ParMensajeApp) object;
        if ((this.idMensajeApp == null && other.idMensajeApp != null) || (this.idMensajeApp != null && !this.idMensajeApp.equals(other.idMensajeApp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParMensajeApp[ idMensajeApp=" + idMensajeApp + " ]";
    }
    
}
