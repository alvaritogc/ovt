package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocTransicionEntity;

import java.util.List;

/**
 * User: rvelasquez
 * Date: 10/10/13
 */
public interface ITransicionService {
    List<DocTransicionEntity> listarTransicionesSiguientes(DocTransicionEntity trans);
}
