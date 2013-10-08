package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/8/13
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "DOC_PLANILLA", schema = "OVT", catalog = "")
@Entity
public class DocPlanillaEntity {
    private Integer idPlanilla;

    @javax.persistence.Column(name = "ID_PLANILLA")
    @Id
    public Integer getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(Integer idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    private Integer idDocumento;

    @javax.persistence.Column(name = "ID_DOCUMENTO")
    @Basic
    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    private String periodo;

    @javax.persistence.Column(name = "PERIODO")
    @Basic
    String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    private String tipoPlanilla;

    @javax.persistence.Column(name = "TIPO_PLANILLA")
    @Basic
    public String getTipoPlanilla() {
        return tipoPlanilla;
    }

    public void setTipoPlanilla(String tipoPlanilla) {
        this.tipoPlanilla = tipoPlanilla;
    }

    private Integer nroAsegCaja;

    @javax.persistence.Column(name = "NRO_ASEG_CAJA")
    @Basic
    public Integer getNroAsegCaja() {
        return nroAsegCaja;
    }

    public void setNroAsegCaja(Integer nroAsegCaja) {
        this.nroAsegCaja = nroAsegCaja;
    }

    private BigDecimal montoAsegCaja;

    @javax.persistence.Column(name = "MONTO_ASEG_CAJA")
    @Basic
    public BigDecimal getMontoAsegCaja() {
        return montoAsegCaja;
    }

    public void setMontoAsegCaja(BigDecimal montoAsegCaja) {
        this.montoAsegCaja = montoAsegCaja;
    }

    private Integer idEntidadSalud;

    @javax.persistence.Column(name = "ID_ENTIDAD_SALUD")
    @Basic
    public Integer getIdEntidadSalud() {
        return idEntidadSalud;
    }

    public void setIdEntidadSalud(Integer idEntidadSalud) {
        this.idEntidadSalud = idEntidadSalud;
    }

    private Integer nroAsegAfp;

    @javax.persistence.Column(name = "NRO_ASEG_AFP")
    @Basic
    public Integer getNroAsegAfp() {
        return nroAsegAfp;
    }

    public void setNroAsegAfp(Integer nroAsegAfp) {
        this.nroAsegAfp = nroAsegAfp;
    }

    private BigDecimal montoAsegAfp;

    @javax.persistence.Column(name = "MONTO_ASEG_AFP")
    @Basic
    public BigDecimal getMontoAsegAfp() {
        return montoAsegAfp;
    }

    public void setMontoAsegAfp(BigDecimal montoAsegAfp) {
        this.montoAsegAfp = montoAsegAfp;
    }

    private BigDecimal haberBasico;

    @javax.persistence.Column(name = "HABER_BASICO")
    @Basic
    public BigDecimal getHaberBasico() {
        return haberBasico;
    }

    public void setHaberBasico(BigDecimal haberBasico) {
        this.haberBasico = haberBasico;
    }

    private BigDecimal bonoAntiguedad;

    @javax.persistence.Column(name = "BONO_ANTIGUEDAD")
    @Basic
    public BigDecimal getBonoAntiguedad() {
        return bonoAntiguedad;
    }

    public void setBonoAntiguedad(BigDecimal bonoAntiguedad) {
        this.bonoAntiguedad = bonoAntiguedad;
    }

    private BigDecimal bonoProduccion;

    @javax.persistence.Column(name = "BONO_PRODUCCION")
    @Basic
    public BigDecimal getBonoProduccion() {
        return bonoProduccion;
    }

    public void setBonoProduccion(BigDecimal bonoProduccion) {
        this.bonoProduccion = bonoProduccion;
    }

    private BigDecimal subsidioFrontera;

    @javax.persistence.Column(name = "SUBSIDIO_FRONTERA")
    @Basic
    public BigDecimal getSubsidioFrontera() {
        return subsidioFrontera;
    }

    public void setSubsidioFrontera(BigDecimal subsidioFrontera) {
        this.subsidioFrontera = subsidioFrontera;
    }

    private BigDecimal laborExtra;

    @javax.persistence.Column(name = "LABOR_EXTRA")
    @Basic
    public BigDecimal getLaborExtra() {
        return laborExtra;
    }

    public void setLaborExtra(BigDecimal laborExtra) {
        this.laborExtra = laborExtra;
    }

    private BigDecimal otrosBonos;

    @javax.persistence.Column(name = "OTROS_BONOS")
    @Basic
    public BigDecimal getOtrosBonos() {
        return otrosBonos;
    }

    public void setOtrosBonos(BigDecimal otrosBonos) {
        this.otrosBonos = otrosBonos;
    }

    private BigDecimal aporteAfp;

    @javax.persistence.Column(name = "APORTE_AFP")
    @Basic
    public BigDecimal getAporteAfp() {
        return aporteAfp;
    }

    public void setAporteAfp(BigDecimal aporteAfp) {
        this.aporteAfp = aporteAfp;
    }

    private BigDecimal rciva;

    @javax.persistence.Column(name = "RCIVA")
    @Basic
    public BigDecimal getRciva() {
        return rciva;
    }

    public void setRciva(BigDecimal rciva) {
        this.rciva = rciva;
    }

    private BigDecimal otrosDescuentos;

    @javax.persistence.Column(name = "OTROS_DESCUENTOS")
    @Basic
    public BigDecimal getOtrosDescuentos() {
        return otrosDescuentos;
    }

    public void setOtrosDescuentos(BigDecimal otrosDescuentos) {
        this.otrosDescuentos = otrosDescuentos;
    }

    private Integer nroM;

    @javax.persistence.Column(name = "NRO_M")
    @Basic
    public Integer getNroM() {
        return nroM;
    }

    public void setNroM(Integer nroM) {
        this.nroM = nroM;
    }

    private Integer nroH;

    @javax.persistence.Column(name = "NRO_H")
    @Basic
    public Integer getNroH() {
        return nroH;
    }

    public void setNroH(Integer nroH) {
        this.nroH = nroH;
    }

    private Integer nroJubiladosM;

    @javax.persistence.Column(name = "NRO_JUBILADOS_M")
    @Basic
    public Integer getNroJubiladosM() {
        return nroJubiladosM;
    }

    public void setNroJubiladosM(Integer nroJubiladosM) {
        this.nroJubiladosM = nroJubiladosM;
    }

    private Integer nroJubiladosH;

    @javax.persistence.Column(name = "NRO_JUBILADOS_H")
    @Basic
    public Integer getNroJubiladosH() {
        return nroJubiladosH;
    }

    public void setNroJubiladosH(Integer nroJubiladosH) {
        this.nroJubiladosH = nroJubiladosH;
    }

    private Integer nroExtranjerosM;

    @javax.persistence.Column(name = "NRO_EXTRANJEROS_M")
    @Basic
    public Integer getNroExtranjerosM() {
        return nroExtranjerosM;
    }

    public void setNroExtranjerosM(Integer nroExtranjerosM) {
        this.nroExtranjerosM = nroExtranjerosM;
    }

    private Integer nroExtranjerosH;

    @javax.persistence.Column(name = "NRO_EXTRANJEROS_H")
    @Basic
    public Integer getNroExtranjerosH() {
        return nroExtranjerosH;
    }

    public void setNroExtranjerosH(Integer nroExtranjerosH) {
        this.nroExtranjerosH = nroExtranjerosH;
    }

    private Integer nroDiscapacidadM;

    @javax.persistence.Column(name = "NRO_DISCAPACIDAD_M")
    @Basic
    public Integer getNroDiscapacidadM() {
        return nroDiscapacidadM;
    }

    public void setNroDiscapacidadM(Integer nroDiscapacidadM) {
        this.nroDiscapacidadM = nroDiscapacidadM;
    }

    private Integer nroDiscapacidadH;

    @javax.persistence.Column(name = "NRO_DISCAPACIDAD_H")
    @Basic
    public Integer getNroDiscapacidadH() {
        return nroDiscapacidadH;
    }

    public void setNroDiscapacidadH(Integer nroDiscapacidadH) {
        this.nroDiscapacidadH = nroDiscapacidadH;
    }

    private Integer nroContratadosM;

    @javax.persistence.Column(name = "NRO_CONTRATADOS_M")
    @Basic
    public Integer getNroContratadosM() {
        return nroContratadosM;
    }

    public void setNroContratadosM(Integer nroContratadosM) {
        this.nroContratadosM = nroContratadosM;
    }

    private Integer nroContratadosH;

    @javax.persistence.Column(name = "NRO_CONTRATADOS_H")
    @Basic
    public Integer getNroContratadosH() {
        return nroContratadosH;
    }

    public void setNroContratadosH(Integer nroContratadosH) {
        this.nroContratadosH = nroContratadosH;
    }

    private Integer nroRetiradosM;

    @javax.persistence.Column(name = "NRO_RETIRADOS_M")
    @Basic
    public Integer getNroRetiradosM() {
        return nroRetiradosM;
    }

    public void setNroRetiradosM(Integer nroRetiradosM) {
        this.nroRetiradosM = nroRetiradosM;
    }

    private Integer nroRetiradosH;

    @javax.persistence.Column(name = "NRO_RETIRADOS_H")
    @Basic
    public Integer getNroRetiradosH() {
        return nroRetiradosH;
    }

    public void setNroRetiradosH(Integer nroRetiradosH) {
        this.nroRetiradosH = nroRetiradosH;
    }

    private Integer nroAccidentes;

    @javax.persistence.Column(name = "NRO_ACCIDENTES")
    @Basic
    public Integer getNroAccidentes() {
        return nroAccidentes;
    }

    public void setNroAccidentes(Integer nroAccidentes) {
        this.nroAccidentes = nroAccidentes;
    }

    private Integer nroMuertes;

    @javax.persistence.Column(name = "NRO_MUERTES")
    @Basic
    public Integer getNroMuertes() {
        return nroMuertes;
    }

    public void setNroMuertes(Integer nroMuertes) {
        this.nroMuertes = nroMuertes;
    }

    private Integer nroEnfermedades;

    @javax.persistence.Column(name = "NRO_ENFERMEDADES")
    @Basic
    public Integer getNroEnfermedades() {
        return nroEnfermedades;
    }

    public void setNroEnfermedades(Integer nroEnfermedades) {
        this.nroEnfermedades = nroEnfermedades;
    }

    private Integer idEntidadBanco;

    @javax.persistence.Column(name = "ID_ENTIDAD_BANCO")
    @Basic
    public Integer getIdEntidadBanco() {
        return idEntidadBanco;
    }

    public void setIdEntidadBanco(Integer idEntidadBanco) {
        this.idEntidadBanco = idEntidadBanco;
    }

    private Timestamp fechaOperacion;

    @javax.persistence.Column(name = "FECHA_OPERACION")
    @Basic
    public Timestamp getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(Timestamp fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    private BigDecimal montoOperacion;

    @javax.persistence.Column(name = "MONTO_OPERACION")
    @Basic
    public BigDecimal getMontoOperacion() {
        return montoOperacion;
    }

    public void setMontoOperacion(BigDecimal montoOperacion) {
        this.montoOperacion = montoOperacion;
    }

    private String numOperacion;

    @javax.persistence.Column(name = "NUM_OPERACION")
    @Basic
    public String getNumOperacion() {
        return numOperacion;
    }

    public void setNumOperacion(String numOperacion) {
        this.numOperacion = numOperacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocPlanillaEntity that = (DocPlanillaEntity) o;

        if (aporteAfp != null ? !aporteAfp.equals(that.aporteAfp) : that.aporteAfp != null) return false;
        if (bonoAntiguedad != null ? !bonoAntiguedad.equals(that.bonoAntiguedad) : that.bonoAntiguedad != null)
            return false;
        if (bonoProduccion != null ? !bonoProduccion.equals(that.bonoProduccion) : that.bonoProduccion != null)
            return false;
        if (fechaOperacion != null ? !fechaOperacion.equals(that.fechaOperacion) : that.fechaOperacion != null)
            return false;
        if (haberBasico != null ? !haberBasico.equals(that.haberBasico) : that.haberBasico != null) return false;
        if (idDocumento != null ? !idDocumento.equals(that.idDocumento) : that.idDocumento != null) return false;
        if (idEntidadBanco != null ? !idEntidadBanco.equals(that.idEntidadBanco) : that.idEntidadBanco != null)
            return false;
        if (idEntidadSalud != null ? !idEntidadSalud.equals(that.idEntidadSalud) : that.idEntidadSalud != null)
            return false;
        if (idPlanilla != null ? !idPlanilla.equals(that.idPlanilla) : that.idPlanilla != null) return false;
        if (laborExtra != null ? !laborExtra.equals(that.laborExtra) : that.laborExtra != null) return false;
        if (montoAsegAfp != null ? !montoAsegAfp.equals(that.montoAsegAfp) : that.montoAsegAfp != null) return false;
        if (montoAsegCaja != null ? !montoAsegCaja.equals(that.montoAsegCaja) : that.montoAsegCaja != null)
            return false;
        if (montoOperacion != null ? !montoOperacion.equals(that.montoOperacion) : that.montoOperacion != null)
            return false;
        if (nroAccidentes != null ? !nroAccidentes.equals(that.nroAccidentes) : that.nroAccidentes != null)
            return false;
        if (nroAsegAfp != null ? !nroAsegAfp.equals(that.nroAsegAfp) : that.nroAsegAfp != null) return false;
        if (nroAsegCaja != null ? !nroAsegCaja.equals(that.nroAsegCaja) : that.nroAsegCaja != null) return false;
        if (nroContratadosH != null ? !nroContratadosH.equals(that.nroContratadosH) : that.nroContratadosH != null)
            return false;
        if (nroContratadosM != null ? !nroContratadosM.equals(that.nroContratadosM) : that.nroContratadosM != null)
            return false;
        if (nroDiscapacidadH != null ? !nroDiscapacidadH.equals(that.nroDiscapacidadH) : that.nroDiscapacidadH != null)
            return false;
        if (nroDiscapacidadM != null ? !nroDiscapacidadM.equals(that.nroDiscapacidadM) : that.nroDiscapacidadM != null)
            return false;
        if (nroEnfermedades != null ? !nroEnfermedades.equals(that.nroEnfermedades) : that.nroEnfermedades != null)
            return false;
        if (nroExtranjerosH != null ? !nroExtranjerosH.equals(that.nroExtranjerosH) : that.nroExtranjerosH != null)
            return false;
        if (nroExtranjerosM != null ? !nroExtranjerosM.equals(that.nroExtranjerosM) : that.nroExtranjerosM != null)
            return false;
        if (nroH != null ? !nroH.equals(that.nroH) : that.nroH != null) return false;
        if (nroJubiladosH != null ? !nroJubiladosH.equals(that.nroJubiladosH) : that.nroJubiladosH != null)
            return false;
        if (nroJubiladosM != null ? !nroJubiladosM.equals(that.nroJubiladosM) : that.nroJubiladosM != null)
            return false;
        if (nroM != null ? !nroM.equals(that.nroM) : that.nroM != null) return false;
        if (nroMuertes != null ? !nroMuertes.equals(that.nroMuertes) : that.nroMuertes != null) return false;
        if (nroRetiradosH != null ? !nroRetiradosH.equals(that.nroRetiradosH) : that.nroRetiradosH != null)
            return false;
        if (nroRetiradosM != null ? !nroRetiradosM.equals(that.nroRetiradosM) : that.nroRetiradosM != null)
            return false;
        if (numOperacion != null ? !numOperacion.equals(that.numOperacion) : that.numOperacion != null) return false;
        if (otrosBonos != null ? !otrosBonos.equals(that.otrosBonos) : that.otrosBonos != null) return false;
        if (otrosDescuentos != null ? !otrosDescuentos.equals(that.otrosDescuentos) : that.otrosDescuentos != null)
            return false;
        if (periodo != null ? !periodo.equals(that.periodo) : that.periodo != null) return false;
        if (rciva != null ? !rciva.equals(that.rciva) : that.rciva != null) return false;
        if (subsidioFrontera != null ? !subsidioFrontera.equals(that.subsidioFrontera) : that.subsidioFrontera != null)
            return false;
        if (tipoPlanilla != null ? !tipoPlanilla.equals(that.tipoPlanilla) : that.tipoPlanilla != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPlanilla != null ? idPlanilla.hashCode() : 0;
        result = 31 * result + (idDocumento != null ? idDocumento.hashCode() : 0);
        result = 31 * result + (periodo != null ? periodo.hashCode() : 0);
        result = 31 * result + (tipoPlanilla != null ? tipoPlanilla.hashCode() : 0);
        result = 31 * result + (nroAsegCaja != null ? nroAsegCaja.hashCode() : 0);
        result = 31 * result + (montoAsegCaja != null ? montoAsegCaja.hashCode() : 0);
        result = 31 * result + (idEntidadSalud != null ? idEntidadSalud.hashCode() : 0);
        result = 31 * result + (nroAsegAfp != null ? nroAsegAfp.hashCode() : 0);
        result = 31 * result + (montoAsegAfp != null ? montoAsegAfp.hashCode() : 0);
        result = 31 * result + (haberBasico != null ? haberBasico.hashCode() : 0);
        result = 31 * result + (bonoAntiguedad != null ? bonoAntiguedad.hashCode() : 0);
        result = 31 * result + (bonoProduccion != null ? bonoProduccion.hashCode() : 0);
        result = 31 * result + (subsidioFrontera != null ? subsidioFrontera.hashCode() : 0);
        result = 31 * result + (laborExtra != null ? laborExtra.hashCode() : 0);
        result = 31 * result + (otrosBonos != null ? otrosBonos.hashCode() : 0);
        result = 31 * result + (aporteAfp != null ? aporteAfp.hashCode() : 0);
        result = 31 * result + (rciva != null ? rciva.hashCode() : 0);
        result = 31 * result + (otrosDescuentos != null ? otrosDescuentos.hashCode() : 0);
        result = 31 * result + (nroM != null ? nroM.hashCode() : 0);
        result = 31 * result + (nroH != null ? nroH.hashCode() : 0);
        result = 31 * result + (nroJubiladosM != null ? nroJubiladosM.hashCode() : 0);
        result = 31 * result + (nroJubiladosH != null ? nroJubiladosH.hashCode() : 0);
        result = 31 * result + (nroExtranjerosM != null ? nroExtranjerosM.hashCode() : 0);
        result = 31 * result + (nroExtranjerosH != null ? nroExtranjerosH.hashCode() : 0);
        result = 31 * result + (nroDiscapacidadM != null ? nroDiscapacidadM.hashCode() : 0);
        result = 31 * result + (nroDiscapacidadH != null ? nroDiscapacidadH.hashCode() : 0);
        result = 31 * result + (nroContratadosM != null ? nroContratadosM.hashCode() : 0);
        result = 31 * result + (nroContratadosH != null ? nroContratadosH.hashCode() : 0);
        result = 31 * result + (nroRetiradosM != null ? nroRetiradosM.hashCode() : 0);
        result = 31 * result + (nroRetiradosH != null ? nroRetiradosH.hashCode() : 0);
        result = 31 * result + (nroAccidentes != null ? nroAccidentes.hashCode() : 0);
        result = 31 * result + (nroMuertes != null ? nroMuertes.hashCode() : 0);
        result = 31 * result + (nroEnfermedades != null ? nroEnfermedades.hashCode() : 0);
        result = 31 * result + (idEntidadBanco != null ? idEntidadBanco.hashCode() : 0);
        result = 31 * result + (fechaOperacion != null ? fechaOperacion.hashCode() : 0);
        result = 31 * result + (montoOperacion != null ? montoOperacion.hashCode() : 0);
        result = 31 * result + (numOperacion != null ? numOperacion.hashCode() : 0);
        return result;
    }
}
