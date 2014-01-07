
package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IActividadEconomicaService;
import bo.gob.mintrabajo.ovt.api.IActividadService;
import bo.gob.mintrabajo.ovt.entities.ParActividadEconomica;
import bo.gob.mintrabajo.ovt.entities.PerActividad;
import bo.gob.mintrabajo.ovt.entities.PerPersona;
import bo.gob.mintrabajo.ovt.entities.PerUnidad;
import bo.gob.mintrabajo.ovt.repositories.ActividadEconomicaRepository;
import bo.gob.mintrabajo.ovt.repositories.ActividadRepository;
import bo.gob.mintrabajo.ovt.repositories.DominioRepository;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static bo.gob.mintrabajo.ovt.Util.Dominios.*;

/**
 *
 * @author pc01
 */
@Named("actividadEconomicaService")
@TransactionAttribute
public class ActividadEconomicaService implements IActividadEconomicaService {

    private final ActividadEconomicaRepository actividadEconomicaRepository;
    private final DominioRepository dominioRepository;
    private final ActividadRepository actividadRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public ActividadEconomicaService(ActividadEconomicaRepository actividadEconomicaRepository,DominioRepository dominioRepository,ActividadRepository actividadRepository) {
        this.actividadEconomicaRepository = actividadEconomicaRepository;
        this.dominioRepository=dominioRepository;
        this.actividadRepository=actividadRepository;
    }


    /*
     * Este metodo realiza un INSERT o UPDATE de un registro de la tabla
     * PAR_ACTIVIDAD_ECONOMICA
     */
    @Override
    public ParActividadEconomica save(ParActividadEconomica actividadEconomica) {

        if(actividadEconomica.getIdActividadEconomica()==null){
            //Nuevo
            actividadEconomica.setIdActividadEconomica(this.obtenerSecuencia("PAR_ACTIVIDAD_ECONOMICA_SEC"));
        }
        actividadEconomica.setEstado(dominioRepository.obtenerDominioPorNombreYValor(DOM_ESTADO,PAR_ESTADO_ACTIVO).getParDominioPK().getValor());
        actividadEconomica.setFechaBitacora(new Date());
        return actividadEconomicaRepository.save(actividadEconomica);
    }
    
    @Override
    public boolean guardarActividadEconomica(ParActividadEconomica actividadEconomica, boolean estadoActividadEconomica,
            String REGISTRO_BITACORA, String tipoNodo, Long idPadre, Long idHijo){
        boolean guardado=false;
        
        ///System.out.println("==> estado " + estadoActividadEconomica);
        ///System.out.println("==>REGISTRO_BITACORA "+REGISTRO_BITACORA);
        ///System.out.println("==> tipoNodo "+tipoNodo);
        ///System.out.println("==> idPadre"+ idPadre);
        
        
        ParActividadEconomica aEconimica= new ParActividadEconomica();
        try {
            if(idHijo < 0){
                //Nuevo
                ///System.out.println("=> nuevo");
                aEconimica.setIdActividadEconomica(this.obtenerSecuencia("PAR_ACTIVIDAD_ECONOMICA_SEC"));
            }else{
                ///System.out.println("=> edicion");
                aEconimica=actividadEconomicaRepository.findOne(idHijo);
            }
            
            if(tipoNodo.equals("Hijo")){
                aEconimica.setIdActividadEconomica2(actividadEconomicaRepository.findOne(idPadre));
            }    
            ///System.out.println("=>0 "+ aEconimica.getIdActividadEconomica());
            aEconimica.setCodActividadEconomica(actividadEconomica.getCodActividadEconomica());
            ///System.out.println("=>1 "+ aEconimica.getCodActividadEconomica());
            aEconimica.setDescripcion(actividadEconomica.getDescripcion());
            ///System.out.println("=>2 "+ aEconimica.getDescripcion());
            aEconimica.setCodImpuestos(actividadEconomica.getCodImpuestos());
            ///System.out.println("=>3 "+ aEconimica.getCodImpuestos());
            aEconimica.setDescricpionImpuestos(actividadEconomica.getDescricpionImpuestos());
            ///System.out.println("=>4 "+ aEconimica.getDescricpionImpuestos());
            if(estadoActividadEconomica==true){
                aEconimica.setEstado(dominioRepository.obtenerDominioPorNombreYValor("ESTADO", "A").getParDominioPK().getValor());
            }
            else{
                aEconimica.setEstado(dominioRepository.obtenerDominioPorNombreYValor("ESTADO", "X").getParDominioPK().getValor());
            }
            ///System.out.println("=>5 "+ aEconimica.getEstado());
            aEconimica.setFechaBitacora(new Date());
            aEconimica.setRegistroBitacora(REGISTRO_BITACORA);
            ///System.out.println("preparando para guardar");
            actividadEconomicaRepository.save(aEconimica);
            ///System.out.println("guardardado");
            guardado=true;
        } catch (Exception e) {
            e.printStackTrace();
            guardado=false;
        }
        return guardado;
    }

    @Override
    public boolean guardarActividadEconomica(ParActividadEconomica actividadEconomica, boolean estadoActividadEconomica,
            String REGISTRO_BITACORA, String tipoNodo, Long idPadre, Long idHijo){
        boolean guardado=false;



        ParActividadEconomica aEconimica= new ParActividadEconomica();
        try {
            if(idHijo < 0){
                //Nuevo
                ///System.out.println("=> nuevo");
                aEconimica.setIdActividadEconomica(this.obtenerSecuencia("PAR_ACTIVIDAD_ECONOMICA_SEC"));
            }else{
                ///System.out.println("=> edicion");
                aEconimica=actividadEconomicaRepository.findOne(idHijo);
            }
            
            if(tipoNodo.equals("Hijo")){
                aEconimica.setIdActividadEconomica2(actividadEconomicaRepository.findOne(idPadre));
            }    
            ///System.out.println("=>0 "+ aEconimica.getIdActividadEconomica());
            aEconimica.setCodActividadEconomica(actividadEconomica.getCodActividadEconomica());
            ///System.out.println("=>1 "+ aEconimica.getCodActividadEconomica());
            aEconimica.setDescripcion(actividadEconomica.getDescripcion());
            ///System.out.println("=>2 "+ aEconimica.getDescripcion());
            aEconimica.setCodImpuestos(actividadEconomica.getCodImpuestos());
            ///System.out.println("=>3 "+ aEconimica.getCodImpuestos());
            aEconimica.setDescricpionImpuestos(actividadEconomica.getDescricpionImpuestos());
            ///System.out.println("=>4 "+ aEconimica.getDescricpionImpuestos());
            if(estadoActividadEconomica==true){
                aEconimica.setEstado(dominioRepository.obtenerDominioPorNombreYValor("ESTADO", "A").getParDominioPK().getValor());
            }
            else{
                aEconimica.setEstado(dominioRepository.obtenerDominioPorNombreYValor("ESTADO", "X").getParDominioPK().getValor());
            }
            ///System.out.println("=>5 "+ aEconimica.getEstado());
            aEconimica.setFechaBitacora(new Date());
            aEconimica.setRegistroBitacora(REGISTRO_BITACORA);
            ///System.out.println("preparando para guardar");
            actividadEconomicaRepository.save(aEconimica);
            ///System.out.println("guardardado");
            guardado=true;
        } catch (Exception e) {
            e.printStackTrace();
            guardado=false;
        }
        return guardado;
    }




    @Override
    public ParActividadEconomica findByIdActividadEconomica(Long idActividadEconomica){
        try{
            return actividadEconomicaRepository.findByIdActividadEconomica(idActividadEconomica);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public ParActividadEconomica obtieneActividadEconomicaPorId(Long idActividadEconomica){
        ParActividadEconomica AE=new ParActividadEconomica();
        try{
            ///System.out.println("===> idActividadEconomica " + idActividadEconomica);
            AE = actividadEconomicaRepository.findOne(idActividadEconomica);
            ///System.out.println("==> id AE " +AE.getIdActividadEconomica());
            return AE;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ParActividadEconomica obtieneActividadEconomicaPorId(Long idActividadEconomica){
        ParActividadEconomica AE=new ParActividadEconomica();
        try{
            ///System.out.println("===> idActividadEconomica " + idActividadEconomica);
            AE = actividadEconomicaRepository.findOne(idActividadEconomica);
            ///System.out.println("==> id AE " +AE.getIdActividadEconomica());
            return AE;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean delete(Long idActividadEconimica){
        boolean deleted = false;
        ///System.out.println("==> nodo eliminado "+ idActividadEconimica);
        //ParActividadEconomica actividadEconomica= new ParActividadEconomica();
        //actividadEconomica=actividadEconomicaRepository.findOne(idActividadEconimica);
        actividadEconomicaRepository.delete(idActividadEconimica);
        return deleted;
    }

    public Long obtenerSecuencia(String nombreSecuencia){
        BigDecimal rtn;
        rtn = (BigDecimal)entityManager.createNativeQuery("SELECT "+nombreSecuencia+".nextval FROM DUAL").getSingleResult();
        entityManager.close();
        return rtn.longValue();
    }

    /*
  *
  * Este metodo obtiene una lista de las actividades que tienen codigo = 0.
  *
   */
    public List<BigDecimal> obtenerActividadEconomicaParaRegistro(){
        List<BigDecimal>lista ;
        lista = (List<BigDecimal>)entityManager.createNativeQuery("SELECT   a.id_actividad_economica\n" +
                "      FROM   par_actividad_economica a\n" +
                "      where level = '2'\n" +
                " START WITH   a.id_actividad_economica2 is null\n" +
                " CONNECT BY   PRIOR a.id_actividad_economica = a.id_actividad_economica2").getResultList();
        entityManager.close();
        return lista;
    }

}
