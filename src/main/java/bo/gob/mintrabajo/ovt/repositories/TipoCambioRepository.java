package bo.gob.mintrabajo.ovt.repositories;

import bo.gob.mintrabajo.ovt.entities.ParTipoCambio;
import java.util.Date;
import java.util.List;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaRepository;
import name.marcelomorales.siqisiqi.openjpa.spring.OpenJpaSettings;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@OpenJpaSettings
public interface TipoCambioRepository extends OpenJpaRepository<ParTipoCambio, Long> {
    @Query("select p "
            + " from ParTipoCambio p ")
            //+ " order by p.parTipoCambio.parTipoCambioPK.idFecha desc")
    List<ParTipoCambio> listaPorOrdenFecha();
    
    @Query("select p "
            + " from ParTipoCambio p "
            + " where p.parTipoCambioPK.tipoMonedaBase = :monedaBase and "
            + " p.parTipoCambioPK.tipoMonedaCambio = :monedaCambio")
    List<ParTipoCambio> listaTiposDeCambio(@Param("monedaBase")String monedaBase, @Param("monedaCambio")String monedaCambio);
    
    @Query("select p "
            + " from ParTipoCambio p "
            + " where p.parTipoCambioPK.tipoMonedaBase = :monedaBase and "
            + " p.parTipoCambioPK.tipoMonedaCambio = :monedaCambio and"
            + " p.parTipoCambioPK.idFecha =:fecha")
    ParTipoCambio tipoDeCambioPorPk(@Param("fecha")Date fecha, @Param("monedaBase")String monedaBase, @Param("monedaCambio")String monedaCambio);
}
