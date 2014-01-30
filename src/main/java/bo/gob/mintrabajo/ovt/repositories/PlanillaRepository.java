package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocPlanilla;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

@OpenJpaSettings
public interface PlanillaRepository extends OpenJpaRepository<DocPlanilla, Long> {
    @Query(
            "   select p "
            + " from DocPlanilla p "
            + " where "
            + " p.idDocumento.idDocumento=:idDocumento"
            )
    List<DocPlanilla> buscarPorDocumento(@Param("idDocumento") Long idDocumento);

    List<DocPlanilla> findByIdDocumento_PerUnidad_PerPersona_IdPersona(String idPersona);

    DocPlanilla findByIdDocumento_IdDocumento(Long idDocumento);

    @Query(
            "   select d "
                    + " from DocPlanilla d"
                    + " where "
                    + " d.idDocumento.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.idDocumento.codEstado.codEstado like '110' and d.idDocumento.docDefinicion.docDefinicionPK.codDocumento = :codDocumento"
    )
    List<DocPlanilla> listarPlanillasParaRectificar(@Param("idEmpleador") String idPersona, @Param("codDocumento") String codDocumento);

    @Query(
            "   select d "
                    + " from DocPlanilla d"
                    + " where "
                    + " d.idDocumento.fechaDocumento between :fechaHasta and :fechaPlazo2"
                    + " and d.idDocumento.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.idDocumento.perUnidad.perUnidadPK.idUnidad=:idUnidad "
                    + " and d.idDocumento.codEstado.codEstado like '110'"
                    + " and d.idDocumento.docDefinicion.docDefinicionPK.codDocumento like 'LC1010'"
    )
    List<DocPlanilla> listarPlanillasTrimestralesParaRectificar(@Param("idEmpleador") String idPersona, @Param("idUnidad") Long idUnidad, @Param("fechaHasta") Date fechaHasta, @Param("fechaPlazo2") Date fechaPlazo2);
}
