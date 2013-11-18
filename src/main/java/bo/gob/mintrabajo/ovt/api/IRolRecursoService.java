package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrRolRecurso;
import bo.gob.mintrabajo.ovt.entities.UsrRolRecursoPK;

public interface IRolRecursoService {
    public UsrRolRecurso tieneRelacionRolRecurso(UsrRolRecursoPK usrRolRecursoPK);
    public UsrRolRecurso guardarRolRecurso(UsrRolRecurso usrRolRecurso, Long idRol, Long idRecurso);
    public boolean eliminarRolRecurso(Long idRol, Long idRecurso);
}
