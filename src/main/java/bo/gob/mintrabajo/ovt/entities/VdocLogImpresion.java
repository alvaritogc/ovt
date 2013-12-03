/*
 * Copyright 2013 gmercado.
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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author gmercado
 */
@Table(name = "VDOC_LOG_IMPRESION")
@NamedQueries({
    @NamedQuery(name = "VdocLogImpresion.findAll", query = "SELECT v FROM VdocLogImpresion v")})
public class VdocLogImpresion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "TIPO_IDENTIFICACION")
    private String tipoIdentificacion;
    @Basic(optional = false)
    @Column(name = "NRO_IDENTIFICACION")
    private String nroIdentificacion;
    @Basic(optional = false)
    @Column(name = "NOMBRE_RAZON_SOCIAL")
    private String nombreRazonSocial;
    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;
    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;
    @Basic(optional = false)
    @Column(name = "ES_NATURAL")
    private short esNatural;
    @Basic(optional = false)
    @Column(name = "COD_LOCALIDAD")
    private String codLocalidad;
    @Basic(optional = false)
    @Column(name = "ID_UNIDAD")
    private long idUnidad;
    @Basic(optional = false)
    @Column(name = "NOMBRE_COMERCIAL")
    private String nombreComercial;
    @Basic(optional = false)
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(name = "NRO_CAJA_SALUD")
    private String nroCajaSalud;
    @Column(name = "NRO_AFP")
    private String nroAfp;
    @Column(name = "NRO_FUNDAEMPRESA")
    private String nroFundaempresa;
    @Column(name = "NRO_OTRO")
    private String nroOtro;
    @Basic(optional = false)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @Basic(optional = false)
    @Column(name = "TIPO_SOCIEDAD")
    private String tipoSociedad;
    @Basic(optional = false)
    @Column(name = "TIPO_EMPRESA")
    private String tipoEmpresa;
    @Basic(optional = false)
    @Column(name = "ACTIVIDAD_DECLARADA")
    private String actividadDeclarada;
    @Basic(optional = false)
    @Column(name = "ESTADO_UNIDAD")
    private String estadoUnidad;
    @Column(name = "NRO_REFERENCIAL")
    private String nroReferencial;
    @Column(name = "TIPO_UNIDAD")
    private String tipoUnidad;
    @Basic(optional = false)
    @Column(name = "ID_DOCUMENTO")
    private long idDocumento;
    @Basic(optional = false)
    @Column(name = "COD_DOCUMENTO")
    private String codDocumento;
    @Basic(optional = false)
    @Column(name = "VERSION")
    private short version;
    @Basic(optional = false)
    @Column(name = "NUMERO_DOCUMENTO")
    private long numeroDocumento;
    @Basic(optional = false)
    @Column(name = "FECHA_DOCUMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDocumento;
    @Column(name = "ID_DOCUMENTO_REF")
    private Long idDocumentoRef;
    @Basic(optional = false)
    @Column(name = "COD_ESTADO")
    private String codEstado;
    @Column(name = "FECHA_REFERENCA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaReferenca;
    @Basic(optional = false)
    @Column(name = "TIPO_MEDIO_REGISTRO")
    private String tipoMedioRegistro;
    @Basic(optional = false)
    @Column(name = "TIPO_IMPRESION")
    private String tipoImpresion;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @Basic(optional = false)
    @Column(name = "ID_DOCLOGIMPRESION")
    private long idDoclogimpresion;

    public VdocLogImpresion() {
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNroIdentificacion() {
        return nroIdentificacion;
    }

    public void setNroIdentificacion(String nroIdentificacion) {
        this.nroIdentificacion = nroIdentificacion;
    }

    public String getNombreRazonSocial() {
        return nombreRazonSocial;
    }

    public void setNombreRazonSocial(String nombreRazonSocial) {
        this.nombreRazonSocial = nombreRazonSocial;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public short getEsNatural() {
        return esNatural;
    }

    public void setEsNatural(short esNatural) {
        this.esNatural = esNatural;
    }

    public String getCodLocalidad() {
        return codLocalidad;
    }

    public void setCodLocalidad(String codLocalidad) {
        this.codLocalidad = codLocalidad;
    }

    public long getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(long idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNroCajaSalud() {
        return nroCajaSalud;
    }

    public void setNroCajaSalud(String nroCajaSalud) {
        this.nroCajaSalud = nroCajaSalud;
    }

    public String getNroAfp() {
        return nroAfp;
    }

    public void setNroAfp(String nroAfp) {
        this.nroAfp = nroAfp;
    }

    public String getNroFundaempresa() {
        return nroFundaempresa;
    }

    public void setNroFundaempresa(String nroFundaempresa) {
        this.nroFundaempresa = nroFundaempresa;
    }

    public String getNroOtro() {
        return nroOtro;
    }

    public void setNroOtro(String nroOtro) {
        this.nroOtro = nroOtro;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipoSociedad() {
        return tipoSociedad;
    }

    public void setTipoSociedad(String tipoSociedad) {
        this.tipoSociedad = tipoSociedad;
    }

    public String getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(String tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public String getActividadDeclarada() {
        return actividadDeclarada;
    }

    public void setActividadDeclarada(String actividadDeclarada) {
        this.actividadDeclarada = actividadDeclarada;
    }

    public String getEstadoUnidad() {
        return estadoUnidad;
    }

    public void setEstadoUnidad(String estadoUnidad) {
        this.estadoUnidad = estadoUnidad;
    }

    public String getNroReferencial() {
        return nroReferencial;
    }

    public void setNroReferencial(String nroReferencial) {
        this.nroReferencial = nroReferencial;
    }

    public String getTipoUnidad() {
        return tipoUnidad;
    }

    public void setTipoUnidad(String tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    public long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getCodDocumento() {
        return codDocumento;
    }

    public void setCodDocumento(String codDocumento) {
        this.codDocumento = codDocumento;
    }

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    public Long getIdDocumentoRef() {
        return idDocumentoRef;
    }

    public void setIdDocumentoRef(Long idDocumentoRef) {
        this.idDocumentoRef = idDocumentoRef;
    }

    public String getCodEstado() {
        return codEstado;
    }

    public void setCodEstado(String codEstado) {
        this.codEstado = codEstado;
    }

    public Date getFechaReferenca() {
        return fechaReferenca;
    }

    public void setFechaReferenca(Date fechaReferenca) {
        this.fechaReferenca = fechaReferenca;
    }

    public String getTipoMedioRegistro() {
        return tipoMedioRegistro;
    }

    public void setTipoMedioRegistro(String tipoMedioRegistro) {
        this.tipoMedioRegistro = tipoMedioRegistro;
    }

    public String getTipoImpresion() {
        return tipoImpresion;
    }

    public void setTipoImpresion(String tipoImpresion) {
        this.tipoImpresion = tipoImpresion;
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

    public long getIdDoclogimpresion() {
        return idDoclogimpresion;
    }

    public void setIdDoclogimpresion(long idDoclogimpresion) {
        this.idDoclogimpresion = idDoclogimpresion;
    }
    
}
