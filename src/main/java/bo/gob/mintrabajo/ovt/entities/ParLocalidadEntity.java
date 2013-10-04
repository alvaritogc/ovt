package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 03-10-13
 */
@javax.persistence.Table(name = "PAR_LOCALIDAD", schema = "ROE", catalog = "")
@Entity
public class ParLocalidadEntity {
    private String codLocalidad;

    @javax.persistence.Column(name = "COD_LOCALIDAD")
    @Id
    public String getCodLocalidad() {
        return codLocalidad;
    }

    public void setCodLocalidad(String codLocalidad) {
        this.codLocalidad = codLocalidad;
    }

    private String descripcion;

    @javax.persistence.Column(name = "DESCRIPCION")
    @Basic
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private String tipoLocalidad;

    @javax.persistence.Column(name = "TIPO_LOCALIDAD")
    @Basic
    public String getTipoLocalidad() {
        return tipoLocalidad;
    }

    public void setTipoLocalidad(String tipoLocalidad) {
        this.tipoLocalidad = tipoLocalidad;
    }

    private Timestamp fechaBitacora;

    @javax.persistence.Column(name = "FECHA_BITACORA")
    @Basic
    public Timestamp getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Timestamp fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    private String registroBitacora;

    @javax.persistence.Column(name = "REGISTRO_BITACORA")
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

        ParLocalidadEntity that = (ParLocalidadEntity) o;

        if (codLocalidad != null ? !codLocalidad.equals(that.codLocalidad) : that.codLocalidad != null) return false;
        if (descripcion != null ? !descripcion.equals(that.descripcion) : that.descripcion != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoLocalidad != null ? !tipoLocalidad.equals(that.tipoLocalidad) : that.tipoLocalidad != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codLocalidad != null ? codLocalidad.hashCode() : 0;
        result = 31 * result + (descripcion != null ? descripcion.hashCode() : 0);
        result = 31 * result + (tipoLocalidad != null ? tipoLocalidad.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
