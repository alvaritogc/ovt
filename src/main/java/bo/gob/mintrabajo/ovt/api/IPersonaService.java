package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.PerPersona;

public interface IPersonaService {
    public PerPersona findById(String id);
}
