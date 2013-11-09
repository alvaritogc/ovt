package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParEntidad;
import java.util.List;

public interface IEntidadService {
    public List<ParEntidad> listaEntidad();
    public List<ParEntidad> listaEntidadPorOrden();
    public ParEntidad saveEntidad(ParEntidad entidad);
    public boolean deleteEntidad(ParEntidad entidad);
    ParEntidad buscaPorId(Long idEntidad);
    public void guardarEliminar(String sw,ParEntidad entidad);
}
