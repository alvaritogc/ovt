package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;

public interface Users {

    void saveUser(UsrUsuarioEntity user);

    UsrUsuarioEntity findByUsuarioAndClave(String usuario, String clave);
}
