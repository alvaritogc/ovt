package bo.gob.mintrabajo.ovt.api;


import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRol;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRolPK;

public interface IUsuarioRolService {
    public boolean tieneRelacionUsuarioRol(UsrUsuarioRolPK llave);
    public UsrUsuarioRol save(UsrUsuarioRol usuarioRol);
    public boolean delete(UsrUsuarioRol usuarioRol);
}
