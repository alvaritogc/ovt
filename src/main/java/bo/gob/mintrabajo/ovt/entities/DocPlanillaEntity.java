package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: rvelasquez
 * Date: 10/8/13
 * Time: 8:16 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "DOC_PLANILLA", schema = "OVT", catalog = "")
@Entity
public class DocPlanillaEntity {
    private int idPlanilla;

    @javax.persistence.Column(name = "ID_PLANILLA")
    @Id
    public int getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(int idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    private int idDocumento;

    @javax.persistence.Column(name = "ID_DOCUMENTO")
    @Basic
    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    private String periodo;

    @javax.persistence.Column(name = "PERIODO")
    @Basic
    public String getPeriodo() {
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

    private int nroAsegCaja;

    @javax.persistence.Column(name = "NRO_ASEG_CAJA")
    @Basic
    public int getNroAsegCaja() {
        return nroAsegCaja;
    }

    public void setNroAsegCaja(int nroAsegCaja) {
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

    private int idEntidadSalud;

    @javax.persistence.Column(name = "ID_ENTIDAD_SALUD")
    @Basic
    public int getIdEntidadSalud() {
        return idEntidadSalud;
    }

    public void setIdEntidadSalud(int idEntidadSalud) {
        this.idEntidadSalud = idEntidadSalud;
    }

    private int nroAsegAfp;

    @javax.persistence.Column(name = "NRO_ASEG_AFP")
    @Basic
    public int getNroAsegAfp() {
        return nroAsegAfp;
    }

    public void setNroAsegAfp(int nroAsegAfp) {
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

    private int nroM;

    @javax.persistence.Column(name = "NRO_M")
    @Basic
    public int getNroM() {
        return nroM;
    }

    public void setNroM(int nroM) {
        this.nroM = nroM;
    }

    private int nroH;

    @javax.persistence.Column(name = "NRO_H")
    @Basic
    public int getNroH() {
        return nroH;
    }

    public void setNroH(int nroH) {
        this.nroH = nroH;
    }

    private int nroJubiladosM;

    @javax.persistence.Column(name = "NRO_JUBILADOS_M")
    @Basic
    public int getNroJubiladosM() {
        return nroJubiladosM;
    }

    public void setNroJubiladosM(int nroJubiladosM) {
        this.nroJubiladosM = nroJubiladosM;
    }

    private int nroJubiladosH;

    @javax.persistence.Column(name = "NRO_JUBILADOS_H")
    @Basic
    public int getNroJubiladosH() {
        return nroJubiladosH;
    }

    public void setNroJubiladosH(int nroJubiladosH) {
        this.nroJubiladosH = nroJubiladosH;
    }

    private int nroExtranjerosM;

    @javax.persistence.Column(name = "NRO_EXTRANJEROS_M")
    @Basic
    public int getNroExtranjerosM() {
        return nroExtranjerosM;
    }

    public void setNroExtranjerosM(int nroExtranjerosM) {
        this.nroExtranjerosM = nroExtranjerosM;
    }

    private int nroExtranjerosH;

    @javax.persistence.Column(name = "NRO_EXTRANJEROS_H")
    @Basic
    public int getNroExtranjerosH() {
        return nroExtranjerosH;
    }

    public void setNroExtranjerosH(int nroExtranjerosH) {
        this.nroExtranjerosH = nroExtranjerosH;
    }

    private int nroDiscapacidadM;

    @javax.persistence.Column(name = "NRO_DISCAPACIDAD_M")
    @Basic
    public int getNroDiscapacidadM() {
        return nroDiscapacidadM;
    }

    public void setNroDiscapacidadM(int nroDiscapacidadM) {
        this.nroDiscapacidadM = nroDiscapacidadM;
    }

    private int nroDiscapacidadH;

    @javax.persistence.Column(name = "NRO_DISCAPACIDAD_H")
    @Basic
    public int getNroDiscapacidadH() {
        return nroDiscapacidadH;
    }

    public void setNroDiscapacidadH(int nroDiscapacidadH) {
        this.nroDiscapacidadH = nroDiscapacidadH;
    }

    private int nroContratadosM;

    @javax.persistence.Column(name = "NRO_CONTRATADOS_M")
    @Basic
    public int getNroContratadosM() {
        return nroContratadosM;
    }

    public void setNroContratadosM(int nroContratadosM) {
        this.nroContratadosM = nroContratadosM;
    }

    private int nroContratadosH;

    @javax.persistence.Column(name = "NRO_CONTRATADOS_H")
    @Basic
    public int getNroContratadosH() {
        return nroContratadosH;
    }

    public void setNroContratadosH(int nroContratadosH) {
        this.nroContratadosH = nroContratadosH;
    }

    private int nroRetiradosM;

    @javax.persistence.Column(name = "NRO_RETIRADOS_M")
    @Basic
    public int getNroRetiradosM() {
        return nroRetiradosM;
    }

    public void setNroRetiradosM(int nroRetiradosM) {
        this.nroRetiradosM = nroRetiradosM;
    }

    private int nroRetiradosH;

    @javax.persistence.Column(name = "NRO_RETIRADOS_H")
    @Basic
    public int getNroRetiradosH() {
        return nroRetiradosH;
    }

    public void setNroRetiradosH(int nroRetiradosH) {
        this.nroRetiradosH = nroRetiradosH;
    }

    private int nroAccidentes;

    @javax.persistence.Column(name = "NRO_ACCIDENTES")
    @Basic
    public int getNroAccidentes() {
        return nroAccidentes;
    }

    public void setNroAccidentes(int nroAccidentes) {
        this.nroAccidentes = nroAccidentes;
    }

    private int nroMuertes;

    @javax.persistence.Column(name = "NRO_MUERTES")
    @Basic
    public int getNroMuertes() {
        return nroMuertes;
    }

    public void setNroMuertes(int nroMuertes) {
        this.nroMuertes = nroMuertes;
    }

    private int nroEnfermedades;

    @javax.persistence.Column(name = "NRO_ENFERMEDADES")
    @Basic
    public int getNroEnfermedades() {
        return nroEnfermedades;
    }

    public void setNroEnfermedades(int nroEnfermedades) {
        this.nroEnfermedades = nroEnfermedades;
    }

    private int idEntidadBanco;

    @javax.persistence.Column(name = "ID_ENTIDAD_BANCO")
    @Basic
    public int getIdEntidadBanco() {
        return idEntidadBanco;
    }

    public void setIdEntidadBanco(int idEntidadBanco) {
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

        if (idDocumento != that.idDocumento) return false;
        if (idEntidadBanco != that.idEntidadBanco) return false;
        if (idEntidadSalud != that.idEntidadSalud) return false;
        if (idPlanilla != that.idPlanilla) return false;
        if (nroAccidentes != that.nroAccidentes) return false;
        if (nroAsegAfp != that.nroAsegAfp) return false;
        if (nroAsegCaja != that.nroAsegCaja) return false;
        if (nroContratadosH != that.nroContratadosH) return false;
        if (nroContratadosM != that.nroContratadosM) return false;
        if (nroDiscapacidadH != that.nroDiscapacidadH) return false;
        if (nroDiscapacidadM != that.nroDiscapacidadM) return false;
        if (nroEnfermedades != that.nroEnfermedades) return false;
        if (nroExtranjerosH != that.nroExtranjerosH) return false;
        if (nroExtranjerosM != that.nroExtranjerosM) return false;
        if (nroH != that.nroH) return false;
        if (nroJubiladosH != that.nroJubiladosH) return false;
        if (nroJubiladosM != that.nroJubiladosM) return false;
        if (nroM != that.nroM) return false;
        if (nroMuertes != that.nroMuertes) return false;
        if (nroRetiradosH != that.nroRetiradosH) return false;
        if (nroRetiradosM != that.nroRetiradosM) return false;
        if (periodo != that.periodo) return false;
        if (aporteAfp != null ? !aporteAfp.equals(that.aporteAfp) : that.aporteAfp != null) return false;
        if (bonoAntiguedad != null ? !bonoAntiguedad.equals(that.bonoAntiguedad) : that.bonoAntiguedad != null)
            return false;
        if (bonoProduccion != null ? !bonoProduccion.equals(that.bonoProduccion) : that.bonoProduccion != null)
            return false;
        if (fechaOperacion != null ? !fechaOperacion.equals(that.fechaOperacion) : that.fechaOperacion != null)
            return false;
        if (haberBasico != null ? !haberBasico.equals(that.haberBasico) : that.haberBasico != null) return false;
        if (laborExtra != null ? !laborExtra.equals(that.laborExtra) : that.laborExtra != null) return false;
        if (montoAsegAfp != null ? !montoAsegAfp.equals(that.montoAsegAfp) : that.montoAsegAfp != null) return false;
        if (montoAsegCaja != null ? !montoAsegCaja.equals(that.montoAsegCaja) : that.montoAsegCaja != null)
            return false;
        if (montoOperacion != null ? !montoOperacion.equals(that.montoOperacion) : that.montoOperacion != null)
            return false;
        if (numOperacion != null ? !numOperacion.equals(that.numOperacion) : that.numOperacion != null) return false;
        if (otrosBonos != null ? !otrosBonos.equals(that.otrosBonos) : that.otrosBonos != null) return false;
        if (otrosDescuentos != null ? !otrosDescuentos.equals(that.otrosDescuentos) : that.otrosDescuentos != null)
            return false;
        if (rciva != null ? !rciva.equals(that.rciva) : that.rciva != null) return false;
        if (subsidioFrontera != null ? !subsidioFrontera.equals(that.subsidioFrontera) : that.subsidioFrontera != null)
            return false;
        if (tipoPlanilla != null ? !tipoPlanilla.equals(that.tipoPlanilla) : that.tipoPlanilla != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPlanilla;
        result = 31 * result + idDocumento;
        result = 31 * result + (periodo != null ? tipoPlanilla.hashCode() : 0);
        result = 31 * result + (tipoPlanilla != null ? tipoPlanilla.hashCode() : 0);
        result = 31 * result + nroAsegCaja;
        result = 31 * result + (montoAsegCaja != null ? montoAsegCaja.hashCode() : 0);
        result = 31 * result + idEntidadSalud;
        result = 31 * result + nroAsegAfp;
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
        result = 31 * result + nroM;
        result = 31 * result + nroH;
        result = 31 * result + nroJubiladosM;
        result = 31 * result + nroJubiladosH;
        result = 31 * result + nroExtranjerosM;
        result = 31 * result + nroExtranjerosH;
        result = 31 * result + nroDiscapacidadM;
        result = 31 * result + nroDiscapacidadH;
        result = 31 * result + nroContratadosM;
        result = 31 * result + nroContratadosH;
        result = 31 * result + nroRetiradosM;
        result = 31 * result + nroRetiradosH;
        result = 31 * result + nroAccidentes;
        result = 31 * result + nroMuertes;
        result = 31 * result + nroEnfermedades;
        result = 31 * result + idEntidadBanco;
        result = 31 * result + (fechaOperacion != null ? fechaOperacion.hashCode() : 0);
        result = 31 * result + (montoOperacion != null ? montoOperacion.hashCode() : 0);
        result = 31 * result + (numOperacion != null ? numOperacion.hashCode() : 0);
        return result;
    }
}
