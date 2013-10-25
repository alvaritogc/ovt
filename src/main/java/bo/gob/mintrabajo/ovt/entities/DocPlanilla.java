/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bo.gob.mintrabajo.ovt.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author rvelasquez
 */
@Entity
@Table(name = "DOC_PLANILLA")
@NamedQueries({
    @NamedQuery(name = "DocPlanilla.findAll", query = "SELECT d FROM DocPlanilla d")})
public class DocPlanilla implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PLANILLA")
    private Long idPlanilla;
    @Basic(optional = false)
    @Column(name = "PERIODO")
    private String periodo;
    @Basic(optional = false)
    @Column(name = "TIPO_PLANILLA")
    private String tipoPlanilla;
    @Basic(optional = false)
    @Column(name = "NRO_ASEG_CAJA")
    private int nroAsegCaja;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "MONTO_ASEG_CAJA")
    private BigDecimal montoAsegCaja;
    @Basic(optional = false)
    @Column(name = "NRO_ASEG_AFP")
    private int nroAsegAfp;
    @Basic(optional = false)
    @Column(name = "MONTO_ASEG_AFP")
    private BigDecimal montoAsegAfp;
    @Basic(optional = false)
    @Column(name = "HABER_BASICO")
    private BigDecimal haberBasico;
    @Basic(optional = false)
    @Column(name = "BONO_ANTIGUEDAD")
    private BigDecimal bonoAntiguedad;
    @Basic(optional = false)
    @Column(name = "BONO_PRODUCCION")
    private BigDecimal bonoProduccion;
    @Basic(optional = false)
    @Column(name = "SUBSIDIO_FRONTERA")
    private BigDecimal subsidioFrontera;
    @Basic(optional = false)
    @Column(name = "LABOR_EXTRA")
    private BigDecimal laborExtra;
    @Basic(optional = false)
    @Column(name = "OTROS_BONOS")
    private BigDecimal otrosBonos;
    @Basic(optional = false)
    @Column(name = "APORTE_AFP")
    private BigDecimal aporteAfp;
    @Basic(optional = false)
    @Column(name = "RCIVA")
    private BigDecimal rciva;
    @Basic(optional = false)
    @Column(name = "OTROS_DESCUENTOS")
    private BigDecimal otrosDescuentos;
    @Basic(optional = false)
    @Column(name = "NRO_M")
    private int nroM;
    @Basic(optional = false)
    @Column(name = "NRO_H")
    private int nroH;
    @Basic(optional = false)
    @Column(name = "NRO_JUBILADOS_M")
    private int nroJubiladosM;
    @Basic(optional = false)
    @Column(name = "NRO_JUBILADOS_H")
    private int nroJubiladosH;
    @Basic(optional = false)
    @Column(name = "NRO_EXTRANJEROS_M")
    private int nroExtranjerosM;
    @Basic(optional = false)
    @Column(name = "NRO_EXTRANJEROS_H")
    private int nroExtranjerosH;
    @Basic(optional = false)
    @Column(name = "NRO_DISCAPACIDAD_M")
    private int nroDiscapacidadM;
    @Basic(optional = false)
    @Column(name = "NRO_DISCAPACIDAD_H")
    private int nroDiscapacidadH;
    @Basic(optional = false)
    @Column(name = "NRO_CONTRATADOS_M")
    private int nroContratadosM;
    @Basic(optional = false)
    @Column(name = "NRO_CONTRATADOS_H")
    private int nroContratadosH;
    @Basic(optional = false)
    @Column(name = "NRO_RETIRADOS_M")
    private int nroRetiradosM;
    @Basic(optional = false)
    @Column(name = "NRO_RETIRADOS_H")
    private int nroRetiradosH;
    @Basic(optional = false)
    @Column(name = "NRO_ACCIDENTES")
    private int nroAccidentes;
    @Basic(optional = false)
    @Column(name = "NRO_MUERTES")
    private int nroMuertes;
    @Basic(optional = false)
    @Column(name = "NRO_ENFERMEDADES")
    private int nroEnfermedades;
    @Basic(optional = false)
    @Column(name = "FECHA_OPERACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaOperacion;
    @Basic(optional = false)
    @Column(name = "MONTO_OPERACION")
    private BigDecimal montoOperacion;
    @Basic(optional = false)
    @Column(name = "NUM_OPERACION")
    private String numOperacion;
    @JoinColumn(name = "ID_ENTIDAD_BANCO", referencedColumnName = "ID_ENTIDAD")
    @ManyToOne(optional = false)
    private ParEntidad idEntidadBanco;
    @JoinColumn(name = "ID_ENTIDAD_SALUD", referencedColumnName = "ID_ENTIDAD")
    @ManyToOne(optional = false)
    private ParEntidad idEntidadSalud;
    @JoinColumn(name = "ID_DOCUMENTO", referencedColumnName = "ID_DOCUMENTO")
    @OneToOne(optional = false)
    private DocDocumento idDocumento;

    public DocPlanilla() {
    }

    public DocPlanilla(Long idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public DocPlanilla(Long idPlanilla, String periodo, String tipoPlanilla, int nroAsegCaja, BigDecimal montoAsegCaja, int nroAsegAfp, BigDecimal montoAsegAfp, BigDecimal haberBasico, BigDecimal bonoAntiguedad, BigDecimal bonoProduccion, BigDecimal subsidioFrontera, BigDecimal laborExtra, BigDecimal otrosBonos, BigDecimal aporteAfp, BigDecimal rciva, BigDecimal otrosDescuentos, int nroM, int nroH, int nroJubiladosM, int nroJubiladosH, int nroExtranjerosM, int nroExtranjerosH, int nroDiscapacidadM, int nroDiscapacidadH, int nroContratadosM, int nroContratadosH, int nroRetiradosM, int nroRetiradosH, int nroAccidentes, int nroMuertes, int nroEnfermedades, Date fechaOperacion, BigDecimal montoOperacion, String numOperacion) {
        this.idPlanilla = idPlanilla;
        this.periodo = periodo;
        this.tipoPlanilla = tipoPlanilla;
        this.nroAsegCaja = nroAsegCaja;
        this.montoAsegCaja = montoAsegCaja;
        this.nroAsegAfp = nroAsegAfp;
        this.montoAsegAfp = montoAsegAfp;
        this.haberBasico = haberBasico;
        this.bonoAntiguedad = bonoAntiguedad;
        this.bonoProduccion = bonoProduccion;
        this.subsidioFrontera = subsidioFrontera;
        this.laborExtra = laborExtra;
        this.otrosBonos = otrosBonos;
        this.aporteAfp = aporteAfp;
        this.rciva = rciva;
        this.otrosDescuentos = otrosDescuentos;
        this.nroM = nroM;
        this.nroH = nroH;
        this.nroJubiladosM = nroJubiladosM;
        this.nroJubiladosH = nroJubiladosH;
        this.nroExtranjerosM = nroExtranjerosM;
        this.nroExtranjerosH = nroExtranjerosH;
        this.nroDiscapacidadM = nroDiscapacidadM;
        this.nroDiscapacidadH = nroDiscapacidadH;
        this.nroContratadosM = nroContratadosM;
        this.nroContratadosH = nroContratadosH;
        this.nroRetiradosM = nroRetiradosM;
        this.nroRetiradosH = nroRetiradosH;
        this.nroAccidentes = nroAccidentes;
        this.nroMuertes = nroMuertes;
        this.nroEnfermedades = nroEnfermedades;
        this.fechaOperacion = fechaOperacion;
        this.montoOperacion = montoOperacion;
        this.numOperacion = numOperacion;
    }

    public Long getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(Long idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getTipoPlanilla() {
        return tipoPlanilla;
    }

    public void setTipoPlanilla(String tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }

    public int getNroAsegCaja() {
        return nroAsegCaja;
    }

    public void setNroAsegCaja(int nroAsegCaja) {
        this.nroAsegCaja = nroAsegCaja;
    }

    public BigDecimal getMontoAsegCaja() {
        return montoAsegCaja;
    }

    public void setMontoAsegCaja(BigDecimal montoAsegCaja) {
        this.montoAsegCaja = montoAsegCaja;
    }

    public int getNroAsegAfp() {
        return nroAsegAfp;
    }

    public void setNroAsegAfp(int nroAsegAfp) {
        this.nroAsegAfp = nroAsegAfp;
    }

    public BigDecimal getMontoAsegAfp() {
        return montoAsegAfp;
    }

    public void setMontoAsegAfp(BigDecimal montoAsegAfp) {
        this.montoAsegAfp = montoAsegAfp;
    }

    public BigDecimal getHaberBasico() {
        return haberBasico;
    }

    public void setHaberBasico(BigDecimal haberBasico) {
        this.haberBasico = haberBasico;
    }

    public BigDecimal getBonoAntiguedad() {
        return bonoAntiguedad;
    }

    public void setBonoAntiguedad(BigDecimal bonoAntiguedad) {
        this.bonoAntiguedad = bonoAntiguedad;
    }

    public BigDecimal getBonoProduccion() {
        return bonoProduccion;
    }

    public void setBonoProduccion(BigDecimal bonoProduccion) {
        this.bonoProduccion = bonoProduccion;
    }

    public BigDecimal getSubsidioFrontera() {
        return subsidioFrontera;
    }

    public void setSubsidioFrontera(BigDecimal subsidioFrontera) {
        this.subsidioFrontera = subsidioFrontera;
    }

    public BigDecimal getLaborExtra() {
        return laborExtra;
    }

    public void setLaborExtra(BigDecimal laborExtra) {
        this.laborExtra = laborExtra;
    }

    public BigDecimal getOtrosBonos() {
        return otrosBonos;
    }

    public void setOtrosBonos(BigDecimal otrosBonos) {
        this.otrosBonos = otrosBonos;
    }

    public BigDecimal getAporteAfp() {
        return aporteAfp;
    }

    public void setAporteAfp(BigDecimal aporteAfp) {
        this.aporteAfp = aporteAfp;
    }

    public BigDecimal getRciva() {
        return rciva;
    }

    public void setRciva(BigDecimal rciva) {
        this.rciva = rciva;
    }

    public BigDecimal getOtrosDescuentos() {
        return otrosDescuentos;
    }

    public void setOtrosDescuentos(BigDecimal otrosDescuentos) {
        this.otrosDescuentos = otrosDescuentos;
    }

    public int getNroM() {
        return nroM;
    }

    public void setNroM(int nroM) {
        this.nroM = nroM;
    }

    public int getNroH() {
        return nroH;
    }

    public void setNroH(int nroH) {
        this.nroH = nroH;
    }

    public int getNroJubiladosM() {
        return nroJubiladosM;
    }

    public void setNroJubiladosM(int nroJubiladosM) {
        this.nroJubiladosM = nroJubiladosM;
    }

    public int getNroJubiladosH() {
        return nroJubiladosH;
    }

    public void setNroJubiladosH(int nroJubiladosH) {
        this.nroJubiladosH = nroJubiladosH;
    }

    public int getNroExtranjerosM() {
        return nroExtranjerosM;
    }

    public void setNroExtranjerosM(int nroExtranjerosM) {
        this.nroExtranjerosM = nroExtranjerosM;
    }

    public int getNroExtranjerosH() {
        return nroExtranjerosH;
    }

    public void setNroExtranjerosH(int nroExtranjerosH) {
        this.nroExtranjerosH = nroExtranjerosH;
    }

    public int getNroDiscapacidadM() {
        return nroDiscapacidadM;
    }

    public void setNroDiscapacidadM(int nroDiscapacidadM) {
        this.nroDiscapacidadM = nroDiscapacidadM;
    }

    public int getNroDiscapacidadH() {
        return nroDiscapacidadH;
    }

    public void setNroDiscapacidadH(int nroDiscapacidadH) {
        this.nroDiscapacidadH = nroDiscapacidadH;
    }

    public int getNroContratadosM() {
        return nroContratadosM;
    }

    public void setNroContratadosM(int nroContratadosM) {
        this.nroContratadosM = nroContratadosM;
    }

    public int getNroContratadosH() {
        return nroContratadosH;
    }

    public void setNroContratadosH(int nroContratadosH) {
        this.nroContratadosH = nroContratadosH;
    }

    public int getNroRetiradosM() {
        return nroRetiradosM;
    }

    public void setNroRetiradosM(int nroRetiradosM) {
        this.nroRetiradosM = nroRetiradosM;
    }

    public int getNroRetiradosH() {
        return nroRetiradosH;
    }

    public void setNroRetiradosH(int nroRetiradosH) {
        this.nroRetiradosH = nroRetiradosH;
    }

    public int getNroAccidentes() {
        return nroAccidentes;
    }

    public void setNroAccidentes(int nroAccidentes) {
        this.nroAccidentes = nroAccidentes;
    }

    public int getNroMuertes() {
        return nroMuertes;
    }

    public void setNroMuertes(int nroMuertes) {
        this.nroMuertes = nroMuertes;
    }

    public int getNroEnfermedades() {
        return nroEnfermedades;
    }

    public void setNroEnfermedades(int nroEnfermedades) {
        this.nroEnfermedades = nroEnfermedades;
    }

    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public BigDecimal getMontoOperacion() {
        return montoOperacion;
    }

    public void setMontoOperacion(BigDecimal montoOperacion) {
        this.montoOperacion = montoOperacion;
    }

    public String getNumOperacion() {
        return numOperacion;
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    public ParEntidad getIdEntidadBanco() {
        return idEntidadBanco;
    }

    public void setIdEntidadBanco(ParEntidad idEntidadBanco) {
        this.idEntidadBanco = idEntidadBanco;
    }

    public ParEntidad getIdEntidadSalud() {
        return idEntidadSalud;
    }

    public void setIdEntidadSalud(ParEntidad idEntidadSalud) {
        this.idEntidadSalud = idEntidadSalud;
    }

    public DocDocumento getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(DocDocumento idDocumento) {
        this.idDocumento = idDocumento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlanilla != null ? idPlanilla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocPlanilla)) {
            return false;
        }
        DocPlanilla other = (DocPlanilla) object;
        if ((this.idPlanilla == null && other.idPlanilla != null) || (this.idPlanilla != null && !this.idPlanilla.equals(other.idPlanilla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bo.gob.mintrabajo.ovt.entities.DocPlanilla[ idPlanilla=" + idPlanilla + " ]";
    }
    
}
