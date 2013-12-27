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
import bo.gob.mintrabajo.ovt.repositories.VRecursosRepository;
import bo.gob.mintrabajo.ovt.api.IVRecursosService;
import bo.gob.mintrabajo.ovt.entities.VparActividadEconomica;
import bo.gob.mintrabajo.ovt.entities.VparRecurso;
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
@Named("vRecursosService")
@TransactionAttribute
public class VRecursosService implements IVRecursosService{
    private final VRecursosRepository vRecursosRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public VRecursosService(VRecursosRepository vRecursosRepository) {
        this.vRecursosRepository = vRecursosRepository;
    }
    
    @Override
    public List<VparRecurso> listarVRecursos(){
        String nombreVista = "vpar_recurso";
        Query query = entityManager.createNativeQuery("select ae.*  from " + nombreVista + " ae order by ae.id_recurso_padre asc, ae.orden asc");
        List<Object[]> objeto = (List<Object[]>) query.getResultList();
        
        List<VparRecurso> lista = new ArrayList<VparRecurso>();
        VparRecurso v;
        
            for(Object[] obj : objeto){
                if (obj != null){
                    try{
                    int i = 0;
                    v = new VparRecurso();                                      
                    v.setIdRecurso(new Long (obj[i++].toString()));
                    v.setIdModulo((String) obj[i++]);
                    v.setTipoRecurso((String) obj[i++]);
                    v.setTipoPlataforma((String) obj[i++]);
                    v.setOrden(new Short (obj[i++].toString()));
                    v.setEtiqueta((String) obj[i++]);
                    v.setDescripcion((String) obj[i++]);
                    v.setEjecutable((String) obj[i++]);
                    v.setEsVerificable(new Short (obj[i++].toString()));
                    try {
                        String id2 = (String) obj[i++];
                            if(!id2.equals("")){
                                v.setIdRecursoPadre(new Long (id2));
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
    public List<VparRecurso> listarVRecursosHijo(Long idPadre){
        String nombreVista = "vpar_recurso";
        Query query = entityManager.createNativeQuery("select ae.*  from " + nombreVista + " ae where ae.id_recurso_padre ='"+idPadre+"' "
                + " order by ae.id_recurso_padre asc, ae.orden asc");
        List<Object[]> objeto = (List<Object[]>) query.getResultList();
        
        List<VparRecurso> lista = new ArrayList<VparRecurso>();
        VparRecurso v;
        
        for(Object[] obj : objeto){
                if (obj != null){
                    try{
                    int i = 0;
                    v = new VparRecurso();                                      
                    v.setIdRecurso(new Long (obj[i++].toString()));
                    v.setIdModulo((String) obj[i++]);
                    v.setTipoRecurso((String) obj[i++]);
                    v.setTipoPlataforma((String) obj[i++]);
                    v.setOrden(new Short (obj[i++].toString()));
                    v.setEtiqueta((String) obj[i++]);
                    v.setDescripcion((String) obj[i++]);
                    v.setEjecutable((String) obj[i++]);
                    v.setEsVerificable(new Short (obj[i++].toString()));
                    try {
                        long id2=new Long (obj[i++].toString());
                            v.setIdRecursoPadre(new Long (id2));
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
    
    
    @Override
    public VparRecurso ObtienePorEtiquetaYNivel(String etiqueta, String nivel){
        String nombreVista = "vpar_recurso";
        Query query = entityManager.createNativeQuery("select a.*  from " + nombreVista + " a "
                + " where a.etiqueta = '"+etiqueta+"' and "
                + " a.nivel = '"+ nivel+"'" );
        
        System.out.println("== SQL "+query.toString());
        List<Object[]> objeto = (List<Object[]>) query.getResultList();
        
        VparRecurso v=new VparRecurso();
        
        for(Object[] obj : objeto){
                if (obj != null){
                    try{
                    int i = 0;
                    v = new VparRecurso();                                      
                    v.setIdRecurso(new Long (obj[i++].toString()));
                    v.setIdModulo((String) obj[i++]);
                    v.setTipoRecurso((String) obj[i++]);
                    v.setTipoPlataforma((String) obj[i++]);
                    v.setOrden(new Short (obj[i++].toString()));
                    v.setEtiqueta((String) obj[i++]);
                    v.setDescripcion((String) obj[i++]);
                    v.setEjecutable((String) obj[i++]);
                    v.setEsVerificable(new Short (obj[i++].toString()));                   
                    try {
                        if (nivel.equals("1")){
                            String idPadre = (String) obj[i++];
                            System.out.println("No tien padre");
                        }else{
                            long id2=new Long (obj[i++].toString());
                            if (id2 >= 0){
                                v.setIdRecursoPadre(new Long (id2));
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
}
