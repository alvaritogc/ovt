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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rvelasquez
 */
@Entity
@Table(name = "DOC_PLANILLA_DETALLE")
@NamedQueries({
    @NamedQuery(name = "DocPlanillaDetalle.findAll", query = "SELECT d FROM DocPlanillaDetalle d")})
public class DocPlanillaDetalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PLANILLA_DETALLE")
    private Long idPlanillaDetalle;
    @Basic(optional = false)
    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;
    @Basic(optional = false)
    @Column(name = "NUMERO_DOCUMENTO")
    private String numeroDocumento;
    @Basic(optional = false)
    @Column(name = "EXTENCION_DOCUMENTO")
    private String extencionDocumento;
    @Column(name = "AFP")
    private String afp;
    @Column(name = "NUA_CUA")
    private String nuaCua;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "NACIONALIDAD")
    private String nacionalidad;
    @Column(name = "FECHA_NACIMIENTO")
    private String fechaNacimiento;
    @Column(name = "SEXO")
    private String sexo;
    @Column(name = "JUBILADO")
    private String jubilado;
    @Column(name = "CLASIFICACION_LABORAL")
    private String clasificacionLaboral;
    @Column(name = "CARGO")
    private String cargo;
    @Column(name = "FECHA_INGRESO")
    private String fechaIngreso;
    @Column(name = "HORAS_PAGADAS_DIA")
    private String horasPagadasDia;
    @Column(name = "DIAS_HABER_BASICO")
    private String diasHaberBasico;
    @Column(name = "DIAS_PAGADOS")
    private String diasPagados;
    @Column(name = "DOMINICAL_MES")
    private String dominicalMes;
    @Column(name = "DOMINICAL_TRABAJADO")
    private String dominicalTrabajado;
    @Column(name = "HORAS_EXTRA")
    private String horasExtra;
    @Column(name = "HORAS_NOCTURNO")
    private String horasNocturno;
    @Column(name = "HORAS_DOMINICALES")
    private String horasDominicales;
    @Column(name = "HABER_BASICO")
    private String haberBasico;
    @Column(name = "HABER_DOMINICAL")
    private String haberDominical;
    @Column(name = "MONTO_DOMINICAL")
    private String montoDominical;
    @Column(name = "MONTO_HORAS_EXTRA")
    private String montoHorasExtra;
    @Column(name = "MONTO_HORAS_NOCTURNO")
    private String montoHorasNocturno;
    @Column(name = "MONTO_HORAS_DOMINICAL")
    private String montoHorasDominical;
    @Column(name = "BONO_ANTIGUEDAD")
    private String bonoAntiguedad;
    @Column(name = "BONO_PRODUCCION")
    private String bonoProduccion;
    @Column(name = "BONO_FRONTERA")
    private String bonoFrontera;
    @Column(name = "BONO_OTROS")
    private String bonoOtros;
    @Column(name = "TOTAL_GANADO")
    private String totalGanado;
    @Column(name = "DESCUENTO_AFP")
    private String descuentoAfp;
    @Column(name = "DESCUENTO_RCIVA")
    private String descuentoRciva;
    @Column(name = "DESCUENTO_OTRO")
    private String descuentoOtro;
    @Column(name = "DESCUENTOS_TOTAL")
    private String descuentosTotal;
    @Column(name = "LIQUIDO_PAGABLE")
    private String liquidoPagable;
    @JoinColumn(name = "ID_PLANILLA", referencedColumnName = "ID_PLANILLA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private DocPlanilla idPlanilla;

    public DocPlanillaDetalle() {
    }

    public DocPlanillaDetalle(Long idPlanillaDetalle) {
        this.idPlanillaDetalle = idPlanillaDetalle;
    }

    public DocPlanillaDetalle(Long idPlanillaDetalle, String tipoDocumento, String numeroDocumento, String extencionDocumento) {
        this.idPlanillaDetalle = idPlanillaDetalle;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.extencionDocumento = extencionDocumento;
    }

    public Long getIdPlanillaDetalle() {
        return idPlanillaDetalle;
    }

    public void setIdPlanillaDetalle(Long idPlanillaDetalle) {
        this.idPlanillaDetalle = idPlanillaDetalle;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getExtencionDocumento() {
        return extencionDocumento;
    }

    public void setExtencionDocumento(String extencionDocumento) {
        this.extencionDocumento = extencionDocumento;
    }

    public String getAfp() {
        return afp;
    }

    public void setAfp(String afp) {
        this.afp = afp;
    }

    public String getNuaCua() {
        return nuaCua;
    }

    public void setNuaCua(String nuaCua) {
        this.nuaCua = nuaCua;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getJubilado() {
        return jubilado;
    }

    public void setJubilado(String jubilado) {
        this.jubilado = jubilado;
    }

    public String getClasificacionLaboral() {
        return clasificacionLaboral;
    }

    public void setClasificacionLaboral(String clasificacionLaboral) {
        this.clasificacionLaboral = clasificacionLaboral;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getHorasPagadasDia() {
        return horasPagadasDia;
    }

    public void setHorasPagadasDia(String horasPagadasDia) {
        this.horasPagadasDia = horasPagadasDia;
    }

    public String getDiasHaberBasico() {
        return diasHaberBasico;
    }

    public void setDiasHaberBasico(String diasHaberBasico) {
        this.diasHaberBasico = diasHaberBasico;
    }

    public String getDiasPagados() {
        return diasPagados;
    }

    public void setDiasPagados(String diasPagados) {
        this.diasPagados = diasPagados;
    }

    public String getDominicalMes() {
        return dominicalMes;
    }

    public void setDominicalMes(String dominicalMes) {
        this.dominicalMes = dominicalMes;
    }

    public String getDominicalTrabajado() {
        return dominicalTrabajado;
    }

    public void setDominicalTrabajado(String dominicalTrabajado) {
        this.dominicalTrabajado = dominicalTrabajado;
    }

    public String getHorasExtra() {
        return horasExtra;
    }

    public void setHorasExtra(String horasExtra) {
        this.horasExtra = horasExtra;
    }

    public String getHorasNocturno() {
        return horasNocturno;
    }

    public void setHorasNocturno(String horasNocturno) {
        this.horasNocturno = horasNocturno;
    }

    public String getHorasDominicales() {
        return horasDominicales;
    }

    public void setHorasDominicales(String horasDominicales) {
        this.horasDominicales = horasDominicales;
    }

    public String getHaberBasico() {
        return haberBasico;
    }

    public void setHaberBasico(String haberBasico) {
        this.haberBasico = haberBasico;
    }

    public String getHaberDominical() {
        return haberDominical;
    }

    public void setHaberDominical(String haberDominical) {
        this.haberDominical = haberDominical;
    }

    public String getMontoDominical() {
        return montoDominical;
    }

    public void setMontoDominical(String montoDominical) {
        this.montoDominical = montoDominical;
    }

    public String getMontoHorasExtra() {
        return montoHorasExtra;
    }

    public void setMontoHorasExtra(String montoHorasExtra) {
        this.montoHorasExtra = montoHorasExtra;
    }

    public String getMontoHorasNocturno() {
        return montoHorasNocturno;
    }

    public void setMontoHorasNocturno(String montoHorasNocturno) {
        this.montoHorasNocturno = montoHorasNocturno;
    }

    public String getMontoHorasDominical() {
        return montoHorasDominical;
    }

    public void setMontoHorasDominical(String montoHorasDominical) {
        this.montoHorasDominical = montoHorasDominical;
    }

    public String getBonoAntiguedad() {
        return bonoAntiguedad;
    }

    public void setBonoAntiguedad(String bonoAntiguedad) {
        this.bonoAntiguedad = bonoAntiguedad;
    }

    public String getBonoProduccion() {
        return bonoProduccion;
    }

    public void setBonoProduccion(String bonoProduccion) {
        this.bonoProduccion = bonoProduccion;
    }

    public String getBonoFrontera() {
        return bonoFrontera;
    }

    public void setBonoFrontera(String bonoFrontera) {
        this.bonoFrontera = bonoFrontera;
    }

    public String getBonoOtros() {
        return bonoOtros;
    }

    public void setBonoOtros(String bonoOtros) {
        this.bonoOtros = bonoOtros;
    }

    public String getTotalGanado() {
        return totalGanado;
    }

    public void setTotalGanado(String totalGanado) {
        this.totalGanado = totalGanado;
    }

    public String getDescuentoAfp() {
        return descuentoAfp;
    }

    public void setDescuentoAfp(String descuentoAfp) {
        this.descuentoAfp = descuentoAfp;
    }

    public String getDescuentoRciva() {
        return descuentoRciva;
    }

    public void setDescuentoRciva(String descuentoRciva) {
        this.descuentoRciva = descuentoRciva;
    }

    public String getDescuentoOtro() {
        return descuentoOtro;
    }

    public void setDescuentoOtro(String descuentoOtro) {
        this.descuentoOtro = descuentoOtro;
    }

    public String getDescuentosTotal() {
        return descuentosTotal;
    }

    public void setDescuentosTotal(String descuentosTotal) {
        this.descuentosTotal = descuentosTotal;
    }

    public String getLiquidoPagable() {
        return liquidoPagable;
    }

    public void setLiquidoPagable(String liquidoPagable) {
        this.liquidoPagable = liquidoPagable;
    }

    public DocPlanilla getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(DocPlanilla idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlanillaDetalle != null ? idPlanillaDetalle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocPlanillaDetalle)) {
            return false;
        }
        DocPlanillaDetalle other = (DocPlanillaDetalle) object;
        if ((this.idPlanillaDetalle == null && other.idPlanillaDetalle != null) || (this.idPlanillaDetalle != null && !this.idPlanillaDetalle.equals(other.idPlanillaDetalle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocPlanillaDetalle[ idPlanillaDetalle=" + idPlanillaDetalle + " ]";
    }
}
