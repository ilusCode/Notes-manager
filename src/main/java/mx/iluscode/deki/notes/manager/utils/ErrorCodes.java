package mx.iluscode.deki.notes.manager.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 10/12/21 - 23:19
 *
 ***************************************/
@AllArgsConstructor
@Getter
public enum ErrorCodes {

    /**
     * DEKI_ERROR_001
     */
    DEKI_ERROR_001("DEKI_ERROR_001", "No se pudo generar el comprobante", NOT_ACCEPTABLE),
    /**
     * DEKI_ERROR_002
     */
    DEKI_ERROR_002("DEKI_ERROR_002", "El número de notas por hoja es invalido", BAD_REQUEST);

    /**
     * Representa el código que identifica la respuesta
     */
    private final String code;

    /**
     * Representa el mensaje de la respuesta
     */
    private final String message;

    /**
     * Representa el código de respuesta {@link HttpStatus}
     */
    private final HttpStatus httpStatus;
}
