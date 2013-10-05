
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IUsuarioRolService;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioRolEntity;
import bo.gob.mintrabajo.ovt.repositories.UsuarioRolRepository;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pc01
 */
@Named("usuarioRolService")
@TransactionAttribute
public class UsuarioRolService implements IUsuarioRolService{
    
    private final UsuarioRolRepository usuarioRolRepository;

    @Inject
    public UsuarioRolService(UsuarioRolRepository usuarioRolRepository) {
        this.usuarioRolRepository = usuarioRolRepository;
    }
    
    @Override
    public List<UsrUsuarioRolEntity> getAllUsuarios() {
        List<UsrUsuarioRolEntity> allUsuarios;

        try {
            allUsuarios = usuarioRolRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            allUsuarios = null;
        }
        return allUsuarios;
    }
    
    @Override
    public UsrUsuarioRolEntity save(UsrUsuarioRolEntity usuarioRol) {
        UsrUsuarioRolEntity entity;

        try {
            entity = usuarioRolRepository.save(usuarioRol);
        } catch (Exception e) {
            e.printStackTrace();
            entity = null;
        }

        return entity;
    }

    @Override
    public boolean delete(UsrUsuarioRolEntity usuarioRol) {
        boolean deleted = false;

        try {
            usuarioRolRepository.delete(usuarioRol);
            deleted = true;
        } catch (Exception e) {
            deleted=false;
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public UsrUsuarioRolEntity findById(BigDecimal id) {
        UsrUsuarioRolEntity entity;

        try {
            entity = usuarioRolRepository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            entity = null;
        }

        return entity;
    }
    
}
