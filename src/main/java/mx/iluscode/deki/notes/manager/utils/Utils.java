package mx.iluscode.deki.notes.manager.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Formatter;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 11/12/21 - 10:24
 *
 ***************************************/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    /**
     * Este método se encarga de agregar zeros a la izquierda
     *
     * @param value
     * @return
     */
    public static String addZero(int value, String format) {
        try (Formatter formatter = new Formatter()) {
            formatter.format(format, value);
            return formatter.toString();
        }
    }

}
