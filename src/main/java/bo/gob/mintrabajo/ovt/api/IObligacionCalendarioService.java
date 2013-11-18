package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParObligacion;
import bo.gob.mintrabajo.ovt.entities.ParObligacionCalendario;

import java.util.List;

public interface IObligacionCalendarioService {
    public List<ParObligacionCalendario> listaObligacionCalendario();
    //public ParObligacionCalendario saveObligacionCalendario(ParObligacionCalendario obligacionCalendario);
    public ParObligacionCalendario saveObligacionCalendario(ParObligacionCalendario obligacionCalendario, 
            String gestion, String periodo,String REGISTRO_BITACORA, ParObligacion parObligacion, boolean evento);
    public boolean deleteObligacionCalendario(ParObligacionCalendario obligacionCalendario);
    public List<ParObligacionCalendario> listaObligacionCalendarioPorObligacion(String codObligacion);
    public List<ParObligacionCalendario> listaObligacionCalendarioOrdenadoPorDescripcionDeObligacion();
    List<ParObligacionCalendario> listaObligacionCalendarioPorGestion(String gestionActual);
}
