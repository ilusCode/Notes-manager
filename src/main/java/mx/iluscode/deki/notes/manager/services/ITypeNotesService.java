package mx.iluscode.deki.notes.manager.services;

import mx.iluscode.deki.notes.manager.model.DataNumbersDTO;

import java.util.List;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 11/12/21 - 10:49
 *
 ***************************************/
public interface ITypeNotesService {

    /**
     * Este metodo se encarga de administrar las ejecuciones conforme al numero de notas por hoja
     *
     * @param notesForSheet representa el número de notas por hoja
     * @param totalSheets   representa el número total de hojas
     * @param position      representa la posición de la ejecución
     * @return una {@link List} de objetos {@link DataNumbersDTO}
     */
    List<DataNumbersDTO> generateList(int notesForSheet, int totalSheets, int position);
}
