package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocBinarioEntity;
import bo.gob.mintrabajo.ovt.entities.DocDocumentoEntity;
import bo.gob.mintrabajo.ovt.entities.DocPlanillaEntity;
import bo.gob.mintrabajo.ovt.entities.PerUnidadEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: renato
 * Date: 03-10-13
 */
public interface IDocumentoService {
    
    public List<DocDocumentoEntity> getAllDocumentos();
    public DocDocumentoEntity save(DocDocumentoEntity documento);
    public boolean delete(DocDocumentoEntity documento);
    public DocDocumentoEntity findById(BigDecimal id);
    public DocDocumentoEntity guardar(DocDocumentoEntity documento);
    public List<DocDocumentoEntity> listarPorPersona(String idPersona);
    public DocPlanillaEntity retornaPlanilla(Long idDocumento);
    public PerUnidadEntity retornaUnidad(String idPersona);
    public Long actualizarNumeroDeOrden(String codDocumento, Integer version);
    public void guardaDocumentoBinarioPlanilla(DocDocumentoEntity docDocumentoEntity, List<DocBinarioEntity> listaBinarios, DocPlanillaEntity docPlanillaEntity);
    public void guardaDocumentoPlanilla(DocDocumentoEntity docDocumentoEntity, DocPlanillaEntity docPlanillaEntity);
    DocDocumentoEntity guardarCambioEstado(DocDocumentoEntity documento, String codEstadoNuevo,String idUsuario);
    List<DocDocumentoEntity> listarPorNumero(String idPersona);
}