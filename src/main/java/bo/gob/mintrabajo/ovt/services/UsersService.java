package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.Users;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
import bo.gob.mintrabajo.ovt.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;

@Named("usrSrv")
@TransactionAttribute
public class UsersService implements Users {
//    private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);

    private final UserRepository repository;

    private final EntityManager entityManager;

    private final DataSource dataSource;

    // SOLO CUANDO SE NECESITE URGENTE private final EntityManager entityManager;

    // NO ESTA PERMITIDO private LytServidoresTrabajoEntity e1;

    @Inject
    public UsersService(UserRepository repository, EntityManager entityManager, DataSource dataSource) {
        //EntityManager entityManager) {
        this.repository = repository;
        // this.entityManager = entityManager;
        this.entityManager = entityManager;
        this.dataSource = dataSource;
    }

    @Override
    public UsrUsuarioEntity findByUsuarioAndClave(String user, String password) {
        UsrUsuarioEntity newUsrUsuarioEntity = new UsrUsuarioEntity();
        newUsrUsuarioEntity.setUsuario(user);
        newUsrUsuarioEntity.setClave(password);
        newUsrUsuarioEntity.setEstadoUsuario("Activo");

        repository.findByExample(newUsrUsuarioEntity, null, null, -1, -1);


//        entityManager.createNamedQuery("{call '''hacer-algo()'''}")

        List<UsrUsuarioEntity> username = repository.findByAttribute("username", user, -1, -1);
        if (username.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        UsrUsuarioEntity usrUsuarioEntity = username.get(0);

        if (!password.equals(usrUsuarioEntity.getClave())) {
            throw new RuntimeException("Password equivocado");
        }

        if (usrUsuarioEntity.getFechaInhabilitacion().getTime() < System.currentTimeMillis()) {
            //....
        }

        return usrUsuarioEntity;
    }

    @Override
    public void saveUser(UsrUsuarioEntity user) {

        repository.save(user);
    }
}
