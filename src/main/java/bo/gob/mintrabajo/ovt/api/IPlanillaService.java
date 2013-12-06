package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocPlanilla;

import java.util.List;

public interface IPlanillaService {
    DocPlanilla findById(Long id);
    DocPlanilla buscarPorDocumento(Long idDocumento);
    List<DocPlanilla> listarporPersona(String idPersona);
    List<DocPlanilla> listarPlanillasParaRectificar(String idPersona, String codDocumento);
}
