package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendario;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

@OpenJpaSettings
public interface ObligacionCalendarioRepository extends OpenJpaRepository<ParObligacionCalendario, Long> {

    @Query(
            "   select a "
            + " from ParObligacionCalendario a"
            + " where "
            + " :fecha between a.fechaHasta and a.fechaPlazo ")
    List<ParObligacionCalendario> buscarPorFecha(@Param("fecha") Date fechaActual);

    List<ParObligacionCalendario> findByCodObligacion_CodObligacion(String codObligacion);

    @Query(
            "   select a "
            + " from ParObligacionCalendario a"
            + " order by "
            + " a.parCalendario.parCalendarioPK.gestion desc, "
            + " a.parCalendario.parCalendarioPK.tipoPeriodo asc, "
            + " a.codObligacion.descripcion asc")
    List<ParObligacionCalendario> listaPorOrdenDescripcionDeObligacion();
    
    @Query(
            "   select a "
            + " from ParObligacionCalendario a where a.codObligacion.codObligacion = :codObligacion"
            + " order by "
            + " a.parCalendario.parCalendarioPK.gestion desc, "
            + " a.parCalendario.parCalendarioPK.tipoPeriodo asc, "
            + " a.codObligacion.descripcion asc "
            + " ")
    List<ParObligacionCalendario> listaEnOrdenYPorCodObligacion(@Param("codObligacion") String codObligacion);

    @Query(
            "   select a "
                    + " from ParObligacionCalendario a"
                    + " where "
                    + " a.parCalendario.parCalendarioPK.gestion= :gestion "
                    + " and a.codObligacion.codObligacion like 'PLAAGU' and a.tipoCalendario like 'ANUAL'")
    ParObligacionCalendario buscarAguinaldoPorGestion(@Param("gestion") String gestion);

    @Query(
            "   select a "
                    + " from ParObligacionCalendario a"
                    + " where "
                    + " a.codObligacion.codObligacion= 'PLATRI' "
                    + " and :fecha between a.fechaHasta and a.fechaPlazo2")
    List<ParObligacionCalendario> listarPlanillaTrimEntreFecha(@Param("fecha") Date fecha);

    @Query(
            "   select a "
                    + " from ParObligacionCalendario a"
                    + " where "
                    + " a.codObligacion.codObligacion like 'PLATRI' "
                    + " and :fechaActual between a.fechaHasta and a.fechaPlazo")
    ParObligacionCalendario listarPlanillaTrimPorFechaHastaFechaPlazo(@Param("fechaActual") Date fechaActual);

    @Query(
            "   select a "
                    + " from ParObligacionCalendario a"
                    + " where "
                    + " a.codObligacion.codObligacion like 'PLATRI' "
                    + " and :fechaActual between a.fechaHasta and a.fechaPlazo2")
    ParObligacionCalendario listarPlanillaTrimPorFechaHastaFechaPlazo2(@Param("fechaActual") Date fechaActual);

    @Query(
            "   select a "
                    + " from ParObligacionCalendario a"
                    + " where "
                    + " a.codObligacion.codObligacion like 'PLAAGU' "
                    + " and :fechaActual between a.fechaHasta and a.fechaPlazo")
    ParObligacionCalendario listarPlanillaAguiPorFechaHastaFechaPlazo(@Param("fechaActual") Date fechaActual);

    @Query(
            "   select a "
                    + " from ParObligacionCalendario a"
                    + " where "
                    + " a.codObligacion.codObligacion like 'PLAAGU' "
                    + " and :fechaActual between a.fechaHasta and a.fechaPlazo2")
    ParObligacionCalendario listarPlanillaAguiPorFechaHastaFechaPlazo2(@Param("fechaActual") Date fechaActual);

    @Query(
            "   select a "
                    + " from ParObligacionCalendario a"
                    + " where "
                    + " a.codObligacion.codObligacion like 'PLATRI' "
                    + " and :fechaActual between a.fechaHasta and a.fechaPlazo2")
    ParObligacionCalendario buscarPorPeriodoAcutal(@Param("fechaActual") Date fechaActual);
}