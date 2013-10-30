package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.api.IUsuarioService;
import bo.gob.mintrabajo.ovt.entities.*;
import bo.gob.mintrabajo.ovt.repositories.PersonaRepository;
import bo.gob.mintrabajo.ovt.repositories.UnidadRepository;
import bo.gob.mintrabajo.ovt.repositories.UsuarioRepository;
import bo.gob.mintrabajo.ovt.repositories.UsuarioUnidadRepository;
import com.google.common.base.Strings;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

@Named("personaService")
//@TransactionAttribute
public class PersonaService implements IPersonaService {

    private final PersonaRepository personaRepository;
    private final UnidadRepository unidadRepository;
    //private final UnidadService unidadService;
    private final UsuarioRepository usuarioRepository;
    //private final IUsuarioService usuarioService;
    private final UsuarioUnidadRepository usuarioUnidadRepository;


    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public PersonaService(PersonaRepository personaRepository,UnidadRepository unidadRepository,UsuarioRepository usuarioRepository,UsuarioUnidadRepository usuarioUnidadRepository) {
    //public PersonaService(PersonaRepository personaRepository,UnidadRepository unidadRepository,UnidadService unidadService,UsuarioRepository usuarioRepository,IUsuarioService usuarioService,UsuarioUnidadRepository usuarioUnidadRepository) {
        this.personaRepository = personaRepository;
        this.unidadRepository=unidadRepository;
        //this.unidadService=unidadService;
        this.usuarioRepository=usuarioRepository;
       // this.usuarioService=usuarioService;
        this.usuarioUnidadRepository=usuarioUnidadRepository;
    }

//    @Override
    public List<PerPersona> getAllPersonas() {
        List<PerPersona> allPersonas;
        allPersonas = personaRepository.findAll();
        return allPersonas;
    }

    @Override
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
      public  List<PerPersona> findAll(){
          return personaRepository.findAll();
      }

    @Override
    @TransactionAttribute
    public List<PerPersona> buscarPorNroNombre(final String nroIdentificacion, final String nombreRazonSocial) {
   //public Map<String,PerPersona> buscarPorNroNombre(final String nroIdentificacion, final String nombreRazonSocial) {
        if (Strings.isNullOrEmpty(nroIdentificacion) && Strings.isNullOrEmpty(nombreRazonSocial)) {
            //return Collections.emptyList();
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PerPersona> criteriaQuery = criteriaBuilder.createQuery(PerPersona.class);
        Root<PerPersona> from = criteriaQuery.from(PerPersona.class);

/*        Specification<PerPersona> specification = new Specification<PerPersona>() {
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

        criteriaQuery.where(specification.toPredicate(from, criteriaQuery, criteriaBuilder));*/
        List<PerPersona>lista=new ArrayList<PerPersona>();
        lista  =entityManager.createQuery(criteriaQuery).getResultList();
        System.out.println("=============================>>>>>");
        System.out.println("=============================>>>>>");
        System.out.println("=============================>>>>> lista "+String.valueOf(lista.size()));
        System.out.println("=============================>>>>>NOMBRE: "+lista.get(0).getNombreRazonSocial()+"PATERNO: "+lista.get(0).getApellidoPaterno()+"MATERNO "+lista.get(0).getApellidoMaterno());
        Map<String,PerPersona>mapa=new HashMap<String, PerPersona>();
/*       for (PerPersona p:lista){
           mapa.put(p.getIdPersona(),p);
       }
          return mapa;*/
        return lista;

        // TODO: esto deberia funcionar... return personaRepository.findAll(specification);
    }


    @Override
    public Long obtenerSecuencia(String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        return rtn.longValue();
    }


    public  boolean registrar(PerPersona persona,PerUnidad unidad,UsrUsuario usuario){
        try{
            final String  REGISTRO_BITACORA="ROE";
            System.out.println("======>>> GUARDADO PERSONA <<<===== ");
            personaRepository.save(persona);
            System.out.println("======>>> GUARDADO PERSONA  OK<<<===== ");

            System.out.println("======================================= ");
            System.out.println("======>>> GUARDADO UNIDAD <<<===== ");
            unidadRepository.save(unidad);
            System.out.println("======>>> GUARDADO UNIDAD OK <<<===== ");

            System.out.println("======================================= ");
            System.out.println("======>>> GUARDADO USUARIO <<<===== ");
            usuarioRepository.save(usuario);
            System.out.println("======>>> GUARDADO USUARIO OK <<<===== ");

            System.out.println("======================================= ");
            System.out.println("======>>> GUARDADO USUARIO_UNIDAD <<<===== ");
            PerUsuarioUnidad usuarioUnidad =new PerUsuarioUnidad();
                PerUsuarioUnidadPK perUsuarioUnidadPK=new PerUsuarioUnidadPK();
                perUsuarioUnidadPK.setIdPersona(persona.getIdPersona());
                perUsuarioUnidadPK.setIdUnidad(unidad.getPerUnidadPK().getIdUnidad());
                perUsuarioUnidadPK.setIdUsuario(usuario.getIdUsuario());

                usuarioUnidad.setFechaBitacora(new Date());
                usuarioUnidad.setRegistroBitacora(REGISTRO_BITACORA);
                usuarioUnidad.setPerUnidad(unidad);
                usuarioUnidad.setUsrUsuario(usuario);
                usuarioUnidad.setPerUsuarioUnidadPK(perUsuarioUnidadPK);
                usuarioUnidadRepository.save(usuarioUnidad);
            System.out.println("======>>> GUARDADO USUARIO_UNIDAD OK <<<===== ");
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }


    }
}
