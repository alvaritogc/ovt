package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IVperPersonaService;
import bo.gob.mintrabajo.ovt.entities.VperPersona;
import bo.gob.mintrabajo.ovt.repositories.VperPersonaRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gmercado
 * Date: 11/1/13
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("vperPersonaService")
@TransactionAttribute
public class VperPersonaService implements IVperPersonaService {

    private final VperPersonaRepository vperPersonaRepository;
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public VperPersonaService(VperPersonaRepository vperPersonaRepository) {
        this.vperPersonaRepository = vperPersonaRepository;
    }


    public VperPersona cargaVistaPersona(String id){
        String nombreVista = "vper_persona";
        Query query = entityManager.createNativeQuery("select p.* from " + nombreVista + " p where id_unidad = 0 and id_persona = " + id);
        List<Object[]> objeto = (List<Object[]>) query.getResultList();

            int i = 0;
        VperPersona vperPersona = new VperPersona();
            for(Object[] obj : objeto){

                vperPersona.setIdPersona((String)obj[i++]);
                vperPersona.setTipoIdentificacion((String) obj[i++]);
                vperPersona.setNroIdentificacion(new BigInteger(obj[i++].toString()));
                vperPersona.setNombreRazonSocial(obj[i++].toString());
                vperPersona.setApellidoPaterno((String) obj[i++]);
                vperPersona.setApellidoMaterno((String) obj[i++]);
                vperPersona.setEsNatural(new BigInteger(obj[i++].toString()));
                vperPersona.setCodLocalidad((String) obj[i++]);
                vperPersona.setLocalidad((String) obj[i++]);
                vperPersona.setIdUnidad(new Long(obj[i++].toString()));
                vperPersona.setNombreComercial(((String) obj[i++]));
                vperPersona.setFechaNacimiento((Date) obj[i++]);
                vperPersona.setNroCajaSalud((String) obj[i++]);
                vperPersona.setNroAfp((String) obj[i++]);
                vperPersona.setNroFundaempresa((String) obj[i++]);
                vperPersona.setNroOtro((String) obj[i++]);
                vperPersona.setObservaciones((String) obj[i++]);
                vperPersona.setTipoSociendad((String) obj[i++]);
                vperPersona.setTsociedad((String) obj[i++]);
                vperPersona.setTipoEmpresa((String) obj[i++]);
                vperPersona.setTempresa((String) obj[i++]);
                vperPersona.setActividadDeclarada((String) obj[i++]);
                vperPersona.setEstadoUnidad((String) obj[i++]);
                vperPersona.setEstado((String) obj[i++]);
                vperPersona.setRlNroPermiso((String) obj[i++]);
                vperPersona.setRlNombre((String) obj[i++]);
                vperPersona.setRlNroIdentidad((String) obj[i++]);
                vperPersona.setDirCodDepartamento(new Long(obj[i++].toString()));
                vperPersona.setDirDepartamento((String) obj[i++]);
                vperPersona.setDirLocalidad((String) obj[i++]);
                vperPersona.setDirZona((String) obj[i++]);
                vperPersona.setDirDireccion((String) obj[i++]);
                vperPersona.setDirNroDireccion((String) obj[i++]);
                vperPersona.setTelefono((String) obj[i++]);
                vperPersona.setFax((String) obj[i++]);
                vperPersona.setEmail((String) obj[i++]);
            }

        return vperPersona;
    }
}
