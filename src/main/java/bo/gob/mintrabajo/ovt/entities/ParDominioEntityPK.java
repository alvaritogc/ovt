package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * User: Renato Velasquez.
 * Date: 05-10-13
 */
public class ParDominioEntityPK implements Serializable {
    private String idDominio;
    private String valor;

@Id@Column(name = "ID_DOMINIO")
public String getIdDominio() {
    return idDominio;
}

    public void setIdDominio(String idDominio) {
        this.idDominio = idDominio;
    }

    @Id@Column(name = "VALOR")
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParDominioEntityPK that = (ParDominioEntityPK) o;

        if (idDominio != null ? !idDominio.equals(that.idDominio) : that.idDominio != null) return false;
        if (valor != null ? !valor.equals(that.valor) : that.valor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDominio != null ? idDominio.hashCode() : 0;
        result = 31 * result + (valor != null ? valor.hashCode() : 0);
        return result;
}}
