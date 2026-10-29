package mx.iluscode.deki.notes.manager.services;

import mx.iluscode.deki.notes.manager.model.CreateNoteResponse;
import reactor.core.publisher.Mono;

import java.io.File;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 09/12/21 - 23:59
 *
 ***************************************/
public interface INotesService {

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
    Mono<CreateNoteResponse> createNotes(File imageFile, File templateFile, int notesForSheet, int totalNotes, int noteInit);
}
