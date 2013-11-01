package bo.gob.mintrabajo.ovt.services;

import bo.gob.mintrabajo.ovt.Util.UtilityData;
import bo.gob.mintrabajo.ovt.api.IDocumentoService;
import bo.gob.mintrabajo.ovt.api.IUtilsService;
import bo.gob.mintrabajo.ovt.entities.*;
import bo.gob.mintrabajo.ovt.repositories.*;
import com.csvreader.CsvReader;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Renato Velasquez
 * Date: 03-10-13
 */
@Named("documentoService")
@TransactionAttribute
public class DocumentoService implements IDocumentoService{

    private final DocumentoRepository documentoRepository;
    private final DocumentoEstadoRepository documentoEstadoRepository;
    private final PlanillaRepository planillaRepository;
    private final BinarioRepository binarioRepository;
    private final UnidadRepository unidadRepository;
    private final NumeracionRepository numeracionRepository;
    private final LogEstadoRepository logEstadoRepository;
    private final PlanillaDetalleRepository planillaDetalleRepository;
    private final IUtilsService utils;

    @Inject
    public DocumentoService(DocumentoRepository documentoRepository, DocumentoEstadoRepository documentoEstadoRepository, PlanillaRepository planillaRepository, BinarioRepository binarioRepository, UnidadRepository unidadRepository, NumeracionRepository numeracionRepository, LogEstadoRepository logEstadoRepository, PlanillaDetalleRepository planillaDetalleRepository, IUtilsService utils) {
        this.documentoRepository = documentoRepository;
        this.documentoEstadoRepository = documentoEstadoRepository;
        this.planillaRepository = planillaRepository;
        this.binarioRepository = binarioRepository;
        this.unidadRepository = unidadRepository;
        this.numeracionRepository = numeracionRepository;
        this.logEstadoRepository = logEstadoRepository;
        this.planillaDetalleRepository = planillaDetalleRepository;
        this.utils = utils;
    }

    //    @Override
    public List<DocDocumento> getAllDocumentos() {
        List<DocDocumento> lista;
        lista = documentoRepository.findAll();
        return lista;
    }

    //    @Override
    public DocDocumento save(DocDocumento documento) {
        DocDocumento entity;
        entity = documentoRepository.save(documento);
        return entity;
    }

    //    @Override
    public boolean delete(DocDocumento documento) {
        boolean deleted = false;
        documentoRepository.delete(documento);
        return deleted;
    }

    //    @Override
    public DocDocumento findById(Long id) {
        DocDocumento entity;
        entity = documentoRepository.findOne(id);
        return entity;
    }

    @Override
    public List<DocDocumento> listarPorPersona(String idPersona) {
        List<DocDocumento> lista;
        lista = documentoRepository.buscarPorPersona(idPersona);
        return lista;
    }

    public List<DocDocumento> listarPorNumero(String idPersona){
        return documentoRepository.findByAttribute("idPersona", idPersona, -1,-1);
    }

    public void guardaDocumentoBinarioPlanilla(DocDocumento docDocumento, List<DocBinario> listaBinarios, DocPlanilla docPlanilla){
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
        //docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", 1)); *************************************** REVISAR !!!!!
        docDocumento.setNumeroDocumento(new BigInteger("11111")); //****************************************************
        docDocumento=documentoRepository.save(docDocumento);
        int idBinario= 1;
        for(DocBinario elementoBinario:listaBinarios){
            DocBinarioPK docBinarioPK = new DocBinarioPK();
            docBinarioPK.setIdDocumento(docDocumento.getIdDocumento());
            docBinarioPK.setIdBinario(idBinario++);
            binarioRepository.save(elementoBinario);
        }
        docPlanilla.setIdDocumento(docDocumento);
        docPlanilla.setIdPlanilla(new Long(utils.planillaSecuencia("DOC_PLANILLA_SEC")));
        planillaRepository.save(docPlanilla);
    }


//    public DocDocumento guardarCambioEstado(DocDocumento documento, ParDocumentoEstado codEstadoFinal,String idUsuario) {
//        //
//        DocLogEstado logEstado=new DocLogEstado();
//        logEstado.setIdDocumento(documento);
//        logEstado.setCodEstadoFinal(codEstadoFinal);
//        logEstado.setCodEstadoInicial(documento.getCodEstado());
//        logEstado.setRegistroBitacora(idUsuario);
//        Date date=new Date();
//        logEstado.setFechaBitacora(new Timestamp(date.getTime()));
//        logEstado.setIdLogestado(utils.valorSecuencia("DOC_LOG_ESTADO_SEC"));
//        logEstadoRepository.save(logEstado);
//        //
//        documento.setCodEstado(codEstadoFinal);
//        return documentoRepository.save(documento);
//    }

//    public DocDocumento guardarCambioEstado(DocDocumento documento, DocLogEstado logEstado) {
//        logEstado.setIdLogestado(utils.valorSecuencia("DOC_LOG_ESTADO_SEC"));
//        logEstadoRepository.save(logEstado);
//        return documentoRepository.save(documento);
//    }
    
     @Override
    public DocDocumento guardarCambioEstado(DocDocumento documento, ParDocumentoEstado codEstadoFinal,String idUsuario) {
//        DocLogEstado logEstado=new DocLogEstado();
//        logEstado.setIdDocumento(documento);
//        logEstado.setCodEstadoFinal(codEstadoFinal);
//        logEstado.setCodEstadoInicial(documento.getCodEstado());
//        logEstado.setRegistroBitacora(idUsuario);
//        Date date=new Date();
//        logEstado.setFechaBitacora(new Timestamp(date.getTime()));
//        logEstado.setIdLogestado(utils.valorSecuencia("DOC_LOG_ESTADO_SEC"));
//        logEstadoRepository.save(logEstado);
        //
        documento.setCodEstado(codEstadoFinal);
        return repository.save(documento);
    }

    public void guardaDocumentoPlanillaBinario(DocDocumento docDocumento, DocPlanilla docPlanilla, List<DocBinario> listaBinarios){
        //guarda documento
        docDocumento.setIdDocumento(utils.valorSecuencia("DOC_DOCUMENTO_SEC"));
//        docDocumento.setNumeroDocumento(actualizarNumeroDeOrden("LC1010", 1));
        docDocumento=documentoRepository.save(docDocumento);

        //guarda planilla
        docPlanilla.setIdDocumento(docDocumento);
        docPlanilla.setIdPlanilla(utils.valorSecuencia("DOC_PLANILLA_SEC"));
        docPlanilla =planillaRepository.save(docPlanilla);

        //validaBinarios

        //guarda binarios
        int idBinario= 1;
        for(DocBinario elementoBinario:listaBinarios){
            elementoBinario.setDocBinarioPK(new DocBinarioPK(idBinario++, docDocumento.getIdDocumento()));
            binarioRepository.save(elementoBinario);
        }
        valida(docPlanilla, listaBinarios);
    }

    public void valida(DocPlanilla docPlanilla, List<DocBinario> listaBinarios){
        List<String> errores = new ArrayList<String>();
        String errorFilas = "";
        String errorCampos = "";
        String mensaje;
        List <DocPlanillaDetalle>docPlanillaDetalleList = new ArrayList<DocPlanillaDetalle>();
        try {
//            FileInputStream file = new FileInputStream(new File("/home/rvelasquez/Desktop/planillaComercialPrueba.xls");
//        csvReader = new CsvReader(new InputStreamReader(file.getInputstream(), "UTF-8"), ';');
//            CsvReader csvReader= new CsvReader("/home/rvelasquez/Desktop/planillaComercialPrueba1.csv");
            for(DocBinario docBinario:listaBinarios){

                CsvReader csvReader = new CsvReader(new InputStreamReader(new ByteArrayInputStream(docBinario.getBinario())));
                int fila = 0;
                csvReader.readRecord();
                while (csvReader.readRecord()) {
                    fila++;

                    int columnCount = csvReader.getColumnCount();
                    if (columnCount != 33) {
                        errorFilas = "La fila " + fila + ", tiene " + columnCount + " columnas, esto no es aceptado como un documento válido: o el SEPARADOR = " + csvReader.getDelimiter() + " No es el adecuado ";
                        errorFilas = errorFilas + fila + ", ";
                        continue;
                    }

                    DocPlanillaDetalle docPlanillaDetalle = new DocPlanillaDetalle();

                    docPlanillaDetalle.setIdPlanilla(docPlanilla);
                    docPlanillaDetalle.setIdPlanillaDetalle(utils.valorSecuencia("DOC_PLANILLA_DETALLE_SEC"));


                    docPlanillaDetalle.setTipoDocumento(csvReader.get(1));

                    if (!UtilityData.isInteger(csvReader.get(2)))
                        errorCampos = errorCampos + "Número de documento de identidad no es un número válido, ";
                    else
                        docPlanillaDetalle.setNumeroDocumento(csvReader.get(2));

                    docPlanillaDetalle.setExtencionDocumento(csvReader.get(3));
                    docPlanillaDetalle.setAfp(csvReader.get(4));
                    docPlanillaDetalle.setNuaCua(csvReader.get(5));
                    docPlanillaDetalle.setNombre(csvReader.get(6)+" "+csvReader.get(7)+" "+csvReader.get(8)+" "+csvReader.get(9)+" "+csvReader.get(10));
                    docPlanillaDetalle.setNacionalidad(csvReader.get(11));
                    docPlanillaDetalle.setFechaNacimiento(csvReader.get(12));
                    docPlanillaDetalle.setSexo(csvReader.get(13));
                    docPlanillaDetalle.setJubilado(csvReader.get(14));
                    docPlanillaDetalle.setClasificacionLaboral(csvReader.get(15));
                    docPlanillaDetalle.setCargo(csvReader.get(16));
                    docPlanillaDetalle.setFechaIngreso(csvReader.get(17));

                    if (!UtilityData.isInteger(csvReader.get(18))&&!csvReader.get(18).equals(""))
                        errorCampos = errorCampos + "Horas pagadas (día) debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setHorasPagadasDia(csvReader.get(18));

                    if (!UtilityData.isInteger(csvReader.get(19)))
                        errorCampos = errorCampos + "Días haber básico debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setDiasHaberBasico(csvReader.get(19));

                    if (!UtilityData.isInteger(csvReader.get(20)))
                        errorCampos = errorCampos + "Días pagados (mes) debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setDiasPagados(csvReader.get(20));

                    if (!UtilityData.isInteger(csvReader.get(21))&&!csvReader.get(21).equals(""))
                        errorCampos = errorCampos + "Nº de dominicales debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setDominicalMes(csvReader.get(21));

                    if (!UtilityData.isDecimal(csvReader.get(22))&&!csvReader.get(22).equals(""))
                        errorCampos = errorCampos + "Domingos trabajados debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setDominicalTrabajado(csvReader.get(22));

                    if (!UtilityData.isInteger(csvReader.get(23))&&!csvReader.get(23).equals(""))
                        errorCampos = errorCampos + "Horas extra debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setHorasExtra(csvReader.get(23));

                    if (!UtilityData.isInteger(csvReader.get(24))&&!csvReader.get(24).equals(""))
                        errorCampos = errorCampos + "Horas de recargo nocturno debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setHorasNocturno(csvReader.get(24));

                    if (!UtilityData.isInteger(csvReader.get(25))&&!csvReader.get(25).equals(""))
                        errorCampos = errorCampos + "Horas extra dominicales debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setHorasDominicales(csvReader.get(25));

                    if (!UtilityData.isDecimal(csvReader.get(26))&&!csvReader.get(26).equals(""))
                        errorCampos = errorCampos + "Haber básico debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setHaberBasico(csvReader.get(26));

                    if (!UtilityData.isDecimal(csvReader.get(27))&&!csvReader.get(27).equals(""))
                        errorCampos = errorCampos + "Salario dominical debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setHaberBasico(csvReader.get(27));

                    if (!UtilityData.isDecimal(csvReader.get(28))&&!csvReader.get(28).equals(""))
                        errorCampos = errorCampos + "Monto pagado por domingo trabajado debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setHaberBasico(csvReader.get(28));

                    if (!UtilityData.isDecimal(csvReader.get(29)))
                        errorCampos = errorCampos + "Monto pagado por horas extra debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setMontoHorasExtra(csvReader.get(29));

                    if (!UtilityData.isDecimal(csvReader.get(30))&&!csvReader.get(30).equals(""))
                        errorCampos = errorCampos + "Monto pagado por horas nocturnas debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setHaberBasico(csvReader.get(30));

                    if (!UtilityData.isDecimal(csvReader.get(31))&&!csvReader.get(31).equals(""))
                        errorCampos = errorCampos + "Monto pagado por horas extra dominicales debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setHaberBasico(csvReader.get(31));

                    if (!UtilityData.isDecimal(csvReader.get(32)))
                        errorCampos = errorCampos + "Bono de antigüedad debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setBonoAntiguedad(csvReader.get(32));

                    if (!UtilityData.isDecimal(csvReader.get(33))&&!csvReader.get(33).equals(""))
                        errorCampos = errorCampos + "Bono de producción debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setBonoProduccion(csvReader.get(33));

                    if (!UtilityData.isDecimal(csvReader.get(34))&&!csvReader.get(34).equals(""))
                        errorCampos = errorCampos + "Subsidio de frontera debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setBonoFrontera(csvReader.get(34));

                    if (!UtilityData.isDecimal(csvReader.get(35)))
                        errorCampos = errorCampos + "Otros bonos o pagos debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setBonoOtros(csvReader.get(35));

                    if (!UtilityData.isDecimal(csvReader.get(36)))
                        errorCampos = errorCampos + "Total ganado debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setTotalGanado(csvReader.get(36));

                    if (!UtilityData.isDecimal(csvReader.get(37)))
                        errorCampos = errorCampos + "Aporte a las AFPs debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setDescuentoAfp(csvReader.get(37));

                    if (!UtilityData.isDecimal(csvReader.get(38)))
                        errorCampos = errorCampos + "RC-IVA debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setDescuentoRciva(csvReader.get(38));

                    if (!UtilityData.isDecimal(csvReader.get(39))&&!csvReader.get(39).equals(""))
                        errorCampos = errorCampos + "Otros descuentos debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setDescuentoOtro(csvReader.get(39));

                    if (!UtilityData.isDecimal(csvReader.get(40)))
                        errorCampos = errorCampos + "Total descuentos debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setDescuentosTotal(csvReader.get(40));

                    if (!UtilityData.isDecimal(csvReader.get(41)))
                        errorCampos = errorCampos + "Líquido pagable debe ser un número válido, ";
                    else
                        docPlanillaDetalle.setLiquidoPagable(csvReader.get(41));

                    docPlanillaDetalleList.add(docPlanillaDetalle);

                    if (errorCampos.length() > 0) {
                        mensaje = "en la fila " + (fila) + ": " + errorCampos;
                        errores.add(mensaje);
                        errorCampos = "";
                    }
                }
            }
            if (!errorFilas.equals("")) {
                mensaje = "Las siguientes filas: " + errorFilas + " no tienen la cantidad de campos requerido";
                errores.add(0, mensaje);
            }else{
                if(errores.size()>0){
                    for(String error:errores)
                        System.out.println(error);
                }
                else{
                    for(DocPlanillaDetalle docPlanillaDetalle:docPlanillaDetalleList)
                        planillaDetalleRepository.save(docPlanillaDetalle);
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}