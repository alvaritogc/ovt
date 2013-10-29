///*
// * Copyright 2013 rvelasquez.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package bo.gob.mintrabajo.ovt.entities;
//
//import java.io.Serializable;
//import java.math.BigInteger;
//import java.util.Date;
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
///**
// *
// * @author rvelasquez
// */
////@Entity
//@Table(name = "VPER_PERSONA")
//@NamedQueries({
//    @NamedQuery(name = "VperPersona.findAll", query = "SELECT v FROM VperPersona v")})
//public class VperPersona implements Serializable {
//    private static final long serialVersionUID = 1L;
//    @Basic(optional = false)
//    @Column(name = "ID_PERSONA")
//    private String idPersona;
//    @Column(name = "TIPO_IDENTIFICACION")
//    private String tipoIdentificacion;
//    @Column(name = "NRO_IDENTIFICACION")
//    private BigInteger nroIdentificacion;
//    @Column(name = "NOMBRE_RAZON_SOCIAL")
//    private String nombreRazonSocial;
//    @Column(name = "APELLIDO_PATERNO")
//    private String apellidoPaterno;
//    @Column(name = "APELLIDO_MATERNO")
//    private String apellidoMaterno;
//    @Column(name = "ES_NATURAL")
//    private BigInteger esNatural;
//    @Column(name = "COD_LOCALIDAD")
//    private String codLocalidad;
//    @Column(name = "LOCALIDAD")
//    private String localidad;
//    @Basic(optional = false)
//    @Column(name = "ID_UNIDAD")
//    private long idUnidad;
//    @Column(name = "NOMBRE_COMERCIAL")
//    private String nombreComercial;
//    @Column(name = "FECHA_NACIMIENTO")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date fechaNacimiento;
//    @Column(name = "NRO_CAJA_SALUD")
//    private String nroCajaSalud;
//    @Column(name = "NRO_AFP")
//    private String nroAfp;
//    @Column(name = "NRO_FUNDAEMPRESA")
//    private String nroFundaempresa;
//    @Column(name = "NRO_OTRO")
//    private String nroOtro;
//    @Column(name = "OBSERVACIONES")
//    private String observaciones;
//    @Column(name = "TIPO_SOCIENDAD")
//    private String tipoSociendad;
//    @Column(name = "TSOCIEDAD")
//    private String tsociedad;
//    @Column(name = "TIPO_EMPRESA")
//    private String tipoEmpresa;
//    @Column(name = "TEMPRESA")
//    private String tempresa;
//    @Column(name = "ACTIVIDAD_DECLARADA")
//    private String actividadDeclarada;
//    @Basic(optional = false)
//    @Column(name = "ESTADO_UNIDAD")
//    private String estadoUnidad;
//    @Column(name = "ESTADO")
//    private String estado;
//    @Column(name = "RL_NRO_PERMISO")
//    private String rlNroPermiso;
//    @Column(name = "RL_NOMBRE")
//    private String rlNombre;
//    @Column(name = "RL_NRO_IDENTIDAD")
//    private String rlNroIdentidad;
//    @Column(name = "DIR_COD_DEPARTAMENTO")
//    private Long dirCodDepartamento;
//    @Column(name = "DIR_DEPARTAMENTO")
//    private String dirDepartamento;
//    @Column(name = "DIR_LOCALIDAD")
//    private String dirLocalidad;
//    @Column(name = "DIR_ZONA")
//    private String dirZona;
//    @Column(name = "DIR_DIRECCION")
//    private String dirDireccion;
//    @Column(name = "DIR_NRO_DIRECCION")
//    private String dirNroDireccion;
//    @Column(name = "TELEFONO")
//    private String telefono;
//    @Column(name = "FAX")
//    private String fax;
//    @Column(name = "EMAIL")
//    private String email;
//
//    public VperPersona() {
//    }
//
//    public String getIdPersona() {
//        return idPersona;
//    }
//
//    public void setIdPersona(String idPersona) {
//        this.idPersona = idPersona;
//    }
//
//    public String getTipoIdentificacion() {
//        return tipoIdentificacion;
//    }
//
//    public void setTipoIdentificacion(String tipoIdentificacion) {
//        this.tipoIdentificacion = tipoIdentificacion;
//    }
//
//    public BigInteger getNroIdentificacion() {
//        return nroIdentificacion;
//    }
//
//    public void setNroIdentificacion(BigInteger nroIdentificacion) {
//        this.nroIdentificacion = nroIdentificacion;
//    }
//
//    public String getNombreRazonSocial() {
//        return nombreRazonSocial;
//    }
//
//    public void setNombreRazonSocial(String nombreRazonSocial) {
//        this.nombreRazonSocial = nombreRazonSocial;
//    }
//
//    public String getApellidoPaterno() {
//        return apellidoPaterno;
//    }
//
//    public void setApellidoPaterno(String apellidoPaterno) {
//        this.apellidoPaterno = apellidoPaterno;
//    }
//
//    public String getApellidoMaterno() {
//        return apellidoMaterno;
//    }
//
//    public void setApellidoMaterno(String apellidoMaterno) {
//        this.apellidoMaterno = apellidoMaterno;
//    }
//
//    public BigInteger getEsNatural() {
//        return esNatural;
//    }
//
//    public void setEsNatural(BigInteger esNatural) {
//        this.esNatural = esNatural;
//    }
//
//    public String getCodLocalidad() {
//        return codLocalidad;
//    }
//
//    public void setCodLocalidad(String codLocalidad) {
//        this.codLocalidad = codLocalidad;
//    }
//
//    public String getLocalidad() {
//        return localidad;
//    }
//
//    public void setLocalidad(String localidad) {
//        this.localidad = localidad;
//    }
//
//    public long getIdUnidad() {
//        return idUnidad;
//    }
//
//    public void setIdUnidad(long idUnidad) {
//        this.idUnidad = idUnidad;
//    }
//
//    public String getNombreComercial() {
//        return nombreComercial;
//    }
//
//    public void setNombreComercial(String nombreComercial) {
//        this.nombreComercial = nombreComercial;
//    }
//
//    public Date getFechaNacimiento() {
//        return fechaNacimiento;
//    }
//
//    public void setFechaNacimiento(Date fechaNacimiento) {
//        this.fechaNacimiento = fechaNacimiento;
//    }
//
//    public String getNroCajaSalud() {
//        return nroCajaSalud;
//    }
//
//    public void setNroCajaSalud(String nroCajaSalud) {
//        this.nroCajaSalud = nroCajaSalud;
//    }
//
//    public String getNroAfp() {
//        return nroAfp;
//    }
//
//    public void setNroAfp(String nroAfp) {
//        this.nroAfp = nroAfp;
//    }
//
//    public String getNroFundaempresa() {
//        return nroFundaempresa;
//    }
//
//    public void setNroFundaempresa(String nroFundaempresa) {
//        this.nroFundaempresa = nroFundaempresa;
//    }
//
//    public String getNroOtro() {
//        return nroOtro;
//    }
//
//    public void setNroOtro(String nroOtro) {
//        this.nroOtro = nroOtro;
//    }
//
//    public String getObservaciones() {
//        return observaciones;
//    }
//
//    public void setObservaciones(String observaciones) {
//        this.observaciones = observaciones;
//    }
//
//    public String getTipoSociendad() {
//        return tipoSociendad;
//    }
//
//    public void setTipoSociendad(String tipoSociendad) {
//        this.tipoSociendad = tipoSociendad;
//    }
//
//    public String getTsociedad() {
//        return tsociedad;
//    }
//
//    public void setTsociedad(String tsociedad) {
//        this.tsociedad = tsociedad;
//    }
//
//    public String getTipoEmpresa() {
//        return tipoEmpresa;
//    }
//
//    public void setTipoEmpresa(String tipoEmpresa) {
//        this.tipoEmpresa = tipoEmpresa;
//    }
//
//    public String getTempresa() {
//        return tempresa;
//    }
//
//    public void setTempresa(String tempresa) {
//        this.tempresa = tempresa;
//    }
//
//    public String getActividadDeclarada() {
//        return actividadDeclarada;
//    }
//
//    public void setActividadDeclarada(String actividadDeclarada) {
//        this.actividadDeclarada = actividadDeclarada;
//    }
//
//    public String getEstadoUnidad() {
//        return estadoUnidad;
//    }
//
//    public void setEstadoUnidad(String estadoUnidad) {
//        this.estadoUnidad = estadoUnidad;
//    }
//
//    public String getEstado() {
//        return estado;
//    }
//
//    public void setEstado(String estado) {
//        this.estado = estado;
//    }
//
//    public String getRlNroPermiso() {
//        return rlNroPermiso;
//    }
//
//    public void setRlNroPermiso(String rlNroPermiso) {
//        this.rlNroPermiso = rlNroPermiso;
//    }
//
//    public String getRlNombre() {
//        return rlNombre;
//    }
//
//    public void setRlNombre(String rlNombre) {
//        this.rlNombre = rlNombre;
//    }
//
//    public String getRlNroIdentidad() {
//        return rlNroIdentidad;
//    }
//
//    public void setRlNroIdentidad(String rlNroIdentidad) {
//        this.rlNroIdentidad = rlNroIdentidad;
//    }
//
//    public Long getDirCodDepartamento() {
//        return dirCodDepartamento;
//    }
//
//    public void setDirCodDepartamento(Long dirCodDepartamento) {
//        this.dirCodDepartamento = dirCodDepartamento;
//    }
//
//    public String getDirDepartamento() {
//        return dirDepartamento;
//    }
//
//    public void setDirDepartamento(String dirDepartamento) {
//        this.dirDepartamento = dirDepartamento;
//    }
//
//    public String getDirLocalidad() {
//        return dirLocalidad;
//    }
//
//    public void setDirLocalidad(String dirLocalidad) {
//        this.dirLocalidad = dirLocalidad;
//    }
//
//    public String getDirZona() {
//        return dirZona;
//    }
//
//    public void setDirZona(String dirZona) {
//        this.dirZona = dirZona;
//    }
//
//    public String getDirDireccion() {
//        return dirDireccion;
//    }
//
//    public void setDirDireccion(String dirDireccion) {
//        this.dirDireccion = dirDireccion;
//    }
//
//    public String getDirNroDireccion() {
//        return dirNroDireccion;
//    }
//
//    public void setDirNroDireccion(String dirNroDireccion) {
//        this.dirNroDireccion = dirNroDireccion;
//    }
//
//    public String getTelefono() {
//        return telefono;
//    }
//
//    public void setTelefono(String telefono) {
//        this.telefono = telefono;
//    }
//
//    public String getFax() {
//        return fax;
//    }
//
//    public void setFax(String fax) {
//        this.fax = fax;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//}
