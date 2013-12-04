package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.api.ILogImpresionService;
import bo.gob.mintrabajo.ovt.entities.DocLogImpresion;
import bo.gob.mintrabajo.ovt.entities.VdocLogImpresion;
import bo.gob.mintrabajo.ovt.repositories.LogImpresionRepository;
import com.google.common.base.Strings;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Named("logImpresionService")
@TransactionAttribute
public class LogImpresionService implements ILogImpresionService {
    private final LogImpresionRepository logImpresionRepository;

    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager entityManager;

    @Inject
    public LogImpresionService(LogImpresionRepository logImpresionRepository) {
        this.logImpresionRepository = logImpresionRepository;
    }

    public DocLogImpresion guarda(DocLogImpresion docLogImpresion){
        return logImpresionRepository.save(docLogImpresion);
    }

    public List<VdocLogImpresion> filtrarLogImpresion(String nroIdentificacion, String codDocumento, Date fechaInicio, Date fechaFinal, String tipoImpresion){
        int contador = 0;
        StringBuilder queryArmado = new StringBuilder();
        queryArmado.append("select dli.* from VDOC_LOG_IMPRESION dli ");
        if(!Strings.isNullOrEmpty(nroIdentificacion)){
            if(contador > 0){
                queryArmado.append("AND ");
            }else{
                queryArmado.append("WHERE ");
                contador++;
            }
            queryArmado.append("NRO_IDENTIFICACION like '" + nroIdentificacion + "%' ");
        }

        if(!Strings.isNullOrEmpty(codDocumento)){
            if(contador > 0){
                queryArmado.append("AND ");
            }else{
                queryArmado.append("WHERE ");
                contador++;
            }
            queryArmado.append("COD_DOCUMENTO like '" + codDocumento + "%' ");
        }

        if(fechaInicio != null){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            Date fechaInicioTmp = new Date(fechaInicio.getTime());
            String fechaInicioTexto = sdf.format(fechaInicioTmp);

            if(contador > 0){
                queryArmado.append("AND ");
            }else{
                queryArmado.append("WHERE ");
                contador++;
            }
            queryArmado.append("trunc(FECHA_BITACORA) >= '" + fechaInicioTexto + "' ");
        }

        if(fechaFinal != null){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            Date fechaFinalTmp = new Date(fechaFinal.getTime());
            String fechaFinalTexto = sdf.format(fechaFinalTmp);

            if(contador > 0){
                queryArmado.append("AND ");
            }else{
                queryArmado.append("WHERE ");
                contador++;
            }
            queryArmado.append("trunc(FECHA_BITACORA) <= '" + fechaFinalTexto + "' ");
        }

        if(contador > 0){
            queryArmado.append("AND ");
        }else{
            queryArmado.append("WHERE ");
            contador++;
        }
        queryArmado.append("TIPO_IMPRESION like '" + tipoImpresion + "%' ");

        System.out.println(queryArmado);
        Query query = entityManager.createNativeQuery(queryArmado.toString());
        List<Object[]> objeto = (List<Object[]>) query.getResultList();
        List<VdocLogImpresion> tmpLista = new ArrayList<VdocLogImpresion>();
        VdocLogImpresion impresion;

        for (Object[] obj : objeto) {
            impresion = new VdocLogImpresion();
            if (obj != null) {
                try {
                    impresion.setTipoIdentificacion(obj[0].toString());
                    impresion.setNroIdentificacion(obj[1].toString());
                    impresion.setNombreRazonSocial(obj[2].toString());
                    if (obj[3] != null)
                        impresion.setApellidoPaterno(obj[3].toString());
                    if (obj[4] != null)
                        impresion.setApellidoMaterno(obj[4].toString());
                    impresion.setEsNatural(new Short(obj[5].toString()));
                    impresion.setCodLocalidad(obj[6].toString());
                    impresion.setIdUnidad(new Long(obj[7].toString()));
                    impresion.setNombreComercial(obj[8].toString());
                    SimpleDateFormat formatoN = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    impresion.setFechaNacimiento(formatoN.parse(obj[9].toString()));
                    if (obj[10] != null)
                        impresion.setNroCajaSalud(obj[10].toString());
                    if (obj[11] != null)
                        impresion.setNroAfp(obj[11].toString());
                    if (obj[12].toString() != null)
                        impresion.setNroFundaempresa(obj[12].toString());
                    if (obj[13].toString() != null)
                        impresion.setNroOtro(obj[13].toString());
                    impresion.setObservaciones(obj[14].toString());
                    impresion.setTipoSociedad(obj[15].toString());
                    impresion.setTipoEmpresa(obj[16].toString());
                    impresion.setActividadDeclarada(obj[17].toString());
                    impresion.setEstadoUnidad(obj[18].toString());
                    if (obj[19]!= null)
                        impresion.setNroReferencial(obj[19].toString());
                    if (obj[20] != null)
                    impresion.setTipoUnidad(obj[20].toString());
                    impresion.setIdDocumento(new Long(obj[21].toString()));
                    impresion.setCodDocumento(obj[22].toString());
                    impresion.setVersion(new Short(obj[23].toString()));
                    impresion.setNumeroDocumento(new Long(obj[24].toString()));
                    SimpleDateFormat formatoD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    impresion.setFechaDocumento(formatoD.parse(obj[25].toString()));
                    if (obj[26] != null)
                    impresion.setIdDocumentoRef(new Long(obj[26].toString()));
                    impresion.setCodEstado(obj[27].toString());
                    SimpleDateFormat formatoR = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    if (obj[28] != null)
                    impresion.setFechaReferenca(formatoR.parse(obj[28].toString()));
                    impresion.setTipoMedioRegistro(obj[29].toString());
                    impresion.setTipoImpresion(obj[30].toString());
                    SimpleDateFormat formatoB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    impresion.setFechaBitacora(formatoB.parse(obj[31].toString()));
                    impresion.setRegistroBitacora(obj[32].toString());
                    impresion.setIdDoclogimpresion(new Long(obj[33].toString()));
                    tmpLista.add(impresion);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    break;
                }
            }
        }

        return tmpLista;
    }
}
