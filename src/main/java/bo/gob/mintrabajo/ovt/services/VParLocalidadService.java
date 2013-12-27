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

import bo.gob.mintrabajo.ovt.api.IVParLocalidadService;
import bo.gob.mintrabajo.ovt.entities.VparLocalidad;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lvaldez
 */
@Named("vParLocalidadService")
@TransactionAttribute
public class VParLocalidadService implements IVParLocalidadService{
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;
    
    @Override
    public List<VparLocalidad> listarVLocalidades(){
        String nombreVista = "vpar_localidad";
        Query query = entityManager.createNativeQuery("select ae.*  from " + nombreVista + " ae order by ae.cod_localidad_padre asc");
        List<Object[]> objeto = (List<Object[]>) query.getResultList();
        
        List<VparLocalidad> lista = new ArrayList<VparLocalidad>();
        VparLocalidad v;
        
            for(Object[] obj : objeto){
                if (obj != null){
                    try{
                    int i = 0;
                    v = new VparLocalidad();                                      
                    v.setCodLocalidad((String) obj[i++]);
                    v.setDescripcion((String) obj[i++]);
                    v.setTipoLocalidad((String) obj[i++]);
                    v.setCodigoOtr((String) obj[i++]);
                    v.setCodigoRef((String) obj[i++]);
                    try {
                        String id2 = (String) obj[i++];
                        //System.out.println("id padre " + id2);
                            if(id2!=null){
                                v.setCodLocalidadPadre(id2);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                    }
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
    public List<VparLocalidad> listarVLocalidadesHijo(String idPadre){
        String nombreVista = "vpar_localidad";
        Query query = entityManager.createNativeQuery("select ae.*  from " + nombreVista + " ae where ae.cod_localidad_padre ='"+idPadre+"'");
        List<Object[]> objeto = (List<Object[]>) query.getResultList();
        
        List<VparLocalidad> lista = new ArrayList<VparLocalidad>();
        VparLocalidad v;
        
        for(Object[] obj : objeto){
                if (obj != null){
                    try{
                    int i = 0;
                    v = new VparLocalidad();                                      
                    v.setCodLocalidad((String) obj[i++]);
                    v.setDescripcion((String) obj[i++]);
                    v.setTipoLocalidad((String) obj[i++]);
                    v.setCodigoOtr((String) obj[i++]);
                    v.setCodigoRef((String) obj[i++]);
                    try {
                        String id2 = (String) obj[i++];
                        //System.out.println("id padre " + id2);
                            if(id2!=null){
                                v.setCodLocalidadPadre(id2);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                    }
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
    public VparLocalidad ObtienePorDescripcionYNivel(String descripcion, String nivel){
        String nombreVista = "vpar_localidad";
        Query query = entityManager.createNativeQuery("select a.*  from " + nombreVista + " a "
                + " where a.descripcion = '"+descripcion+"' and "
                + " a.nivel = '"+ nivel+"'" );
        
        System.out.println("== SQL "+query.toString());
        List<Object[]> objeto = (List<Object[]>) query.getResultList();
        
        VparLocalidad v=new VparLocalidad();
        
        for(Object[] obj : objeto){
                if (obj != null){
                    try{
                    int i = 0;
                    v = new VparLocalidad();                                      
                    v.setCodLocalidad((String) obj[i++]);
                    v.setDescripcion((String) obj[i++]);
                    v.setTipoLocalidad((String) obj[i++]);
                    v.setCodigoOtr((String) obj[i++]);
                    v.setCodigoRef((String) obj[i++]);  
                        //System.out.println("=================================");
//                    try {
//                        if (nivel.equals("1")){
//                            String idPadre = (String) obj[i++];
//                            System.out.println("No tien padre");
//                        }else{
//                            long id2=new Long (obj[i++].toString());
//                            if (id2 >= 0){
//                                v.setIdRecursoPadre(new Long (id2));
//                            }
//                        }
//                    } catch (Exception e) {
//                        System.out.println("=> No tiene IdActividadEconomica2");    
//                        e.printStackTrace();    
//                    }
                    try {
                        String id2 = (String) obj[i++];
                        //System.out.println("id padre " + id2);
                            if(id2!=null){
                                v.setCodLocalidadPadre(id2);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                    }
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
}
