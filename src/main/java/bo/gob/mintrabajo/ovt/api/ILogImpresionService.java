package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocLogImpresionEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rvelasquez
 * Date: 10/12/13
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ILogImpresionService {

    void guarda(List<DocLogImpresionEntity> docLogImpresionEntity);

}
