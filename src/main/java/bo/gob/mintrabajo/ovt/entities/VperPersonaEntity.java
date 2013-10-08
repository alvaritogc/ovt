package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/8/13
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "VPER_PERSONA", schema = "OVT", catalog = "")
public class VperPersonaEntity {
    private String idPersona;

    @javax.persistence.Column(name = "ID_PERSONA")
    @Basic
    String getIdPersona() {
        return idPersona;
    }

    void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    private String tipoIdentificacion;

    @javax.persistence.Column(name = "TIPO_IDENTIFICACION")
    @Basic
    String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    private String nroIdentificacion;

    @javax.persistence.Column(name = "NRO_IDENTIFICACION")
    @Basic
    String getNroIdentificacion() {
        return nroIdentificacion;
    }

    void setNroIdentificacion(String nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    private String nombreRazonSocial;

    @javax.persistence.Column(name = "NOMBRE_RAZON_SOCIAL")
    @Basic
    String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    private String apellidoPaterno;

    @javax.persistence.Column(name = "APELLIDO_PATERNO")
    @Basic
    String getApellidoPaterno() {
        return apellidoPaterno;
    }

    void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    private String apellidoMaterno;

    @javax.persistence.Column(name = "APELLIDO_MATERNO")
    @Basic
    String getApellidoMaterno() {
        return apellidoMaterno;
    }

    void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    private BigInteger esNatural;

    @javax.persistence.Column(name = "ES_NATURAL")
    @Basic
    BigInteger getEsNatural() {
        return esNatural;
    }

    void setEsNatural(BigInteger esNatural) {
        this.esNatural = esNatural;
    }

    private String codLocalidad;

    @javax.persistence.Column(name = "COD_LOCALIDAD")
    @Basic
    String getCodLocalidad() {
        return codLocalidad;
    }

    void setCodLocalidad(String codLocalidad) {
        this.codLocalidad = codLocalidad;
    }

    private String localidad;

    @javax.persistence.Column(name = "LOCALIDAD")
    @Basic
    String getLocalidad() {
        return localidad;
    }

    void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    private Integer idUnidad;

    @javax.persistence.Column(name = "ID_UNIDAD")
    @Basic
    Integer getIdUnidad() {
        return idUnidad;
    }

    void setIdUnidad(Integer idUnidad) {
        this.idUnidad = idUnidad;
    }

    private String nombreComercial;

    @javax.persistence.Column(name = "NOMBRE_COMERCIAL")
    @Basic
    String getNombreComercial() {
        return nombreComercial;
    }

    void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    private Timestamp fechaNacimiento;

    @javax.persistence.Column(name = "FECHA_NACIMIENTO")
    @Basic
    Timestamp getFechaNacimiento() {
        return fechaNacimiento;
    }

    void setFechaNacimiento(Timestamp fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    private String nroCajaSalud;

    @javax.persistence.Column(name = "NRO_CAJA_SALUD")
    @Basic
    String getNroCajaSalud() {
        return nroCajaSalud;
    }

    void setNroCajaSalud(String nroCajaSalud) {
        this.nroCajaSalud = nroCajaSalud;
    }

    private String nroAfp;

    @javax.persistence.Column(name = "NRO_AFP")
    @Basic
    String getNroAfp() {
        return nroAfp;
    }

    void setNroAfp(String nroAfp) {
        this.nroAfp = nroAfp;
    }

    private String nroFundaempresa;

    @javax.persistence.Column(name = "NRO_FUNDAEMPRESA")
    @Basic
    String getNroFundaempresa() {
        return nroFundaempresa;
    }

    void setNroFundaempresa(String nroFundaempresa) {
        this.nroFundaempresa = nroFundaempresa;
    }

    private String nroOtro;

    @javax.persistence.Column(name = "NRO_OTRO")
    @Basic
    String getNroOtro() {
        return nroOtro;
    }

    void setNroOtro(String nroOtro) {
        this.nroOtro = nroOtro;
    }

    private String observaciones;

    @javax.persistence.Column(name = "OBSERVACIONES")
    @Basic
    String getObservaciones() {
        return observaciones;
    }

    void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    private String tipoSociedad;

    @javax.persistence.Column(name = "TIPO_SOCIEDAD")
    @Basic
    String getTipoSociedad() {
        return tipoSociedad;
    }

    void setTipoSociedad(String tipoSociedad) {
        this.tipoSociedad = tipoSociedad;
    }

    private String tsociedad;

    @javax.persistence.Column(name = "TSOCIEDAD")
    @Basic
    String getTsociedad() {
        return tsociedad;
    }

    void setTsociedad(String tsociedad) {
        this.tsociedad = tsociedad;
    }

    private String tipoEmpresa;

    @javax.persistence.Column(name = "TIPO_EMPRESA")
    @Basic
    String getTipoEmpresa() {
        return tipoEmpresa;
    }

    void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    private String tempresa;

    @javax.persistence.Column(name = "TEMPRESA")
    @Basic
    String getTempresa() {
        return tempresa;
    }

    void setTempresa(String tempresa) {
        this.tempresa = tempresa;
    }

    private String actividadDeclarada;

    @javax.persistence.Column(name = "ACTIVIDAD_DECLARADA")
    @Basic
    String getActividadDeclarada() {
        return actividadDeclarada;
    }

    void setActividadDeclarada(String actividadDeclarada) {
        this.actividadDeclarada = actividadDeclarada;
    }

    private String estadoUnidad;

    @javax.persistence.Column(name = "ESTADO_UNIDAD")
    @Basic
    String getEstadoUnidad() {
        return estadoUnidad;
    }

    void setEstadoUnidad(String estadoUnidad) {
        this.estadoUnidad = estadoUnidad;
    }

    private String estado;

    @javax.persistence.Column(name = "ESTADO")
    @Basic
    String getEstado() {
        return estado;
    }

    void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VperPersonaEntity that = (VperPersonaEntity) o;

        if (actividadDeclarada != null ? !actividadDeclarada.equals(that.actividadDeclarada) : that.actividadDeclarada != null)
            return false;
        if (apellidoMaterno != null ? !apellidoMaterno.equals(that.apellidoMaterno) : that.apellidoMaterno != null)
            return false;
        if (apellidoPaterno != null ? !apellidoPaterno.equals(that.apellidoPaterno) : that.apellidoPaterno != null)
            return false;
        if (codLocalidad != null ? !codLocalidad.equals(that.codLocalidad) : that.codLocalidad != null) return false;
        if (esNatural != null ? !esNatural.equals(that.esNatural) : that.esNatural != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;
        if (estadoUnidad != null ? !estadoUnidad.equals(that.estadoUnidad) : that.estadoUnidad != null) return false;
        if (fechaNacimiento != null ? !fechaNacimiento.equals(that.fechaNacimiento) : that.fechaNacimiento != null)
            return false;
        if (idPersona != null ? !idPersona.equals(that.idPersona) : that.idPersona != null) return false;
        if (idUnidad != null ? !idUnidad.equals(that.idUnidad) : that.idUnidad != null) return false;
        if (localidad != null ? !localidad.equals(that.localidad) : that.localidad != null) return false;
        if (nombreComercial != null ? !nombreComercial.equals(that.nombreComercial) : that.nombreComercial != null)
            return false;
        if (nombreRazonSocial != null ? !nombreRazonSocial.equals(that.nombreRazonSocial) : that.nombreRazonSocial != null)
            return false;
        if (nroAfp != null ? !nroAfp.equals(that.nroAfp) : that.nroAfp != null) return false;
        if (nroCajaSalud != null ? !nroCajaSalud.equals(that.nroCajaSalud) : that.nroCajaSalud != null) return false;
        if (nroFundaempresa != null ? !nroFundaempresa.equals(that.nroFundaempresa) : that.nroFundaempresa != null)
            return false;
        if (nroIdentificacion != null ? !nroIdentificacion.equals(that.nroIdentificacion) : that.nroIdentificacion != null)
            return false;
        if (nroOtro != null ? !nroOtro.equals(that.nroOtro) : that.nroOtro != null) return false;
        if (observaciones != null ? !observaciones.equals(that.observaciones) : that.observaciones != null)
            return false;
        if (tempresa != null ? !tempresa.equals(that.tempresa) : that.tempresa != null) return false;
        if (tipoEmpresa != null ? !tipoEmpresa.equals(that.tipoEmpresa) : that.tipoEmpresa != null) return false;
        if (tipoIdentificacion != null ? !tipoIdentificacion.equals(that.tipoIdentificacion) : that.tipoIdentificacion != null)
            return false;
        if (tipoSociedad != null ? !tipoSociedad.equals(that.tipoSociedad) : that.tipoSociedad != null) return false;
        if (tsociedad != null ? !tsociedad.equals(that.tsociedad) : that.tsociedad != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPersona != null ? idPersona.hashCode() : 0;
        result = 31 * result + (tipoIdentificacion != null ? tipoIdentificacion.hashCode() : 0);
        result = 31 * result + (nroIdentificacion != null ? nroIdentificacion.hashCode() : 0);
        result = 31 * result + (nombreRazonSocial != null ? nombreRazonSocial.hashCode() : 0);
        result = 31 * result + (apellidoPaterno != null ? apellidoPaterno.hashCode() : 0);
        result = 31 * result + (apellidoMaterno != null ? apellidoMaterno.hashCode() : 0);
        result = 31 * result + (esNatural != null ? esNatural.hashCode() : 0);
        result = 31 * result + (codLocalidad != null ? codLocalidad.hashCode() : 0);
        result = 31 * result + (localidad != null ? localidad.hashCode() : 0);
        result = 31 * result + (idUnidad != null ? idUnidad.hashCode() : 0);
        result = 31 * result + (nombreComercial != null ? nombreComercial.hashCode() : 0);
        result = 31 * result + (fechaNacimiento != null ? fechaNacimiento.hashCode() : 0);
        result = 31 * result + (nroCajaSalud != null ? nroCajaSalud.hashCode() : 0);
        result = 31 * result + (nroAfp != null ? nroAfp.hashCode() : 0);
        result = 31 * result + (nroFundaempresa != null ? nroFundaempresa.hashCode() : 0);
        result = 31 * result + (nroOtro != null ? nroOtro.hashCode() : 0);
        result = 31 * result + (observaciones != null ? observaciones.hashCode() : 0);
        result = 31 * result + (tipoSociedad != null ? tipoSociedad.hashCode() : 0);
        result = 31 * result + (tsociedad != null ? tsociedad.hashCode() : 0);
        result = 31 * result + (tipoEmpresa != null ? tipoEmpresa.hashCode() : 0);
        result = 31 * result + (tempresa != null ? tempresa.hashCode() : 0);
        result = 31 * result + (actividadDeclarada != null ? actividadDeclarada.hashCode() : 0);
        result = 31 * result + (estadoUnidad != null ? estadoUnidad.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        return result;
    }
}
