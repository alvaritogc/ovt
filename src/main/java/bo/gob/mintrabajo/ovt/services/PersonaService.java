package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
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

@Named("personaService")
@TransactionAttribute
public class PersonaService implements IPersonaService {

    private final PersonaRepository personaRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    public PersonaService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

//    @Override
    public List<PerPersona> getAllPersonas() {
        List<PerPersona> allPersonas;
        allPersonas = personaRepository.findAll();
        return allPersonas;
    }

////    @Override
    public PerPersona save(PerPersona persona) {
        PerPersona perPersonaEntity;
        perPersonaEntity = personaRepository.save(persona);
        return perPersonaEntity;
    }

//    @Override
    public boolean delete(PerPersona persona) {
        boolean deleted = false;
        personaRepository.delete(persona);
        return deleted;
    }

    @Override
    public PerPersona findById(String id) {
        PerPersona perPersonaEntity;
        perPersonaEntity = personaRepository.findOne(id);
        return perPersonaEntity;
    }

    public PerPersona buscarPorId(String id) {
        PerPersona perPersonaEntity;
        perPersonaEntity = personaRepository.findByAttribute("idPersona", id, -1, -1).get(0);
        return perPersonaEntity;
    }

    

    @Override
    public List<PerPersona> buscarPorNroNombre(final String nroIdentificacion, final String nombreRazonSocial) {
        if (Strings.isNullOrEmpty(nroIdentificacion) && Strings.isNullOrEmpty(nombreRazonSocial)) {
            return Collections.emptyList();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PerPersona> criteriaQuery = criteriaBuilder.createQuery(PerPersona.class);
        Root<PerPersona> from = criteriaQuery.from(PerPersona.class);

        Specification<PerPersona> specification = new Specification<PerPersona>() {
            @Override
            public Predicate toPredicate(Root<PerPersona> perPersonaEntityRoot, CriteriaQuery<?> criteriaQuery,
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
