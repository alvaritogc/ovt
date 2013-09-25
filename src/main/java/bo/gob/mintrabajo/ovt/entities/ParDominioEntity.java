package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * User: gveramendi
 * Date: 9/25/13
 * Time: 5:33 PM
 */

@javax.persistence.IdClass(bo.gob.mintrabajo.ovt.entities.ParDominioEntityPK.class)
@javax.persistence.Table(name = "PAR_DOMINIO", schema = "ROE", catalog = "")
@Entity
public class ParDominioEntity implements Serializable {
    private String idDominio;

    @javax.persistence.Column(name = "ID_DOMINIO", nullable = false, insertable = true, updatable = true, length = 15, precision = 0)
    @Id
    public String getIdDominio() {
        return idDominio;
    }

    public void setIdDominio(String idDominio) {
        this.idDominio = idDominio;
    }

    private String valor;

    @javax.persistence.Column(name = "VALOR", nullable = false, insertable = true, updatable = true, length = 80, precision = 0)
    @Id
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    private String descripcion;

    @javax.persistence.Column(name = "DESCRIPCION", nullable = false, insertable = true, updatable = true, length = 120, precision = 0)
    @Basic
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private String observacion;

    @javax.persistence.Column(name = "OBSERVACION", nullable = false, insertable = true, updatable = true, length = 120, precision = 0)
    @Basic
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    private Timestamp fechaBitacora;

    @javax.persistence.Column(name = "FECHA_BITACORA", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Timestamp fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    private String registroBitacora;

    @javax.persistence.Column(name = "REGISTRO_BITACORA", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    public String getRegistroBitacora() {
        return registroBitacora;
    }

    public void setRegistroBitacora(String registroBitacora) {
        this.registroBitacora = registroBitacora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParDominioEntity that = (ParDominioEntity) o;

        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (idDominio != null ? !idDominio.equals(that.idDominio) : that.idDominio != null) return false;
        if (observacion != null ? !observacion.equals(that.observacion) : that.observacion != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (valor != null ? !valor.equals(that.valor) : that.valor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDominio != null ? idDominio.hashCode() : 0;
        result = 31 * result + (valor != null ? valor.hashCode() : 0);
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (observacion != null ? observacion.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }

    private ParDominioEntity parDominio;

    @ManyToOne
    @javax.persistence.JoinColumns({@javax.persistence.JoinColumn(name = "ID_DOMINIO_PADRE", referencedColumnName = "ID_DOMINIO"), @javax.persistence.JoinColumn(name = "VALOR_PADRE", referencedColumnName = "VALOR")})
    public ParDominioEntity getParDominio() {
        return parDominio;
    }

    public void setParDominio(ParDominioEntity parDominio) {
        this.parDominio = parDominio;
    }

    private Collection<ParDominioEntity> parDominios;

    @OneToMany(mappedBy = "parDominio")
    public Collection<ParDominioEntity> getParDominios() {
        return parDominios;
    }

    public void setParDominios(Collection<ParDominioEntity> parDominios) {
        this.parDominios = parDominios;
    }

    private UsrModuloEntity usrModuloByIdModulo;

    @ManyToOne
    @JoinColumn(name = "ID_MODULO", referencedColumnName = "ID_MODULO", nullable = false)
    public UsrModuloEntity getUsrModuloByIdModulo() {
        return usrModuloByIdModulo;
    }

    public void setUsrModuloByIdModulo(UsrModuloEntity usrModuloByIdModulo) {
        this.usrModuloByIdModulo = usrModuloByIdModulo;
    }
}
