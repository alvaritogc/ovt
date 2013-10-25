/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IRolService;
import bo.gob.mintrabajo.ovt.entities.UsrRol;
import bo.gob.mintrabajo.ovt.repositories.RolRepository;
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
@Named("rolService")
@TransactionAttribute
public class RolService implements IRolService{
    private final RolRepository rolRepository;

    @Inject
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }
    
//    @Override
    public List<UsrRol> getAllRoles() {
        List<UsrRol> allRoles;

        try {
            allRoles = rolRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            allRoles = null;
        }
        return allRoles;
    }
    
//    @Override
    public UsrRol save(UsrRol rol) {
        UsrRol usrRolEntity;
        usrRolEntity = rolRepository.save(rol);
        return usrRolEntity;
    }

//    @Override
    public boolean delete(UsrRol rol) {
        boolean deleted = false;
        rolRepository.delete(rol);
        return deleted;
    }

//    @Override
    public UsrRol findById(Long id) {
        UsrRol usrRolEntity;
        usrRolEntity = rolRepository.findOne(id);
        return usrRolEntity;
    }
    
}
