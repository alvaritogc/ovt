/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IRolService;
import bo.gob.mintrabajo.ovt.entities.UsrRolEntity;
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
@Named
@TransactionAttribute
public class RolService implements IRolService{
    private final RolRepository rolRepository;

    @Inject
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }
    
    @Override
    public List<UsrRolEntity> getAllRoles() {
        List<UsrRolEntity> allRoles;

        try {
            allRoles = rolRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            allRoles = null;
        }
        return allRoles;
    }
    
    @Override
    public UsrRolEntity save(UsrRolEntity rol) {
        UsrRolEntity usrRolEntity;

        try {
            usrRolEntity = rolRepository.save(rol);
        } catch (Exception e) {
            e.printStackTrace();
            usrRolEntity = null;
        }

        return usrRolEntity;
    }

    @Override
    public boolean delete(UsrRolEntity rol) {
        boolean deleted = false;

        try {
            rolRepository.delete(rol);
            deleted = true;
        } catch (Exception e) {
            deleted=false;
            e.printStackTrace();
        }
        return deleted;
    }

    @Override
    public UsrRolEntity findById(BigDecimal id) {
        UsrRolEntity usrRolEntity;

        try {
            usrRolEntity = rolRepository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            usrRolEntity = null;
        }

        return usrRolEntity;
    }
    
}
