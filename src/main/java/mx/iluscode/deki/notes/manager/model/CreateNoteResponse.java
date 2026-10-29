package mx.iluscode.deki.notes.manager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/***************************************
 *  @description
 *
 *  @project deki
 *  @author Team GFT Dev BaaS
 *  @created 10/12/21 - 16:50
 *
 ***************************************/
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateNoteResponse implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6203561167556140087L;

    /**
     * representa el documento en base64
     */
    @JsonProperty("doc")
    private String doc;

    /**
     * Representa las notificaciones de respuesta
     */
    @JsonProperty("notifications")
    private NotificationWrapper notificationWrapper;
}
