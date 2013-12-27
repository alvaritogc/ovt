/*
 * Copyright 2013 lvaldez.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.IVActividadEconomicaService;
import bo.gob.mintrabajo.ovt.api.IVperPersonaService;
import bo.gob.mintrabajo.ovt.entities.VparActividadEconomica;
import bo.gob.mintrabajo.ovt.repositories.VActividadEconomicaRepository;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lvaldez
 */

@Named("vActividadEconomicaService")
@TransactionAttribute
public class VActividadEconomicaService implements IVActividadEconomicaService {
    private final VActividadEconomicaRepository vActividadEconomicaRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public VActividadEconomicaService(VActividadEconomicaRepository vActividadEconomicaRepository) {
        this.vActividadEconomicaRepository = vActividadEconomicaRepository;
    }
    
//    @Override
//    public List<VparActividadEconomica> listarVActividadEconomica(){
//        try {
//            return vActividadEconomicaRepository.findAll();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    @Override
    public List<VparActividadEconomica> listarVActividadEconomica(){
        String nombreVista = "vpar_actividad_economica";
        Query query = entityManager.createNativeQuery("select ae.*  from " + nombreVista + " ae order by ae.id_actividad_economica2 asc");
        List<Object[]> objeto = (List<Object[]>) query.getResultList();
        
        List<VparActividadEconomica> lista = new ArrayList<VparActividadEconomica>();
        VparActividadEconomica v;
        
            for(Object[] obj : objeto){
                if (obj != null){
                    try{
                    int i = 0;
                    v = new VparActividadEconomica();                                      
                    v.setIdActividadEconomica( new Long (obj[i++].toString()));
                    v.setCodActividadEconomica((String) obj[i++]);
                    v.setDescripcion((String) obj[i++]);
                    v.setCodImpuestos((String) obj[i++]);
                    v.setDescricpionImpuestos((String) obj[i++]);
                    try {
                        String id2 = (String) obj[i++];
                            if(!id2.equals("")){
                                v.setIdActividadEconomica2(new Long (id2));
                            }
                        } catch (Exception e) {
                    }
                    v.setEstado((String) obj[i++]);
                    v.setFechaBitacora((Date) obj[i++]);
                    v.setRegistroBitacora((String) obj[i++]);
                    v.setNivel(new BigInteger (obj[i++].toString()));
                    lista.add(v);
                    }catch(Exception e){
                        e.printStackTrace();
                        break;
                    }
                            
                }
            }

        return lista;
    }
    
    @Override
    public VparActividadEconomica ObtienePorDescripcionYNivel(String descripcion, String nivel){
        String nombreVista = "vpar_actividad_economica";
        Query query = entityManager.createNativeQuery("select a.*  from " + nombreVista + " a "
                + " where a.descripcion = '"+descripcion+"' and "
                + " a.nivel = '"+ nivel+"'" );
        System.out.println("== nivel en obtine " + nivel);
        System.out.println("== SQL "+query.toString());
        
        List<Object[]> objeto = (List<Object[]>) query.getResultList();
        
        VparActividadEconomica v=new VparActividadEconomica();
        
            for(Object[] obj : objeto){
                if (obj != null){
                    try{
                    int i = 0;
                    v = new VparActividadEconomica();                                      
                    v.setIdActividadEconomica( new Long (obj[i++].toString()));
                    v.setCodActividadEconomica((String) obj[i++]);
                    v.setDescripcion((String) obj[i++]);
                    v.setCodImpuestos((String) obj[i++]);
                    v.setDescricpionImpuestos((String) obj[i++]);
                    try {
                        if (nivel.equals("1")){
                            String idPadre = (String) obj[i++];
                            System.out.println("No tiene padre");
                        }else{
                            long id2=new Long (obj[i++].toString());
                            if (id2 >= 0){
                                v.setIdActividadEconomica2(new Long (id2));
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("=> No tiene IdActividadEconomica2");    
                        e.printStackTrace();    
                    }
                    
                    v.setEstado((String) obj[i++]);
                    v.setFechaBitacora((Date) obj[i++]);
                    v.setRegistroBitacora((String) obj[i++]);
                    v.setNivel(new BigInteger (obj[i++].toString()));
                    }catch(Exception e){
                        e.printStackTrace();
                        break;
                    }
                            
                }
            }

        return v;
    }
    
    @Override
    public List<VparActividadEconomica> listarVActividadEconomicaHijo(Long idPadre){
        String nombreVista = "vpar_actividad_economica";
        Query query = entityManager.createNativeQuery("select ae.*  from " + nombreVista + " ae where ae.id_actividad_economica2 = '"+idPadre+"'");
        List<Object[]> objeto = (List<Object[]>) query.getResultList();
        
        List<VparActividadEconomica> lista = new ArrayList<VparActividadEconomica>();
        VparActividadEconomica v;
        
            for(Object[] obj : objeto){
                if (obj != null){
                    try{
                    int i = 0;
                    v = new VparActividadEconomica();                                      
                    v.setIdActividadEconomica( new Long (obj[i++].toString()));
                    v.setCodActividadEconomica((String) obj[i++]);
                    v.setDescripcion((String) obj[i++]);
                    v.setCodImpuestos((String) obj[i++]);
                    v.setDescricpionImpuestos((String) obj[i++]);
                    try {
                        long id2=new Long (obj[i++].toString());
                            v.setIdActividadEconomica2(new Long (id2));
                        } catch (Exception e) {
                            e.printStackTrace();
                    }
                    v.setEstado((String) obj[i++]);
                    v.setFechaBitacora((Date) obj[i++]);
                    v.setRegistroBitacora((String) obj[i++]);
                    v.setNivel(new BigInteger (obj[i++].toString()));
                    lista.add(v);
                    }catch(Exception e){
                        e.printStackTrace();
                        break;
                    }
                            
                }
            }

        return lista;
    }
      
}
