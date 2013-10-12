package bo.gob.mintrabajo.ovt.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the VPER_PERSONA database table.
 *
 */

public class VperPersonaEntity {

    private static final long serialVersionUID = 1L;

    @Column(name="ACTIVIDAD_DECLARADA")
    private String actividadDeclarada;

    @Column(name="APELLIDO_MATERNO")
    private String apellidoMaterno;

    @Column(name="APELLIDO_PATERNO")
    private String apellidoPaterno;

    @Column(name="COD_LOCALIDAD")
    private String codLocalidad;

    @Column(name="DIR_COD_DEPARTAMENTO")
    private BigDecimal dirCodDepartamento;

    @Column(name="DIR_DEPARTAMENTO")
    private String dirDepartamento;

    @Column(name="DIR_DIRECCION")
    private String dirDireccion;

    @Column(name="DIR_LOCALIDAD")
    private String dirLocalidad;

    @Column(name="DIR_NRO_DIRECCION")
    private String dirNroDireccion;

    @Column(name="DIR_ZONA")
    private String dirZona;

    @Column(name="EMAIL")
    private String email;

    @Column(name="ES_NATURAL")
    private BigDecimal esNatural;

    @Column(name="ESTADO")
    private String estado;

    @Column(name="ESTADO_UNIDAD")
    private String estadoUnidad;

    @Column(name="FAX")
    private String fax;

    @Temporal(TemporalType.DATE)
    @Column(name="FECHA_NACIMIENTO")
    private Date fechaNacimiento;

    @Column(name="ID_PERSONA")
    private String idPersona;

    @Column(name="ID_UNIDAD")
    private BigDecimal idUnidad;

    @Column(name="LOCALIDAD")
    private String localidad;

    @Column(name="NOMBRE_COMERCIAL")
    private String nombreComercial;

    @Column(name="NOMBRE_RAZON_SOCIAL")
    private String nombreRazonSocial;

    @Column(name="NRO_AFP")
    private String nroAfp;

    @Column(name="NRO_CAJA_SALUD")
    private String nroCajaSalud;

    @Column(name="NRO_FUNDAEMPRESA")
    private String nroFundaempresa;

    @Column(name="NRO_IDENTIFICACION")
    private BigDecimal nroIdentificacion;

    @Column(name="NRO_OTRO")
    private String nroOtro;

    @Column(name="OBSERVACIONES")
    private String observaciones;

    @Column(name="RL_NOMBRE")
    private String rlNombre;

    @Column(name="RL_NRO_IDENTIDAD")
    private String rlNroIdentidad;

    @Column(name="RL_NRO_PERMISO")
    private String rlNroPermiso;

    @Column(name="TELEFONO")
    private String telefono;

    @Column(name="TEMPRESA")
    private String tempresa;

    @Column(name="TIPO_EMPRESA")
    private String tipoEmpresa;

    @Column(name="TIPO_IDENTIFICACION")
    private String tipoIdentificacion;

    @Column(name="TIPO_SOCIENDAD")
    private String tipoSociendad;

    @Column(name="TSOCIEDAD")
    private String tsociedad;

    public VperPersonaEntity() {
    }

    public String getActividadDeclarada() {
        return this.actividadDeclarada;
    }

    public void setActividadDeclarada(String actividadDeclarada) {
        this.actividadDeclarada = actividadDeclarada;
    }

    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getCodLocalidad() {
        return this.codLocalidad;
    }

    public void setCodLocalidad(String codLocalidad) {
        this.codLocalidad = codLocalidad;
    }

    public BigDecimal getDirCodDepartamento() {
        return this.dirCodDepartamento;
    }

    public void setDirCodDepartamento(BigDecimal dirCodDepartamento) {
        this.dirCodDepartamento = dirCodDepartamento;
    }

    public String getDirDepartamento() {
        return this.dirDepartamento;
    }

    public void setDirDepartamento(String dirDepartamento) {
        this.dirDepartamento = dirDepartamento;
    }

    public String getDirDireccion() {
        return this.dirDireccion;
    }

    public void setDirDireccion(String dirDireccion) {
        this.dirDireccion = dirDireccion;
    }

    public String getDirLocalidad() {
        return this.dirLocalidad;
    }

    public void setDirLocalidad(String dirLocalidad) {
        this.dirLocalidad = dirLocalidad;
    }

    public String getDirNroDireccion() {
        return this.dirNroDireccion;
    }

    public void setDirNroDireccion(String dirNroDireccion) {
        this.dirNroDireccion = dirNroDireccion;
    }

    public String getDirZona() {
        return this.dirZona;
    }

    public void setDirZona(String dirZona) {
        this.dirZona = dirZona;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getEsNatural() {
        return this.esNatural;
    }

    public void setEsNatural(BigDecimal esNatural) {
        this.esNatural = esNatural;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoUnidad() {
        return this.estadoUnidad;
    }

    public void setEstadoUnidad(String estadoUnidad) {
        this.estadoUnidad = estadoUnidad;
    }

    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getIdPersona() {
        return this.idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public BigDecimal getIdUnidad() {
        return this.idUnidad;
    }

    public void setIdUnidad(BigDecimal idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getLocalidad() {
        return this.localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getNombreComercial() {
        return this.nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNombreRazonSocial() {
        return this.nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public String getNroAfp() {
        return this.nroAfp;
    }

    public void setNroAfp(String nroAfp) {
        this.nroAfp = nroAfp;
    }

    public String getNroCajaSalud() {
        return this.nroCajaSalud;
    }

    public void setNroCajaSalud(String nroCajaSalud) {
        this.nroCajaSalud = nroCajaSalud;
    }

    public String getNroFundaempresa() {
        return this.nroFundaempresa;
    }

    public void setNroFundaempresa(String nroFundaempresa) {
        this.nroFundaempresa = nroFundaempresa;
    }

    public BigDecimal getNroIdentificacion() {
        return this.nroIdentificacion;
    }

    public void setNroIdentificacion(BigDecimal nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    public String getNroOtro() {
        return this.nroOtro;
    }

    public void setNroOtro(String nroOtro) {
        this.nroOtro = nroOtro;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getRlNombre() {
        return this.rlNombre;
    }

    public void setRlNombre(String rlNombre) {
        this.rlNombre = rlNombre;
    }

    public String getRlNroIdentidad() {
        return this.rlNroIdentidad;
    }

    public void setRlNroIdentidad(String rlNroIdentidad) {
        this.rlNroIdentidad = rlNroIdentidad;
    }

    public String getRlNroPermiso() {
        return this.rlNroPermiso;
    }

    public void setRlNroPermiso(String rlNroPermiso) {
        this.rlNroPermiso = rlNroPermiso;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTempresa() {
        return this.tempresa;
    }

    public void setTempresa(String tempresa) {
        this.tempresa = tempresa;
    }

    public String getTipoEmpresa() {
        return this.tipoEmpresa;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public String getTipoIdentificacion() {
        return this.tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getTipoSociendad() {
        return this.tipoSociendad;
    }

    public void setTipoSociendad(String tipoSociendad) {
        this.tipoSociendad = tipoSociendad;
    }

    public String getTsociedad() {
        return this.tsociedad;
    }

    public void setTsociedad(String tsociedad) {
        this.tsociedad = tsociedad;
    }
}