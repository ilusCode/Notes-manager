package mx.iluscode.deki.notes.manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.iluscode.deki.notes.manager.model.CreateNoteResponse;
import mx.iluscode.deki.notes.manager.model.DataNumbersDTO;
import mx.iluscode.deki.notes.manager.model.NotificationWrapper;
import mx.iluscode.deki.notes.manager.transform.INotificationWrapperTransform;
import mx.iluscode.deki.notes.manager.utils.ErrorCodes;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static mx.iluscode.deki.notes.manager.utils.DekiConstants.PARAMETER_IMAGE;
import static org.springframework.http.HttpStatus.OK;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 09/12/21 - 23:59
 *
 ***************************************/
@Service
@Slf4j
@RequiredArgsConstructor
public class NotesService implements INotesService {

    /**
     * Se instancia la interfaz {@link ITypeNotesService}
     */
    private final ITypeNotesService typeNotesService;

    /**
     * Se instancia la interfaz {@link INotificationWrapperTransform}
     */
    private final INotificationWrapperTransform wrapperTransform;

    /**
     * Este método se encarga de crear el archivo en base64 del lote de notas
     *
     * @param imageFile     representa la imagen que se tomara como el fondo
     * @param templateFile  representa la plantilla de JasperReport
     * @param notesForSheet representa el numero de notas por hoja
     * @param totalNotes    representa el total de notas
     * @param noteInit      representa el numero de nota inicial
     * @return regresa un objeto {@link Mono} encapsulando un objeto {@link CreateNoteResponse}
     */
    @Override
    public Mono<CreateNoteResponse> createNotes(File imageFile, File templateFile, int notesForSheet, int totalNotes, int noteInit) {
        CreateNoteResponse response = new CreateNoteResponse();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(PARAMETER_IMAGE, imageFile.getAbsolutePath());
        int totalSheets = (totalNotes) / notesForSheet;
        List<JasperPrint> jasperPrints = new ArrayList<>();
        FileInputStream resourceAsStream = null;
        int total = totalSheets + noteInit - 1;
        int initial = noteInit - 1;
        for (int i = initial; i < total; i++) {
            List<DataNumbersDTO> list = typeNotesService.generateList(notesForSheet, totalSheets, i);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
            try {
                resourceAsStream = new FileInputStream(templateFile);
                JasperReport jasperReport = JasperCompileManager.compileReport(resourceAsStream);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
                jasperPrints.add(jasperPrint);
            } catch (JRException | FileNotFoundException e) {
                log.error("ERROR -> ", e);
            }
        }

        for (int i = 1; i < jasperPrints.size(); i++) {
            for (JRPrintPage p : jasperPrints.get(i).getPages()) {
                jasperPrints.get(0).addPage(p);
            }
        }

        /**
         * Se valida que la variable resourceAsStream no sea nula y que la {@link List} de {@link JasperPrint} no este vaciá
         */
        if (nonNull(resourceAsStream) && !jasperPrints.isEmpty()) {
            return getString(resourceAsStream, jasperPrints.get(0)).map(body -> {
                response.setDoc(body);
                NotificationWrapper nw = wrapperTransform.generateNotificationWrapper(String.valueOf(OK.value()), "Se genero el documento de forma correcta", OK);
                response.setNotificationWrapper(nw);
                return response;
            });
        }
        NotificationWrapper nw = wrapperTransform.generateNotificationWrapper(ErrorCodes.DEKI_ERROR_001.getCode(), ErrorCodes.DEKI_ERROR_001.getMessage(), ErrorCodes.DEKI_ERROR_001.getHttpStatus());
        response.setNotificationWrapper(nw);
        return Mono.just(response);
    }

    /**
     * getString El método se encarga de convertir el pdf de tipo Stream a un objeto String
     *
     * @param stream      the stream variable contains the class type of the report
     * @param jasperPrint Objeto que contiene el pdf
     * @return reporte codificado en base64
     */
    private Mono<String> getString(InputStream stream, JasperPrint jasperPrint) {
        /**
         * Se crea el arreglo de bytes del archivo a generar
         */
        byte[] pdf;

        String encode = "";
        try {
            pdf = JasperExportManager.exportReportToPdf(jasperPrint);
            /**
             * El arreglo de bytes es convertido a una cadena de texto planos
             */
            encode = Base64.getEncoder().encodeToString(pdf);
            log.info("SE CREO EL ARCHIVO CORRECTAMENTE");
            log.debug("******** End voucher generation ********");
        } catch (JRException e) {
            /**
             * Se genera un error al no poder obtener la información  que requiere el comprobante
             */
            log.error("ERROR ->", e);
        } finally {

            /**
             * Se cierra el stream del archivo creado
             */
            try {
                stream.close();
            } catch (IOException e) {
                /**
                 * Se genera una excepción cuando no es posible cerrar el {@link InputStream}
                 */
                log.error("ERROR ->", e);
            }
        }
        return Mono.just(encode);
    }
}
