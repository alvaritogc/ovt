package bo.gob.mintrabajo.ovt.api;


import bo.gob.mintrabajo.ovt.entities.UsrModulo;
import bo.gob.mintrabajo.ovt.entities.UsrRol;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/4/13
 * Time: 5:13 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IRolService {
    public List<UsrRol> getAllRoles();
    public UsrRol save(UsrRol rol, short esInterno);
    public boolean delete(UsrRol rol);
}
