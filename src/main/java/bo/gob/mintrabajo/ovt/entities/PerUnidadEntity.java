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

@javax.persistence.IdClass(bo.gob.mintrabajo.ovt.entities.PerUnidadEntityPK.class)
@javax.persistence.Table(name = "PER_UNIDAD", schema = "ROE", catalog = "")
@Entity
public class PerUnidadEntity implements Serializable {
    private String idPersona;

    @javax.persistence.Column(name = "ID_PERSONA", nullable = false, insertable = true, updatable = true, length = 20, precision = 0)
    @Id
    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    private int idUnidad;

    @javax.persistence.Column(name = "ID_UNIDAD", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    private String nombreComercial;

    @javax.persistence.Column(name = "NOMBRE_COMERCIAL", nullable = false, insertable = true, updatable = true, length = 80, precision = 0)
    @Basic
    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    private Timestamp fechaNacimiento;

    @javax.persistence.Column(name = "FECHA_NACIMIENTO", nullable = false, insertable = true, updatable = true, length = 7, precision = 0)
    @Basic
    public Timestamp getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Timestamp fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    private String nroCajaSalud;

    @javax.persistence.Column(name = "NRO_CAJA_SALUD", nullable = true, insertable = true, updatable = true, length = 80, precision = 0)
    @Basic
    public String getNroCajaSalud() {
        return nroCajaSalud;
    }

    public void setNroCajaSalud(String nroCajaSalud) {
        this.nroCajaSalud = nroCajaSalud;
    }

    private String nroAfp;

    @javax.persistence.Column(name = "NRO_AFP", nullable = true, insertable = true, updatable = true, length = 80, precision = 0)
    @Basic
    public String getNroAfp() {
        return nroAfp;
    }

    public void setNroAfp(String nroAfp) {
        this.nroAfp = nroAfp;
    }

    private String nroFundaempresa;

    @javax.persistence.Column(name = "NRO_FUNDAEMPRESA", nullable = true, insertable = true, updatable = true, length = 80, precision = 0)
    @Basic
    public String getNroFundaempresa() {
        return nroFundaempresa;
    }

    public void setNroFundaempresa(String nroFundaempresa) {
        this.nroFundaempresa = nroFundaempresa;
    }

    private String nroOtro;

    @javax.persistence.Column(name = "NRO_OTRO", nullable = true, insertable = true, updatable = true, length = 80, precision = 0)
    @Basic
    public String getNroOtro() {
        return nroOtro;
    }

    public void setNroOtro(String nroOtro) {
        this.nroOtro = nroOtro;
    }

    private String observaciones;

    @javax.persistence.Column(name = "OBSERVACIONES", nullable = false, insertable = true, updatable = true, length = 500, precision = 0)
    @Basic
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    private String tipoSociedad;

    @javax.persistence.Column(name = "TIPO_SOCIEDAD", nullable = false, insertable = true, updatable = true, length = 15, precision = 0)
    @Basic
    public String getTipoSociedad() {
        return tipoSociedad;
    }

    public void setTipoSociedad(String tipoSociedad) {
        this.tipoSociedad = tipoSociedad;
    }

    private String tipoEmpresa;

    @javax.persistence.Column(name = "TIPO_EMPRESA", nullable = false, insertable = true, updatable = true, length = 15, precision = 0)
    @Basic
    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    private String actividadDeclarada;

    @javax.persistence.Column(name = "ACTIVIDAD_DECLARADA", nullable = false, insertable = true, updatable = true, length = 500, precision = 0)
    @Basic
    public String getActividadDeclarada() {
        return actividadDeclarada;
    }

    public void setActividadDeclarada(String actividadDeclarada) {
        this.actividadDeclarada = actividadDeclarada;
    }

    private String estadoUnidad;

    @javax.persistence.Column(name = "ESTADO_UNIDAD", nullable = false, insertable = true, updatable = true, length = 15, precision = 0)
    @Basic
    public String getEstadoUnidad() {
        return estadoUnidad;
    }

    public void setEstadoUnidad(String estadoUnidad) {
        this.estadoUnidad = estadoUnidad;
    }

    private String nroReferencial;

    @javax.persistence.Column(name = "NRO_REFERENCIAL", nullable = false, insertable = true, updatable = true, length = 120, precision = 0)
    @Basic
    public String getNroReferencial() {
        return nroReferencial;
    }

    public void setNroReferencial(String nroReferencial) {
        this.nroReferencial = nroReferencial;
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

        PerUnidadEntity that = (PerUnidadEntity) o;

        if (idUnidad != that.idUnidad) return false;
        if (actividadDeclarada != null ? !actividadDeclarada.equals(that.actividadDeclarada) : that.actividadDeclarada != null)
            return false;
        if (estadoUnidad != null ? !estadoUnidad.equals(that.estadoUnidad) : that.estadoUnidad != null) return false;
        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (fechaNacimiento != null ? !fechaNacimiento.equals(that.fechaNacimiento) : that.fechaNacimiento != null)
            return false;
        if (idPersona != null ? !idPersona.equals(that.idPersona) : that.idPersona != null) return false;
        if (nombreComercial != null ? !nombreComercial.equals(that.nombreComercial) : that.nombreComercial != null)
            return false;
        if (nroAfp != null ? !nroAfp.equals(that.nroAfp) : that.nroAfp != null) return false;
        if (nroCajaSalud != null ? !nroCajaSalud.equals(that.nroCajaSalud) : that.nroCajaSalud != null) return false;
        if (nroFundaempresa != null ? !nroFundaempresa.equals(that.nroFundaempresa) : that.nroFundaempresa != null)
            return false;
        if (nroOtro != null ? !nroOtro.equals(that.nroOtro) : that.nroOtro != null) return false;
        if (nroReferencial != null ? !nroReferencial.equals(that.nroReferencial) : that.nroReferencial != null)
            return false;
        if (observaciones != null ? !observaciones.equals(that.observaciones) : that.observaciones != null)
            return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoEmpresa != null ? !tipoEmpresa.equals(that.tipoEmpresa) : that.tipoEmpresa != null) return false;
        if (tipoSociedad != null ? !tipoSociedad.equals(that.tipoSociedad) : that.tipoSociedad != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPersona != null ? idPersona.hashCode() : 0;
        result = 31 * result + idUnidad;
        result = 31 * result + (nombreComercial != null ? nombreComercial.hashCode() : 0);
        result = 31 * result + (fechaNacimiento != null ? fechaNacimiento.hashCode() : 0);
        result = 31 * result + (nroCajaSalud != null ? nroCajaSalud.hashCode() : 0);
        result = 31 * result + (nroAfp != null ? nroAfp.hashCode() : 0);
        result = 31 * result + (nroFundaempresa != null ? nroFundaempresa.hashCode() : 0);
        result = 31 * result + (nroOtro != null ? nroOtro.hashCode() : 0);
        result = 31 * result + (observaciones != null ? observaciones.hashCode() : 0);
        result = 31 * result + (tipoSociedad != null ? tipoSociedad.hashCode() : 0);
        result = 31 * result + (tipoEmpresa != null ? tipoEmpresa.hashCode() : 0);
        result = 31 * result + (actividadDeclarada != null ? actividadDeclarada.hashCode() : 0);
        result = 31 * result + (estadoUnidad != null ? estadoUnidad.hashCode() : 0);
        result = 31 * result + (nroReferencial != null ? nroReferencial.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }

    private PerPersonaEntity perPersonaByIdPersona;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA", nullable = false)
    public PerPersonaEntity getPerPersonaByIdPersona() {
        return perPersonaByIdPersona;
    }

    public void setPerPersonaByIdPersona(PerPersonaEntity perPersonaByIdPersona) {
        this.perPersonaByIdPersona = perPersonaByIdPersona;
    }

    private Collection<PerUsuarioEntity> perUsuarios;

    @OneToMany(mappedBy = "perUnidad")
    public Collection<PerUsuarioEntity> getPerUsuarios() {
        return perUsuarios;
    }

    public void setPerUsuarios(Collection<PerUsuarioEntity> perUsuarios) {
        this.perUsuarios = perUsuarios;
    }
}
