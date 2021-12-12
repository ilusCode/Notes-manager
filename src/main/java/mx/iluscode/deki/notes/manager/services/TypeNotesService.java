package mx.iluscode.deki.notes.manager.services;

import mx.iluscode.deki.notes.manager.model.DataNumbersDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static mx.iluscode.deki.notes.manager.utils.DekiConstants.FORMAT_FOUR_ZEROS;
import static mx.iluscode.deki.notes.manager.utils.Utils.addZero;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 11/12/21 - 10:50
 *
 ***************************************/
@Service
public class TypeNotesService implements ITypeNotesService {

    /**
     * Este metodo se encarga de administrar las ejecuciones conforme al numero de notas por hoja
     *
     * @param notesForSheet representa el numero de notas por hoja
     * @param totalSheets   representa el numero total de hojas
     * @param position      representa la posición de la ejecución
     * @return una {@link List} de objetos {@link DataNumbersDTO}
     */
    @Override
    public List<DataNumbersDTO> generateList(int notesForSheet, int totalSheets, int position) {
        List<DataNumbersDTO> list;
        switch (notesForSheet) {
            case 1:
                list = notesOne(position);
                break;
            case 2:
                list = notesTwo(position, totalSheets);
                break;
            case 3:
                list = notesThree(position, totalSheets);
                break;
            case 4:
                list = notesFour(position, totalSheets);
                break;
            case 5:
                list = notesFive(position, totalSheets);
                break;
            case 6:
                list = notesSix(position, totalSheets);
                break;
            default:
                return new ArrayList<>();
        }
        return list;
    }

    /**
     * Este metodó se encarga de generar una lista de una nota por hoja
     *
     * @param position representa la posición de la ejecución
     * @return una {@link List} de objetos {@link DataNumbersDTO}
     */
    private List<DataNumbersDTO> notesOne(int position) {
        return singletonList(new DataNumbersDTO(addZero(position + 1, FORMAT_FOUR_ZEROS)));
    }

    /**
     * Este metodó se encarga de generar una lista de dos notas por hoja
     *
     * @param position representa la posición de la ejecución
     * @return una {@link List} de objetos {@link DataNumbersDTO}
     */
    private List<DataNumbersDTO> notesTwo(int position, int totalSheets) {
        return singletonList(new DataNumbersDTO(addZero(position + 1, FORMAT_FOUR_ZEROS), addZero(totalSheets + 1 + position, FORMAT_FOUR_ZEROS)));
    }

    /**
     * Este metodó se encarga de generar una lista de tres notas por hoja
     *
     * @param position representa la posición de la ejecución
     * @return una {@link List} de objetos {@link DataNumbersDTO}
     */
    private List<DataNumbersDTO> notesThree(int position, int totalSheets) {
        return singletonList(new DataNumbersDTO(addZero(position + 1, FORMAT_FOUR_ZEROS), addZero(totalSheets + 1 + position, FORMAT_FOUR_ZEROS), addZero((totalSheets * 2) + 1 + position, FORMAT_FOUR_ZEROS)));
    }

    /**
     * Este metodó se encarga de generar una lista de cuatro notas por hoja
     *
     * @param position representa la posición de la ejecución
     * @return una {@link List} de objetos {@link DataNumbersDTO}
     */
    private List<DataNumbersDTO> notesFour(int position, int totalSheets) {
        return singletonList(new DataNumbersDTO(addZero(position + 1, FORMAT_FOUR_ZEROS), addZero(totalSheets + 1 + position, FORMAT_FOUR_ZEROS), addZero((totalSheets * 2) + 1 + position, FORMAT_FOUR_ZEROS), addZero((totalSheets * 3) + 1 + position, FORMAT_FOUR_ZEROS)));
    }

    /**
     * Este metodó se encarga de generar una lista de cinto notas por hoja
     *
     * @param position representa la posición de la ejecución
     * @return una {@link List} de objetos {@link DataNumbersDTO}
     */
    private List<DataNumbersDTO> notesFive(int position, int totalSheets) {
        return singletonList(new DataNumbersDTO(addZero(position + 1, FORMAT_FOUR_ZEROS), addZero(totalSheets + 1 + position, FORMAT_FOUR_ZEROS), addZero((totalSheets * 2) + 1 + position, FORMAT_FOUR_ZEROS), addZero((totalSheets * 3) + 1 + position, FORMAT_FOUR_ZEROS), addZero((totalSheets * 4) + 1 + position, FORMAT_FOUR_ZEROS)));
    }

    /**
     * Este metodó se encarga de generar una lista de seis notas por hoja
     *
     * @param position representa la posición de la ejecución
     * @return una {@link List} de objetos {@link DataNumbersDTO}
     */
    private List<DataNumbersDTO> notesSix(int position, int totalSheets) {
        return singletonList(new DataNumbersDTO(addZero(position + 1, FORMAT_FOUR_ZEROS), addZero(totalSheets + 1 + position, FORMAT_FOUR_ZEROS), addZero((totalSheets * 2) + 1 + position, FORMAT_FOUR_ZEROS), addZero((totalSheets * 3) + 1 + position, FORMAT_FOUR_ZEROS), addZero((totalSheets * 4) + 1 + position, FORMAT_FOUR_ZEROS), addZero((totalSheets * 5) + 1 + position, FORMAT_FOUR_ZEROS)));
    }


}
