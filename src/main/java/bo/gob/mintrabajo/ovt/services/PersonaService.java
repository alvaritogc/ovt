package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IPersonaService;
import bo.gob.mintrabajo.ovt.Util.Dominios;
import bo.gob.mintrabajo.ovt.entities.*;
import bo.gob.mintrabajo.ovt.repositories.*;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.ejb.TransactionAttribute;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.persistence.criteria.Subquery;

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
    private final LocalidadRepository localidadRepository;

    private static final Logger log = LoggerFactory.getLogger(PersonaService.class);

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public PersonaService(PersonaRepository personaRepository, UnidadRepository unidadRepository, UsuarioRepository usuarioRepository, UsuarioUnidadRepository usuarioUnidadRepository, RolRepository rolRepository, ModuloRepository moduloRepository, UsuarioRolRepository usuarioRolRepository, LocalidadRepository localidadRepository) {
        //public PersonaService(PersonaRepository personaRepository,UnidadRepository unidadRepository,UnidadService unidadService,UsuarioRepository usuarioRepository,IUsuarioService usuarioService,UsuarioUnidadRepository usuarioUnidadRepository) {
        this.personaRepository = personaRepository;
        this.unidadRepository = unidadRepository;
        //this.unidadService=unidadService;
        this.usuarioRepository = usuarioRepository;
        // this.usuarioService=usuarioService;
        this.usuarioUnidadRepository = usuarioUnidadRepository;
        this.rolRepository = rolRepository;
        this.moduloRepository = moduloRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.localidadRepository = localidadRepository;
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

    @Override
    public PerPersona findByNroIdentificacion(String nroIdentificacion) {
        try {
            return personaRepository.findByNroIdentificacion(nroIdentificacion);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void editarPersona(PerPersona persona, PerUnidad unidad, String idLocalidad) {
        log.info("Editando el Usuario ...");
        try {
            persona.setCodLocalidad(localidadRepository.findOne(idLocalidad));
            personaRepository.save(persona);
            log.info("Guardando la persona ");

            unidadRepository.save(unidad);
            log.info("Guardada la unidad sin falla ...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PerPersona obtenerPersonaPorUsuario(UsrUsuario usrUsuario) {
        return personaRepository.obtenerPersonaPorIdUsuario(usrUsuario.getIdUsuario());
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

    public PerPersona buscarPorId(String idPersona) {
        return personaRepository.findOne(idPersona);
    }

    @Override
    public List<PerPersona> findAll() {

        Sort sort = new Sort(Sort.Direction.DESC, "idPersona", "nombreRazonSocial");
        return personaRepository.findAll(new PageRequest(0, 200, sort)).getContent();
    }

    @Override
    public List<PerPersona> buscarPorNroNombre(final String nombreRazonSocial, final String tipoIdentificacion, final String nroIdentificacion) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PerPersona> criteriaQuery = criteriaBuilder.createQuery(PerPersona.class);
        Root<PerPersona> from = criteriaQuery.from(PerPersona.class);
        criteriaQuery.orderBy(criteriaBuilder.desc(from.get("idPersona")));
        if (Strings.isNullOrEmpty(nroIdentificacion) && Strings.isNullOrEmpty(nombreRazonSocial) && Strings.isNullOrEmpty(tipoIdentificacion)) {
            //return Collections.emptyList();

            Query q = entityManager.createQuery(criteriaQuery);
            q.setFirstResult(0);
            q.setMaxResults(200);
            return q.getResultList();
            //return entityManager.createQuery(criteriaQuery).getResultList();
        }

        Specification<PerPersona> specification = new Specification<PerPersona>() {
            @Override
            public Predicate toPredicate(Root<PerPersona> perPersonaEntityRoot, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {

                List<Predicate> pr = new LinkedList<Predicate>();

                if (!Strings.isNullOrEmpty(nroIdentificacion)) {
                    pr.add(criteriaBuilder.equal(perPersonaEntityRoot.get("nroIdentificacion"), nroIdentificacion));
                    // PUEDE SER: return criteriaBuilder.equal(perPersonaEntityRoot.get("nroIdentificacion"), nroIdentificacion);
                }

                if (!Strings.isNullOrEmpty(tipoIdentificacion)) {
                    pr.add(criteriaBuilder.equal(perPersonaEntityRoot.get("tipoIdentificacion"), tipoIdentificacion));
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

    public List<PerPersona> listarEmpleadores(final String nombreRazonSocial, final String nroIdentificacion) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PerPersona> criteriaQuery = criteriaBuilder.createQuery(PerPersona.class);
        Root<PerPersona> from = criteriaQuery.from(PerPersona.class);
        criteriaQuery.orderBy(criteriaBuilder.desc(from.get("idPersona")));
        if (Strings.isNullOrEmpty(nroIdentificacion) && Strings.isNullOrEmpty(nombreRazonSocial)) {
            Query q = entityManager.createQuery(criteriaQuery);
            q.setFirstResult(0);
            q.setMaxResults(200);
            return q.getResultList();
        }
        Specification<PerPersona> specification = new Specification<PerPersona>() {
            @Override
            public Predicate toPredicate(Root<PerPersona> perPersonaEntityRoot, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> pr = new LinkedList<Predicate>();
                if (!Strings.isNullOrEmpty(nroIdentificacion)) {
                    pr.add(criteriaBuilder.equal(perPersonaEntityRoot.get("nroIdentificacion"), nroIdentificacion));
                }
                if (!Strings.isNullOrEmpty(nombreRazonSocial)) {
                    pr.add(criteriaBuilder.like(criteriaBuilder.lower(perPersonaEntityRoot.<String>get("nombreRazonSocial")), "%" + nombreRazonSocial.toLowerCase() + "%"));
                }
                //
                Subquery<UsrUsuario> subquery = criteriaQuery.subquery(UsrUsuario.class);
                Root fromProject = subquery.from(UsrUsuario.class);
                subquery.select(fromProject.get("idPersona")); // field to map with main-query
                subquery.where(criteriaBuilder.equal(fromProject.get("esInterno"), (short) 0));
                subquery.where(criteriaBuilder.equal(fromProject.get("esDelegado"), (short) 0));

                //select.where(criteriaBuilder.in(path).value(subquery));
                //pr.add(criteriaBuilder.in("idPersona").value(subquery));
                pr.add(criteriaBuilder.in(perPersonaEntityRoot.get("idPersona")).value(subquery));
                //
                return criteriaBuilder.and(pr.toArray(new Predicate[pr.size()])); // O puede ser or()
            }
        };
//        Specification<UsrUsuario> specification2=new Specification<UsrUsuario>() {
//            @Override
//            public Predicate toPredicate(Root<UsrUsuario> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
//                List<Predicate> pr = new LinkedList<Predicate>();
//                criteriaBuilder.in()
//                return criteriaBuilder.and(pr.toArray(new Predicate[pr.size()])); // O puede ser or()
//            }
//        };
        criteriaQuery.where(specification.toPredicate(from, criteriaQuery, criteriaBuilder));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

//    public List<PerPersona> listarEmpleadores(final String nombreRazonSocial, final String nroIdentificacion) {
//        return personaRepository.listarEmpladores(("%"+nroIdentificacion+"%"), ("%"+nombreRazonSocial+"%"));
//    }

    @Override
    public Long obtenerSecuencia(String nombreSecuencia) {
        BigDecimal rtn;
        rtn = (BigDecimal) entityManager.createNativeQuery("SELECT " + nombreSecuencia + ".nextval FROM DUAL").getSingleResult();
        return rtn.longValue();
    }

    @Override
    public boolean registrar(PerPersona persona, PerUnidad unidad, UsrUsuario usuario) {
        try {
            final String REGISTRO_BITACORA = "ROE";
            System.out.println("======>>> GUARDADO PERSONA <<<===== ");
            persona.setFechaBitacora(new Date());
            //personaRepository.save(persona);
            persona = personaRepository.save(persona);
            System.out.println("======>>> GUARDADO PERSONA  OK<<<===== ");

            System.out.println("======================================= ");
            System.out.println("======>>> GUARDADO UNIDAD <<<===== ");
            unidad.setFechaBitacora(new Date());
            unidad.setPerPersona(persona);
            unidad = unidadRepository.save(unidad);
            System.out.println("======>>> GUARDADO UNIDAD OK <<<===== ");

            System.out.println("======================================= ");
            System.out.println("======>>> GUARDADO USUARIO <<<===== ");
            usuario.setFechaBitacora(new Date());
            usuario.setIdPersona(persona);
            usuario = usuarioRepository.save(usuario);
            System.out.println("======>>> GUARDADO USUARIO OK <<<===== ");

            System.out.println("======================================= ");
            System.out.println("======>>> GUARDADO USUARIO_UNIDAD <<<===== ");
            PerUsuarioUnidad usuarioUnidad = new PerUsuarioUnidad();
            PerUsuarioUnidadPK perUsuarioUnidadPK = new PerUsuarioUnidadPK();
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

            UsrModulo modulo = moduloRepository.findByIdModulo("PER");
            UsrRol rol = rolRepository.findByIdRol((Long.valueOf("2")));

            UsrUsuarioRol usuarioRol = new UsrUsuarioRol();
            usuarioRol.setFechaBitacora(new Date());
            usuarioRol.setRegistroBitacora(REGISTRO_BITACORA);
            usuarioRol.setUsrRol(rol);
            usuarioRol.setUsrUsuario(usuario);
            UsrUsuarioRolPK usrUsuarioRolPK = new UsrUsuarioRolPK();
            usrUsuarioRolPK.setIdRol(rol.getIdRol());
            usrUsuarioRolPK.setIdUsuario(usuario.getIdUsuario());

            usuarioRol.setUsrUsuarioRolPK(usrUsuarioRolPK);

            usuarioRolRepository.save(usuarioRol);
            System.out.println("======>>> GUARDADO USUARIO_ROL OK <<<===== ");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    ////////////////////////////////////LUIS
    @Override
    public boolean registrarDependiente(PerPersona persona, PerUnidad unidad, UsrUsuario usuario, String REGISTRO_BITACORA, boolean estado) {
        try {
            persona.setFechaBitacora(new Date());
            persona = personaRepository.save(persona);

//            unidad.setFechaBitacora(new Date());
//            unidad.setPerPersona(persona);
//            unidad=unidadRepository.save(unidad);
            usuario.setFechaBitacora(new Date());
            usuario.setIdPersona(persona);
            usuario = usuarioRepository.save(usuario);
            PerUsuarioUnidad usuarioUnidad = new PerUsuarioUnidad();

            PerUsuarioUnidadPK perUsuarioUnidadPK = new PerUsuarioUnidadPK();
            perUsuarioUnidadPK.setIdPersona(unidad.getPerUnidadPK().getIdPersona());
            perUsuarioUnidadPK.setIdUnidad(unidad.getPerUnidadPK().getIdUnidad());
            perUsuarioUnidadPK.setIdUsuario(usuario.getIdUsuario());

            usuarioUnidad.setFechaBitacora(new Date());
            usuarioUnidad.setRegistroBitacora(REGISTRO_BITACORA);
            usuarioUnidad.setPerUnidad(unidad);
            usuarioUnidad.setUsrUsuario(usuario);
            usuarioUnidad.setPerUsuarioUnidadPK(perUsuarioUnidadPK);
            if (estado == true) {
                usuarioUnidad.setEstado(Dominios.PAR_ESTADO_ACTIVO);
            } else {
                usuarioUnidad.setEstado(Dominios.PAR_ESTADO_INACTIVO);
            }
            usuarioUnidadRepository.save(usuarioUnidad);

            UsrModulo modulo = moduloRepository.findByIdModulo("PER");
            UsrRol rol = rolRepository.findByIdRol((Long.valueOf("2")));

            UsrUsuarioRol usuarioRol = new UsrUsuarioRol();
            usuarioRol.setFechaBitacora(new Date());
            usuarioRol.setRegistroBitacora(REGISTRO_BITACORA);
            usuarioRol.setUsrRol(rol);
            usuarioRol.setUsrUsuario(usuario);

            UsrUsuarioRolPK usrUsuarioRolPK = new UsrUsuarioRolPK();
            usrUsuarioRolPK.setIdRol(rol.getIdRol());
            usrUsuarioRolPK.setIdUsuario(usuario.getIdUsuario());

            usuarioRol.setUsrUsuarioRolPK(usrUsuarioRolPK);

            usuarioRolRepository.save(usuarioRol);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<PerUsuarioUnidad> listaUsuarioUnidadPorIdPersona(String idPersona) {
        List<PerUsuarioUnidad> lista = new ArrayList<PerUsuarioUnidad>();
        try {
            lista = usuarioUnidadRepository.listaUsuarioUnidadPorIdPersona(idPersona);
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }

    @Override
    public List<PerUsuarioUnidad> listaUsuarioUnidadPorIdUsuario(Long idUsuario) {
        List<PerUsuarioUnidad> lista = new ArrayList<PerUsuarioUnidad>();
        try {
            lista = usuarioUnidadRepository.listaUsuarioUnidadPorIdUsuario(idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }

    @Override
    public List<PerUsuarioUnidad> listaUsuarioUnidadPorIdUsuarioIdPersona(Long idUsuario, String idPersona) {
        List<PerUsuarioUnidad> lista = new ArrayList<PerUsuarioUnidad>();
        try {
            lista = usuarioUnidadRepository.listaUsuarioUnidadPorIdUsuarioIdPersona(idUsuario, idPersona);
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }

    @Override
    public List<PerUsuarioUnidad> listaUsuarioUnidadPersonaPorIdUsuario(Long idUsuario) {
        List<PerUsuarioUnidad> lista = new ArrayList<PerUsuarioUnidad>();
        try {
            lista = usuarioUnidadRepository.listaUsuarioUnidadPorIdUsuarioAgrupadoPorIdPersona(idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }

    @Override
    public boolean guardarUsuarioInterno(PerPersona persona, PerUnidad unidad, UsrUsuario usuario) {
        log.info("Guardando el registro de usuario Interno ... " + getClass().getSimpleName());
        persona = personaRepository.save(persona);

        log.info("Guardando la persona ");
        unidad.setObservaciones("NINGUNA");
        unidad.setPerPersona(persona);
        unidad = unidadRepository.save(unidad);

        log.info("Guardando la unidad");
        usuario.setIdPersona(persona);
        usuario.setEstadoUsuario("A");
        usuario.setEsInterno(new Short("1"));
        usuario = usuarioRepository.save(usuario);

        log.info("Guardando el usuario");
        PerUsuarioUnidad usuarioUnidad = new PerUsuarioUnidad();
        PerUsuarioUnidadPK perUsuarioUnidadPK = new PerUsuarioUnidadPK();
        perUsuarioUnidadPK.setIdPersona(persona.getIdPersona());
        perUsuarioUnidadPK.setIdUnidad(unidad.getPerUnidadPK().getIdUnidad());
        perUsuarioUnidadPK.setIdUsuario(usuario.getIdUsuario());

        log.info("Guardando la relación usuario_unidad");
        usuarioUnidad.setFechaBitacora(new Timestamp(new Date().getTime()));
        usuarioUnidad.setRegistroBitacora(persona.getRegistroBitacora());
        usuarioUnidad.setPerUnidad(unidad);
        usuarioUnidad.setUsrUsuario(usuario);
        usuarioUnidad.setPerUsuarioUnidadPK(perUsuarioUnidadPK);
        usuarioUnidadRepository.save(usuarioUnidad);

        log.info("Guardado del usuario completado sin falla .... ");
        return true;
    }

    public void cambiarEstadoUsuario(Long usuario) {
        UsrUsuario usuarioSinConfirmar = usuarioRepository.findOne(usuario);
        usuarioSinConfirmar.setEstadoUsuario("A");
        usuarioRepository.save(usuarioSinConfirmar);
    }

    public boolean eliminarRegistro(String perPersona, UsrUsuario usrUsuario) {
        log.info("Eliminando id Persona " + perPersona + " persona usuario " + usrUsuario.getIdPersona().getIdPersona() + " id Usuario " + usrUsuario.getIdUsuario());

        UsrUsuarioRolPK usrPK = new UsrUsuarioRolPK();
        usrPK.setIdUsuario(usrUsuario.getIdUsuario());
        usrPK.setIdRol(new Long("2"));
        UsrUsuarioRol usrUsuarioRolTmp = usuarioRolRepository.findOne(usrPK);

        if (usrUsuarioRolTmp != null) {
            usuarioRolRepository.delete(usrUsuarioRolTmp.getUsrUsuarioRolPK());
            usuarioRolRepository.flush();
        }

        PerUsuarioUnidad pu = usuarioUnidadRepository.unidadPorIdPersona(perPersona);
        usuarioUnidadRepository.delete(pu);
        usuarioUnidadRepository.flush();
        log.info("Elimina usuarioUnidad");

        PerUnidad unidadTmp = unidadRepository.unidadPorIdPersona(perPersona);
        unidadRepository.delete(unidadTmp);
        unidadRepository.flush();
        log.info("Elimina unidad ");

        UsrUsuario usuarioTmp = usuarioRepository.findOne(usrUsuario.getIdUsuario());
        usuarioRepository.delete(usuarioTmp);
        usuarioRepository.flush();
        log.info("Elimina usuario");

        PerPersona personaTmp = personaRepository.findOne(perPersona);
        personaRepository.delete(personaTmp);
        personaRepository.flush();
        log.info("Elimina a la persona");
        return true;
    }

    public List<PerPersona> listarPorSucursal(String idPersona) {
        return personaRepository.findByIdPersonaAndEsNatural(idPersona, true);
    }

    public PerPersona obtienePorCentral(String idPersona) {
        List<PerPersona> personaList = personaRepository.findByIdPersonaAndEsNatural(idPersona, false);
        if (personaList.size() == 0) {
            return new PerPersona();
        }
        return personaList.get(0);
    }

    public boolean guardarUsuarioRol(Long idUsuario, Long idRol) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        UsrRol rol = rolRepository.findByIdRol(idRol);
        UsrUsuario usuario = usuarioRepository.findOne(idUsuario);

        UsrUsuarioRol usuarioRol = new UsrUsuarioRol();
        usuarioRol.setFechaBitacora(new Timestamp(new Date().getTime()));
        usuarioRol.setRegistroBitacora(session.getAttribute("bitacoraSession").toString());

        usuarioRol.setUsrRol(rol);
        usuarioRol.setUsrUsuario(usuario);
        UsrUsuarioRolPK usrUsuarioRolPK = new UsrUsuarioRolPK();
        usrUsuarioRolPK.setIdRol(rol.getIdRol());
        usrUsuarioRolPK.setIdUsuario(usuario.getIdUsuario());

        usuarioRol.setUsrUsuarioRolPK(usrUsuarioRolPK);
        usuarioRolRepository.save(usuarioRol);

        return true;
    }

    public void eliminarUsuarioRol(Long idUsuario, Long idRol) {
        UsrUsuarioRolPK usrPK = new UsrUsuarioRolPK();
        usrPK.setIdUsuario(idUsuario);
        usrPK.setIdRol(idRol);
        UsrUsuarioRol usrUsuarioRolTmp = usuarioRolRepository.findOne(usrPK);
        if (usrUsuarioRolTmp != null) {
            usuarioRolRepository.delete(usrUsuarioRolTmp);
        }
    }
}
