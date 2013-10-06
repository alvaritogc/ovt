package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocDefinicionEntity;
import java.math.BigDecimal;
import java.util.List;

public interface IDefinicionService {
    DocDefinicionEntity guardarDefincion(DocDefinicionEntity docDefinicionEntity);

    long getSize();
    
    public List<DocDefinicionEntity> getAllDefinicion();
    public DocDefinicionEntity save(DocDefinicionEntity definicion);
    public boolean delete(DocDefinicionEntity definicion);
    public DocDefinicionEntity findById(BigDecimal id);
}
