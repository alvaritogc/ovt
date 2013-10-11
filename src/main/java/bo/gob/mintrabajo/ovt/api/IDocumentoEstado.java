package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstadoEntity;

/**
 * Created with IntelliJ IDEA.
 * User: rvelasquez
 * Date: 10/10/13
 * Time: 5:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDocumentoEstado {
    ParDocumentoEstadoEntity retornaDocEstado(String codEstado);

    ParDocumentoEstadoEntity retornaCodEstado(String Descripcion);

}
