package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParEntidadEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 10/8/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IEntidadService {

    public List<ParEntidadEntity> getEntidadLista();
}
