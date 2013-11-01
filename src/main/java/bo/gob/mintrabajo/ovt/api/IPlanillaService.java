package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.DocPlanilla;

public interface IPlanillaService {
    DocPlanilla findById(Long id);
}
