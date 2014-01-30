package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.DocDocumento;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

@OpenJpaSettings
public interface DocumentoRepository extends OpenJpaRepository<DocDocumento, Long> {

    List<DocDocumento> findByPerUnidad_PerPersona_IdPersonaOrderByIdDocumentoDesc(String idPersona);

    //    List<DocDocumento> findByIdPersona_IdPersona(String idPersona);
    @Query(
            "   select d "
                    + " from DocDocumento d"
//            + " join fetch d.perUnidad"
//            + " join fetch d.codEstado"
//            + " join fetch d.idDocumentoRef"
                    + " join fetch d.docDefinicion"
                    + " where "
                    + " d.perUnidad.perPersona.idPersona=:idPersona"
                    + " order by d.idDocumento desc"
    )
    List<DocDocumento> listarPorPersona(@Param("idPersona") String idPersona);


    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.perUnidad.perPersona.idPersona=:idEmpleador "
                    + " and d.docDefinicion.docDefinicionPK.codDocumento = :codDocumento "
    )
    List<DocDocumento> listarPorEmpleadorAndCodDocumento(@Param("idEmpleador") String idPersona, @Param("codDocumento") String codDocumento);


    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.perUnidad.perUnidadPK.idUnidad=:idUnidad"
                    + " and d.docDefinicion.docDefinicionPK.codDocumento='ROE013'"
                    + " and d.codEstado.codEstado = '010'"
    )
    List<DocDocumento> listarRoe011(@Param("idEmpleador") String idPersona, @Param("idUnidad") long idUnidad);


    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.perUnidad.perUnidadPK.idPersona=:idPersona "
                    + " and d.perUnidad.perUnidadPK.idUnidad=:idUnidad"

    )
    List<DocDocumento> ObtenerRoes(@Param("idPersona") String idPersona, @Param("idUnidad") long idUnidad);

    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.fechaDocumento between :fechaDesde and :fechaHasta"
                    + " and d.docDefinicion.docDefinicionPK.codDocumento = :codDocumento"
                    + " and d.perUnidad.perUnidadPK.idPersona = :idEmpleador  "
    )
    List<DocDocumento> listarPlanillaALaFecha(@Param("idEmpleador") String idEmpleador, @Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta, @Param("codDocumento") String codDocumento);

    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.codEstado.codEstado like '110'"
    )
    List<DocDocumento> listarDeclarados(@Param("idEmpleador") String idPersona);

    List<DocDocumento> findByPerUnidad_PerPersona_IdPersonaAndCodEstado_CodEstado(String idPersona, String codEstado);


    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.docDefinicion.docDefinicionPK.codDocumento = 'ROE010'"
                    + " and d.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.perUnidad.perUnidadPK.idUnidad=:idUnidad "
                    + " and d.codEstado.codEstado <> :codigoBaja "

    )
    DocDocumento buscarRoe010PorUnidad(@Param("idEmpleador") String idPersona, @Param("idUnidad") long idUnidad, @Param("codigoBaja") String codigoBaja);

    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.codEstado.codEstado like '110' and d.docDefinicion.docDefinicionPK.codDocumento = :codDocumento"

    )
    List<DocDocumento> listarDocumentosParaRectificar(@Param("idEmpleador") String idPersona, @Param("codDocumento") String codDocumento);


    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.codEstado.codEstado = :codEstado"
                    + " and d.docDefinicion.docDefinicionPK.codDocumento = :codDocumento"
    )
    List<DocDocumento> listarPorDocDefinicionYCodEstado(@Param("idEmpleador") String idPersona, @Param("codDocumento") String codDocumento, @Param("codEstado") String codEstado);

    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.perUnidad.perUnidadPK.idUnidad=:idUnidad "
                    + " and (d.docDefinicion.docDefinicionPK.codDocumento like 'LC1010' "
                    + " or d.docDefinicion.docDefinicionPK.codDocumento like 'LC1011')"
    )
    List<DocDocumento> listarDocumentosTrimSm(@Param("idEmpleador") String idPersona, @Param("idUnidad") Long idUnidad);

    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.perUnidad.perUnidadPK.idUnidad=:idUnidad "
                    + " and d.fechaDocumento between :fechaDesde and :fechaHasta"
                    + " and (upper( d.docDefinicion.docDefinicionPK.codDocumento) like 'LC1010' "
                    + " or upper( d.docDefinicion.docDefinicionPK.codDocumento) like 'LC1011')"
    )
    List<DocDocumento> listarDocumentosTrimSmPorUnidad(@Param("idEmpleador") String idPersona, @Param("idUnidad") Long idUnidad, @Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta);


    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.fechaDocumento between :fechaDesde and :fechaHasta"
                    + " and upper( d.docDefinicion.docDefinicionPK.codDocumento) = :codDocumento"
    )
    List<DocDocumento> listarDocumentosPorpersonaUnidadFechasCodDocumento(@Param("idEmpleador") String idPersona, @Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta, @Param("codDocumento") String codDocumento);

    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.perUnidad.perUnidadPK.idUnidad=:idUnidad "
                    + " and d.docDefinicion.docDefinicionPK.codDocumento like 'LC1020'"

    )
    DocDocumento buscarPorUnindad(@Param("idEmpleador") String idPersona, @Param("idUnidad") Long idUnidad);

    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.fechaDocumento between :fechaDesde and :fechaHasta"
                    + " and (upper( d.docDefinicion.docDefinicionPK.codDocumento) = :codDocumento1 or upper( d.docDefinicion.docDefinicionPK.codDocumento) = :codDocumento2)"
    )
    List<DocDocumento> listarDocumentosPorpersonaUnidadFechasCodDocumentos(@Param("idEmpleador") String idPersona, @Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta, @Param("codDocumento1") String codDocumento1, @Param("codDocumento2") String codDocumento2);

    @Query(
            "   select d "
                    + " from DocDocumento d"
                    + " where "
                    + " d.perUnidad.perUnidadPK.idPersona=:idEmpleador "
                    + " and d.perUnidad.perUnidadPK.idUnidad=:idUnidad "
                    + " and d.fechaDocumento between :fechaHasta and :fechaPlazo2"
                    + " and upper( d.docDefinicion.docDefinicionPK.codDocumento) =:codDocumento"
    )
    List<DocDocumento> listarDocumentosPorUniCodFecha(@Param("idEmpleador") String idPersona, @Param("idUnidad") Long idUnidad, @Param("codDocumento") String codDocumento, @Param("fechaHasta") Date fechaHasta, @Param("fechaPlazo2") Date fechaPlazo2);
}