package bo.gob.mintrabajo.ovt.api;


import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionPK;
import java.util.List;

public interface IDefinicionService {
    DocDefinicion guardarDefincion(DocDefinicion docDefinicion);
    boolean eliminarDefinicion(DocDefinicion docDefinicion);
    List<DocDefinicion> listaPorOrdenDocDefinicion();
    List<DocDefinicion> listaCodDocumento(String cod, Short ver);
    DocDefinicion buscaPorId (DocDefinicionPK docDefinicionPK);
    List<DocDefinicion> listarDefiniciones();
    List<DocDefinicion> listaVersionesPorCodDocumento(String codDocumento);
    DocDefinicion obtenerDefinicion(String codigo, short vesion);
    public DocDefinicion buscarActivoPorParametro(String parametroDocDefinicion);
    public DocDefinicion buscarActivoPorCodDocumento(String codDocumento);
}
