/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IRolService;
import bo.gob.mintrabajo.ovt.entities.UsrModulo;
import bo.gob.mintrabajo.ovt.entities.UsrRol;
import bo.gob.mintrabajo.ovt.repositories.ModuloRepository;
import bo.gob.mintrabajo.ovt.repositories.RolRepository;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.sql.SQLException;
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

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }
    
    @Override
    public List<UsrRol> getAllRoles() {

        try {
            return rolRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public UsrRol save(UsrRol rol, short esInterno) {
        if(rol.getIdRol() == null){
            Long tmp_id = obtenerSecuenciaId("USR_ROL_SEC");
            rol.setIdRol(tmp_id);
            rol.setRegistroBitacora("ROE");
            rol.setFechaBitacora(new Date());
        }
        rol.setEsInterno(esInterno);
        return rolRepository.save(rol);
    }

    @Override
    public boolean delete(UsrRol rol) {
        boolean deleted;
        try {
            UsrRol usrRolTmp = rolRepository.findOne(rol.getIdRol());
            rolRepository.delete(usrRolTmp);
            rolRepository.flush();
            deleted = true;
        } catch (Exception e) {
            deleted = false;
        }
        return deleted;
    }

//    @Override
    public UsrRol findById(Long id) {
        UsrRol usrRolEntity;
        usrRolEntity = rolRepository.findOne(id);
        return usrRolEntity;
    }

    public Long obtenerSecuenciaId(String nombreSecuencia){
        BigDecimal tmp = (BigDecimal) entityManager.createNativeQuery("SELECT " + nombreSecuencia + ".nextval FROM DUAL").getSingleResult();
        Long IdLong = tmp.longValue();
        return IdLong;
    }
    
}
