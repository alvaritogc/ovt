package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocLogImpresion;
import bo.gob.mintrabajo.ovt.entities.VdocLogImpresion;

import java.util.Date;
import java.util.List;

public interface ILogImpresionService {
    DocLogImpresion guarda(DocLogImpresion docLogImpresion);
    public List<VdocLogImpresion> filtrarLogImpresion(String nroIdentificacion, String codDocumento, Date fechaInicio, Date fechaFinal);
}
