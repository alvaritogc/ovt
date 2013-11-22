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

package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParCalendario;
import java.util.List;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author lvaldez
 */
@OpenJpaSettings
public interface CalendarioRepository extends OpenJpaRepository<ParCalendario, Long>{
    @Query("select p "
            + " from ParCalendario p "
            + " where p.parCalendarioPK.tipoPeriodo = :tPeriodo and "
            + " p.tipoCalendario = :tCalendario"
            + " order by p.parCalendarioPK.gestion desc")
    List<ParCalendario> listaCalendarioPorTPeriodoYTCalendario(@Param("tPeriodo")String tPeriodo, @Param("tCalendario")String tCalendario);
    
    @Query("select p "
            + " from ParCalendario p "
            + " where p.parCalendarioPK.tipoPeriodo = :tPeriodo and "
            + " p.parCalendarioPK.gestion = :gestion")
    ParCalendario obtenerCalendarioPorGestionYPeriodo(@Param("gestion")String gestion, @Param("tPeriodo")String tPeriodo);
    
    @Query("select p "
            + " from ParCalendario p "
            + " where p.parCalendarioPK.gestion = :gestion")
    List<ParCalendario> listaCalendarioPorGestion(@Param("gestion")String gestion);
    
    @Query("select p "
            + " from ParCalendario p "
            + " order by p.parCalendarioPK.gestion desc, p.parCalendarioPK.tipoPeriodo asc")
    List<ParCalendario> listaCalendarioDesc();
    
    @Query("select DISTINCT p.parCalendarioPK.gestion"
            + " from ParCalendario p "
            + " order by p.parCalendarioPK.gestion asc")
    List<String> listarGestiones();
}