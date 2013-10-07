package bo.gob.mintrabajo.ovt.entities;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * User: Renato Velasquez.
 * Date: 06-10-13
 */
@javax.persistence.Table(name = "PAR_OBLIGACION_CALENDARIO", schema = "OVT", catalog = "")
@Entity
public class ParObligacionCalendarioEntity {
    private Integer idObligacionCalendario;

    @javax.persistence.Column(name = "ID_OBLIGACION_CALENDARIO")
    @Id
    public Integer getIdObligacionCalendario() {
        return idObligacionCalendario;
    }

    public void setIdObligacionCalendario(Integer idObligacionCalendario) {
        this.idObligacionCalendario = idObligacionCalendario;
    }

    private String tipoCalendario;

    @javax.persistence.Column(name = "TIPO_CALENDARIO")
    @Basic
    public String getTipoCalendario() {
        return tipoCalendario;
    }

    public void setTipoCalendario(String tipoCalendario) {
        this.tipoCalendario = tipoCalendario;
    }

    private String gestion;

    @javax.persistence.Column(name = "GESTION")
    @Basic
    public String getGestion() {
        return gestion;
    }

    public void setGestion(String gestion) {
        this.gestion = gestion;
    }

    private Timestamp fechaDesde;

    @javax.persistence.Column(name = "FECHA_DESDE")
    @Basic
    public Timestamp getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Timestamp fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    private Timestamp fechaHasta;

    @javax.persistence.Column(name = "FECHA_HASTA")
    @Basic
    public Timestamp getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Timestamp fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    private Timestamp fechaPlazo;

    @javax.persistence.Column(name = "FECHA_PLAZO")
    @Basic
    public Timestamp getFechaPlazo() {
        return fechaPlazo;
    }

    public void setFechaPlazo(Timestamp fechaPlazo) {
        this.fechaPlazo = fechaPlazo;
    }

    private Timestamp fechaBitacora;

    @javax.persistence.Column(name = "FECHA_BITACORA")
    @Basic
    public Timestamp getFechaBitacora() {
        return fechaBitacora;
    }

    public void setFechaBitacora(Timestamp fechaBitacora) {
        this.fechaBitacora = fechaBitacora;
    }

    private String registroBitacora;

    @javax.persistence.Column(name = "REGISTRO_BITACORA")
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

        ParObligacionCalendarioEntity that = (ParObligacionCalendarioEntity) o;

        if (fechaBitacora != null ? !fechaBitacora.equals(that.fechaBitacora) : that.fechaBitacora != null)
            return false;
        if (fechaDesde != null ? !fechaDesde.equals(that.fechaDesde) : that.fechaDesde != null) return false;
        if (fechaHasta != null ? !fechaHasta.equals(that.fechaHasta) : that.fechaHasta != null) return false;
        if (fechaPlazo != null ? !fechaPlazo.equals(that.fechaPlazo) : that.fechaPlazo != null) return false;
        if (gestion != null ? !gestion.equals(that.gestion) : that.gestion != null) return false;
        if (idObligacionCalendario != null ? !idObligacionCalendario.equals(that.idObligacionCalendario) : that.idObligacionCalendario != null)
            return false;
        if (registroBitacora != null ? !registroBitacora.equals(that.registroBitacora) : that.registroBitacora != null)
            return false;
        if (tipoCalendario != null ? !tipoCalendario.equals(that.tipoCalendario) : that.tipoCalendario != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idObligacionCalendario != null ? idObligacionCalendario.hashCode() : 0;
        result = 31 * result + (tipoCalendario != null ? tipoCalendario.hashCode() : 0);
        result = 31 * result + (gestion != null ? gestion.hashCode() : 0);
        result = 31 * result + (fechaDesde != null ? fechaDesde.hashCode() : 0);
        result = 31 * result + (fechaHasta != null ? fechaHasta.hashCode() : 0);
        result = 31 * result + (fechaPlazo != null ? fechaPlazo.hashCode() : 0);
        result = 31 * result + (fechaBitacora != null ? fechaBitacora.hashCode() : 0);
        result = 31 * result + (registroBitacora != null ? registroBitacora.hashCode() : 0);
        return result;
    }
}
