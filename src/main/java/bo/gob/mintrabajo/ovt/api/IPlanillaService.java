package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.*;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/8/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPlanillaService {
    void guardar(DocPlanillaEntity objeto);

    void generaReporte(DocPlanillaEntity docPlanillaEntity, PerPersonaEntity persona , DocDocumentoEntity docDocumentoEntity, PerUnidadEntity perUnidadEntity, VperPersonaEntity vperPersonaEntity);
    
    DocPlanillaEntity obtenerPorDocumento(Long idDocumento);
}
