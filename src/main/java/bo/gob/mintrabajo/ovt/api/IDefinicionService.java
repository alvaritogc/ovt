package bo.gob.mintrabajo.ovt.api;


import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocDefinicionPK;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import java.util.List;

public interface IDefinicionService {
    DocDefinicion guardarDefincion(DocDefinicion docDefinicion);
    public boolean saveDefincion(DocDefinicion docDefinicion, String codDocumento, short version, String documentoEstado,
            String REGISTRO_BITACORA, boolean docEstado, String tipoGrupoDocumento);
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
