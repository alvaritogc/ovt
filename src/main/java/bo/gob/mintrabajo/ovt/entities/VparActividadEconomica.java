/*
 * Copyright 2013 rvelasquez.
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
import java.math.BigInteger;
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
 * @author rvelasquez
 */
@Entity
@Table(name = "VPAR_ACTIVIDAD_ECONOMICA")
@NamedQueries({
    @NamedQuery(name = "VparActividadEconomica.findAll", query = "SELECT v FROM VparActividadEconomica v")})
public class VparActividadEconomica implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ID_ACTIVIDAD_ECONOMICA")
    private long idActividadEconomica;
    @Basic(optional = false)
    @Column(name = "COD_ACTIVIDAD_ECONOMICA")
    private String codActividadEconomica;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "COD_IMPUESTOS")
    private String codImpuestos;
    @Column(name = "DESCRICPION_IMPUESTOS")
    private String descricpionImpuestos;
    @Column(name = "ID_ACTIVIDAD_ECONOMICA2")
    private Long idActividadEconomica2;
    @Basic(optional = false)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @Column(name = "FECHA_BITACORA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBitacora;
    @Basic(optional = false)
    @Column(name = "REGISTRO_BITACORA")
    private String registroBitacora;
    @Column(name = "NIVEL")
    private BigInteger nivel;

    public VparActividadEconomica() {
    }

    public long getIdActividadEconomica() {
        return idActividadEconomica;
    }

    public void setIdActividadEconomica(long idActividadEconomica) {
        this.idActividadEconomica = idActividadEconomica;
    }

    public String getCodActividadEconomica() {
        return codActividadEconomica;
    }

    public void setCodActividadEconomica(String codActividadEconomica) {
        this.codActividadEconomica = codActividadEconomica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodImpuestos() {
        return codImpuestos;
    }

    public void setCodImpuestos(String codImpuestos) {
        this.codImpuestos = codImpuestos;
    }

    public String getDescricpionImpuestos() {
        return descricpionImpuestos;
    }

    public void setDescricpionImpuestos(String descricpionImpuestos) {
        this.descricpionImpuestos = descricpionImpuestos;
    }

    public Long getIdActividadEconomica2() {
        return idActividadEconomica2;
    }

    public void setIdActividadEconomica2(Long idActividadEconomica2) {
        this.idActividadEconomica2 = idActividadEconomica2;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public BigInteger getNivel() {
        return nivel;
    }

    public void setNivel(BigInteger nivel) {
        this.nivel = nivel;
    }
    
}
