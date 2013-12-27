package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrModulo;
import java.util.List;

public interface IModuloService {
    public UsrModulo findById(String id);
    public List<UsrModulo> getAllModulos();

}
