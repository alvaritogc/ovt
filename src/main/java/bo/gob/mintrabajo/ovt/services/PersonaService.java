package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.entities.*;
import bo.gob.mintrabajo.ovt.repositories.*;
import com.google.common.base.Strings;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Named("personaService")
@TransactionAttribute
public class PersonaService implements IPersonaService {

    private final PersonaRepository personaRepository;
    private final UnidadRepository unidadRepository;
    //private final UnidadService unidadService;
    private final UsuarioRepository usuarioRepository;
    //private final IUsuarioService usuarioService;
    private final UsuarioUnidadRepository usuarioUnidadRepository;
    private final RolRepository rolRepository;
    private final ModuloRepository moduloRepository;
    private final UsuarioRolRepository usuarioRolRepository;


    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public PersonaService(PersonaRepository personaRepository,UnidadRepository unidadRepository,UsuarioRepository usuarioRepository,UsuarioUnidadRepository usuarioUnidadRepository,RolRepository rolRepository,ModuloRepository moduloRepository,UsuarioRolRepository usuarioRolRepository) {
    //public PersonaService(PersonaRepository personaRepository,UnidadRepository unidadRepository,UnidadService unidadService,UsuarioRepository usuarioRepository,IUsuarioService usuarioService,UsuarioUnidadRepository usuarioUnidadRepository) {
        this.personaRepository = personaRepository;
        this.unidadRepository=unidadRepository;
        //this.unidadService=unidadService;
        this.usuarioRepository=usuarioRepository;
       // this.usuarioService=usuarioService;
        this.usuarioUnidadRepository=usuarioUnidadRepository;
        this.rolRepository=rolRepository;
        this.moduloRepository=moduloRepository;
        this.usuarioRolRepository=usuarioRolRepository;
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

        Sort sort=new Sort(Sort.Direction.DESC,"idPersona","nombreRazonSocial");
        return personaRepository.findAll(new PageRequest(0,200,sort)).getContent();
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


    @Override
    public Long obtenerSecuencia(String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        return rtn.longValue();
    }

    @Override
    public  boolean registrar(PerPersona persona,PerUnidad unidad,UsrUsuario usuario){
        try{
            final String  REGISTRO_BITACORA="ROE";
            System.out.println("======>>> GUARDADO PERSONA <<<===== ");
            persona.setFechaBitacora(new Date());
            //personaRepository.save(persona);
            persona=personaRepository.save(persona);
            System.out.println("======>>> GUARDADO PERSONA  OK<<<===== ");

            System.out.println("======================================= ");
            System.out.println("======>>> GUARDADO UNIDAD <<<===== ");
            unidad.setFechaBitacora(new Date());
            unidad.setPerPersona(persona);
            unidad=unidadRepository.save(unidad);
            System.out.println("======>>> GUARDADO UNIDAD OK <<<===== ");

            System.out.println("======================================= ");
            System.out.println("======>>> GUARDADO USUARIO <<<===== ");
            usuario.setFechaBitacora(new Date());
            usuario.setIdPersona(persona);
            usuario=usuarioRepository.save(usuario);
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
            System.out.println("======================================= ");
            System.out.println("======>>> GUARDADO USUARIO_ROL <<<===== ");

            UsrModulo modulo=moduloRepository.findByIdModulo("PER");
            UsrRol rol=rolRepository.findByIdRol((Long.valueOf("2")));

            UsrUsuarioRol usuarioRol=new UsrUsuarioRol();
            usuarioRol.setFechaBitacora(new Date());
            usuarioRol.setRegistroBitacora(REGISTRO_BITACORA);
            usuarioRol.setIdModulo(modulo);
            usuarioRol.setUsrRol(rol);
            usuarioRol.setUsrUsuario(usuario);
            UsrUsuarioRolPK usrUsuarioRolPK=new UsrUsuarioRolPK();
            usrUsuarioRolPK.setIdRol(rol.getIdRol());
            usrUsuarioRolPK.setIdUsuario(usuario.getIdUsuario());

            usuarioRol.setUsrUsuarioRolPK(usrUsuarioRolPK);

            usuarioRolRepository.save(usuarioRol);
            System.out.println("======>>> GUARDADO USUARIO_ROL OK <<<===== ");
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public void cambiarEstadoUsuario(Long usuario) {
        UsrUsuario usuarioSinConfirmar = usuarioRepository.findOne(usuario);
        usuarioSinConfirmar.setEstadoUsuario("A");
        usuarioRepository.save(usuarioSinConfirmar);
    }

    public boolean eliminarRegistro(String perPersona, UsrUsuario usrUsuario) {
        System.out.println("Eliminando id Persona " + perPersona + " usuario " + usrUsuario.getIdPersona().getIdPersona() + " id Usuario " + usrUsuario.getIdUsuario());

        UsrUsuarioRolPK usrPK = new UsrUsuarioRolPK();
        usrPK.setIdUsuario(usrUsuario.getIdUsuario());
        usrPK.setIdRol(new Long("2"));
        UsrUsuarioRol usrUsuarioRolTmp = usuarioRolRepository.findOne(usrPK);


        usuarioRolRepository.delete(usrUsuarioRolTmp.getUsrUsuarioRolPK());
        usuarioRolRepository.flush();
        System.out.println("Elimina usuarioRol");

        PerUsuarioUnidad pu = usuarioUnidadRepository.unidadPorIdPersona(perPersona);
        usuarioUnidadRepository.delete(pu);
        usuarioUnidadRepository.flush();
        System.out.println("Elimina usuarioUnidad");

        PerUnidad unidadTmp = unidadRepository.unidadPorIdPersona(perPersona);
        unidadRepository.delete(unidadTmp);
        unidadRepository.flush();
        System.out.println("Elimina unidad ");

        UsrUsuario usuarioTmp = usuarioRepository.findOne(usrUsuario.getIdUsuario());
        usuarioRepository.delete(usuarioTmp);
        usuarioRepository.flush();
        System.out.println("Elimina usuario");

        PerPersona personaTmp = personaRepository.findOne(perPersona);
        personaRepository.delete(personaTmp);
        personaRepository.flush();
        System.out.println("Elimina a la persona");
        return true;
    }

    public List<PerPersona> listarPorSucursal(String idPersona){
       return personaRepository.findByIdPersonaAndEsNatural(idPersona, true);
    }

    public PerPersona obtienePorCentral(String idPersona){
        List<PerPersona> personaList=personaRepository.findByIdPersonaAndEsNatural(idPersona, false);
        if(personaList.size()==0)
            return new PerPersona();
        return personaList.get(0);
    }
}
