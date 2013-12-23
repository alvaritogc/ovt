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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rvelasquez
 */
@Entity
@Table(name = "DOC_ALERTA_DEFINICION")
@NamedQueries({
    @NamedQuery(name = "DocAlertaDefinicion.findAll", query = "SELECT d FROM DocAlertaDefinicion d")})
public class DocAlertaDefinicion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_ALERTA")
    private String codAlerta;
    @Column(name = "DESCRICPION")
    private String descricpion;
    @Column(name = "TIPO_ALERTA")
    private String tipoAlerta;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @JoinColumns({
        @JoinColumn(name = "COD_DOCUMENTO", referencedColumnName = "COD_DOCUMENTO"),
        @JoinColumn(name = "VERSION", referencedColumnName = "VERSION")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DocDefinicion docDefinicion;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codAlerta", fetch = FetchType.LAZY)
//    private List<DocAlerta> docAlertaList;

    public DocAlertaDefinicion() {
    }

    public DocAlertaDefinicion(String codAlerta) {
        this.codAlerta = codAlerta;
    }

    public DocAlertaDefinicion(String codAlerta, Date fechaBitacora, String registroBitacora) {
        this.codAlerta = codAlerta;
        this.fechaBitacora = fechaBitacora;
        this.registroBitacora = registroBitacora;
    }

    public String getCodAlerta() {
        return codAlerta;
    }

    public void setCodAlerta(String codAlerta) {
        this.codAlerta = codAlerta;
    }

    public String getDescricpion() {
        return descricpion;
    }

    public void setDescricpion(String descricpion) {
        this.descricpion = descricpion;
    }

    public String getTipoAlerta() {
        return tipoAlerta;
    }

    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
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

    public DocDefinicion getDocDefinicion() {
        return docDefinicion;
    }

    public void setDocDefinicion(DocDefinicion docDefinicion) {
        this.docDefinicion = docDefinicion;
    }

//    public List<DocAlerta> getDocAlertaList() {
//        return docAlertaList;
//    }
//
//    public void setDocAlertaList(List<DocAlerta> docAlertaList) {
//        this.docAlertaList = docAlertaList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codAlerta != null ? codAlerta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocAlertaDefinicion)) {
            return false;
        }
        DocAlertaDefinicion other = (DocAlertaDefinicion) object;
        if ((this.codAlerta == null && other.codAlerta != null) || (this.codAlerta != null && !this.codAlerta.equals(other.codAlerta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DocAlertaDefinicion{" +
                "codAlerta='" + codAlerta + '\'' +
                ", descricpion='" + descricpion + '\'' +
                ", tipoAlerta='" + tipoAlerta + '\'' +
                ", fechaBitacora=" + fechaBitacora +
                ", registroBitacora='" + registroBitacora + '\'' +
                ", docDefinicion=" + docDefinicion.getDocDefinicionPK() +
                '}';
    }
}
