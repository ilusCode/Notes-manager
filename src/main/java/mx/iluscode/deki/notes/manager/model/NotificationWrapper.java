package mx.iluscode.deki.notes.manager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 10/12/21 - 23:36
 *
 ***************************************/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationWrapper implements Serializable {
    private static final long serialVersionUID = 8862568472711995242L;

    /**
     * Representa la fecha de ejecución
     */
    private LocalDateTime time;

    /**
     * Representa el mensaje de respuesta
     */
    private String mesagge;

    /**
     * Representa el {@link HttpStatus} de respuesta
     */
    private HttpStatus httpStatus;

    /**
     * Representa el código de respuesta
     */
    private String code;
}
