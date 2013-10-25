/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo.gob.mintrabajo.ovt.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author rvelasquez
 */
@Embeddable
public class ParDominioPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "ID_DOMINIO")
    private String idDominio;
    @Basic(optional = false)
    @Column(name = "VALOR")
    private String valor;

    public ParDominioPK() {
    }

    public ParDominioPK(String idDominio, String valor) {
        this.idDominio = idDominio;
        this.valor = valor;
    }

    public String getIdDominio() {
        return idDominio;
    }

    public void setIdDominio(String idDominio) {
        this.idDominio = idDominio;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDominio != null ? idDominio.hashCode() : 0);
        hash += (valor != null ? valor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParDominioPK)) {
            return false;
        }
        ParDominioPK other = (ParDominioPK) object;
        if ((this.idDominio == null && other.idDominio != null) || (this.idDominio != null && !this.idDominio.equals(other.idDominio))) {
            return false;
        }
        if ((this.valor == null && other.valor != null) || (this.valor != null && !this.valor.equals(other.valor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.ParDominioPK[ idDominio=" + idDominio + ", valor=" + valor + " ]";
    }
    
}
