package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 05-10-13
 */
@javax.persistence.Table(name = "DOC_DEFINICION", schema = "ROE", catalog = "")
@Entity
public class DocDefinicionEntity {
    private String codDocumento;

    @javax.persistence.Column(name = "COD_DOCUMENTO")
    @Id
    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    private String nombre;

    @javax.persistence.Column(name = "NOMBRE")
    @Basic
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private String tipoGrupoDocumento;

    @javax.persistence.Column(name = "TIPO_GRUPO_DOCUMENTO")
    @Basic
    public String getTipoGrupoDocumento() {
        return tipoGrupoDocumento;
    }

    public void setTipoGrupoDocumento(String tipoGrupoDocumento) {
        this.tipoGrupoDocumento = tipoGrupoDocumento;
    }

    private Timestamp fechaBitacora;

    @javax.persistence.Column(name = "FECHA_BITACORA")
    @Basic
    public Timestamp getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Timestamp fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }    private String registroBitacora;

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

        DocDefinicionEntity that = (DocDefinicionEntity) o;

        if (codDocumento != null ? !codDocumento.equals(that.codDocumento) : that.codDocumento != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoGrupoDocumento != null ? !tipoGrupoDocumento.equals(that.tipoGrupoDocumento) : that.tipoGrupoDocumento != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codDocumento != null ? codDocumento.hashCode() : 0;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (tipoGrupoDocumento != null ? tipoGrupoDocumento.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
