package bo.gob.mintrabajo.ovt.api;


import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRecurso;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRecursoPK;

public interface IUsuarioRecursoService {
    public UsrUsuarioRecurso save(UsrUsuarioRecurso usuarioRecurso, Long idUsuario);
    public UsrUsuarioRecurso editar(UsrUsuarioRecurso usrUsuarioRecurso, UsrUsuarioRecursoPK usrUsuarioRecursoPK);
    public UsrUsuarioRecurso buscarUnUsuarioRecurso(UsrUsuarioRecursoPK usrUsuarioRecursoPK);
    public boolean eliminarUsuarioRecurso(UsrUsuarioRecursoPK usrUsuarioRecursoPK);
}
