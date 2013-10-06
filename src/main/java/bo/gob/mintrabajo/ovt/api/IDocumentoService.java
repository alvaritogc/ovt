package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocDocumentoEntity;
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
}