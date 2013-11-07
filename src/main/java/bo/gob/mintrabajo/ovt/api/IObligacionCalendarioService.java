package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParObligacion;
import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendario;
import java.util.List;

public interface IObligacionCalendarioService {
    public List<ParObligacionCalendario> listaObligacionCalendario();
    public ParObligacionCalendario saveObligacionCalendario(ParObligacionCalendario obligacionCalendario);
    public boolean deleteObligacionCalendario(ParObligacionCalendario obligacionCalendario);
    public List<ParObligacionCalendario> listaObligacionCalendarioPorObligacion(String codObligacion);
    public List<ParObligacionCalendario> listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion();
}
