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

import bo.gob.mintrabajo.ovt.api.ICalendarioService;
import bo.gob.mintrabajo.ovt.entities.ParCalendario;
import bo.gob.mintrabajo.ovt.entities.ParCalendarioPK;
import bo.gob.mintrabajo.ovt.repositories.CalendarioRepository;
import java.util.Date;
import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author lvaldez
 */
@Named("calendarioService")
@TransactionAttribute
public class CalendarioService implements ICalendarioService{
    
    private final CalendarioRepository calendarioRepository;
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public CalendarioService(CalendarioRepository calendarioRepository) {
        this.calendarioRepository = calendarioRepository;
    }
    
    @Override
    public List<ParCalendario> listaCalendarioPorTipoPeriodoTipoCalendario(String tipoPeriodo, String tipoCalendario){
        List<ParCalendario> lista;
        try {
            lista = calendarioRepository.listaCalendarioPorTPeriodoYTCalendario(tipoPeriodo, tipoCalendario);
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public ParCalendario obtenerCalendarioPorGestionYPeriodo(String gestion,String tipoPeriodo){
        ParCalendario calendario;
        try {
            calendario=calendarioRepository.obtenerCalendarioPorGestionYPeriodo(gestion, tipoPeriodo);
        } catch (Exception e) {
            calendario = null;
        }
        return calendario;
    }
    
    @Override
    public List<ParCalendario> listaCalendario(){
        List<ParCalendario> lista;
        try {
            lista=calendarioRepository.listaCalendarioDesc();
        } catch (Exception e) {
            lista = null;
        }
        return lista;
    }
    @Override
    public List<ParCalendario> listaCalendarioPorGestion (Integer gestion){
        List<ParCalendario> lista;
        try {
            lista=calendarioRepository.listaCalendarioPorGestion(gestion.toString());
        } catch (Exception e) {
            lista = null;
        }
        return lista;
    }
    
    @Override
    public ParCalendario saveCalendario(Integer gestion, String periodo, String tipoCalendario, String REGISTRO_BITACORA){                
        ParCalendario calendario =new ParCalendario();
        ParCalendarioPK  parCalendarioPK = new ParCalendarioPK();
        parCalendarioPK.setGestion(gestion.toString());
        parCalendarioPK.setTipoPeriodo(periodo);
        calendario.setParCalendarioPK(parCalendarioPK);
        calendario.setTipoCalendario(tipoCalendario);
        calendario.setFechaBitacora(new Date());
        calendario.setRegistroBitacora(REGISTRO_BITACORA);
        try {
            calendario=calendarioRepository.save(calendario);
        } catch (Exception e) {
            e.printStackTrace();
            calendario=null;
        }
        return calendario;
    }
}
