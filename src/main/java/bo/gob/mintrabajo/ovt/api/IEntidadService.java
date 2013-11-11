package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParEntidad;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import java.util.List;

public interface IEntidadService {
    public List<ParEntidad> listaEntidad();
    public List<ParEntidad> listaEntidadPorOrden();
//    public ParEntidad saveEntidad(ParEntidad entidad);
    public ParEntidad saveEntidad(ParEntidad entidad,String REGISTRO_BITACORA,PerUnidad unidad, boolean evento);
    public boolean deleteEntidad(ParEntidad entidad);
    public ParEntidad buscaPorId(Long idEntidad);

}
