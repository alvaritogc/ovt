
package bo.gob.mintrabajo.ovt.services;


import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.entities.PerPersonaEntity;
import bo.gob.mintrabajo.ovt.repositories.PersonaRepository;
import com.google.common.base.Strings;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

/**
 * User: gveramendi
 * Date: 9/23/13
 * Time: 6:38 PM
 */

@Named("personaService")
@TransactionAttribute
public class PersonaService implements IPersonaService{
    private final PersonaRepository personaRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

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
    
    @Override
    public PerPersonaEntity buscarPorId(String id) {
        PerPersonaEntity perPersonaEntity;
        
        try {
            perPersonaEntity = personaRepository.findByAttribute("idPersona", id, -1, -1).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            perPersonaEntity = null;
        }

        return perPersonaEntity;
    }
    
    //@Override
    public List<PerPersonaEntity> buscarPorNroNombre1(String nroIdentificacion, String nombreRazonSocial) {
        List<PerPersonaEntity> allPersonas;

        PerPersonaEntity persona=new PerPersonaEntity();
        persona.setNroIdentificacion(nroIdentificacion);
        persona.setNombreRazonSocial(nombreRazonSocial);
        try {
            //allPersonas = personaRepository.buscarPorNumeroNombre(nroIdentificacion, nombreRazonSocial);
            //allPersonas = personaRepository.findByAttribute("nombreRazonSocial", nombreRazonSocial, -1, -1);
            //allPersonas = personaRepository.findByExample(persona, null, null, -1, -1);
            //allPersonas=personaRepository.findByFullTexts(nombreRazonSocial, null, -1,-1);
            allPersonas = personaRepository.buscarPorNumeroNombre(nroIdentificacion, nombreRazonSocial);
        } catch (Exception e) {
            e.printStackTrace();
            allPersonas = null;
        }
        return allPersonas;
    }
    
     public List<PerPersonaEntity> buscarPorNroNombre(final String nroIdentificacion, final String nombreRazonSocial) {
        if (Strings.isNullOrEmpty(nroIdentificacion) && Strings.isNullOrEmpty(nombreRazonSocial)) {
            return Collections.emptyList();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PerPersonaEntity> criteriaQuery = criteriaBuilder.createQuery(PerPersonaEntity.class);
        Root<PerPersonaEntity> from = criteriaQuery.from(PerPersonaEntity.class);

        Specification<PerPersonaEntity> specification = new Specification<PerPersonaEntity>() {

            @Override
            public Predicate toPredicate(Root<PerPersonaEntity> perPersonaEntityRoot, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {


                List<Predicate> pr = new LinkedList<Predicate>();

                if (!Strings.isNullOrEmpty(nroIdentificacion)) {
                    pr.add(criteriaBuilder.equal(perPersonaEntityRoot.get("nroIdentificacion"), nroIdentificacion));
                    // PUEDE SER: return criteriaBuilder.equal(perPersonaEntityRoot.get("nroIdentificacion"), nroIdentificacion);
                }

                if (!Strings.isNullOrEmpty(nombreRazonSocial)) {
                    pr.add(criteriaBuilder.like(criteriaBuilder.lower(perPersonaEntityRoot.<String>get("nombreRazonSocial")),
                            "%" + nombreRazonSocial.toLowerCase() + "%"));
// TAMBIEN PUEDE SER:
//                    return criteriaBuilder.like(criteriaBuilder.lower(perPersonaEntityRoot.<String>get("nombreRazonSocial")),
//                            "%" + nombreRazonSocial.toLowerCase() + "%");
                }

                return criteriaBuilder.and(pr.toArray(new Predicate[pr.size()])); // O puede ser or()
            }
        };

        criteriaQuery.where(specification.toPredicate(from, criteriaQuery, criteriaBuilder));
        return entityManager.createQuery(criteriaQuery).getResultList();

        // TODO: esto deberia funcionar... return personaRepository.findAll(specification);
    }
    
}
