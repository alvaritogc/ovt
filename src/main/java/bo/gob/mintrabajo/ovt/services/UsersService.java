package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.Users;
import bo.gob.mintrabajo.ovt.entities.UsrUsuarioEntity;
import bo.gob.mintrabajo.ovt.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@TransactionAttribute
public class UsersService implements Users {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);

    private final UserRepository repository;

    // SOLO CUANDO SE NECESITE URGENTE private final EntityManager entityManager;

    // NO ESTA PERMITIDO private LytServidoresTrabajoEntity e1;

    @Inject
    public UsersService(UserRepository repository) {
        //EntityManager entityManager) {
        this.repository = repository;
        // this.entityManager = entityManager;
    }

    @Override
    public UsrUsuarioEntity findByUsuarioAndClave(String user, String password) {
        return repository.findByUsuarioAndClave(user, password);
    }

    @Override
    public void saveUser(UsrUsuarioEntity user) {

        repository.save(user);
    }
}
