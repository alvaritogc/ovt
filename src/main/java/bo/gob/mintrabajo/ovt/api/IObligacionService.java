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

package bo.gob.mintrabajo.ovt.api;

import bo.gob.mintrabajo.ovt.entities.ParObligacion;
import java.util.List;

/**
 *
 * @author lvaldez
 */
public interface IObligacionService {
    public List<ParObligacion> listaObligacion();
    public List<ParObligacion> listaObligacionPorOrden();
    public ParObligacion obligacionPorCod(String codObligacion);
    //public ParObligacion saveObligacion(ParObligacion obligacion);
    public boolean saveObligacion(ParObligacion obligacion, String REGISTRO_BITACORA, boolean estadoObligacion,boolean evento);
    public boolean deleteObligacion(ParObligacion obligacion);
}
