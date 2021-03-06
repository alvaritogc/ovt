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

import bo.gob.mintrabajo.ovt.api.IObligacionService;
import bo.gob.mintrabajo.ovt.entities.ParObligacion;
import bo.gob.mintrabajo.ovt.repositories.ObligacionRepository;
import java.util.Date;
import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author lvaldez
 */
@Named("obligacionService")
@TransactionAttribute
public class ObligacionService implements IObligacionService{
    private final ObligacionRepository obligacionRepository;
    
    @Inject
    public ObligacionService(ObligacionRepository obligacionRepository) {
        this.obligacionRepository = obligacionRepository;
    }
    
    @Override
    public List<ParObligacion> listaObligacion(){
        List<ParObligacion> lista;
        try {
            lista = obligacionRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
     @Override
    public List<ParObligacion> listaObligacionPorOrden(){
        List<ParObligacion> lista;
        try {
            lista = obligacionRepository.listaPorOrdenDescripcionDeObligacion();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public ParObligacion obligacionPorCod(String codObligacion){
        ParObligacion obligacion;
        try {
            obligacion = obligacionRepository.findByCodObligacion(codObligacion);
        } catch (Exception e) {
            e.printStackTrace();
            obligacion = null;
        }
        return obligacion;
    }
    
    @Override
    public boolean saveObligacion(ParObligacion obligacion, String REGISTRO_BITACORA, boolean estadoObligacion, boolean evento){               
        ParObligacion parObligacion= new ParObligacion();
        if(obligacionRepository.findByCodObligacion(obligacion.getCodObligacion())!=null && !evento){
            return false;
        }
        
        if(!evento){
            parObligacion.setCodObligacion(obligacion.getCodObligacion());
        }
        
        if(evento){
            parObligacion=obligacionRepository.findByCodObligacion(obligacion.getCodObligacion());
        }
        
        parObligacion.setDescripcion(obligacion.getDescripcion());
        if(estadoObligacion==true){
            parObligacion.setEstado("A");
        }
        else{
            parObligacion.setEstado("X");
        }
        parObligacion.setFechaBitacora(new Date());
        parObligacion.setRegistroBitacora(REGISTRO_BITACORA);
            
        try {
            obligacionRepository.save(parObligacion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteObligacion(ParObligacion obligacion){
        boolean deleted = false;
        try {
            ParObligacion po=obligacionRepository.findByCodObligacion(obligacion.getCodObligacion());
            obligacionRepository.delete(po);
            obligacionRepository.flush();
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
            deleted=false;
        }
        return deleted;
    }
}
