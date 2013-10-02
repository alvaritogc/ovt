
package bo.gob.mintrabajo.ovt.services;


import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.entities.PerPersonaEntity;
import bo.gob.mintrabajo.ovt.repositories.PersonaRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * User: gveramendi
 * Date: 9/23/13
 * Time: 6:38 PM
 */

@Named
@TransactionAttribute
public class PersonaService implements IPersonaService{
    private final PersonaRepository personaRepository;

    @Inject
    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }
    
    @Override
    public List<PerPersonaEntity> getAllPersonas() {
        List<PerPersonaEntity> allPersonas;

        try {
            allPersonas = personaRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            allPersonas = null;
        }
        return allPersonas;
    }

    @Override
    public PerPersonaEntity save(PerPersonaEntity persona) {
        PerPersonaEntity perPersonaEntity;

        try {
            perPersonaEntity = personaRepository.save(persona);
        } catch (Exception e) {
            e.printStackTrace();
            perPersonaEntity = null;
        }

        return perPersonaEntity;
    }

    @Override
    public boolean delete(PerPersonaEntity persona) {
        boolean deleted = false;

        try {
            personaRepository.delete(persona);
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return deleted;
    }

    @Override
    public PerPersonaEntity findById(BigDecimal id) {
        PerPersonaEntity perPersonaEntity;

        try {
            perPersonaEntity = personaRepository.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            perPersonaEntity = null;
        }

        return perPersonaEntity;
    }
    
}
