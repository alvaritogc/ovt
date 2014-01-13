package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocDefinicion;
import bo.gob.mintrabajo.ovt.entities.DocTransicion;
import bo.gob.mintrabajo.ovt.entities.DocTransicionPK;
import bo.gob.mintrabajo.ovt.entities.ParDocumentoEstado;
import java.util.List;

public interface ITransicionService {
    public List<DocTransicion> listaTransicion();
    public List<DocTransicion> listaTransicionPorDocumento(String codDocumento);
    public boolean saveTransicion(
            DocTransicion transicion,
            boolean estadoTransicion,
            DocDefinicion definicion,
            ParDocumentoEstado parDocumentoEstado1,
            ParDocumentoEstado parDocumentoEstado2,
            String REGISTRO_BITACORA,             
            boolean evento,DocTransicionPK docTransicionPK, Long idRol);
    public boolean deleteTransicion(DocTransicion transicion);
    public DocTransicion guardar(DocTransicion docTransicion);
}
