package bo.gob.mintrabajo.ovt.api;


import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionPK;
import java.util.List;

public interface IDefinicionService {
    DocDefinicion buscaPorId (DocDefinicionPK docDefinicionPK);
    public List<DocDefinicion> getAllDefinicion();
    public List<DocDefinicion> listaVersionesPorCodDocumento(String codDocumento);
    public DocDefinicion obtenerDefinicion(String codigo, short vesion);
  
}
