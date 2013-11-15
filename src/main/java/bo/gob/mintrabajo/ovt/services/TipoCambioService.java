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

import bo.gob.mintrabajo.ovt.api.ITipoCambioService;
import bo.gob.mintrabajo.ovt.entities.ParTipoCambio;
import bo.gob.mintrabajo.ovt.entities.ParTipoCambioPK;
import bo.gob.mintrabajo.ovt.repositories.TipoCambioRepository;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author lvaldez
 */
@Named("tipoCambioService")
@TransactionAttribute
public class TipoCambioService implements ITipoCambioService {
    private final TipoCambioRepository tipoCambioServiceRepository;
    
    @Inject
    public TipoCambioService(TipoCambioRepository tipoCambioServiceRepository) {
        this.tipoCambioServiceRepository = tipoCambioServiceRepository;
    }
    
    @Override
    public List<ParTipoCambio> listaTipoCalendario(){
        List<ParTipoCambio> lista;
        try {
            lista = tipoCambioServiceRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public List<ParTipoCambio> listaTipoDeCambios(String monedaBase, String monedaCambio){
        List<ParTipoCambio> lista;
        try {
            lista = tipoCambioServiceRepository.listaTiposDeCambio(monedaBase, monedaCambio);
        } catch (Exception e) {
            e.printStackTrace();
            lista = null;
        }
        return lista;
    }
    
    @Override
    public boolean saveTipoCambio(Date fecha,ParTipoCambio parTipoCambio, String REGISTRO_BITACORA, String monedaBase, String monedaCambio, boolean accion ){       
        boolean estado;
      ParTipoCambio ptc= new ParTipoCambio();
      ParTipoCambioPK ptck = new ParTipoCambioPK();

      if(accion && tipoCambioServiceRepository.tipoDeCambioPorPk(fecha, monedaBase, monedaCambio)!= null){
         return false; 
      }
      
      //if(accion && tipoCambioServiceRepository.tipoDeCambioPorPk(fecha, monedaBase, monedaCambio)== null){
       if(accion){
          ptck.setIdFecha(fecha);
          ptck.setTipoMonedaBase(monedaBase);
          ptc.setParTipoCambioPK(ptck);
          ptck.setTipoMonedaCambio(monedaCambio);
      }
      //if(!accion && tipoCambioServiceRepository.tipoDeCambioPorPk(fecha, monedaBase, monedaCambio)!= null){
      if(!accion){
          ptc=tipoCambioServiceRepository.tipoDeCambioPorPk(fecha, monedaBase, monedaCambio);
      }
      
      ptc.setValorCompra(parTipoCambio.getValorCompra());
      ptc.setValorOficial(parTipoCambio.getValorOficial());
      ptc.setValorVenta(parTipoCambio.getValorVenta());
      ptc.setValorOtro(parTipoCambio.getValorOtro());
      
      ptc.setFechaBitacora(new Date());
      ptc.setRegistroBitacora(REGISTRO_BITACORA);
            
        try {
            tipoCambioServiceRepository.save(ptc);
            estado= true;
            System.out.println("==> true");
        } catch (Exception e) {
            e.printStackTrace();
            parTipoCambio = null;
            System.out.println("==> false");
            estado=false;
        }
        return estado;
    }
    
     @Override
    public boolean deleteTipoCambio(ParTipoCambio parTipoCambio){
        boolean deleted = false;
        try {
            tipoCambioServiceRepository.delete(parTipoCambio);
            tipoCambioServiceRepository.flush();
            deleted = true;
        } catch (Exception e) {
            e.printStackTrace();
            deleted=false;
        }
        return deleted;
    }
}
